package com.mygdx.Pong.Engine.Shapes.Classes;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.Pong.Engine.Shapes.Interfaces.Shape;

public class Circle extends com.badlogic.gdx.math.Circle implements Shape {
    private final float centerX, centerY;
    private final Vector2 center;
    private final float height;

    private Vector2 closestPointToPlayer;
    private Vector2 distanceToPlayer;
    private Vector2 velocity;

    public Circle(float x, float y, float radius, Vector2 velocity) {
        super(x, y, radius);
        this.velocity = velocity;

        centerX = this.x;
        centerY = this.y;
        center = new Vector2(centerX, centerY);
        height = this.radius - this.x;
    }

    public Vector2 getVelocity() { return this.velocity; }
    public void setVelocity(float x, float y) { this.velocity.x = x; this.velocity.y = y; }
    public void setVelocity(Vector2 velocity) { this.velocity = velocity; };
    public void setVelocityX(float velocityX) { this.velocity.x = velocityX; }
    public void setVelocityY(float velocityY) { this.velocity.y = velocityY; }
    public float getRadius() { return this.radius; }
    public float getHeight() { return height; }
    public float getX() { return this.x; }
    public float getY() { return this.y; }
    public Vector2 getPosition() { return new Vector2(x, y); }

    public float getCenterX() {
        return this.x;
    }

    public float getCenterY() {
        return this.y;
    }

    public Vector2 getCenter() {
        return this.center;
    }

    public void setCenterX(float xPosition) { this.x = xPosition; }
    public void setCenterY(float yPosition) { this.y = yPosition; }
    public void setCenter(Vector2 center) { this.x = center.x; this.y = center.y; }
}