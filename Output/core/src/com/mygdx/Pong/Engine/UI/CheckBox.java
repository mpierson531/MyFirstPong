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
        setPosition(x, y);
        setSize(width, height);
    }

    public CheckBox(String text, CheckBoxStyle style, float x, float y, float width, float height, Color color) {
        super(text, style);
        setPosition(x, y);
        setSize(width, height);
        setStyle(style);
        if (color != null)
            setColor(color);
    }

    public CheckBox(String text, CheckBoxStyle style, Vector2 position, float width, float height) {
        super(text, style);
        setPosition(position.x, position.y);
        setSize(width, height);
    }

    public CheckBox(String text, CheckBoxStyle style, Vector2 position, float width, float height, Color color) {
        this(text, style, position.x, position.y, width, height, color);
    }

    public CheckBox(String text, CheckBoxStyle style, Vector2 position, Vector2 size) {
        this(text, style, position, size, null);
    }

    public CheckBox(String text, CheckBoxStyle style, Vector2 position, Vector2 size, Color color) {
        this(text, style, position.x, position.y, size.x, size.y, color);
    }

    public void drawOutline(Artist2D artist2D, Color color) {
        artist2D.drawLineActor(this, color);
    }

    public void drawFilled(Artist2D artist2D, Color color) {
        artist2D.drawFilledActor(getX(), getY(), getWidth(), getHeight(), color);
    }

    public void drawFilledOnPress(Artist2D artist2d, Color color) {
        if (this.isPressed()) {
            artist2d.drawFilledActor(this, color);
        }
    }

    public void drawOutlineOnPress(Artist2D artist2d, Color color) {
        if (this.isPressed()) {
            artist2d.drawLineActor(this, color);
        }
    }
}