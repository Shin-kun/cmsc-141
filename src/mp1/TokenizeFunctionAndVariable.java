package mp1;

import java.util.StringTokenizer;

public class TokenizeFunctionAndVariable {

	private int[] varAndfuncChecker;
	private String[][] tokenizedVarAndFuncLines;
	
	//a wrapper function 
	//tokenize everything
	public void makeTokenizedVariableAndFunctionLines(String[] variableAndfunctionLines) {
		variableAndfunctionLines = reformatingCommasWithSpace(variableAndfunctionLines);	
		varAndfuncChecker = checksVarAndFuncStrings(variableAndfunctionLines);
		tokenizedVarAndFuncLines = tokenizeVariableAndFunctionLines(variableAndfunctionLines); 
	}
	
	//separates certain tokens with space in between
	public static String[] reformatingCommasWithSpace(String[] variableAndfunctionLines) {
		for(int i = 0; i < variableAndfunctionLines.length; i++) {
			variableAndfunctionLines[i] = CheckTokens.addSpacesBetweenTokens(variableAndfunctionLines[i]);
		}
		return variableAndfunctionLines;
	}
	
	//determines whether certain cases would be function declaration or definition or variable declaration
	public static int[] checksVarAndFuncStrings(String[] variableAndfunctionLines) {
		int[] varAndFuncChecker = new int[variableAndfunctionLines.length];
		for(int i = 0; i < variableAndfunctionLines.length; i++) {
			if(variableAndfunctionLines[i].indexOf('{') >= 0) { 
				varAndFuncChecker[i] = 3;  //function definition
			} 
			else if(variableAndfunctionLines[i].indexOf('(') >= 0 && !variableAndfunctionLines[i].contains(CheckTokens.EQUALS.getToken())) { 
				varAndFuncChecker[i] = 2; //function declaration
			} 
			else { 
				varAndFuncChecker[i] = 1; //variable declaration
			}
		}
		return varAndFuncChecker;
	}

	/*
	 * tokenizes the variable and function lines and 
	 * put them in a 2D array
	 * then we would return the 2D array which is composed of tokenized lines
	 */
	public static String[][] tokenizeVariableAndFunctionLines(String[] variableAndfunctionLines) {
		StringTokenizer tokenizer;
		String[][] initVarAndFuncLinesToken = new String[variableAndfunctionLines.length][];
		for(int i = 0; i < variableAndfunctionLines.length && !variableAndfunctionLines[i].equals(""); i++) {
			int j = 0;
			tokenizer = new StringTokenizer(variableAndfunctionLines[i], " ");
			initVarAndFuncLinesToken[i] = new String[tokenizer.countTokens()];
			while(tokenizer.hasMoreTokens()) {
				initVarAndFuncLinesToken[i][j] = tokenizer.nextToken();
				j++;
			}
		}
		return initVarAndFuncLinesToken;
	}

	public int[] getvarAndfuncChecker() {
		return varAndfuncChecker;
	}
	
	public String[][] getTokenizedVarAndFuncLines() {
		return tokenizedVarAndFuncLines;
	}
}
