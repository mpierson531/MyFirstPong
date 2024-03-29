package com.mygdx.Pong.Engine.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.Pong.Engine.Math.Vector2;

public class Label extends com.badlogic.gdx.scenes.scene2d.ui.Label {
    public Label(CharSequence text, Skin skin) {
        super(text, skin);
    }

    public Label(CharSequence text, LabelStyle style) {
        super(text, style);
    }

    public Label(CharSequence text, LabelStyle style, Vector2 position, Vector2 size) {
        super(text, style);
        setPosition(position);
        setSize(size);
    }

    public Label(CharSequence text, LabelStyle style, float x, float y, float width, float height) {
        super(text, style);
        setPosition(x, y);
        setSize(width, height);
    }

    public void setPosition(Vector2 position) {
        super.setPosition(position.x ,position.y);
    }

    public void setSize(Vector2 size) {
        super.setSize(size.x, size.y);
    }

    public void drawOutline(Artist2D artist2D, Color color) {
        artist2D.drawLineActor(this, color);
    }

    public void drawFilled(Artist2D artist2D, Color color) {
        artist2D.drawFilledActor(getX(), getY(), getWidth(), getHeight(), color);
    }
}