package com.mygdx.Pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.Pong.Engine.Math.Vector2;

public class Defaults {
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
}