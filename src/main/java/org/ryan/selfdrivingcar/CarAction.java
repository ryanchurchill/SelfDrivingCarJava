package org.ryan.selfdrivingcar;

import java.util.Random;

public enum CarAction {
    ROTATE_LEFT,
    ROTATE_RIGHT,
    MOVE_FORWARD;

    public static CarAction getRandomAction() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
