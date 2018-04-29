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
	private static NFA NFAstateDiagram;
	private static DFA DFAstateDiagram;
	
	RegularExpressionReader(String filename) {
		this.filename = filename;
		this.tree = new ExpressionTree();
		this.augmentedRegexExpression = new ArrayList<Character>();
		this.postfix = new ArrayList<Character>();
		this.NFAstateDiagram = new NFA();
		this.DFAstateDiagram = new DFA();
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
				NFAstateDiagram.conjoin(postfix);
				NFAstateDiagram.printStateDiagram();
				
				System.out.println("done");
				
				
				DFAstateDiagram = new DFA(NFAstateDiagram.getNFADiagram());
				DFAstateDiagram.convertNFAToDFA();
				DFAstateDiagram.printEclosure();
				int regexTestCasesNum = Integer.parseInt(bufferRead.readLine());
				validateStringExpression(regexTestCasesNum, bufferRead);
			}
	

			bufferRead.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
			
	}
	
	public static ArrayList<Character> augmentRegex(String regexExpression) {
		ArrayList<Character> augmentedRegex = new ArrayList<Character>();
		for(int index = 0; index < regexExpression.length(); index++) {
			augmentedRegex.add(regexExpression.charAt(index));
			
			if(index < regexExpression.length() - 1 && 
				(((regexExpression.charAt(index) == 'a' || regexExpression.charAt(index) == 'b' || regexExpression.charAt(index) == '*') &&
				(regexExpression.charAt(index+1) == 'a' || regexExpression.charAt(index+1) == 'b' || regexExpression.charAt(index+1) == '(')) 
				|| (regexExpression.charAt(index) == ')' && (regexExpression.charAt(index+1) == 'a' || regexExpression.charAt(index+1) == 'b' || regexExpression.charAt(index+1) == '('))))	{
				
				augmentedRegex.add('.');

			}
		}
		
		for(int i = 0; i < augmentedRegex.size(); i++) {
			System.out.print(augmentedRegex.get(i) + " ");
		}
		System.out.println("");
		return augmentedRegex;
	}
	
	public static void validateStringExpression(int regexTestCasesNum, BufferedReader bufferRead) 
			throws IOException {
		
		for(int i = 0; i < regexTestCasesNum; i++) {
			
			String verificationString = bufferRead.readLine();	
			//TODO verify string
			
		}
	}

}
