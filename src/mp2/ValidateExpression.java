package mp2;

import java.util.ArrayList;
import mp2.states.DFA;
import mp2.states.State;


public class ValidateExpression {

	private ArrayList<State> dfaStateDiagram;
	private ArrayList<Integer> lastStates;
	
	public ValidateExpression() { }
	
	public ValidateExpression(DFA dfa) {
		this.lastStates = dfa.getLastStates();
		this.dfaStateDiagram = dfa.getTransitions();
	}
	
	public boolean checkExpressionIfValid(String verificationExpression) {
		int travelstate = 0;		
		for(int i = 0; i < verificationExpression.length(); i++) {
			char input = verificationExpression.charAt(i);
			
			for(int j = 0; j < dfaStateDiagram.size(); j++) {
				if(input == dfaStateDiagram.get(j).symbol && travelstate == dfaStateDiagram.get(j).vertexFrom) {
					travelstate = dfaStateDiagram.get(j).vertexTo;
					break;
				}
			}
		}
		if(checkIfTravelStateIsLastState(travelstate)) {
			return true;
		} 
		return false;
	}
	
	private boolean checkIfTravelStateIsLastState(int travelstate) {
		for(int i = 0; i < lastStates.size(); i++) {
			if(lastStates.get(i) == travelstate) {
				return true;
			}
		}
		return false;
	}
	
	
}
