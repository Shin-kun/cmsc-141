package mp1.checkers;

import java.util.Stack;

import mp1.CheckTokens;

/**
 * an abstract class that extends its methods to its checker childrens
**/
public abstract class CheckerFunctionAndVariableParent {
	protected int currentState;
	protected int referenceState;
	protected String[] variableNamesArrayBeforeSemicolon; 
	protected String[] variableNamesArrayAfterSemicolon;
	protected Stack<String> parenthesisStack = new Stack(); 

	CheckerFunctionAndVariableParent(int length) {
		currentState = 0;
		referenceState = 0;
		variableNamesArrayBeforeSemicolon = new String[length];
		variableNamesArrayAfterSemicolon = new String[length];
		for(int i = 0; i < length; i++) {
			variableNamesArrayBeforeSemicolon[i] = "";
			variableNamesArrayAfterSemicolon[i] = "";
		}
	}
	
	CheckerFunctionAndVariableParent() {
		
	}
	
	
	abstract public boolean checkStatementIfValid(String tokenValue); 
	abstract protected int returnStateDiagram(String tokenValue);

	protected void pushOpenParenthesisInStack(String tokenValue) {
		if(tokenValue.equals(CheckTokens.OPEN_PARANTHESIS.getToken())) {
			parenthesisStack.push(tokenValue);
		} 
	}
		
	protected boolean popOpenParenthesisInStack(String tokenValue) {
		if(tokenValue.equals(CheckTokens.CLOSE_PARANTHESIS.getToken())){
			if(!parenthesisStack.isEmpty()) { 
				parenthesisStack.pop();
			}
			else {
				return false;
			}
		}
		return true;
	}
	
	protected boolean checkIfOpenParenthesisStackIsEmpty(String tokenValue, boolean checkStack) {
		if(tokenValue.equals(CheckTokens.SEMICOLON.getToken())) {
			if(parenthesisStack.isEmpty()) return true;
			else { return false; } 
		}
		return checkStack;
	}
	
	
	protected void putIdentifiersIntempCheckStorage(String identifier) {
		if(currentState == 1) { //1 is identifier state
			for(int i = 0; i < variableNamesArrayBeforeSemicolon.length; i++) {
				if(variableNamesArrayBeforeSemicolon[i].equals("")) {
					variableNamesArrayBeforeSemicolon[i] = identifier;
					break;
				}
			}
		}
	}
	
	protected boolean checksIfVariableIsAlreadyDeclared(String tokenValue) {
		for(String checker : variableNamesArrayAfterSemicolon) {
			if(checker.equals(tokenValue)) {
				if(referenceState == 5 && currentState == 1) {//sees = and then expects an identifier
					return true;
				} else if(referenceState == 1 && currentState == 1){ //declaration
					return false;
				}
			}
		} 
		if(referenceState == 5 && currentState == 1 ) { return false; }
		return true;
	}
	
	protected boolean checksIfMoreThanOneVariableTempCheckStorage() {
		for(int i = 0; i < variableNamesArrayBeforeSemicolon.length; i++) {
			for(int j = i + 1; j < variableNamesArrayBeforeSemicolon.length; j++) {
				if(!variableNamesArrayBeforeSemicolon[i].equals("") && variableNamesArrayBeforeSemicolon[i].equals(variableNamesArrayBeforeSemicolon[j])) {
					return false;
				}
			}
		}
		return true;
	}
	
	protected void putTempCheckStorageToPermanentCheckStorage() {
		if(referenceState == 3) { //meaning we are now in semicolon, we put in the values of temp to permanent
			for(int i = 0; i < variableNamesArrayBeforeSemicolon.length; i++) {				
				for(int j = 0; j < variableNamesArrayAfterSemicolon.length; j++) {
					if(variableNamesArrayAfterSemicolon[j].equals("")) {
						variableNamesArrayAfterSemicolon[j] = variableNamesArrayBeforeSemicolon[i];
						break;
					}
				}
			}
		}
	}
	
	protected void resetTempCheckStorage(String token) {
		if(token.equals(CheckTokens.SEMICOLON.getToken()) || token.equals(CheckTokens.CLOSE_PARANTHESIS.getToken())) {
			for(int i = 0 ; i < variableNamesArrayBeforeSemicolon.length; i++) {
				variableNamesArrayBeforeSemicolon[i] = "";
			}
		}
	}
	
	protected String checkASCII(String token) {
		if(token.contains("'")) {
			if(token.length() != 3) {
				return token;
			} 
			token = token.substring(1, token.length()-1);
			char ascii = token.charAt(0);
			int valueASCII = (int) ascii;
			return Integer.toString(valueASCII);
		}
		return token;
	}
}


/**
 *  refract tempCheckStorage and permanentCheckStorage
 *  refract VariableAndFunctionParent
 * 
 * */
