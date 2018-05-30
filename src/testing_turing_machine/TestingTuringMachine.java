package testing_turing_machine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.List;
import java.util.Set;
import java.util.Map;
 
public class TestingTuringMachine {
    private List<String> tape;
    private String blankSymbol;
    private ListIterator<String> head;
    private Map<StateTapeSymbolPair, Transition> transitions = new HashMap<StateTapeSymbolPair, Transition>();
    private Set<String> terminalStates;
    private String initialState;
 
    public TestingTuringMachine(Set<Transition> transitions, Set<String> terminalStates, String initialState, String blankSymbol) {
        this.blankSymbol = blankSymbol;
        for (Transition t : transitions) {
            this.transitions.put(t.from, t);
        }
        this.terminalStates = terminalStates;
        this.initialState = initialState;
    }
 
    public static class StateTapeSymbolPair {
        private String state;
        private String tapeSymbol;
 
        public StateTapeSymbolPair(String state, String tapeSymbol) {
            this.state = state;
            this.tapeSymbol = tapeSymbol;
        }
 
        // These methods can be auto-generated by Eclipse.
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result
                    + ((state == null) ? 0 : state.hashCode());
            result = prime
                    * result
                    + ((tapeSymbol == null) ? 0 : tapeSymbol
                            .hashCode());
            return result;
        }
 
