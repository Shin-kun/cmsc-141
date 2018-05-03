package mp2;

import mp2.ExpressionTree;
import mp2.states.DFA;
import mp2.states.NFA;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class RegularExpressionReader {
	private static String filename;
	private static ArrayList<Character> augmentedRegexExpression;
	private static ArrayList<Character> postfix;
	private static ExpressionTree tree;
	private static NFA nfa;
	private static DFA dfa;
	private static ValidateExpression validation;
	
	RegularExpressionReader(String filename) {
		this.filename = filename;
		this.tree = new ExpressionTree();
		this.augmentedRegexExpression = new ArrayList<Character>();
		this.postfix = new ArrayList<Character>();
		this.nfa = new NFA();
		this.dfa = new DFA();
	}
	
	public static void fileReading()  {
		BufferedReader bufferRead = null;
		try {
			bufferRead = new BufferedReader(new FileReader(filename));
			int testcases = Integer.parseInt(bufferRead.readLine());
			
			for(int i = 0; i < testcases; i++) {
				String regexExpression = bufferRead.readLine();
				regexExpression = regexExpression.replaceAll("\\s+","");
				augmentedRegexExpression = augmentRegex(regexExpression);
				
				tree.postFixTravel(tree.insertExpressionIntoTree(augmentedRegexExpression));
				postfix = tree.getPostFixExpression();
				nfa.buildNFA(postfix);
								
				dfa = new DFA(nfa.getNfa());
				dfa.convertNFAToDFA();
				int regexTestCasesNum = Integer.parseInt(bufferRead.readLine());
				validateStringExpression(regexTestCasesNum, bufferRead);
			}
	
			bufferRead.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
			
	}
	
	public static ArrayList<Character> augmentRegex(String regexExpression) {
		ArrayList<Character> augmentedRegex = new ArrayList<Character>();
		for(int index = 0; index < regexExpression.length(); index++) {
			augmentedRegex.add(regexExpression.charAt(index));
			
			if(index < regexExpression.length() - 1) { 

				if(regexExpression.charAt(index) == ')' && regexExpression.charAt(index+1) == '(') {
					augmentedRegex.add('.');						
				
				} else if(regexExpression.charAt(index) == '*' && regexExpression.charAt(index+1) == '(' ) {
					augmentedRegex.add('.');
				
				} else if(regexExpression.charAt(index) == '*' && isAlphabetSymbol(regexExpression.charAt(index+1))) {
					augmentedRegex.add('.');
				
				} else if(regexExpression.charAt(index) == ')' && isAlphabetSymbol(regexExpression.charAt(index+1))) {
					augmentedRegex.add('.');

				} else if(isAlphabetSymbol(regexExpression.charAt(index)) && regexExpression.charAt(index+1) == '(') {
					augmentedRegex.add('.');
				
				} else if(isAlphabetSymbol(regexExpression.charAt(index)) && isAlphabetSymbol(regexExpression.charAt(index+1))) {
					augmentedRegex.add('.');
				}
			}
		}
		
		return augmentedRegex;
	}
	
	private static boolean isAlphabetSymbol(char symbol) {
		if(symbol == 'a' || symbol == 'b') return true;  
		return false;
	}
	
	public static void validateStringExpression(int regexTestCasesNum, BufferedReader bufferRead) 
			throws Exception {
		validation = new ValidateExpression(dfa.getDfa());
		
		for(int i = 0; i < regexTestCasesNum; i++) {
			
			String verificationExpression = bufferRead.readLine();	
			
			if(validation.checkExpressionIfValid(verificationExpression)) {
				System.out.println("yes");
			} else {
				System.out.println("no");
			}
		}
	}

}
