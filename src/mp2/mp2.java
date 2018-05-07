package mp2;

/**
 * @Resources  
 * - https://github.com/prijatelj/thompson-construction/blob/master/Thompson.java (regex to nfa code)
 * - https://github.com/awangdev/LintCode/blob/master/Java/Expression%20Evaluation.java (convert to postfix code)
 * - https://www.cs.york.ac.uk/fp/lsa/lectures/REToC.pdf (nfa to dfa pdf)
 */

/** 
 * @author - Michael Loewe Alivio
 * @author - Michel Jimro Quiambao 
 * @author - Paul Christian Kiunisala
 * @author - Mary Danielle Amora
 */


public class mp2 {
	private static final String FILENAME = "src/mp2/mpa2.in";
	
	
	public static void main(String[] args) {
		RegularExpressionReader read = new RegularExpressionReader(FILENAME);
		read.fileReading();	
	}
	
	
}
