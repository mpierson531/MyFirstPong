package com.mygdx.Pong.Engine.Shapes.Classes;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.Pong.Constants;

public class Collisions {
    private Vector2 distanceToPlayer = new Vector2();
    private Vector2 closestPointToPlayer = new Vector2();

    public float calculateDeflectionAngle(Rectangle player, Circle ball) {
        float relativeIntersectY = (player.getCenterY()) - (ball.getCenterY());
        float normalIntersectY = relativeIntersectY / (player.getHeight()/2);
        float theta = normalIntersectY * Constants.MAX_ANGLE;
        return (float) Math.toRadians(theta);
    }

    public float calculateDeflectionAngle(float centerY, Circle ball) {
        float relativeIntersectY = (centerY) - (ball.getCenterY());
        float normalIntersectY = relativeIntersectY / (centerY);
        float theta = normalIntersectY * Constants.MAX_ANGLE;
        return (float) Math.toRadians(theta);
    }

    public boolean isCollidingCircleRect(Rectangle player, Circle ball) {
        closestPointToPlayer = new Vector2(ball.getCenter());

        if (ball.getCenterX() < player.getMinX()) {
            closestPointToPlayer.x = player.getMinX();
        } else if (ball.getCenterX() > player.getMaxX()) {
            closestPointToPlayer.x = player.getMaxX();
        }

        if (ball.getCenterY() < player.getMinY()) {
            closestPointToPlayer.y = player.getMinY();
        } else if (ball.getCenterY() > player.getMaxY()) {
            closestPointToPlayer.y = player.getMaxY();
        }

        distanceToPlayer = new Vector2(ball.getCenter().sub(closestPointToPlayer));

        return distanceToPlayer.len2() <= ball.getRadius() * ball.getRadius();
    }

    public void reset() {
        distanceToPlayer = new Vector2(0,0);
        closestPointToPlayer = new Vector2(0,0);
    }
}