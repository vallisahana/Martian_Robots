package com.sahana.martianrobots;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.util.Queue;


public class InputParserTests {

    @Test
    public void cityParserShouldParseGridBoundsCorrectly() {
        //Arrange
        InputParser parser = new InputParser("src/test/resources/test_input.txt");
        Point expectedGridBounds = new Point(5, 3);
        //Act
        StateMachine stateMachine = parser.generateApplicationState();

        //Assert
        Assert.assertEquals(expectedGridBounds, stateMachine.getGridBounds());
    }

    @Test
    public void cityParserShouldParseInitialRobotStatesCorrectly() {
        //Arrange
        InputParser parser = new InputParser("src/test/resources/test_input.txt");

        //Act
        StateMachine stateMachine = parser.generateApplicationState();

        //Assert
        Assert.assertEquals(stateMachine.getRobotStates().size(), 3);

        RobotState r1 = stateMachine.getRobotStates().poll();
        RobotState r2 = stateMachine.getRobotStates().poll();
        RobotState r3 = stateMachine.getRobotStates().poll();

        Assert.assertEquals(new Point(1, 1), r1.getCurrentPosition());
        Assert.assertEquals(Orientation.E, r1.getOrientation());

        Assert.assertEquals(new Point(3, 2), r2.getCurrentPosition());
        Assert.assertEquals(Orientation.N, r2.getOrientation());

        Assert.assertEquals(new Point(0, 3), r3.getCurrentPosition());
        Assert.assertEquals(Orientation.W, r3.getOrientation());
    }

    @Test
    public void cityParserShouldParseRobotInstructionsCorrectly() {
        //Arrange
        InputParser parser = new InputParser("src/test/resources/test_input.txt");

        //Act
        StateMachine stateMachine = parser.generateApplicationState();

        //Assert
        Queue<Instruction> queue = stateMachine.getRobotStates().poll().getInstructions();

        Assert.assertEquals(Instruction.R, queue.poll());
        Assert.assertEquals(Instruction.F, queue.poll());
        Assert.assertEquals(Instruction.L, queue.poll());
    }

}
