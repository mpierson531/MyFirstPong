package com.mygdx.Pong.Shapes.Classes;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.Pong.Shapes.Interfaces.Shape;

public class Circle extends com.badlogic.gdx.math.Circle implements Shape {
    private final float centerX, centerY;

    private final Vector2 center;

    private Vector2 closestPointToPlayer;
    private Vector2 distanceToPlayer;

    public Circle(float x, float y, float radius) {
        super(x, y, radius);
        centerX = this.x;
        centerY = this.y;
        center = new Vector2(centerX, centerY);
    }

    public float getRadius() { return this.radius; }

    public float getCenterX() {
        return this.x;
    }

    public float getCenterY() {
        return this.y;
    }

    public Vector2 getCenter() {
        return this.center;
    }

    @Override
    public boolean isColliding(Rectangle player) {
        Rectangle _player = player;

        closestPointToPlayer = new Vector2(center);

        if (centerX < _player.getMinX()) {
            closestPointToPlayer.x = _player.getMinX();
        } else if (centerX > player.getMaxX()) {
            closestPointToPlayer.x = _player.getMaxX();
        }

        if (centerY < _player.getMinY()) {
            closestPointToPlayer.y = _player.getMinY();
        } else if (centerY > _player.getMaxY()) {
            closestPointToPlayer.y = _player.getMaxY();
        }

        distanceToPlayer = new Vector2(center.sub(closestPointToPlayer));

        return distanceToPlayer.len2() <= this.radius * this.radius;
    }

    @Override
    public boolean isColliding(Circle ball) {
        return false;
    }
}
