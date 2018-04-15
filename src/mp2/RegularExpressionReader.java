package mp2;

import mp2.regex_operations.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class RegularExpressionReader {
	private static ArrayList<String> kleeneStarContainer;
	private static ArrayList<String> unionContainer;
	private static String regexExpressionInCaseNoOperation = "";
	
	RegularExpressionReader() { }
	
	public static void fileReading(String filename)  {
		BufferedReader bufferRead = null;
		
		try {
			bufferRead = new BufferedReader(new FileReader(filename));
			kleeneStarContainer = new ArrayList<String>();
			unionContainer = new ArrayList<String>();
			
			int testcases = Integer.parseInt(bufferRead.readLine());
			
			for(int i = 0; i < testcases; i++) {
				String regexExpression = bufferRead.readLine();
				//insert here separating to regex into individual strings
				slicingRegexIntoArray(regexExpression);
				
				int regexTestCasesNum = Integer.parseInt(bufferRead.readLine());
				checkRegexString(regexTestCasesNum, bufferRead);
			}
	

			bufferRead.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	
	}
	
	public static void checkRegexString(int regexTestCasesNum, BufferedReader bufferRead) 
			throws IOException {
		
		RegexOperation kleeneStar = new KleeneStarOperator(kleeneStarContainer);
		RegexOperation union = new UnionOperator(unionContainer);
		
		for(int i = 0; i < regexTestCasesNum; i++) {
			
			String verificationRegex = bufferRead.readLine();			
			kleeneStar.validateStringRegex(verificationRegex);
			
			// for now I just consider that all verifications
			// are kleene stars. 
			// kamo na lay bahala unsaon pag validate sa UnionOperation
		}
		
		
	}
	
	public static void slicingRegexIntoArray(String regexExpression) {
		
		while(regexExpression.equals("")) {
			
			if(regexExpression.indexOf("*") != -1) { 
				
				kleeneStarContainer.add(regexExpression.substring(0, regexExpression.indexOf("*")));
				regexExpression.replace(regexExpression.substring(0, regexExpression.indexOf("*") + 1) , "");
			} else if(regexExpression.indexOf("U") != -1) {
				// slice between right and left

			
			
			} else {
				
				regexExpressionInCaseNoOperation = regexExpression;
				regexExpression = "";
			}
			
		}
		
	}
	
}
