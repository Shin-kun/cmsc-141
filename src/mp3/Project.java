package mp3;

import java.io.FileNotFoundException;

import mp3.turingMachine.TuringMachine;

public class Project {
	private static final String FILENAME = "src/mp3/power";

	public static void main(String[] args) {
		try {

			// Import addition machine
			TuringMachine power = TuringMachine.inParser(FILENAME);

			// Print addition machine
//			System.out.println("Machine content:");
//			System.out.println(power);

			// Process input
			power.process("111#11");

			// Print the tape content after the process
			System.out.println("Tape content after processing '111#11':");
			System.out.println(power.getTapeSnapshot());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
