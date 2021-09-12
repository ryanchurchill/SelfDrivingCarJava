package org.ryan.selfdrivingcar;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Car {
    // position is the center of the front of the car
    private double positionX;
    private double positionY;
    // angle between y-axis and front of car
    private double angle = 0;

    private final double width = 25;
    private final double height = 10;
    private double rotationIncrement = 45;
    private double movementIncrement = 20;
    private final double sensorDistance = 15;
    private final double sensorRadius = 5;

    // not a JavaFX node
    private Rectangle2D rectCar;

    // note that these circles are JavaFX nodes, but we won't use them as such
    private Circle sensorMiddle;
    private Circle sensorRight;
    private Circle sensorLeft;

    public Car(double positionX, double positionY)
    {
        this.positionX = positionX;
        this.positionY = positionY;
        initialize();
    }

    private void fillOval(GraphicsContext gc, Circle circle)
    {
        gc.fillOval(circle.getCenterX() - circle.getRadius(), circle.getCenterY() - circle.getRadius(), circle.getRadius()*2, circle.getRadius()*2);
    }

    private Point2D rotateVectorClockwise(Point2D vector, double angleDeg)
    {
        // https://jvm-gaming.org/t/2d-vectors-and-rotation/35003
        double angleRad = Math.toRadians(angleDeg) * -1;
        double cosa=Math.cos(angleRad);
        double sina=Math.sin(angleRad);
        double x=vector.getX()*cosa + vector.getY()*sina;
        double y=-vector.getX()*sina + vector.getY()*cosa;

        Point2D ret = new Point2D(x, y);

        System.out.println(vector.angle(ret));

        return ret;
    }

    public void draw(GraphicsContext gc)
    {
        // draw car
        gc.setFill(Color.WHITE);
        gc.fillRect(rectCar.getMinX(), rectCar.getMinY(), rectCar.getWidth(), rectCar.getHeight());

        // draw sensor2
        gc.setFill(Color.RED);
//        gc.fillOval(sensor2.getCenterX() - sensor2.getRadius(), sensor2.getCenterY() - sensor2.getRadius(), sensor2.getRadius()*2, sensor2.getRadius()*2);
        fillOval(gc, sensorMiddle);
//        gc.setFill(Color.YELLOW);
//        fillOval(gc, sensorRight);
//        gc.setFill(Color.BLUE);
//        fillOval(gc, sensorLeft);
//        gc.fillOval()
    }

    private void initialize()
    {
        double x = positionX;
        double y = positionY - height / 2;
        rectCar = new Rectangle2D(x, y, width, height);

        Point2D sensorMiddleVector = new Point2D(-this.sensorDistance, 0);
        sensorMiddleVector = rotateVectorClockwise(sensorMiddleVector, 45);

        sensorMiddle = new Circle(this.positionX + sensorMiddleVector.getX(), this.positionY + sensorMiddleVector.getY(), this.sensorRadius);
//        sensorMiddle = new Circle(this.positionX - sensorDistance, this.positionY, this.sensorRadius);
//        sensorRight = new Circle(this.positionX - (sensorDistance * Math.cos(45)), this.positionY - (sensorDistance * Math.sin(45)), this.sensorRadius);
//        sensorLeft = new Circle(this.positionX - (sensorDistance * Math.cos(45)), this.positionY + (sensorDistance * Math.sin(45)), this.sensorRadius);

        double angle = Math.toRadians(45);
        sensorRight = new Circle(this.positionX - (sensorDistance * Math.cos(angle)), this.positionY - (sensorDistance * Math.sin(angle)), this.sensorRadius);
        sensorLeft = new Circle(this.positionX - (sensorDistance * Math.cos(angle)), this.positionY + (sensorDistance * Math.sin(angle)), this.sensorRadius);

//        updateRectangleBasedOnAngle();
    }

//    private Circle initializeSensor()

//    private void updateRectangleBasedOnAngle()
//    {
//        //TODO: pos is wrong
//        rectCar.setX(positionX);
//        rectCar.setY(positionY + width / 2);
//    }

    private void setAngle(double angle)
    {
        this.angle = angle;
        if (this.angle < 0) {
            this.angle += 360;
        }
        if (this.angle > 360) {
            this.angle -= 360;
        }

//        rectCar.setRotate(angle);
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
