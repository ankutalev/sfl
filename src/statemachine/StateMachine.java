package statemachine;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


class StateMachineInputException extends IOException {
    StateMachineInputException(String str) {
        super(str);
    }
}


public class StateMachine {
    private Map<Integer, Map<Character, Integer>> transitions = new HashMap<>();
    private Set<Integer> finishStates = new HashSet<>();
    private InputStream is;

    public StateMachine(String filename) throws StateMachineInputException {
        this.is = System.in;
        try (var is = getClass().getClassLoader().getResourceAsStream(filename); var sc = new Scanner(is)) {
            var statesStrings = sc.nextLine();
            for (String substr : statesStrings.split(" ")) {
                var finishState = Integer.valueOf(substr);
                finishStates.add(finishState);
            }
            while (sc.hasNextInt()) {
                var beginState = sc.nextInt();
                var transition = sc.next();
                if (transition.length() > 1)
                    throw new StateMachineInputException("Found transition which is more than 1 symbol");
                var endState = sc.nextInt();
                addState(beginState, transition.charAt(0), endState);
            }
        } catch (Exception exc) {
            throw new StateMachineInputException("Can't parse finish states file. Assume it exists and matches format and rerun app");
        }
    }


    public StateMachine(String statesFilename, String dataFilename) throws StateMachineInputException {
        this(statesFilename);
        this.is = getClass().getClassLoader().getResourceAsStream(dataFilename);
        if (is == null)
            throw new StateMachineInputException("Can't find data file!");
    }

    private void addState(int beginState, char trans, int endState) {
        if (!transitions.containsKey(beginState)) {
            transitions.put(beginState, new HashMap<>());
        }
        transitions.get(beginState).put(trans, endState);
    }

    public boolean matchString() throws StateMachineInputException {
        int transSymb;
        int currentState = 1;
        try {
            while ((transSymb = is.read()) != -1 && transSymb != '\n') {
                currentState = transitions.get(currentState).get((char) transSymb);
            }
        } catch (IOException exc) {
            throw new StateMachineInputException("Error during input!");
        } catch (NullPointerException exc) {
            throw new StateMachineInputException("Unknown transition or state found!");
        }
        if (finishStates.contains(currentState)) {
            System.out.println("String matches!");
            return true;
        } else {
            System.out.println("String not matches!");
            return false;
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("No states file given! Terminating");
        }
        try {
            StateMachine stateMachine = args.length == 2 ? new StateMachine(args[0], args[1]) : new StateMachine(args[0]);
            stateMachine.matchString();
        } catch (StateMachineInputException exc) {
            System.out.println(exc.getMessage());
        }
    }
}
