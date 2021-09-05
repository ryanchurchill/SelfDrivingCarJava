package org.openjfx.gamedevtut;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {
    private Image image;
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    private double width;
    private double height;


    //<editor-fold desc="setters and getters">
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
        this.setWidth(image.getWidth());
        this.setHeight(image.getHeight());
    }

    public void setImage(String url) {
        this.setImage(new Image(getClass().getResource(url).toString()));
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public void setPosition(double x, double y) {
        this.setPositionX(x);
        this.setPositionY(y);
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public void setVelocity(double velocityX, double velocityY) {
        this.setVelocityX(velocityX);
        this.setVelocityY(velocityY);
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
    //</editor-fold>

    public void addVelocity(double x, double y)
    {
        this.velocityX += x;
        this.velocityY += y;
    }

    public void update(double elapsedTime)
    {
        positionX += velocityX * elapsedTime;
        positionY += velocityY * elapsedTime;
    }

    public void render(GraphicsContext gc)
    {
        gc.drawImage( image, positionX, positionY );
    }

    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(positionX,positionY,width,height);
    }

    public boolean intersects(Sprite s)
    {
        return s.getBoundary().intersects( this.getBoundary() );
    }
}
