package com.mygdx.Pong.Shapes.Classes;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.Pong.Shapes.Interfaces.Shape;

public class Rectangle extends com.badlogic.gdx.math.Rectangle implements Shape {
    private final float minX, minY, maxX, maxY;

    private final Vector2 min;
    private final Vector2 max;

    private final float centerX, centerY;

    private final Vector2 center;
    private Vector2 closestPointToBall;
    public Vector2 distanceToBall;
    public Rectangle(float x, float y, float width, float height) {
        super(x, y, width, height);

        this.minX = this.x;
        this.minY = this.y;
        this.min = new Vector2(this.minX, this.minY);

        this.maxX = this.x + this.width;
        this.maxY = this.y + this.height;
        this.max = new Vector2(this.maxX, this.maxY);

        this.centerX = this.x + this.width / 2;
        this.centerY = this.y + this.height / 2;
        this.center = new Vector2(this.centerX, this.centerY);
    }

    public float getMinX() {
        return this.x;
    }

    public float getMinY() {
        return this.y;
    }

    public float getMaxX() {
        return this.x + this.width;
    }

    public float getMaxY() {
        return this.y + this.height;
    }

    public Vector2 getMin() {
        return this.min;
    }

    public Vector2 getMax() { return this.max; }

    public float getCenterX() {
        return this.centerX;
    }

    public float getCenterY() {
        return this.centerY;
    }

    public Vector2 getCenter() { return this.center; }

    @Override
    public boolean isColliding(Circle ball) {
        Circle _ball = ball;

        closestPointToBall = new Vector2(_ball.getCenter());

        if (_ball.getCenterX() < this.minX) {
            closestPointToBall.x = this.minX;
        } else if (_ball.getCenterX() > this.maxX) {
            closestPointToBall.x = this.maxX;
        }

        if (_ball.getCenterY() < this.minY) {
            closestPointToBall.y = this.minY;
        } else if (_ball.getCenterY() > this.maxY) {
            closestPointToBall.y = this.maxY;
        }

        distanceToBall = new Vector2(_ball.getCenter().sub(closestPointToBall));

        return distanceToBall.len2() <= _ball.getRadius() * _ball.getRadius();
    }

    @Override
    public boolean isColliding(Rectangle player) {
        return false;
    }
}