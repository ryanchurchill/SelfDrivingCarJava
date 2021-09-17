package org.ryan.selfdrivingcar.ai;

import javafx.animation.AnimationTimer;
import org.ryan.selfdrivingcar.Car;
import org.ryan.selfdrivingcar.CarAction;

public class CarAILoop extends AnimationTimer {
    CarAI carAI;
    Car car;

    public CarAILoop(CarAI carAI, Car car) {
        this.carAI = carAI;
        this.car = car;
    }

    public void handle(long currentNanoTime)
    {
        CarAction action = carAI.getNextAction();
        if (action == CarAction.ROTATE_RIGHT) {
            car.rotateRight();
        }
        else if (action == CarAction.ROTATE_LEFT) {
            car.rotateLeft();
        }
        else if (action == CarAction.MOVE_FORWARD) {
            car.moveForward();
        }
        car.draw();
    }
}
