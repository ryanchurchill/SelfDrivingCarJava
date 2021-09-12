package org.ryan.selfdrivingcar;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

enum SensorType {
    LEFT,
    MIDDLE,
    RIGHT
}

/**
 * Includes Car and sensors - this is both the model, and the draw() method for the view
 */
public class Car {
    // position is the center of the front of the car
    private double positionX;
    private double positionY;
    // angle between y-axis and front of car
    private double angleDeg = 0;

    private final double carWidth = 25;
    private final double carHeight = 10;
    private double rotationIncrementDeg = 45;
    private double movementIncrement = 20;
    private final double sensorDistance = 15;
    private final double sensorRadius = 4;

    // we don't have a rectangle for the car - we rely on position, size, and angle

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

        return ret;
    }

    public void draw(Canvas canvas)
    {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // clear
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // draw car
        drawCar(canvas);

        // draw sensor2
        gc.setFill(Color.RED);
        fillOval(gc, sensorMiddle);
        gc.setFill(Color.YELLOW);
        fillOval(gc, sensorRight);
        gc.setFill(Color.BLUE);
        fillOval(gc, sensorLeft);
    }

    private void drawCar(Canvas canvas)
    {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        // upper left of un-rotated car
        double x = positionX;
        double y = positionY - carHeight / 2;

        // rotation + fill https://stackoverflow.com/questions/42582239/javafx-rotate-rectangle-about-center
        gc.save();
        gc.translate(positionX, positionY); // rotate around center of front of car
        gc.rotate(angleDeg);
        gc.translate(-positionX, -positionY);

        gc.fillRect(x, y, this.carWidth, this.carHeight);

        gc.restore();
    }

    private void initialize()
    {
//        double x = positionX;
//        double y = positionY - carHeight / 2;
//        rectCar = new Rectangle2D(x, y, width, height);
//        rectCar = new Rectangle(x, y, carWidth, carHeight);

        Point2D sensorMiddleCoordinates = getSensorCoordinates(SensorType.MIDDLE);
        sensorMiddle = new Circle(sensorMiddleCoordinates.getX(), sensorMiddleCoordinates.getY(), this.sensorRadius);

        Point2D sensorLeftCoordinates = getSensorCoordinates(SensorType.LEFT);
        sensorLeft = new Circle(sensorLeftCoordinates.getX(), sensorLeftCoordinates.getY(), this.sensorRadius);

        Point2D sensorRightCoordinates = getSensorCoordinates(SensorType.RIGHT);
        sensorRight = new Circle(sensorRightCoordinates.getX(), sensorRightCoordinates.getY(), this.sensorRadius);
    }

    private void updateSensorCoordinates()
    {
        Point2D sensorMiddleCoordinates = getSensorCoordinates(SensorType.MIDDLE);
        sensorMiddle.setCenterX(sensorMiddleCoordinates.getX());
        sensorMiddle.setCenterY(sensorMiddleCoordinates.getY());

        Point2D sensorLeftCoordinates = getSensorCoordinates(SensorType.LEFT);
        sensorLeft.setCenterX(sensorLeftCoordinates.getX());
        sensorLeft.setCenterY(sensorLeftCoordinates.getY());

        Point2D sensorRightCoordinates = getSensorCoordinates(SensorType.RIGHT);
        sensorRight.setCenterX(sensorRightCoordinates.getX());
        sensorRight.setCenterY(sensorRightCoordinates.getY());
    }

    private Point2D getSensorCoordinates(SensorType sensorType)
    {
        Point2D sensorVector = new Point2D(-this.sensorDistance, 0);

        double additionalAngleDeg = 0;
        if (sensorType == SensorType.LEFT) {
            additionalAngleDeg -= 45;
        }
        else if (sensorType == SensorType.RIGHT) {
            additionalAngleDeg += 45;
        }

        sensorVector = rotateVectorClockwise(sensorVector, angleDeg + additionalAngleDeg);

        return new Point2D(this.positionX + sensorVector.getX(), this.positionY + sensorVector.getY());
    }

//    private void updateRectangleBasedOnAngle()
//    {
//        //TODO: pos is wrong
//        rectCar.setX(positionX);
//        rectCar.setY(positionY + width / 2);
//    }

    private void setAngleDeg(double angleDeg)
    {
        this.angleDeg = angleDeg;
        if (this.angleDeg < 0) {
            this.angleDeg += 360;
        }
        if (this.angleDeg > 360) {
            this.angleDeg -= 360;
        }

        this.updateSensorCoordinates();

//        rectCar.setRotate(angle);
    }

    public void rotateLeft()
    {
        this.setAngleDeg(this.angleDeg - this.rotationIncrementDeg);
    }

    public void rotateRight()
    {
        this.setAngleDeg(this.angleDeg + this.rotationIncrementDeg);
    }

    public void moveForward()
    {
        double newX = positionX - movementIncrement * Math.sin(angleDeg);
        double newY = positionY - movementIncrement * Math.cos(angleDeg);
    }
}
