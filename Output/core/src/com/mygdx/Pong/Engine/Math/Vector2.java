package com.mygdx.Pong.Engine.Math;

public class Vector2 extends com.badlogic.gdx.math.Vector2 implements Cloneable {

    public Vector2() {

    }

    public Vector2(float x, float y) {
        super(x, y);
    }

    public Vector2(Vector2 vector2) {
        super(vector2);
    }

    @Override
    public Vector2 clone() {
        try {
            return (Vector2) super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println(e.getMessage());
            throw new AssertionError();
        }
    }
}