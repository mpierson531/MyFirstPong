package com.mygdx.Pong.Engine.Shapes.Classes;

import com.badlogic.gdx.Gdx;
import com.mygdx.Pong.Engine.Math.Vector2;
import com.mygdx.Pong.Engine.Shapes.Interfaces.Shape;

public class Circle extends com.badlogic.gdx.math.Circle implements Shape, Cloneable {
    private float height;
    private Vector2 velocity, position;

    public Circle(float x, float y, float radius, Vector2 velocity) {
        super(x, y, radius);
        this.height = this.radius + this.radius;
        this.velocity = velocity.clone();
        this.position = new Vector2(x, y);
    }

    public Circle(Vector2 position, float radius, Vector2 velocity) {
        this(position.x, position.y, radius, velocity);
    }

    public boolean isTouched() {
        return Gdx.input.isTouched() && isOver();
    }

    public boolean justTouched() {
        return Gdx.input.justTouched() && isOver();
    }

    public boolean isOver() {
        float cursorX = Gdx.input.getX();
        float convertedCursorY = Gdx.graphics.getHeight() - Gdx.input.getY();

        float distance = (cursorX - getCenterX()) * (cursorX - getCenterX())
                + (convertedCursorY - getCenterY()) * (convertedCursorY - getCenterY());

        return distance <= getRadius() * getRadius();
    }

    public Vector2 getVelocity() { return this.velocity; }
    public void setVelocity(float x, float y) { this.velocity.x = x; this.velocity.y = y; }
    public void setVelocity(Vector2 velocity) { this.velocity = velocity; }
    public void setVelocityX(float velocityX) { this.velocity.x = velocityX; }
    public void setVelocityY(float velocityY) { this.velocity.y = velocityY; }
    public float getRadius() { return this.radius; }
    public float getWidth() {return getHeight(); }
    public float getHeight() { return height; }
    public void setHeight(float height) {this.height = height; }

    public float getDiameter() {
        return this.height;
    }

    public void setDiameter(float diameter) {
        this.height = diameter;
    }

    public float getX() {
        return this.x;
    }

    public void setX(float x) {
        super.setX(x);
        this.position.x = x;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float y) {
        this.y = y;
        this.position.y = y;
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public void setPosition(Vector2 position) {
        this.position = position.clone();
    }

    public float getCenterX() {
        return this.x;
    }

    public void setCenterX(float xPosition) {
        this.x = xPosition;
        this.position.x = xPosition;
    }

    public float getCenterY() {
        return this.y;
    }

    public void setCenterY(float yPosition) {
        this.y = yPosition;
        this.position.y = yPosition;
    }

    public Vector2 getCenter() {
        return getPosition();
    }

    public void setCenter(Vector2 center) {
        this.x = center.x;
        this.y = center.y;
        this.position = new Vector2(center.clone());
    }

    @Override
    public Circle clone() {
        try {
            Circle clone = (Circle) super.clone();
            clone.setVelocity(this.velocity.clone());
            return clone;
        } catch (CloneNotSupportedException e) {
            System.out.println(e.getMessage());
            throw new AssertionError();
        }
    }
}