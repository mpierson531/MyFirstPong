package com.mygdx.Pong.Engine.Shapes.Interfaces;

import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.Pong.Engine.Shapes.Classes.Circle;
import com.mygdx.Pong.Engine.Shapes.Classes.Rectangle;

import java.io.Serializable;

public interface Shape extends Shape2D, Serializable {
    public float getX();
    public float getY();

    public float getCenterX();
    public float getCenterY();
    public Vector2 getCenter();
}