package mp1;

import mp1.checkers.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 * @author - Michael Loewe Alivio
 */

/**
 * Collaborators : Michael Loewe Alivio
 *  			   Paul Christian Kiunisala
 *  			   Mary Danielle Amora
 *                 Michel Jimro Quiambao
 * 
 * Source : CMSC 21 (Gatekeepers of 244 MP) Using state diagrams
 * please import java commons-lang3-3.7.jar
**/

/**
 * Class reader 
 */
public class FunctionAndVariableReader {
	private static final String FILENAME = "src/mp1/mpa1";
	private static String currentLine;
	
	public static void main(String[] args) {
		//reading some file here
		try {
			BufferedReader bufferRead = new BufferedReader(new FileReader(FILENAME));
			TokenizeFunctionAndVariable tokenizedLines = new TokenizeFunctionAndVariable();
			
			int testcases = returnNumberofCases(bufferRead);			
			String[] variableAndfunctionLinesFromFile = initializeVariableAndFunctionLines(testcases);
			variableAndfunctionLinesFromFile = readingLinesfromFile(bufferRead, variableAndfunctionLinesFromFile);
			
			tokenizedLines.makeTokenizedVariableAndFunctionLines(variableAndfunctionLinesFromFile);			
			printResults(tokenizedLines.getTokenizedVarAndFuncLines(), tokenizedLines.getvarAndfuncChecker());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String[] initializeVariableAndFunctionLines(int testcases) {
		String[] variableAndfunctionLines = new String[testcases];
		for(int i = 0; i < testcases; i++) {
			variableAndfunctionLines[i] = "";
		}
		return variableAndfunctionLines;
	}
	
	//first line is always a number	
	public static int returnNumberofCases(BufferedReader bufferReadCase) throws IOException {
		String testCaseString = bufferReadCase.readLine();
		return Integer.parseInt(testCaseString);
	}
	
	/*
	 * reads the entire file
	 */
	public static String[] readingLinesfromFile(BufferedReader bufferRead, String[] variableAndfunctionLines) throws IOException {
		//buffer read and something
		currentLine = "";
		boolean determinerEndCurly = false;
		for(int i = 0; (currentLine = bufferRead.readLine()) != null;) { 
			boolean noSemicolon = false;
			while(!currentLine.equals("")) {
				currentLine = currentLine.trim();				
				determinerEndCurly = determineEndCurly(determinerEndCurly);
				noSemicolon = determineSemiColon(noSemicolon);
				variableAndfunctionLines[i] = variableAndfunctionLines[i] + " " + reduceVarAndFuncLines();
				if(!noSemicolon && !determinerEndCurly) {
					i++;
				} 
			}
		}
		bufferRead.close();
		return variableAndfunctionLines;
	}
		
	/*
	 * checks if a test case is a function definition
	 */
	public static boolean determineEndCurly(boolean determinerEndCurly) {
		if(currentLine.contains(";")) {
			if(currentLine.contains("{") && currentLine.indexOf("{") < currentLine.indexOf(";")) {
				return true;
			}
		}
		else if(currentLine.contains("{")) return true;
		else if(currentLine.contains("}")) return false;
		return determinerEndCurly;
	}
	
	/*
	 * checks if line has no semicolon
	 */
	public static boolean determineSemiColon(boolean noSemiColon) {
		if(currentLine.contains("}")){
			return false; //must not add
		} else if(!currentLine.contains(";")) {
			return true;
		}  
		return noSemiColon;
	}
	
	/*
	 * reduces the string to "" in case there are two cases at the same line 
	 * @return - returns the substring that was reduced from the currentline
	 */
	public static String reduceVarAndFuncLines() {
		String getSubString = "";
		if(currentLine.contains(CheckTokens.SEMICOLON.getToken())) {
			getSubString = currentLine.substring(0, currentLine.indexOf(CheckTokens.SEMICOLON.getToken()) + 1);
			currentLine = currentLine.replace(getSubString, "");
		} else {
			getSubString = currentLine;
			currentLine = "";
		}
		return getSubString;
	}

	
	/**
	 * printing the results
	 * @param tokenizedVarAndFuncLines
	 * @param varAndFuncChecker
	 */
	public static void printResults(String[][] tokenizedVarAndFuncLines, int[] varAndFuncChecker) throws IOException {		
		File outputFile = new File("mpa1.out");
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
		for(int i = 0; i < tokenizedVarAndFuncLines.length; i++) {
			boolean checkVarAndFunctionToken = changingVariableAndFunctionState(tokenizedVarAndFuncLines[i], varAndFuncChecker[i]);
			String printStatements = "";
			
			if(checkVarAndFunctionToken) { printStatements = "VALID"; }  
			else { printStatements = "INVALID"; }
			
			if(varAndFuncChecker[i] == 1) { 
				printStatements = printStatements + " VARIABLE DECLARATION\n";
			} else if(varAndFuncChecker[i] == 2) { 
				printStatements = printStatements + " FUNCTION DECLARATION\n";
			} else { 
				printStatements = printStatements + " FUNCTION DEFINITION\n";
			}
			bufferedWriter.write(printStatements);
		}
		bufferedWriter.close();
	}
		
	public static boolean changingVariableAndFunctionState(String[] OnetokenizedVarAndFuncLines, int flagIfVarAndFunc) {
		//passing in this argument in order to populate storage values... please see check variable class
		CheckingVariableDeclaration checkVarDec = new CheckingVariableDeclaration(OnetokenizedVarAndFuncLines.length); 
		CheckingFunctionDeclaration checkFuncDec = new CheckingFunctionDeclaration(OnetokenizedVarAndFuncLines.length);
		CheckingFunctionDefinition checkFuncDef = new CheckingFunctionDefinition(OnetokenizedVarAndFuncLines.length);
		
		for(int i = 0; i < OnetokenizedVarAndFuncLines.length; i++) {	
			if(flagIfVarAndFunc == 1) {
				if(!checkVarDec.checkStatementIfValid(OnetokenizedVarAndFuncLines[i]))
					return false;
			} else if(flagIfVarAndFunc == 2) {
				if(!checkFuncDec.checkStatementIfValid(OnetokenizedVarAndFuncLines[i]))
					return false;
			} else {
				if(!checkFuncDef.checkStatementIfValid(OnetokenizedVarAndFuncLines[i]))
					return false;
			}			
		}
		return true;
	}
}
