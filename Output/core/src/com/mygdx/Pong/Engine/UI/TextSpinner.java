package com.mygdx.Pong.Engine.UI;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.mygdx.Pong.Engine.Math.Vector2;

public class TextSpinner extends Spinner {
    public TextSpinner(CharSequence buttonOneText,
                       CharSequence buttonTwoText,
                       CharSequence labelText,
                       Vector2 position,
                       Vector2 size,
                       TextButton.TextButtonStyle buttonStyle,
                       Label.LabelStyle labelStyle,
                       int alignment, float min, float max, float buttonOffset, float labelFontSize) {
        this(buttonOneText, buttonTwoText, labelText, position, size, buttonStyle, labelStyle, alignment, min, max, 1, buttonOffset, labelFontSize);
    }

    public TextSpinner(CharSequence buttonOneText,
                       CharSequence buttonTwoText,
                       CharSequence labelText,
                       Vector2 position,
                       Vector2 size,
                       TextButton.TextButtonStyle buttonStyle,
                       Label.LabelStyle labelStyle,
                       int alignment, float min, float max, float step, float buttonOffset, float labelFontSize) {
        super(new TextButton(buttonOneText, buttonStyle, position.x, position.y, size.x / 3f, size.y),
                new Label(labelText, labelStyle, position.x + (size.x/3f), position.y, size.x / 3f, size.y),
                new TextButton(buttonTwoText, buttonStyle, position.x + (size.x/3f) * 2, position.y, size.x / 3f, size.y), alignment, min,
                max, step, buttonOffset, labelFontSize);
    }

    public void setButtonText(TextButton button, CharSequence buttonText) {
        if (actorBelongs(button))
            button.setText(buttonText.toString());
    }
}