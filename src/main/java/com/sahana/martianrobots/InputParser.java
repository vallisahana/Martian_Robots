package com.sahana.martianrobots;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class InputParser {
    private final String fileDir;
    private Point gridBounds;
    private Queue<RobotState> robotStates;

    public InputParser(String fileDir) {
        this.fileDir = fileDir;
        this.robotStates = new LinkedList<>();
        this.gridBounds = null;
    }

    public StateMachine generateApplicationState() {

        BufferedReader br = null;
        FileReader fr = null;

        try {
            fr = new FileReader(fileDir);
            br = new BufferedReader(fr);

            String gridBoundsString;

            if ((gridBoundsString = br.readLine()) != null) {
                gridBounds = generateGrid(gridBoundsString);
            }

            String currentLine;

            while ((currentLine = br.readLine()) != null) {
                if(!currentLine.isEmpty()) {
                    String initialRobotState = currentLine;
                    String instructions = br.readLine();
                    RobotState rs = generateRobotState(initialRobotState);
                    rs.addInstructions(generateInstructionQueue(instructions));
                    robotStates.add(rs);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();

                if (fr != null)
                    fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return new StateMachine(robotStates, gridBounds);
    }

    private RobotState generateRobotState(String initialState) {
        String delimiters = " ";
        StringTokenizer tokenizer = new StringTokenizer(initialState, delimiters);

        int xCoordinate = Integer.parseInt(tokenizer.nextToken());
        int yCoordinate = Integer.parseInt(tokenizer.nextToken());
        Point startCoordinates = new Point(xCoordinate, yCoordinate);

        Orientation orientation = Orientation.valueOf(tokenizer.nextToken());

        return new RobotState(orientation, startCoordinates);
    }

    private Queue<Instruction> generateInstructionQueue(String instructions) {

        Queue<Instruction> instructionQueue = new LinkedList<>();

        for (char c : instructions.toCharArray()) {
            Instruction i = Instruction.valueOf(Character.toString(c));
            instructionQueue.add(i);
        }

        return instructionQueue;
    }

    private Point generateGrid(String gridString) {
        String delimiters = " ";
        StringTokenizer tokenizer = new StringTokenizer(gridString, delimiters);

        int xBounds = Integer.parseInt(tokenizer.nextToken());
        int yBounds = Integer.parseInt(tokenizer.nextToken());

        return new Point(xBounds, yBounds);
    }

}
