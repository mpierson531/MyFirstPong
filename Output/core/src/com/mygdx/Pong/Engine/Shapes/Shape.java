package com.mygdx.Pong.Engine.Shapes;

import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;

public interface Shape extends Shape2D, Serializable, Cloneable {
    float getX();
    float getY();
    float getWidth();
    float getHeight();

    void setPosition(com.mygdx.Pong.Engine.Math.Vector2 position);

    float getCenterX();
    float getCenterY();
    Vector2 getCenter();
    boolean isTouched();
    boolean justTouched();
}