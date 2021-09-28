package com.sahana.martianrobots;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class StateMachineTests {

    @Test
    public void triggerStateMachineShouldExecuteInstructionsInCorrectOrder() {
        //Arrange
        RobotState state = new RobotState(Orientation.E, new Point(1, 1));
        Queue<Instruction> instructionQueue = new LinkedList<>();
        instructionQueue.add(Instruction.R);
        instructionQueue.add(Instruction.F);
        instructionQueue.add(Instruction.L);
        state.addInstructions(instructionQueue);

        RobotState state2 = new RobotState(Orientation.N, new Point(3, 2));
        Queue<Instruction> instructionQueue2 = new LinkedList<>();
        instructionQueue2.add(Instruction.F);
        instructionQueue2.add(Instruction.R);
        state2.addInstructions(instructionQueue2);

        Queue<RobotState> robotStates = new LinkedList<>();
        robotStates.add(state);
        robotStates.add(state2);

        StateMachine stateMachine = new StateMachine(robotStates, new Point(5, 3));

        String expectedOutput = "1 0 E\n3 3 E\n";

        //Act
        String output = stateMachine.triggerStateMachine();

        //Assert
        Assert.assertEquals(expectedOutput, output);
    }

}
