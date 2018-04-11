package mp2;

import mp2.regex_operations.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RegularExpressionReader {
	
	RegularExpressionReader() { }
	
	RegularExpressionReader(String filename) {
		try {
			fileReading(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void fileReading(String filename) throws IOException {
		BufferedReader bufferRead = null;
		bufferRead = new BufferedReader(new FileReader(filename));
		
		int testcases = Integer.parseInt(bufferRead.readLine());
		
		for(int i = 0; i < testcases; i++) {
			String regexString = bufferRead.readLine();
			int regexTestCasesNum = Integer.parseInt(bufferRead.readLine());
			checkRegexString(regexString, regexTestCasesNum, bufferRead);
		}
	
		bufferRead.close();
		
	}
	
	public static void checkRegexString(String regexString, int regexTestCasesNum, BufferedReader bufferRead) 
			throws IOException {
		
		for(int i = 0; i < regexTestCasesNum; i++) {
			
			String verificationRegex = bufferRead.readLine();
			
			//insert regex_operations here
		}
		
		
	}
	
	
	
}