        // These methods can be auto-generated by Eclipse.
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            StateTapeSymbolPair other = (StateTapeSymbolPair) obj;
            if (state == null) {
                if (other.state != null)
                    return false;
            } else if (!state.equals(other.state))
                return false;
            if (tapeSymbol == null) {
                if (other.tapeSymbol != null)
                    return false;
            } else if (!tapeSymbol.equals(other.tapeSymbol))
                return false;
            return true;
        }
 
        @Override
        public String toString() {
            return "(" + state + "," + tapeSymbol + ")";
        }
    }
 
    public static class Transition {
        private StateTapeSymbolPair from;
        private StateTapeSymbolPair to;
        private int direction; // -1 left, 0 neutral, 1 right.
 
        public Transition(StateTapeSymbolPair from, StateTapeSymbolPair to, int direction) {
             this.from = from;
            this.to = to;
            this.direction = direction;
        }
 
        @Override
        public String toString() {
            return from + "=>" + to + "/" + direction;
        }
    }
 
    public void initializeTape(List<String> input) { // Arbitrary Strings as symbols.
        tape = input;
    }
 
    public void initializeTape(String input) { // Uses single characters as symbols.
        tape = new LinkedList<String>();
        for (int i = 0; i < input.length(); i++) {
            tape.add(input.charAt(i) + "");
        }
    }
 
    public List<String> runTM() { // Returns null if not in terminal state.
        if (tape.size() == 0) {
            tape.add(blankSymbol);
        }
 
        head = tape.listIterator();
        head.next();
        head.previous();
 
        StateTapeSymbolPair tsp = new StateTapeSymbolPair(initialState, tape.get(0));
 
        while (transitions.containsKey(tsp)) { // While a matching transition exists.
            System.out.println(this + " --- " + transitions.get(tsp));
            Transition trans = transitions.get(tsp);
            head.set(trans.to.tapeSymbol); // Write tape symbol.
            tsp.state = trans.to.state; // Change state.
            if (trans.direction == -1) { // Go left.
                if (!head.hasPrevious()) {
                    head.add(blankSymbol); // Extend tape.
                }
                tsp.tapeSymbol = head.previous(); // Memorize tape symbol.
            } else if (trans.direction == 1) { // Go right.
                head.next();
                if (!head.hasNext()) {
                    head.add(blankSymbol); // Extend tape.
                    head.previous();
                }
                tsp.tapeSymbol = head.next(); // Memorize tape symbol.
                head.previous();
            } else {
                tsp.tapeSymbol = trans.to.tapeSymbol;
            }
        }
 
        System.out.println(this + " --- " + tsp);
 
        if (terminalStates.contains(tsp.state)) {
            return tape;
        } else {
            return null;
        }
    }
 
    @Override
    public String toString() {
        try {
        	int headPos = head.previousIndex();
            String s = "[ ";
 
            for (int i = 0; i <= headPos; i++) {
                s += tape.get(i) + " ";
            }
 
            s += "[H] ";
 
            for (int i = headPos + 1; i < tape.size(); i++) {
                s += tape.get(i) + " ";
            }
 
            return s + "]";
        } catch (Exception e) {
            return "";
        }
    }
 
    public static void main(String[] args) {
        // Simple incrementer.
        String init = "q0";
        String blank = "b";
 
        Set<String> term = new HashSet<String>();
        term.add("qf");
 
        Set<Transition> trans = new HashSet<Transition>();
 
        trans.add(new Transition(new StateTapeSymbolPair("q0", "1"), new StateTapeSymbolPair("q0", "1"), 1));
        trans.add(new Transition(new StateTapeSymbolPair("q0", "b"), new StateTapeSymbolPair("qf", "1"), 0));
 
        TestingTuringMachine machine = new TestingTuringMachine(trans, term, init, blank);
        machine.initializeTape("111");
        System.out.println("Output (si): " + machine.runTM() + "\n");
 
        // Busy Beaver (overwrite variables from above).
        init = "a";
 
        term.clear();
        term.add("halt");
 
        blank = "0";
 
        trans.clear();
 
        // Change state from "a" to "b" if "0" is read on tape, write "1" and go to the right. (-1 left, 0 nothing, 1 right.)
        trans.add(new Transition(new StateTapeSymbolPair("a", "0"), new StateTapeSymbolPair("b", "1"), 1));
        trans.add(new Transition(new StateTapeSymbolPair("a", "1"), new StateTapeSymbolPair("c", "1"), -1));
        trans.add(new Transition(new StateTapeSymbolPair("b", "0"), new StateTapeSymbolPair("a", "1"), -1));
        trans.add(new Transition(new StateTapeSymbolPair("b", "1"), new StateTapeSymbolPair("b", "1"), 1));
        trans.add(new Transition(new StateTapeSymbolPair("c", "0"), new StateTapeSymbolPair("b", "1"), -1));
        trans.add(new Transition(new StateTapeSymbolPair("c", "1"), new StateTapeSymbolPair("halt", "1"), 0));
 
        machine = new TestingTuringMachine(trans, term, init, blank);
        machine.initializeTape("");
        System.out.println("Output (bb): " + machine.runTM());
 
        // Sorting test (overwrite variables from above).
        init = "s0";
        blank = "*";
 
        term = new HashSet<String>();
        term.add("see");
 
        trans = new HashSet<Transition>();
 
        trans.add(new Transition(new StateTapeSymbolPair("s0", "a"), new StateTapeSymbolPair("s0", "a"), 1));
        trans.add(new Transition(new StateTapeSymbolPair("s0", "b"), new StateTapeSymbolPair("s1", "B"), 1));
        trans.add(new Transition(new StateTapeSymbolPair("s0", "*"), new StateTapeSymbolPair("se", "*"), -1));
        trans.add(new Transition(new StateTapeSymbolPair("s1", "a"), new StateTapeSymbolPair("s1", "a"), 1));
        trans.add(new Transition(new StateTapeSymbolPair("s1", "b"), new StateTapeSymbolPair("s1", "b"), 1));
        trans.add(new Transition(new StateTapeSymbolPair("s1", "*"), new StateTapeSymbolPair("s2", "*"), -1));
        trans.add(new Transition(new StateTapeSymbolPair("s2", "a"), new StateTapeSymbolPair("s3", "b"), -1));
        trans.add(new Transition(new StateTapeSymbolPair("s2", "b"), new StateTapeSymbolPair("s2", "b"), -1));
        trans.add(new Transition(new StateTapeSymbolPair("s2", "B"), new StateTapeSymbolPair("se", "b"), -1));
        trans.add(new Transition(new StateTapeSymbolPair("s3", "a"), new StateTapeSymbolPair("s3", "a"), -1));
        trans.add(new Transition(new StateTapeSymbolPair("s3", "b"), new StateTapeSymbolPair("s3", "b"), -1));
        trans.add(new Transition(new StateTapeSymbolPair("s3", "B"), new StateTapeSymbolPair("s0", "a"), 1));
        trans.add(new Transition(new StateTapeSymbolPair("se", "a"), new StateTapeSymbolPair("se", "a"), -1));
        trans.add(new Transition(new StateTapeSymbolPair("se", "*"), new StateTapeSymbolPair("see", "*"), 1));
 
        machine = new TestingTuringMachine(trans, term, init, blank);
        machine.initializeTape("babbababaa");
        System.out.println("Output (sort): " + machine.runTM() + "\n");
    }
}
