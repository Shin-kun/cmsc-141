package mp2.states;

import java.util.ArrayList;
import java.util.Stack;

public class NFA {

	int laststate;
	ArrayList<State> transitions; // used in concatenation to have record of the first state to be concatenated
	ArrayList<Integer> states;
	private Stack<NFA> incompleteStates;
	
	public NFA() {
		this.laststate = 0;
		this.transitions = new ArrayList<State>();
		this.states = new ArrayList<Integer>();
	}
	
	public NFA(int size) {
		this.laststate = 0;
		this.transitions = new ArrayList<State>();
		this.states = new ArrayList<Integer>();		
		setStateSize(size);
	}
	
	public NFA(char symbol) {
		this.laststate = 1;
		this.transitions = new ArrayList<State>();
		this.states = new ArrayList<Integer>();
		setStateSize(2);
		this.transitions.add(new State(0,1,symbol));
	}
	
	public void setStateSize(int size) {
		for(int i = 0; i < size; i++) {
			this.states.add(i);
		}
	}
		
	public NFA getNFADiagram() {
		return incompleteStates.pop();
	}
	
	public void conjoin(ArrayList<Character> postfix) {

		if(postfix.size() == 0) { return; }
		incompleteStates = new Stack<NFA>();
		
		for(int position = 0; position < postfix.size(); position++) {
			NFA newstate;
			
			if(postfix.get(position) == 'a' || postfix.get(position) == 'b') {
				newstate = (postfix.get(position) == 'a') ? new NFA('a') : new NFA('b');
							
				incompleteStates.push(newstate);
				
			} else if(postfix.get(position) == '.') {
				conjoinConcatOperation();
			
			} else if(postfix.get(position) == 'U') {
				conjoinUnionOperation();
			
			} else if(postfix.get(position) == '*') {
				conjoinStarOperation();
				
			}
 		}
	}
	
	public void printStateDiagram() {
		if(incompleteStates.isEmpty()) { return ; }
		NFA result = incompleteStates.peek();
		for (State state: result.transitions){
            System.out.println("("+ state.vertexFrom +", "+ state.symbol +
                ", "+ state.vertexTo +")");
        }
		System.out.println("");
		
		for (Integer states : result.states) {
			System.out.println("State Number : " + states.toString());
		}
		
	}
	
	public void conjoinConcatOperation() {
		System.out.println("");
		NFA secondstate = incompleteStates.pop();
		NFA firststate = incompleteStates.pop();	
				
		secondstate.states.remove(0); //removing the initial state for the second state
		
		for(State state: secondstate.transitions) {
			firststate.transitions.add(new State(state.vertexFrom + firststate.states.size() - 1, 
				state.vertexTo + firststate.states.size() - 1, state.symbol));
		}
	
		for(Integer statenum : secondstate.states) {
			firststate.states.add(statenum + firststate.states.size() - 1);
		}
				
		firststate.laststate = firststate.states.size() + secondstate.states.size() - 2;
		System.out.println("last state: " + firststate.laststate);
		incompleteStates.push(firststate);
	}
	
	public void conjoinUnionOperation() {
		NFA secondstate = incompleteStates.pop();
		NFA firststate = incompleteStates.pop();
		NFA result = new NFA(firststate.states.size() + secondstate.states.size() + 2);
		
		result.transitions.add(new State(0,1,'e'));
		
		for(State state: firststate.transitions) {
			result.transitions.add(new State(state.vertexFrom + 1, state.vertexTo + 1, state.symbol));
		}
		
		result.transitions.add(new State(firststate.states.size(), firststate.states.size() + secondstate.states.size() + 1,'e'));
		result.transitions.add(new State(0,firststate.states.size() + 1, 'e'));
	
		
        for (State state: secondstate.transitions){
            result.transitions.add(new State(state.vertexFrom + firststate.states.size()
                + 1, state.vertexTo + firststate.states.size() + 1, state.symbol));
        }
        
        result.transitions.add(new State(secondstate.states.size() + firststate.states.size(),
                firststate.states.size() + secondstate.states.size() + 1, 'e'));
        
        result.laststate = firststate.states.size() + secondstate.states.size() + 1;
        incompleteStates.push(result);
	}
	
	public void conjoinStarOperation() {
		NFA starstate = incompleteStates.pop();
		NFA result = new NFA(starstate.states.size() + 2);
		
		result.transitions.add(new State(0,1,'e'));
		for(State state: starstate.transitions) {
			result.transitions.add(new State(state.vertexFrom + 1, state.vertexTo + 1, state.symbol));
		}
		
		result.transitions.add(new State(starstate.states.size(), starstate.states.size() + 1, 'e'));
		result.transitions.add(new State(starstate.states.size(), 1, 'e'));
		result.transitions.add(new State(0, starstate.states.size() + 1, 'e'));
		
		result.laststate = starstate.states.size() + 1;
		
		System.out.println("This is result.laststate: " + result.laststate);
		incompleteStates.push(result);
	}
	
}
