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
		if(diagramNFA == null) { return ; }		
		eClosure('e', 0);		
		
		for(int dfastate = 0; dfastate < nfaStates.size(); dfastate++) {		
			
			for(char symbol : inputTable) {
				nfaStates.add(new LinkedList<Integer>());
				eClosure(symbol, dfastate);
				addTransitions(symbol, dfastate);
				removeDuplicates();
				removeEmpty();
			}			
			addLastStates(dfastate);
		}
	}
		
	private void addLastStates(int dfastate) {
		for(int i = 0; i < nfaStates.get(dfastate).size(); i++) {
			if(nfaStates.get(dfastate).get(i) == diagramNFA.laststate) {
				laststates.add(dfastate);
				return ;
			}
		}
	}
	
	// set of NFA states reachable from NFA state s on E-transitions alone
	private void eClosure(char transitionSymbol, int dfastate) {		
		int nextstate = nfaStates.size() - 1;
		Stack<Integer> ePossiblePaths = new Stack<Integer>();

		for(int i = 0; i < nfaStates.get(dfastate).size(); i++) {
			ePossiblePaths.push(nfaStates.get(dfastate).get(i));
			char tempSymbol = transitionSymbol;
			for(int j = 0; !ePossiblePaths.isEmpty(); j++) {
				if(j == diagramNFA.transitions.size()) {
					j = 0;				
					ePossiblePaths.pop();
					
				} else {
					State state = diagramNFA.transitions.get(j);
					
					if(!nfaStates.get(nextstate).contains(state.vertexTo)) {			
						if(ePossiblePaths.peek() == state.vertexFrom && state.symbol == transitionSymbol) {
							ePossiblePaths.push(state.vertexTo);
							nfaStates.get(nextstate).add(state.vertexTo);
							transitionSymbol = 'e';
							j = 0; // there is a chance that when looping, it might go back. meaning in kleene star it would go back to the way it was
						} 
					}
				}
			}
			transitionSymbol = tempSymbol;
		}
	}
	
	private void addTransitions(char transSymbol, int statefrom) {
		if(nfaStates.get(nfaStates.size() - 1).size() == 0) { 
			transitions.add(new State(statefrom, -1, transSymbol));	// if negative -1 then the state should not read this input // this is a dead state
		} else {		
			if(dfaStateHasDuplicateIn() != -1) {
				int stateTo = dfaStateHasDuplicateIn();
				transitions.add(new State(statefrom, stateTo, transSymbol));
			} else {
				transitions.add(new State(statefrom, nfaStates.size()-1 ,transSymbol)); 
			}
		}
	}
	
	public void printStateDiagram() {
		for(int i = 0; i < nfaStates.size(); i++) {
			System.out.println("DFA NUMBER: " + i);
			for(int j = 0; j < nfaStates.get(i).size(); j++) {
				System.out.print(nfaStates.get(i).get(j) + " ");
			}
			System.out.println("");
		}
		
		for (State state: transitions){
            System.out.println("("+ state.vertexFrom +", "+ state.symbol +
                ", "+ state.vertexTo +")");
        }
		System.out.println("Laststates: ");
		for(Integer i : laststates) {
			System.out.print(i + " ");
		}
		
	}
		
	private void removeDuplicates() {
		if(dfaStateHasDuplicateIn() != -1) {
			nfaStates.remove(nfaStates.size() - 1);
		}
	}
	
	private int dfaStateHasDuplicateIn() {
		for(int dfastate = 0; dfastate < nfaStates.size() - 1; dfastate++) {
			if(new HashSet<>(nfaStates.get(nfaStates.size() - 1)).equals(new HashSet<>(nfaStates.get(dfastate)))) {
				return dfastate;
			}
		}	
		return -1;
	}
	
	private void removeEmpty() {
		if(nfaStates.get(nfaStates.size() - 1).size() == 0) {
			nfaStates.remove(nfaStates.get(nfaStates.size() - 1));
		}
	}
	
	public DFA getDfa() {
		return this;
	}

	public ArrayList<Integer> getLastStates() {
		return laststates;
	}
	
	public ArrayList<State> getTransitions() {
		return transitions;
	}
}
