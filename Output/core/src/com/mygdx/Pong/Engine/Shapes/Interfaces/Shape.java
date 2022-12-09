package com.mygdx.Pong.Engine.Shapes.Interfaces;

import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;

public interface Shape extends Shape2D, Serializable, Cloneable {
    float getX();
    float getY();
    float getWidth();
    float getHeight();
    float getCenterX();
    float getCenterY();
    Vector2 getCenter();
}