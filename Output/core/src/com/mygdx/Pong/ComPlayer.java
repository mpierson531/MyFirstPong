package com.mygdx.Pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.Pong.Engine.Shapes.Classes.Rectangle;

public class ComPlayer {
    private final Rectangle playerRect;
    private int score;
    public ComPlayer() {
        score = 0;
        playerRect = new Rectangle(Gdx.graphics.getWidth() - 50, 50, 30f, 100f);
    }

    public void keepInBounds() {
        if (playerRect.getY() < 10f) playerRect.setY(10f);
        if (playerRect.getY() > 370f) playerRect.setY(370f);
    }

    public void move(float basis) {
            playerRect.y += (basis - playerRect.getY()) - 10f;
    }

    public void updateScore() {
        score++;
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