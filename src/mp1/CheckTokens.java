package mp1;

/**
 * enum class that defines each token
 */

public enum CheckTokens {
	CHAR("char"), INT("int"), DOUBLE("double"), FLOAT("float"), VOID("void"),
	PLUS("+"), MINUS("-"), TIMES("*"), DIVIDE("/"),
	SEMICOLON(";"), COMMA(","), EQUALS("="), OPEN_PARANTHESIS("("), CLOSE_PARANTHESIS(")"),
	OPEN_CURLY("{"), CLOSE_CURLY("}");
	
	private final String token;
	
	CheckTokens(String token){
		this.token = token;
	}
	
	public String getToken () { return this.token; }	

	public static String addSpacesBetweenTokens(String variableAndFunctionLine) {
		variableAndFunctionLine = variableAndFunctionLine.replace(PLUS.token, " " + PLUS.token + " ");
		variableAndFunctionLine = variableAndFunctionLine.replace(DIVIDE.token, " " + DIVIDE.token + " ");
		variableAndFunctionLine = variableAndFunctionLine.replace(TIMES.token, " " + TIMES.token + " ");
		variableAndFunctionLine = variableAndFunctionLine.replace(MINUS.token, " " + MINUS.token + " ");
		variableAndFunctionLine = variableAndFunctionLine.replace(EQUALS.token, " " + EQUALS.token + " ");
		variableAndFunctionLine = variableAndFunctionLine.replace(COMMA.token, " " + COMMA.token + " ");
		variableAndFunctionLine = variableAndFunctionLine.replace(SEMICOLON.token, " " + SEMICOLON.token + " ");
		variableAndFunctionLine = variableAndFunctionLine.replace(OPEN_PARANTHESIS.token, " " + OPEN_PARANTHESIS.token + " ");
		variableAndFunctionLine = variableAndFunctionLine.replace(CLOSE_PARANTHESIS.token, " " + CLOSE_PARANTHESIS.token + " ");			
		variableAndFunctionLine = variableAndFunctionLine.replace(OPEN_CURLY.token, " " + OPEN_CURLY.token + " ");			
		variableAndFunctionLine = variableAndFunctionLine.replace(CLOSE_CURLY.token, " " + CLOSE_CURLY.token + " ");			
		
		return variableAndFunctionLine;
	}

}
