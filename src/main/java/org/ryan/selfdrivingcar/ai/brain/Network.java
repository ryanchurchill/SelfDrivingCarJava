package org.ryan.selfdrivingcar.ai.brain;

import org.pytorch.Module;

public class Network{
    int inputSize;
    int numActions;

    public Network(int inputSize, int numActions)
    {
        Module mod = Module.load("demo-model.pt1");
        this.inputSize = inputSize;
        this.numActions = numActions;
    }
}
