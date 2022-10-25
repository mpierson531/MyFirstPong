package com.mygdx.Pong.Shapes.Classes;

import com.badlogic.gdx.math.Vector2;

public class Utils {
    private static Vector2 distanceToPlayer = new Vector2();
    private static Vector2 closestPointToPlayer = new Vector2();
    
    public static boolean isColliding(Rectangle player, Circle ball) {
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
}
