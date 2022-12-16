package com.mygdx.Pong.Engine.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.Pong.Engine.Math.Vector2;

public class TextButton extends com.badlogic.gdx.scenes.scene2d.ui.TextButton {
    private Vector2 position, size;

    public TextButton(CharSequence text, Skin skin) {
        super((String) text, skin);
    }

    public TextButton(CharSequence text, TextButtonStyle style, Vector2 position, Vector2 size) {
        this(text, style, position.x, position.y, size.x, size.y);
    }

    public TextButton(CharSequence text, TextButtonStyle style) {
        super((String) text, style);
    }

    public TextButton(CharSequence text, TextButtonStyle style, float x, float y, float width, float height) {
        super((String) text, style);
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        this.position = new Vector2(getX(), getY());
        this.size = new Vector2(getWidth(), getHeight());
        setPosition(position);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();
        batch.begin();
    }

    public void drawBorderOnHover(Artist2D artist2d, Color color) {
        if (this.isOver()) {
            artist2d.drawLineActor(this, color);
        }
    }

    public void drawBorderOnHover(Artist2D artist2D, Vector2 position, float width, float height, Color color) {
        if (this.isOver()) {
            artist2D.drawLineActor(this, color);
        }
    }

    public void drawLineRectBackground(Artist2D artist2D, Color color) {
        artist2D.drawLineActor(this, color);
    }

    public void drawFilledRectBackground(Artist2D artist2D, Color color) {
        artist2D.drawFilledActor(getX(), getY(), getWidth(), getHeight(), color);
    }

    public void drawFilledRectOnPress(Artist2D artist2d, Color color) {
        if (this.isPressed()) {
            artist2d.drawFilledActor(this, color);
        }
    }

    public void drawLineRectOnPress(Artist2D artist2d, Color color) {
        if (this.isPressed()) {
            artist2d.drawLineActor(this, color);
        }
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public Vector2 getSize() {
        return this.size;
    }

    public void setPosition(Vector2 position) {
        super.setPosition(position.x, position.y);
        this.position = new Vector2(getX(), getY());
    }

    public void setPosition(com.badlogic.gdx.math.Vector2 position) {
        super.setPosition(position.x, position.y);
        this.position = new Vector2(getX(), getY());
    }

    public void setSize(Vector2 size) {
        super.setSize(size.x, size.y);
        this.size = new Vector2(getWidth(), getHeight());
    }

    public void setSize(com.badlogic.gdx.math.Vector2 size) {
        super.setSize(size.x, size.y);
        this.size = new Vector2(getWidth(), getHeight());
    }
}