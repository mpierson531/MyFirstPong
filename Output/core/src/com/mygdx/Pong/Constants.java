package com.mygdx.Pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mygdx.Pong.Engine.Math.Vector2;

@JsonIgnoreProperties(value = { "centerScreen" })
public class Constants {
    public static float MAX_BALL_SPEED = 425f;
    public static float MAX_BALL_ANGLE = 45f;
    public static float PLAYER_MOVEMENT_SPEED = 350f;
    public static float COM_PLAYER_SPEED = 1.825f;
    public static float COM_PLAYER_SPEED_MULTIPLIER = 1f;
    public static float LEFT_GOAL_X = -2.5f;
    public static float RIGHT_GOAL_X = Gdx.graphics.getWidth() + 2.5f;
    public static float UPPER_BOUNDS_Y = Gdx.graphics.getHeight() - 4.2f;
    public static float LOWER_BOUNDS_Y = 0 + 4.2f;
    public static Vector2 CENTER_SCREEN = new Vector2(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f);
    public static float VOLUME = 100;
    public static boolean MUTE = false;
    public static Color PADDLE_COLOR = Color.WHITE;
    public static Color BALL_COLOR = Color.WHITE;
    public static Color FONT_COLOR = Color.WHITE;

    public float maxBallSpeed;
    public float maxBallAngle;
    public float playerMovementSpeed;
    public float comPlayerSpeed;
    public float comPlayerSpeedMultiplier;
    public float leftGoalX;
    public float rightGoalX;
    public float upperBoundsY;
    public float lowerBoundsY;
    public Vector2 centerScreen;
    public float volume;
    public boolean mute;
    public Color paddleColor, ballColor, fontColor;

    public Constants() {
        maxBallSpeed = 475f;
        maxBallAngle = 45f;
        playerMovementSpeed = 375f;
        comPlayerSpeed = 1.825f;
        comPlayerSpeedMultiplier = 1f;
        leftGoalX = -2.5f;
        rightGoalX = Gdx.graphics.getWidth() + 2.5f;
        upperBoundsY = Gdx.graphics.getHeight() - 4.2f;
        lowerBoundsY = 0 + 4.2f;
        centerScreen = new Vector2(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f);
        volume = 100;
        MUTE = false;
        paddleColor = Color.WHITE;
        ballColor = Color.WHITE;
        fontColor = Color.WHITE;
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
        PADDLE_COLOR = this.paddleColor;
        BALL_COLOR = this.ballColor;
        FONT_COLOR = this.fontColor;
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

    public void setPaddleColor(Color color) {
        this.paddleColor = color;
    }

    public void setBallColor(Color color) {
        this.ballColor = color;
    }

    public void setFontColor(Color color) {
        this.fontColor = color;
    }
}