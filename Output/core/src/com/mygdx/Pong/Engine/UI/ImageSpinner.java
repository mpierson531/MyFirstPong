package com.mygdx.Pong.Engine.UI;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class ImageSpinner extends Spinner {

    public ImageSpinner(ImageButton buttonOne, Label label, ImageButton buttonTwo, int alignment, float min, float max, float buttonOffset) {
        this(buttonOne, label, buttonTwo, alignment, min, max, 1, buttonOffset);
    }

    public ImageSpinner(ImageButton buttonOne, Label label, ImageButton buttonTwo, int alignment, float min, float max, float step, float buttonOffset) {
        super(buttonOne, label, buttonTwo, alignment, min, max, step, buttonOffset);
    }

    public Drawable getImage() {
        if (getButtonOne().getBackground() != getButtonTwo().getBackground()) {throw new RuntimeException("'buttonOne' and 'buttonTwo' backgrounds could not rectified with each other.");
        }

        return getButtonOne().getBackground();
    }

    public Drawable getImage(ImageButton button) {
        if (actorBelongs(button)) {
            return button.getBackground();
        } else {
            return null;
        }
    }

    public void setImage(ImageButton button, Image image) {
        if (actorBelongs(button)) {
            button.setBackground(image.getDrawable());
        }
    }

    public void setImage(ImageButton button, Drawable drawable) {
        if (actorBelongs(button)) {
            button.setBackground(drawable);
        }
    }

    public void setImage(Image image) {
        getButtonOne().setBackground(image.getDrawable());
        getButtonTwo().setBackground(image.getDrawable());
    }

    public void setImage(Drawable drawable) {
        getButtonOne().setBackground(drawable);
        getButtonTwo().setBackground(drawable);
    }
}