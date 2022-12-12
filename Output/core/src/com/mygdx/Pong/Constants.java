package com.mygdx.Pong;

import com.badlogic.gdx.Gdx;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mygdx.Pong.Engine.Math.Vector2;

@JsonIgnoreProperties(value = { "centerScreen" })
public class Constants {

    // TODO: MAKE SURE TO IMPLEMENT SCREEN SIZE PERCENTAGES AS VALUES FOR EVERYTHING

    public static float MAX_BALL_SPEED = 425f;
    public static float MAX_BALL_ANGLE = 45f;
    public static float PLAYER_MOVEMENT_SPEED = 278.5f;
    public static float COM_PLAYER_SPEED = 1.825f;
    public static float COM_PLAYER_SPEED_MULTIPLIER = 1f;
    public static float LEFT_GOAL_X = -2.5f;
    public static float RIGHT_GOAL_X = Gdx.graphics.getWidth() + 2.5f;
    public static float UPPER_BOUNDS_Y = Gdx.graphics.getHeight() - 4.2f;
    public static float LOWER_BOUNDS_Y = 0 + 4.2f;
    public static Vector2 CENTER_SCREEN = new Vector2(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f);
    public static float VOLUME = 100;
    public static boolean MUTE = false;

    public float maxBallSpeed = 425f;
    public float maxBallAngle = 45f;
    public float playerMovementSpeed = 278.5f;
    public float comPlayerSpeed = 1.825f;
    public float comPlayerSpeedMultiplier = 1f;
    public float leftGoalX = -2.5f;
    public float rightGoalX = Gdx.graphics.getWidth() + 2.5f;
    public float upperBoundsY = Gdx.graphics.getHeight() - 4.2f;
    public float lowerBoundsY = 0 + 4.2f;
    public Vector2 centerScreen = new Vector2(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f);
    public float volume = 100;
    public boolean mute = false;

    public Constants() {
        maxBallSpeed = 425f;
        maxBallAngle = 45f;
        playerMovementSpeed = 278.5f;
        comPlayerSpeed = 1.825f;
        comPlayerSpeedMultiplier = 1f;
        leftGoalX = -2.5f;
        rightGoalX = Gdx.graphics.getWidth() + 2.5f;
        upperBoundsY = Gdx.graphics.getHeight() - 4.2f;
        lowerBoundsY = 0 + 4.2f;
        centerScreen = new Vector2(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f);
        if (centerScreen == null) {
            centerScreen = new Vector2(Constants.CENTER_SCREEN);
        }
        volume = 100;
        MUTE = false;
    }

    public void setStaticFieldsToInstanceFields() {
        MAX_BALL_SPEED = this.maxBallSpeed;
        MAX_BALL_ANGLE = this.maxBallAngle;
        PLAYER_MOVEMENT_SPEED = this.playerMovementSpeed;
        COM_PLAYER_SPEED = this.comPlayerSpeed;
        COM_PLAYER_SPEED_MULTIPLIER = this.comPlayerSpeedMultiplier;
        LEFT_GOAL_X = this.leftGoalX;
        RIGHT_GOAL_X = this.rightGoalX;
        UPPER_BOUNDS_Y = this.upperBoundsY;
        LOWER_BOUNDS_Y = this.lowerBoundsY;
        CENTER_SCREEN = this.centerScreen;
        VOLUME = this.volume;
        MUTE = this.mute;
    }

    public void setMaxBallSpeed(float speed) {
        this.maxBallSpeed = speed;
    }

    public void setMaxBallAngle(float angle) {
        this.maxBallAngle = angle;
    }

    public void setPlayerMovementSpeed(float speed) {
        this.playerMovementSpeed = speed;
    }

    public void setComPlayerSpeed(float speed) {
        comPlayerSpeed = speed;
    }

    public void setComPlayerSpeedMultiplier(float speed) {
        comPlayerSpeedMultiplier = speed;
    }
    public void setVolume(float volume) {
        this.volume = volume;
    }

    public void setMute(boolean mute) {
        this.mute = mute;
    }
}