package com.mygdx.Pong.Shapes.Interfaces;

import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.Pong.Shapes.Classes.Circle;
import com.mygdx.Pong.Shapes.Classes.Rectangle;

import java.io.Serializable;

public interface Shape extends Shape2D, Serializable {
    public float getCenterX();
    public float getCenterY();
    public Vector2 getCenter();
    public boolean isColliding(Circle ball);
    public boolean isColliding(Rectangle player);
}
