package com.sahana.martianrobots;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class RobotStateTests {

    @Test
    public void getOrientationShouldReturnRobotStateCurrentOrientation() throws Exception {
        //Arrange
        RobotState state = new RobotState(Orientation.E, null);

        //Act
        Orientation o = state.getOrientation();

        //Assert
        Assert.assertEquals(Orientation.E, o);
    }

    @Test
    public void getCurrentPositionShouldReturnRobotCurrentPosition() throws Exception {
        //Arrange
        RobotState state = new RobotState(Orientation.E, new Point(1, 2));

        //Act
        Point p = state.getCurrentPosition();

        //Assert
        Assert.assertEquals(new Point(1, 2), p);
    }

    @Test
    public void getPreviousPositionShouldBeNullAtInitialisation() throws Exception {
        //Arrange
        RobotState state = new RobotState(Orientation.E, new Point(1, 2));

        //Act
        Point p = state.getPreviousPosition();

        //Assert
        Assert.assertEquals(null, p);
    }

    @Test
    public void AddInstructionsAndDequeueNextInstructionShouldAddAndRemoveInstructionsInCorrectOrder() throws Exception {
        //Arrange
        RobotState state = new RobotState(Orientation.E, new Point(1, 2));
        Queue<Instruction> queue = new LinkedList<>();
        queue.add(Instruction.F);
        queue.add(Instruction.L);
        state.addInstructions(queue);

        //Act
        Instruction x = state.dequeueNextInstruction();
        Instruction y = state.dequeueNextInstruction();

        //Assert
        Assert.assertEquals(Instruction.F, x);
        Assert.assertEquals(Instruction.L, y);
    }

    @Test
    public void canExecuteNextInstructionShouldReturnFalseIfInstructionSetIsEmpty() throws Exception {
        //Arrange
        RobotState state = new RobotState(Orientation.E, new Point(1, 2));

        //Act
        boolean canExecuteNextInstruction = state.canExecuteNextInstruction();

        //Assert
        Assert.assertFalse(canExecuteNextInstruction);
    }

    @Test
    public void executeNextInstructionShouldChangeRobotStateCorrectly() throws Exception {
        //Arrange
        RobotState state = new RobotState(Orientation.E, new Point(1, 2));
        Queue<Instruction> queue = new LinkedList<>();
        queue.add(Instruction.F);
        queue.add(Instruction.L);
        state.addInstructions(queue);

        //Act & Assert
        Assert.assertTrue(state.canExecuteNextInstruction());

        state.executeNextInstruction();
        Assert.assertEquals(new Point(2, 2), state.getCurrentPosition());
        Assert.assertEquals(Orientation.E, state.getOrientation());
        Assert.assertTrue(state.canExecuteNextInstruction());

        state.executeNextInstruction();
        Assert.assertEquals(new Point(2, 2), state.getCurrentPosition());
        Assert.assertEquals(Orientation.N, state.getOrientation());
        Assert.assertFalse(state.canExecuteNextInstruction());
    }

    @Test
    public void peekNextInstructionExecutionPositionResultShouldNotAlterRobotState() throws Exception {
        //Arrange
        RobotState state = new RobotState(Orientation.E, new Point(1, 2));
        Queue<Instruction> queue = new LinkedList<>();
        queue.add(Instruction.L);
        queue.add(Instruction.F);
        state.addInstructions(queue);

        //Act & Assert
        Point p = state.peekNextInstructionExecutionPositionResult();
        Assert.assertEquals(new Point(1, 2), p);
        Assert.assertTrue(state.getInstructions().size() == 2);

        state.dequeueNextInstruction();

        p = state.peekNextInstructionExecutionPositionResult();
        Assert.assertEquals(new Point(2, 2), p);
        Assert.assertTrue(state.getInstructions().size() == 1);

        Assert.assertEquals(new Point(1, 2), state.getCurrentPosition());
        Assert.assertEquals(Orientation.E, state.getOrientation());
    }

}