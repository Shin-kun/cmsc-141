package mp2.states;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;
import java.util.LinkedList;

public class DFA {
	private final char[] inputTable = {'a','b'};
	ArrayList<LinkedList<Integer>> nfaStates;
	ArrayList<State> transitions;
	ArrayList<Integer> laststates;
	NFA diagramNFA;
	
	public DFA() { }
	
	public DFA(NFA diagramNFA) {
		this.diagramNFA = diagramNFA;
		this.nfaStates = new ArrayList<LinkedList<Integer>>();   //nfa states
		this.transitions = new ArrayList<State>(); //dfa state transition
		this.laststates = new ArrayList<Integer>();
		nfaStates.add(new LinkedList<Integer>());
		nfaStates.get(0).add(0);
	}
		
	// subset construction algorithm
	public void convertNFAToDFA() {
				
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
