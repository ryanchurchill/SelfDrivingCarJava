package org.ryan.selfdrivingcar;

public class CarAIController {
    public CarAction getNextAction()
    {
        return CarAction.getRandomAction();
    }
}
