package com.mygdx.Pong.Engine.Shapes.Classes;

import com.mygdx.Pong.Engine.Math.Vector2;
import com.mygdx.Pong.Engine.Shapes.Interfaces.Shape;

public class Circle extends com.badlogic.gdx.math.Circle implements Shape, Cloneable {
    private final Vector2 center;
    private float height;

    private Vector2 closestPointToPlayer;
    private Vector2 distanceToPlayer;
    private Vector2 velocity;

    public Circle(float x, float y, float radius, Vector2 velocity) {
        super(x, y, radius);
        this.velocity = velocity;
        center = new Vector2(this.x, this.y);
        height = this.radius - this.x;
    }

    public Vector2 getVelocity() { return this.velocity; }
    public void setVelocity(float x, float y) { this.velocity.x = x; this.velocity.y = y; }
    public void setVelocity(Vector2 velocity) { this.velocity = velocity; }
    public void setVelocityX(float velocityX) { this.velocity.x = velocityX; }
    public void setVelocityY(float velocityY) { this.velocity.y = velocityY; }
    public float getRadius() { return this.radius; }
    public float getWidth() {return this.getRadius() * 2f;}
    public float getHeight() { return height; }
    public void setHeight(float height) {this.height = height; }
    public float getX() { return this.x; }
    public float getY() { return this.y; }
    public Vector2 getPosition() { return this.center; }

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

    @Override
    public Circle clone() {
        try {
            Circle clone = (Circle) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            clone.setVelocity(this.velocity.clone());
            return clone;
        } catch (CloneNotSupportedException e) {
            System.out.println(e.getMessage());
            throw new AssertionError();
        }
    }
}