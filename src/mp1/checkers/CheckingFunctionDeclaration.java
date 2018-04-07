
package mp1.checkers;

import mp1.CheckTokens;

//checker function declaration
public class CheckingFunctionDeclaration extends CheckerFunctionAndVariableParent{

	protected static final int[][] functionDeclarationDiagram = {{1,4,4,4,4,4,4}, 
								 							     {4,2,4,4,4,4,4},
															     {4,4,6,4,4,4,4},
															     {4,3,4,5,4,6,4},
															     {4,4,4,4,4,4,4},
															     {4,4,4,4,4,1,5},
															     {3,4,4,5,4,4,4}};
	
	public CheckingFunctionDeclaration(int length){
		super(length);
	}
	
	@Override
	public boolean checkStatementIfValid(String tokenValue) {
		
		currentState = returnStateDiagram(tokenValue);
		putIdentifiersIntempCheckStorage(tokenValue);
		boolean variableCheckIfAlreadyDeclared = checksIfVariableIsAlreadyDeclared(tokenValue);   //please clean tis part
		boolean variableCheckIfDeclaredSameType = checksIfMoreThanOneVariableTempCheckStorage();
		
		referenceState = functionDeclarationDiagram[referenceState][currentState];
		putTempCheckStorageToPermanentCheckStorage();
		resetTempCheckStorage(tokenValue);		
		
		if(!variableCheckIfAlreadyDeclared || !variableCheckIfDeclaredSameType || referenceState == 4)
			return false;
		return true;
	}
	
	protected void putTempCheckStorageToPermanentCheckStorage() {
		if(currentState == 3 || currentState == 5) { //meaning we are now in semicolon, we put in the values of temp to permanent			
			for(int i = 0; i < variableNamesArrayBeforeSemicolon.length; i++) {				
				for(int j = 0; j < variableNamesArrayBeforeSemicolon.length; j++) {
					if(variableNamesArrayAfterSemicolon[j].equals("")) {
						variableNamesArrayAfterSemicolon[j] = variableNamesArrayBeforeSemicolon[i];
						break;
					}
				}
			}
		}
	}
	
	protected boolean checksIfVariableIsAlreadyDeclared(String tokenValue) {
		if(referenceState == 1 && currentState == 1) { //declaration
			for(String checker : variableNamesArrayAfterSemicolon) {
				if(checker.equals(tokenValue)) {				
					return false;
				}
			} 
		}
		return true;
	}
	
	@Override
	protected int returnStateDiagram(String tokenValue) {
		if(tokenValue.equals(CheckTokens.CHAR.getToken()) || tokenValue.equals(CheckTokens.INT.getToken()) ||
		   tokenValue.equals(CheckTokens.DOUBLE.getToken()) || tokenValue.equals(CheckTokens.FLOAT.getToken()) || 
		   tokenValue.equals(CheckTokens.VOID.getToken())) { return 0; }
		
		else if(tokenValue.equals(CheckTokens.OPEN_PARANTHESIS.getToken())) { return 2; }
		else if(tokenValue.equals(CheckTokens.CLOSE_PARANTHESIS.getToken())) { return 3; }
		else if(tokenValue.equals(CheckTokens.EQUALS.getToken()) || tokenValue.equals("return")) { return 4; }
		else if(tokenValue.equals(CheckTokens.COMMA.getToken())) { return 5; } 
		else if(tokenValue.equals(CheckTokens.SEMICOLON.getToken())) { return 6; } 
		return 1;
	}
	
	//wrapper function
}
