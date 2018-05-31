package mp3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import mp3.resources.turingMachine.TuringMachine;

public class Project {
	private static final String POWER = "src/mp3/power";
	private static final String REVERSE = "src/mp3/reverse";

	public static void main(String[] args) {
		try {
			computePower();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		

		
	}
	
	public static void computePower() throws FileNotFoundException{
		// Import addition machine
		TuringMachine power = TuringMachine.inParser(POWER);

		// Print addition machine
//		System.out.println("Machine content:");
//		System.out.println(power);

		// Process input
		power.process("11#1111");
		
		// Print the tape content after the process
		System.out.println("Tape content after processing '11#11':");
		System.out.println(power.getTapeSnapshot());
		
	}
	
	public static void doReverse() throws FileNotFoundException {
		// Import addition machine
		TuringMachine reverse = TuringMachine.inParser(REVERSE);

		//Scanner sc = new Scanner(System.in);
		// Process input
		reverse.process("aa");
		
		// Print the tape content after the process
		System.out.println("Tape content after processing '11#11':");
		System.out.println(reverse.getTapeSnapshot());
		
	}

}
