package com.mygdx.Pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.Pong.Engine.Shapes.Classes.Rectangle;

public class Player {
    private final Rectangle playerRect;
    private int score;
    public Player(float x, float y, float width, float height) {
        score = 0;
        playerRect = new Rectangle(x, y, width, height);
    }

    public void wasdArrowMove() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) playerRect.y += Constants.PLAYER_MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) playerRect.y -= Constants.PLAYER_MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.W)) playerRect.y += Constants.PLAYER_MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.S)) playerRect.y -= Constants.PLAYER_MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
    }

    public void wasdMove() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) playerRect.y += Constants.PLAYER_MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.S)) playerRect.y -= Constants.PLAYER_MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
    }

    public void arrowKeyMove() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) playerRect.y += Constants.PLAYER_MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) playerRect.y -= Constants.PLAYER_MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
    }

    public void keepInBounds() {
        if (playerRect.getY() < 10f) playerRect.setY(10f);
        if (playerRect.getY() > 370f) playerRect.setY(370f);
    }

    public Rectangle getRectangle() {
        return this.playerRect;
    }

    public void updateScore(int numToIncrementBy) {
        score += numToIncrementBy;
    }

    public void resetScore() {
        score = 0;
    }

    public int getScore() {
        return this.score;
    }
}