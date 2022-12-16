package com.mygdx.Pong;

import com.badlogic.gdx.Gdx;
import com.mygdx.Pong.Engine.Math.Vector2;

public class Defaults {
    public static final float MAX_BALL_SPEED = 425f;
    public static final float MAX_BALL_ANGLE = 45f;
    public static final float PLAYER_MOVEMENT_SPEED = 278.5f;
    public static final float COM_PLAYER_SPEED = 1.825f;
    public static final float COM_PLAYER_SPEED_MULTIPLIER = 1f;
    public static final float LEFT_GOAL_X = -2.5f;
    public static final float RIGHT_GOAL_X = Gdx.graphics.getWidth() + 2.5f;
    public static final float UPPER_BOUNDS_Y = Gdx.graphics.getHeight() - 4.2f;
    public static final float LOWER_BOUNDS_Y = 0 + 4.2f;
    public static final Vector2 CENTER_SCREEN = new Vector2(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f);
}