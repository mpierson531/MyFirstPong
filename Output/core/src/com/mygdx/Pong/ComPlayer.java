package com.mygdx.Pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.Pong.Engine.Shapes.Classes.Circle;
import com.mygdx.Pong.Engine.Shapes.Classes.Rectangle;

public class ComPlayer {
    private final Rectangle playerRect;
    private int score;
    private float distanceToCover;
    private float movementDistance;

    public ComPlayer() {
        score = 0;
        playerRect = new Rectangle(Gdx.graphics.getWidth() - 50, 50, 30f, 100f);
    }

    public void keepInBounds() {
        if (playerRect.getY() < 10f) playerRect.setY(10f);
        if (playerRect.getY() > 370f) playerRect.setY(370f);
    }

    public void move(Circle ballClone, float offset) {
        if (playerRect.getY() != ballClone.getY()) {
            movementDistance = (MathUtils.clamp(distanceToCover - offset,
                    -Constants.COM_PLAYER_SPEED, Constants.COM_PLAYER_SPEED)) * Constants.COM_PLAYER_SPEED_MULTIPLIER;
            playerRect.y += movementDistance;
//            System.out.println(movementDistance);
        }
    }

    public void calculateNewBallPosition(Rectangle otherPlayer, Circle ballClone) {
        if (ballClone.getVelocity().x < 0) {
            do {
                ballClone.x += ballClone.getVelocity().x * 1.05f * Gdx.graphics.getDeltaTime();
                ballClone.y += ballClone.getVelocity().y * Gdx.graphics.getDeltaTime();
            } while (ballClone.getX() > otherPlayer.getMaxX() && ballClone.getY() < Constants.UPPER_BOUNDS_Y && ballClone.getY() > Constants.LOWER_BOUNDS_Y);
        } else if (ballClone.getVelocity().x > 0) {
            do {
                ballClone.x += ballClone.getVelocity().x * 1.05f * Gdx.graphics.getDeltaTime();
                ballClone.y += ballClone.getVelocity().y * Gdx.graphics.getDeltaTime();
            } while (ballClone.getX() < playerRect.getX() && ballClone.getY() < Constants.UPPER_BOUNDS_Y && ballClone.getY() > Constants.LOWER_BOUNDS_Y);
        }

        distanceToCover = ballClone.getY() - playerRect.getY();
    }

    public void updateScore(int numToIncrementBy) {
        score += numToIncrementBy;
    }

    public void resetScore() {
        score = 0;
    }

    public Rectangle getRectangle() {
        return this.playerRect;
    }

    public int getScore() {
        return this.score;
    }
}