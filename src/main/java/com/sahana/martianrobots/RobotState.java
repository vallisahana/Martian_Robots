package com.sahana.martianrobots;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

import static com.sahana.martianrobots.Instruction.*;
import static com.sahana.martianrobots.Orientation.*;

public class RobotState {

    private Queue<Instruction> instructions;
    private Orientation orientation;
    private Point currentPosition;
    private Point previousPosition;
    private boolean isLost;

    public RobotState(Orientation orientation, Point position) {
        this.orientation = orientation;
        this.currentPosition = position;
        this.instructions = new LinkedList<>();
        this.isLost = false;
        this.previousPosition = null;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public Point getCurrentPosition() {
        return currentPosition;
    }

    public Point getPreviousPosition() {
        return previousPosition;
    }

    public void setLostState(boolean isLost) {
        this.isLost = isLost;
    }

    public Queue<Instruction> getInstructions() {
        return instructions;
    }

    public void addInstructions(Queue<Instruction> instructions) {
        this.instructions.addAll(instructions);
    }

    public Instruction dequeueNextInstruction() {
        assert(!instructions.isEmpty());
        return instructions.poll();
    }


    public boolean canExecuteNextInstruction() {
        return (instructions.size() > 0);
    }

    public void executeNextInstruction() {
        assert(!instructions.isEmpty());

        Instruction i = dequeueNextInstruction();

        switch (i) {
            case F:
                previousPosition = currentPosition;
                currentPosition = move(i);
                break;
            default:
                orientation = rotate(i);
        }
    }

    public Point peekNextInstructionExecutionPositionResult() {
        assert(!instructions.isEmpty());
        return move(instructions.peek());
    }

    private Orientation rotate(Instruction i) {
        if(i == R) {
            return rotateClockwise(orientation);
        } else {
            return rotateAntiClockwise(orientation);
        }
    }

    private Point move(Instruction i) {
        int x = currentPosition.x;
        int y = currentPosition.y;

        if(i == F) {
            switch (getOrientation()) {
                case N: return new Point(x, y + 1);
                case S: return new Point(x, y - 1);
                case E: return new Point(x + 1, y);
                case W: return new Point(x - 1, y);
            }
        }
        return currentPosition;
    }

    @Override
    public String toString() {
        if(isLost) {
            return String.format("%d %d %s LOST", previousPosition.x, previousPosition.y, orientation.toString());
        } else {
            return String.format("%d %d %s", currentPosition.x, currentPosition.y, orientation.toString());
        }
    }
}
