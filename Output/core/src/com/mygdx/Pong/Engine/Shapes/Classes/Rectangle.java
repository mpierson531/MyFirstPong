package com.mygdx.Pong.Engine.Shapes.Classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.Pong.Engine.Shapes.Interfaces.Shape;

public class Rectangle extends com.badlogic.gdx.math.Rectangle implements Shape {
    private final float minX, minY, maxX, maxY;
    private float perimeter;
    private final Vector2 min;
    private final Vector2 max;
    private final float centerX;
    private final float centerY;
    private final Vector2 center;

    public Rectangle(float x, float y, float width, float height) {
        super(x, y, width, height);

        this.minX = this.x;
        this.minY = this.y;
        this.min = new Vector2(this.minX, this.minY);

        this.maxX = this.x + this.width;
        this.maxY = this.y + this.height;
        this.max = new Vector2(this.maxX, this.maxY);

        this.centerX = x + width/2;
        this.centerY = y + height/2;
        this.center = new Vector2(this.centerX, this.centerY);
    }
    
    public boolean isTouched() {
        return (Gdx.input.isTouched()) &&
                (Gdx.input.getX() >= this.getX()) && (Gdx.input.getX() <= this.getMaxX())
                && (Gdx.input.getY() <= this.getY() && Gdx.input.getY() >= this.getY() - getHeight());
                /*&& (Gdx.input.getY() >= this.getY()) && (Gdx.input.getY() <= this.getMaxY());*/
    }

    public boolean justTouched() {
        return (Gdx.input.justTouched()) &&
                (Gdx.input.getX() >= this.getX()) && (Gdx.input.getX() <= this.getMaxX())
                && (Gdx.input.getY() <= getY()) && (Gdx.input.getY() >= this.getY() - getHeight());
    }

    public Vector2 getPosition() { return new Vector2(x, y); }

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
}