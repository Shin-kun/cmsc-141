package mp3;
/*
	Collaborators: Mary Danielle Amora
               Michael Loewe Alivio
               Michael Jimro Quiambao
               Paul Christian Kiunisala
*/
// resources : https://github.com/amirbawab/Turing-Machine-simulator

import java.io.FileNotFoundException;
import java.util.Scanner;
import mp3.resources.turingMachine.TuringMachine;

public class Project {
	private static final String POWER = "src/mp3/power";
	private static final String REVERSE = "src/mp3/reverse";

	public static void main(String[] args) {
		menu();
	}
	
	public static void menu() {
		System.out.println("PROJECT IN CMSC 141\n");
		
		try {
            Scanner scan = new Scanner(System.in);
            int choice;

            while (true){
	            System.out.print("Menu:\n1. Power\n2. Reverse String\n3. Exit\n\nChoice: ");
	            choice = scan.nextInt();

	            if (choice == 1){
	                String equation = "";
	                boolean choose = true;
	            	int x = 1, y = 1;
	                
	                while(choose) {
	                	choose = false;
	                    System.out.println("Compute for power: x^y");
	                    System.out.print("Enter a positive x: ");
	                    x = scan.nextInt();
	                    System.out.print("Enter a positive y: ");
	                    y = scan.nextInt();
	                    if(x < 0 || y < 0 || x == 0 || y == 0) {
	                    	choose = true;
	                    }
	                }
                    System.out.println("Equation: "+x+"^"+y);
	                
	                for (int ex = 0; ex < x; ex++){
	                    equation = equation+"1";
	                }
	                equation = equation+"#";

	                for (int way = 0; way < y; way++){
	                    equation = equation+"1";
	                }

	                System.out.println(equation);
	                computePower(equation, scan);
		            System.out.println("PROCESS DONE\n\n\n");

	            } else if (choice == 2){
	            	
	            	boolean choose = true;
	            	String equation = "";
	                while(choose) {

	                    System.out.print("Please input a string with symbols a and b only: ");
	                    equation = scan.next();
	                	
	                	choose = false;
	                    for(int i = 0; i < equation.length(); i++) {
	                        if(!(equation.charAt(i) == 'a' || equation.charAt(i) == 'b')) {
	                            choose = true;
	                            break;
	                        } 
	                    }
	                }
	            	reverseString(equation, scan);
		            System.out.println("PROCESS DONE\n\n\n");

	            } else if (choice == 3){
	                System.out.println("bye!");
	                break;
	            }
	        }
        	scan.close();

	    }  catch (FileNotFoundException e) {
            e.printStackTrace();
        }
	}
	
	public static void computePower(String equation, Scanner sc) throws FileNotFoundException{
		// Import power machine
		TuringMachine power = TuringMachine.inParser(POWER);

		// Process input
        power.process(equation, sc);
		
		// Print the tape content after the process
		System.out.println("Final Tape content after processing for equation "+equation+":");
		System.out.println(power.getTapeSnapshot());
		
	}
	
	public static void reverseString(String equation, Scanner sc) throws FileNotFoundException {
		// Import reverse machine

        TuringMachine reverse = TuringMachine.inParser(REVERSE);

		// Process input
		reverse.process(equation, sc);
		
		// Print the tape content after the process
		System.out.println("Tape content after processing :"+equation);
		System.out.println(reverse.getTapeSnapshot());
		
	}
}
