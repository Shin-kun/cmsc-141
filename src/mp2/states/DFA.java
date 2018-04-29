package mp2.states;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;
import java.util.Hashtable;
import java.util.LinkedList;

public class DFA {
	private final char[] inputTable = {'a','b'};
	///private ArrayList<Integer> dfaStates;
	private ArrayList<LinkedList<State>> nfaStatesTransitions;
	private ArrayList<State> transitions;
	private ArrayList<State> lastStates;
	private ArrayList<String> markedDFA;
	
	private NFA diagramNFA;
	
	public DFA() { }
	
	public DFA(NFA diagramNFA) {
		this.diagramNFA = diagramNFA;
		//this.dfaStates = new ArrayList<Integer>(); //dfa states
		this.nfaStatesTransitions = new ArrayList<LinkedList<State>>();   //nfa states
		
		this.transitions = new ArrayList<State>(); //dfa state transition
		//this.markedDFA = new ArrayList<String>();
		this.lastStates = new ArrayList<State>();
		//this.dfaStates.add(0);
		//this.markedDFA.add("unchecked");
		
		nfaStatesTransitions.add(new LinkedList<State>());
		nfaStatesTransitions.get(0).add(diagramNFA.transitions.get(0));
	}
		
	// subset construction algorithm
	public void convertNFAToDFA() {
		int travelstate = diagramNFA.states.get(0); //initial 
				
		eClosure('e');		
					
//		for(int index = 0; index < nfaStatesTransitions.size(); index++) {
			//markedDFA.get(index).replace("unchecked", "checked");			
//			for(char symbol: inputTable) {
//				int i = 0;
//				nfaStatesTransitions.add(new LinkedList<State>());
//				eClosure(symbol);
//			
//				check for duplicates 
//			
//			}
//		}
	}
	
	public void printEclosure() {
		for(int i = 0; i < nfaStatesTransitions.size(); i++) {
			System.out.println("DFA NUMBER: " + i);
			for(int j = 0; j < nfaStatesTransitions.get(i).size(); j++) {
				System.out.println("(" + nfaStatesTransitions.get(i).get(j).vertexFrom + ", " + 
						nfaStatesTransitions.get(i).get(j).symbol + ", " + nfaStatesTransitions.get(i).get(j).vertexTo +
						")");
			}
		}
	}
		
	// set of NFA states reachable from NFA state s on E-transitions alone
	private void eClosure(char transitionSymbol) {		
		int index = 0;
		Stack<State> ePossiblePaths = new Stack<State>();

		if(nfaStatesTransitions.size() > 1) {
			index = nfaStatesTransitions.size() - 1; // the latest
		}		
	
		for(int i = 0; i < nfaStatesTransitions.get(index).size(); i++) {
			ePossiblePaths.push(nfaStatesTransitions.get(index).get(i));
			for(int j = 0; !ePossiblePaths.isEmpty(); j++) {
				if(j == diagramNFA.transitions.size()) {
					j = 0;				
					ePossiblePaths.pop();
					
				} else {
					State state = diagramNFA.transitions.get(j);
					
					if(!nfaStatesTransitions.get(index).contains(state)) { // no duplicate	
//						if(ePossiblePaths.peek().vertexTo != state.vertexFrom) {
//							while(!ePossiblePaths.isEmpty() && ePossiblePaths.peek().vertexTo == state.vertexFrom) {
//								ePossiblePaths.pop();
//								j--;
//							}	
//						}
//						else {
							if(ePossiblePaths.peek().vertexFrom == state.vertexFrom || ePossiblePaths.peek().vertexTo == state.vertexFrom &&
								(state.symbol == transitionSymbol || (nfaStatesTransitions.get(index).size() > 0 && state.symbol == 'e'))) {
								ePossiblePaths.push(state);
								nfaStatesTransitions.get(index).add(state);
							}										
//						}
					}	
				}
			}
		}
	}
	
	/*public boolean removeEClosureEmptyInputs(char transSymbol) {
		if(nfaStatesTransitions.get(nfaStatesTransitions.size() - 1).size() > 0) { 
			for(int i = 0; i < nfaStatesTransitions.get(nfaStatesTransitions.size()-1).size(); i++) {
				if(nfaStatesTransitions.get(nfaStatesTransitions.size()-1).get(i).symbol == transSymbol) {
					return true;
				}
			}
		}
		nfaStatesTransitions.remove(nfaStatesTransitions.size() - 1);
		return false;
	}*/
}
