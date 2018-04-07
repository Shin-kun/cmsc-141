package mp1.checkers;

import org.apache.commons.lang3.math.NumberUtils;

import mp1.CheckTokens;

/*
 * checker function definition
 */

public class CheckingFunctionDefinition extends CheckerFunctionAndVariableParent{
	private boolean startFuncCheck;
	private boolean startVarCheck;
	private String returnTypeDeterminer;
	private CheckingFunctionDeclaration funcChecker;
	private CheckingVariableDeclaration varChecker;
	private int[][] returnStateDiagramChecker = { {1,1,3,3,3,3},
												  {3,3,0,0,3,2},
												  {3,3,3,3,3,2},
												  {3,3,3,3,3,3}};
	
	public CheckingFunctionDefinition(int length){
		super(length);
		funcChecker = new CheckingFunctionDeclaration(length);
		varChecker = new CheckingVariableDeclaration(length);
		startFuncCheck = true;
		startVarCheck = false;
	}	

	@Override
	public boolean checkStatementIfValid(String tokenValue) {
		typeDeterminer(tokenValue);
		shiftChecker(tokenValue);
		puttingVariablePermanentStorage(tokenValue);		
		
		boolean validator = false;
		if(startFuncCheck) {
			validator = funcChecker.checkStatementIfValid(tokenValue);
		} else if(startVarCheck){
			validator = checkValueVariable(tokenValue);
		} else {
			pushOpenParenthesisInStack(tokenValue);
			boolean checkOpenParenthesisStack = popOpenParenthesisInStack(tokenValue);
			checkOpenParenthesisStack = checkIfOpenParenthesisStackIsEmpty(tokenValue, checkOpenParenthesisStack);
			if(checkOpenParenthesisStack) {
				if(tokenValue.equals("(") || tokenValue.equals(")")) {
					return true;
				}
				validator = checkValueReturn(tokenValue);	
			} 
		}
		puttingFunctionNamesStorage(tokenValue);
		validator = checkIfArgumentExists(tokenValue, validator);

		return (!validator || referenceState == 3) ? false : true;
	}
	
	private void typeDeterminer(String tokenValue) {
		if(funcChecker.referenceState == 0) {
			returnTypeDeterminer = tokenValue;
		}
	}
		
	private void shiftChecker(String tokenValue) {
		if(startFuncCheck) {
			if(tokenValue.equals(CheckTokens.OPEN_CURLY.getToken())) {
				//start variable check
				startFuncCheck = false;
				startVarCheck = true;
			} 
		} else if(startVarCheck) {
			if(tokenValue.equals("return")) {
				startVarCheck = false;
			} else if(tokenValue.equals("}")) {
				varChecker.referenceState = 3;
				varChecker.currentState = 3;
			}
		}
	}
	
	private void puttingVariablePermanentStorage(String returnToken) {
		if(returnToken.equals("return")) {
			for(int i = 0; i < varChecker.variableNamesArrayAfterSemicolon.length; i++) {
				for(int j = 0; j < variableNamesArrayAfterSemicolon.length; j++) {
					if(variableNamesArrayAfterSemicolon[j].equals("")) {
						variableNamesArrayAfterSemicolon[j] = varChecker.variableNamesArrayAfterSemicolon[i];
						break;
					}
				}
			}
		}
	}
	
	//or reference state = semicolon or close paranthesis
	private void puttingFunctionNamesStorage(String token) {
		 if(token.equals(CheckTokens.CLOSE_PARANTHESIS.getToken())){
			for(int i = 0; i < funcChecker.variableNamesArrayAfterSemicolon.length; i++) {
				variableNamesArrayAfterSemicolon[i] = funcChecker.variableNamesArrayAfterSemicolon[i];
			}
		 }
	}
	
	private boolean checkValueVariable(String tokenValue) {
		if(!tokenValue.equals("{")) {
			boolean validator = varChecker.checkStatementIfValid(tokenValue);
			validator = checkIfSameFuncAndVarName(tokenValue, validator);
			return validator;
		}
		return true;
	}
	
	private boolean checkIfArgumentExists(String tokenValue, boolean validator) {
		if(tokenValue.equals(CheckTokens.OPEN_CURLY.getToken())) {
			if(variableNamesArrayAfterSemicolon[1].equals("")) return false;
		}
		return validator;
	}
	
	private boolean checkValueReturn(String tokenValue) {
		if(!tokenValue.equals("return")) {
			tokenValue = checkASCII(tokenValue);
			currentState = returnStateDiagram(tokenValue);			
			boolean validator = checkVarPermanentStorage(tokenValue);			
			validator = checkIfVoidCanReturnNothing(tokenValue, validator);			
			referenceState = returnStateDiagramChecker[referenceState][currentState];
			return validator;
		} else {
			return true;
		}
	}
	
	private boolean checkIfVoidCanReturnNothing(String token, boolean validator) {
		if(referenceState == 0) {
			if(returnTypeDeterminer.equals("void") && token.equals(CheckTokens.SEMICOLON.getToken())) {
				referenceState = 2; //doing two things at the same time. bad practice
				return true;
			}
		}
		return validator;
	}
	
	private boolean checkIfSameFuncAndVarName(String token, boolean validator) {
		boolean variableDeclared = validator; //false
		if(varChecker.currentState == 1) {
			for(int i = 0; i < variableNamesArrayAfterSemicolon.length; i++) {
				if(variableNamesArrayAfterSemicolon[i].equals(token)) { //return false if same function name
					if(i == 0) { variableDeclared = false; }
					else {						
						variableDeclared = true; //if using the arguments
					}
				}
			}
			if(variableDeclared) {
				if(varChecker.referenceState == 6) { varChecker.referenceState = 6; }
				else { varChecker.referenceState = 2; }
			}
		}
		return variableDeclared;
	}
	
	//when returning something, variable is already declared
	private boolean checkVarPermanentStorage(String token) {
		if(currentState == 0) {
			for(int i = 0; i < variableNamesArrayAfterSemicolon.length; i++) {
				if(variableNamesArrayAfterSemicolon[i].equals(token)) {
					if(i == 0) { 
						return false; 
					}
 					else { return true; }
				}
			}
			return false;
		}
		return true;
	}

	@Override
	protected int returnStateDiagram(String tokenValue) {
		// TODO Auto-generated method stub
		if(CheckTokens.CLOSE_CURLY.getToken().equals(tokenValue) || CheckTokens.SEMICOLON.getToken().equals(tokenValue)) {
			return 5;
			
		} else if(CheckTokens.EQUALS.getToken().equals(tokenValue)) {
			return 3;
			
		} else if(CheckTokens.PLUS.getToken().equals(tokenValue) || CheckTokens.TIMES.getToken().equals(tokenValue) 
				|| CheckTokens.MINUS.getToken().equals(tokenValue) || CheckTokens.DIVIDE.getToken().equals(tokenValue)) {
			return 2;
		
		} else if(NumberUtils.isCreatable(tokenValue)) {		
			return 1;
		
		} else {
			return 0;
			
		}
	}
}
