package mp2;

import java.util.ArrayList;
import java.util.Stack;

public class ExpressionTree {
	
	class ExpressionNode {
		public char symbol;
		public ExpressionNode left, right;
		public int value;
		
		ExpressionNode(int value, char symbol) {
			this.value = value;
			this.symbol = symbol;
			this.left = this.right = null;
			
		}
	}
		
	private ExpressionNode root;
	private ArrayList<Character> postfix;
	
	public ExpressionTree() {
		root = null;
		postfix = new ArrayList<Character>();
	}
	
	public ExpressionNode insertExpressionIntoTree(ArrayList<Character> expression) {
		if(expression.size() == 0) { 
			return root;
		}
		Stack<ExpressionNode> expressionStack = new Stack<ExpressionNode>();
		int base = 0;
		int value = 0;
		
		for(int i = 0; i < expression.size(); i++) {
			if(expression.get(i) == '(') {
				base += 10;
				continue;
				
			} else if(expression.get(i) == ')') {
				base -= 10;
				continue;
			}
			
			value = getValueWeight(base, expression.get(i));
			ExpressionNode node = new ExpressionNode(value, expression.get(i));
			
			while(!expressionStack.isEmpty() && node.value <= expressionStack.peek().value) {
				node.left = expressionStack.pop();
			}
			if(!expressionStack.isEmpty()) {
				expressionStack.peek().right = node;
			}
			expressionStack.push(node);	
		}
		if(expressionStack.isEmpty()) { return null; }
		
		root = expressionStack.pop();
		while(!expressionStack.isEmpty()) {
			root = expressionStack.pop();
		}
		return root;
	}
	
	public int getValueWeight(int base, char symbol) {
		if(symbol == 'U') { return base + 1; } 
		
		else if(symbol == '.') { return base + 2; } 
		
		else if(symbol == '*') { return base + 3; }
		
		return Integer.MAX_VALUE;
	}
	
	public void postFixTravel(ExpressionNode root) {
		if(root != null) {
			postFixTravel(root.left);
			postFixTravel(root.right);
			System.out.print(root.symbol + " ");
			postfix.add(root.symbol);
		}
	}
	
	public ArrayList<Character> getPostFixExpression() { return this.postfix; }
}
