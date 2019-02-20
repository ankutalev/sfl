package statemachine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StateMachineTest {
    StateMachine sm;

    @Test
    void executorTest1() {
        assertThrows(StateMachineInputException.class, () -> new StateMachine("statessdf"));
    }

    @Test
    void executorTest2() {
        assertThrows(StateMachineInputException.class, () -> new StateMachine("statemachine/states", "adfssd"));
    }

    @Test
    void executorTest3() {
        try {
            sm = new StateMachine("states", "test1");
            assertTrue(sm.matchString());
        } catch (StateMachineInputException e) {
            e.printStackTrace();
        }
    }

    @Test
    void executorTest4() {
        try {
            sm = new StateMachine("states", "test2");
            assertFalse(sm.matchString());
        } catch (StateMachineInputException e) {
            e.printStackTrace();
        }
    }

    @Test
    void executorTest5() {
        try {
            sm = new StateMachine("states", "test3");
            assertTrue(sm.matchString());
        } catch (StateMachineInputException e) {
            e.printStackTrace();
        }
    }

    @Test
    void executorTest6() {
        try {
            sm = new StateMachine("states", "test4");
            assertFalse(sm.matchString());
        } catch (StateMachineInputException e) {
            e.printStackTrace();
        }
    }

    @Test
    void executorTest7() {
        assertThrows(StateMachineInputException.class, () -> {
            sm = new StateMachine("states", "test5");
            sm.matchString();
        });
    }
    @Test
    void executorTest8() {
        try {
            sm = new StateMachine("states", "test6");
            assertTrue(sm.matchString());
        } catch (StateMachineInputException e) {
            e.printStackTrace();
        }
    }

}