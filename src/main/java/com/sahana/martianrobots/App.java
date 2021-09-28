package com.sahana.martianrobots;

public class App {

    public static void main(String[] args) {
        String fileDir = args[0];

        InputParser parser = new InputParser(fileDir);

        StateMachine stateMachine = parser.generateApplicationState();

        String output = stateMachine.triggerStateMachine();

        System.out.println(output);
    }
}
