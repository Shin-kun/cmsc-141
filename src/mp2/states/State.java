package mp2.states;

public class State {

	public int vertexFrom;
	public int vertexTo;
	public char symbol;
	
	public State(int vertexFrom, int vertexTo, char symbol) {
		this.vertexFrom = vertexFrom;
		this.vertexTo = vertexTo;
		this.symbol = symbol;
	}
	
}
