package com.mygdx.Pong.Engine.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.Pong.Engine.Math.Vector2;

public class ImageButton extends com.badlogic.gdx.scenes.scene2d.ui.ImageButton {
    public ImageButton(Skin skin) {
        super(skin);
    }

    public ImageButton(Image backgroundImage, Vector2 position, Vector2 size, ImageButtonStyle style) {
        super(style);
        setPosition(position.x, position.y);
        setSize(size.x, size.y);
        setBackground(backgroundImage.getDrawable());
        super.setBackground(backgroundImage.getDrawable());
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

    public void drawLineOnPress(Artist2D artist2d, Color color) {
        if (this.isPressed()) {
            artist2d.drawLineActor(this, color);
        }
    }
}