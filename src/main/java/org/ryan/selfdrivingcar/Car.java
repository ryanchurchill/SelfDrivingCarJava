package org.ryan.selfdrivingcar;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Car {
    // position is the center of the front of the car
    private double positionX;
    private double positionY;
    // 0-360?
    private double angle;

    public Rectangle getRectangle() {
        return rectCar;
    }

    private Rectangle rectCar;

    private final double width = 50;
    private final double height = 20;
    private double rotationIncrement = 20;
    private double movementIncrement = 20;

    public Car(double positionX, double positionY)
    {
        this.positionX = positionX;
        this.positionY = positionY;
        initializeRectangle();
    }

    private void initializeRectangle()
    {
        rectCar = new Rectangle(width, height);
        rectCar.setFill(Color.WHITE);
        updateRectangleBasedOnAngle();
    }

    private void updateRectangleBasedOnAngle()
    {
        //TODO: pos is wrong
        rectCar.setX(positionX);
        rectCar.setY(positionY + width / 2);
    }

    private void setAngle(double angle)
    {
        this.angle = angle;
        if (this.angle < 0) {
            this.angle += 360;
        }
        if (this.angle > 360) {
            this.angle -= 360;
        }

        rectCar.setRotate(angle);
    }

    public void rotateLeft()
    {
        this.setAngle(this.angle - this.rotationIncrement);
    }

    public void rotateRight()
    {
        this.setAngle(this.angle + this.rotationIncrement);
    }

    public void moveForward()
    {
        double newX = positionX - movementIncrement * Math.sin(angle);
        double newY = positionY - movementIncrement * Math.cos(angle);
    }
}
