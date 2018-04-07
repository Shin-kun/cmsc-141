package mp1.checkers;


import org.apache.commons.lang3.math.NumberUtils;
import mp1.CheckTokens;

//checker variableDeclaration
public class CheckingVariableDeclaration extends CheckerFunctionAndVariableParent{
	private static final int[][] variableDiagram = {{1,4,4,4,4,4,4,4},
												    {4,2,4,4,4,4,4,4},
												    {4,4,1,3,4,5,4,4},
												    {1,2,4,3,4,4,4,4},
												    {4,4,4,4,4,4,4,4},
												    {4,6,4,4,4,4,6,4},
												    {4,6,1,3,4,4,2,5}};  
		
	public CheckingVariableDeclaration(int length){ //initialize
		super(length);
	}
	
	//a wrapper function 
	//if it is 4 then it should return false which is invalid
	@Override
	public boolean checkStatementIfValid(String tokenValue) {
		tokenValue = checkASCII(tokenValue);
		pushOpenParenthesisInStack(tokenValue);
		boolean checkOpenParenthesisStack = popOpenParenthesisInStack(tokenValue);
		checkOpenParenthesisStack = checkIfOpenParenthesisStackIsEmpty(tokenValue, checkOpenParenthesisStack);
		
		if(checkOpenParenthesisStack) {
			if(tokenValue.equals("(") || tokenValue.equals(")")) {
				return true;
			} else {
				currentState = returnStateDiagram(tokenValue);
				putIdentifiersIntempCheckStorage(tokenValue);
				boolean variableCheckIfAlreadyDeclared = checksIfVariableIsAlreadyDeclared(tokenValue);   //please clean tis part
				boolean variableCheckIfDeclaredSameType = checksIfMoreThanOneVariableTempCheckStorage();
				
				referenceState = variableDiagram[referenceState][currentState];
				putTempCheckStorageToPermanentCheckStorage();
				resetTempCheckStorage(tokenValue);
				if(!variableCheckIfDeclaredSameType || !variableCheckIfAlreadyDeclared || referenceState == 4) {//4 is failed state
					return false;
				}
				return true;
			}
		}
		return false;
	}
	
	@Override
	protected int returnStateDiagram(String tokenValue) {
		if(tokenValue.equals(CheckTokens.CHAR.getToken()) || 
		   tokenValue.equals(CheckTokens.INT.getToken()) || 
		   tokenValue.equals(CheckTokens.DOUBLE.getToken()) ||
		   tokenValue.equals(CheckTokens.FLOAT.getToken())) { return 0; }
		
		else if(tokenValue.equals(CheckTokens.COMMA.getToken())) { return 2; } 
		else if(tokenValue.equals(CheckTokens.SEMICOLON.getToken())) { return 3; } 
		else if(tokenValue.equals(CheckTokens.EQUALS.getToken())) { return 5; } 
		else if(NumberUtils.isCreatable(tokenValue)) { return 6; }
		else if(tokenValue.equals(CheckTokens.TIMES.getToken()) || tokenValue.equals(CheckTokens.DIVIDE.getToken()) || tokenValue.equals(CheckTokens.MINUS.getToken()) || tokenValue.equals(CheckTokens.PLUS.getToken())) { return 7; }
		return 1;
	}
		
	protected boolean checksIfMoreThanOneVariableTempCheckStorage() {
		if(referenceState == 2) {
			for(int i = 0; i < variableNamesArrayBeforeSemicolon.length; i++) {
				for(int j = i + 1; j < variableNamesArrayBeforeSemicolon.length; j++) {
					if(!variableNamesArrayBeforeSemicolon[i].equals("") && variableNamesArrayBeforeSemicolon[i].equals(variableNamesArrayBeforeSemicolon[j])) {
						return false;
					}
				}
			}
		}
		return true;
	}
}
