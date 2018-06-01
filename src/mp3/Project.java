package mp3;

import java.io.FileNotFoundException;
import java.util.Scanner;
import mp3.resources.turingMachine.TuringMachine;

public class Project {
	private static final String POWER = "src/mp3/power";
	private static final String REVERSE = "src/mp3/reverse";

	public static void main(String[] args) {

	    while (true){

            int choice = 1;

            System.out.println("Menu:\n1. Power\n2. Reverse String\n\nChoice: ");
            Scanner scan = new Scanner(System.in);
            choice = scan.nextInt();

            if (choice == 1){
                String equation = "";
                int x, y;
                System.out.println("Compute for power: x^y");
                System.out.print("Enter x: ");
                x = scan.nextInt();
                System.out.print("Enter y: ");
                y = scan.nextInt();
                System.out.println("Equation: "+x+"^"+y);


                for (int ex = 0; ex < x; ex++){
                    equation = equation+"1";
                }
                equation = equation+"#";

                for (int way = 0; way < y; way++){
                    equation = equation+"1";
                }

                System.out.println(equation);
                try {
                    computePower(equation);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }else if (choice == 2){

                System.out.println("Enter string: ");
                Scanner sc = new Scanner(System.in);
                String equation = sc.nextLine();

                try {
                    doReverse(equation);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }

            System.out.println("PROCESS DONE");
            System.out.println("Process again? Press 1 for yes and 2 for no");
            if (scan.nextInt() == 1){
                //do nothing
            }else{
                System.out.println("bye!");
                break;
            }

        }
	}
	
	public static void computePower(String equation) throws FileNotFoundException{
		// Import addition machine
		TuringMachine power = TuringMachine.inParser(POWER);

		// Print addition machine
//		System.out.println("Machine content:");
//		System.out.println(power);

		// Process input
        power.process(equation);
		
		// Print the tape content after the process
		System.out.println("Final Tape content after processing for equation "+equation+":");
		System.out.println(power.getTapeSnapshot());
		
	}
	
	public static void doReverse(String equation) throws FileNotFoundException {
		// Import addition machine



        TuringMachine reverse = TuringMachine.inParser(REVERSE);



		//Scanner sc = new Scanner(System.in);
		// Process input
		reverse.process(equation);
		
		// Print the tape content after the process
		System.out.println("Tape content after processing :"+equation);
		System.out.println(reverse.getTapeSnapshot());
		
	}

}
