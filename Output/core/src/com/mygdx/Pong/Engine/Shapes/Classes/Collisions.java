package com.mygdx.Pong.Engine.Shapes.Classes;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.Pong.Constants;

public class Collisions {
    private Vector2 distanceToPlayer = new Vector2();
    private Vector2 closestPointToPlayer = new Vector2();

    /** Pong-specific below **/
    public float calculateDeflectionAngle(Rectangle rectangle, Circle circle) {
        float relativeIntersectY = (rectangle.getCenterY()) - (circle.getCenterY());
        float normalIntersectY = relativeIntersectY / (rectangle.getHeight()/2);
        float theta = normalIntersectY * Constants.MAX_BALL_ANGLE;
        return MathUtils.clamp((float) Math.toRadians(theta), -Constants.MAX_BALL_SPEED, Constants.MAX_BALL_SPEED);
    }

    /** Pong-specific below **/
    public float calculateDeflectionAngle(float centerY, Circle circle) {
        float relativeIntersectY = (centerY) - (circle.getCenterY());
        float normalIntersectY = relativeIntersectY / (centerY);
        float theta = normalIntersectY * Constants.MAX_BALL_ANGLE;
        return MathUtils.clamp((float) Math.toRadians(theta), -Constants.MAX_BALL_SPEED, Constants.MAX_BALL_SPEED);
    }

    public boolean isCollidingCircleRect(Rectangle rectangle, Circle circle) {
        closestPointToPlayer = new Vector2(circle.getCenter());

        if (circle.getCenterX() < rectangle.getMinX()) {
            closestPointToPlayer.x = rectangle.getMinX();
        } else if (circle.getCenterX() > rectangle.getMaxX()) {
            closestPointToPlayer.x = rectangle.getMaxX();
        }

        if (circle.getCenterY() < rectangle.getMinY()) {
            closestPointToPlayer.y = rectangle.getMinY();
        } else if (circle.getCenterY() > rectangle.getMaxY()) {
            closestPointToPlayer.y = rectangle.getMaxY();
        }

        distanceToPlayer = new Vector2(circle.getCenter().sub(closestPointToPlayer));

        return distanceToPlayer.len2() <= circle.getRadius() * circle.getRadius();
    }

    public void reset() {
        distanceToPlayer = new Vector2(0,0);
        closestPointToPlayer = new Vector2(0,0);
    }
}