package org.ryan.selfdrivingcar.ai;

import org.ryan.selfdrivingcar.CarAction;

public class RandomCarAI implements CarAI {
    public CarAction getNextAction()
    {
        return CarAction.getRandomAction();
    }
}
