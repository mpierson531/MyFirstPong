package com.mygdx.Pong.Engine.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.Pong.Engine.Math.Vector2;

public class CheckBox extends com.badlogic.gdx.scenes.scene2d.ui.CheckBox {

    public CheckBox(String text, CheckBoxStyle style) {
        super(text, style);
    }

    public CheckBox(String text, Skin skin) {
        super(text, skin);
    }

    public CheckBox(String text, Skin skin, String name) {
        super(text, skin, name);
    }

    public CheckBox(String text, CheckBoxStyle style, float x, float y, float width, float height) {
        super(text, style);
        setSize(width, height); setWidth(width); setHeight(height);
    }

    public CheckBox(String text, CheckBoxStyle style, float x, float y, float width, float height, Color color) {
        super(text, style);
        setSize(width, height);
        setWidth(width); setHeight(height);
        setColor(color);
    }

    public CheckBox(String text, CheckBoxStyle style, Vector2 position, float width, float height) {
        super(text, style);
        setPosition(position.x, position.y);
        setSize(width, height);
    }

    public CheckBox(String text, CheckBoxStyle style, Vector2 position, float width, float height, Color color) {
        super(text, style);
        setPosition(position.x, position.y);
        setSize(width, height);
        setColor(color);
    }

    public void drawLineRectBackground(Artist2D artist2D, Color color) {
        artist2D.drawLineRectangle(getX(), getY(), getWidth(), getHeight(), color);
    }

    public void drawFilledRectBackground(Artist2D artist2D, Color color) {
        artist2D.drawFilledRectangle(getX(), getY(), getWidth(), getHeight(), color);
    }

    public void drawFilledRectOnPress(Artist2D artist2d, Color color) {
        if (this.isPressed()) {
            artist2d.drawFilledRectangle(this, color);
        }
    }

    public void drawLineRectOnPress(Artist2D artist2d, Color color) {
        if (this.isPressed()) {
            artist2d.drawLineRectangle(this, color);
        }
    }
}