package com.mygdx.Pong.Engine.Shapes;

import com.badlogic.gdx.Gdx;
import com.mygdx.Pong.Engine.Math.Vector2;

public class Rectangle extends com.badlogic.gdx.math.Rectangle implements Shape {
    private float perimeter;
    private Vector2 position;

    public Rectangle(float x, float y, float width, float height) {
        super(x, y, width, height);
        this.position = new Vector2(x, y);
        this.perimeter = width * 2 + height * 2;
    }

    public Rectangle(Vector2 position, Vector2 size) {
        this(position.x, position.y, size.x, size.y);
    }
    
    public boolean isTouched() {
        return Gdx.input.isTouched() && isOver();
    }

    public boolean justTouched() {
        return Gdx.input.justTouched() && isOver();
    }

    public boolean isOver() {
        return (Gdx.input.getX() >= getX())
                && (Gdx.input.getX() <= getMaxX())
                && (Gdx.graphics.getHeight() - Gdx.input.getY() >= getY())
                && (Gdx.graphics.getHeight() - Gdx.input.getY() <= getY() + getHeight());
    }

    public Rectangle setX(float x) {
        super.setX(x);
        this.position.x = x;
        return null;
    }

    public Rectangle setY(float y) {
        this.y = y;
        this.position.y = y;
        return null;
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public void setPosition(Vector2 position) {
        this.position = position.clone();
        this.x = position.x;
        this.y = position.y;
    }

    public float getMinX() {
        return this.x;
    }

    public float getMinY() {
        return this.y;
    }

    public Vector2 getMin() {
        return getPosition();
    }

    public float getMaxX() {
        return getX() + getWidth();
    }

    public float getMaxY() {
        return getY() + getHeight();
    }

    public Vector2 getMax() {
        return new Vector2(getMaxX(), getMaxY());
    }

    public float getPerimeter() {
        return this.perimeter;
    }

    public float getCenterX() {
        return getX() + (getWidth() / 2);
    }

    public float getCenterY() {
        return getY() + (getHeight() / 2);
    }

    public Vector2 getCenter() {
        return new Vector2(getCenterX(), getCenterY());
    }
}