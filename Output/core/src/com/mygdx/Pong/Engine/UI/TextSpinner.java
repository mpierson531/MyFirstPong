package com.mygdx.Pong.Engine.UI;

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
                       int alignment, float min, float max, float step,
                       float buttonOffset, float labelFontSize) {
        super(new TextButton(buttonOneText, buttonStyle, position.x, position.y, size.x / 3f, size.y),
                new Label(labelText, labelStyle, position.x + (size.x/3f), position.y, size.x / 3f, size.y),
                new TextButton(buttonTwoText, buttonStyle, position.x + (size.x/3f) * 2, position.y, size.x / 3f, size.y), alignment, min,
                max, step, buttonOffset, labelFontSize, 0, 0);
    }

    public TextSpinner(CharSequence buttonOneText,
                       CharSequence buttonTwoText,
                       CharSequence labelText,
                       Vector2 position,
                       Vector2 size,
                       TextButton.TextButtonStyle buttonStyle,
                       Label.LabelStyle labelStyle,
                       int alignment, float min, float max, float step,
                       float buttonOffset, float labelFontSize, float holdDelaySeconds, float intervalBetweenIncrements) {
        super(new TextButton(buttonOneText, buttonStyle, position.x, position.y, size.x / 3f, size.y),
                new Label(labelText, labelStyle, position.x + (size.x/3f), position.y, size.x / 3f, size.y),
                new TextButton(buttonTwoText, buttonStyle, position.x + (size.x/3f) * 2, position.y, size.x / 3f, size.y), alignment, min,
                max, step, buttonOffset, labelFontSize, holdDelaySeconds, intervalBetweenIncrements);
    }

    public TextSpinner(CharSequence buttonOneText,
                       CharSequence buttonTwoText,
                       CharSequence labelText,
                       float x, float y,
                       float width, float height,
                       TextButton.TextButtonStyle buttonStyle,
                       Label.LabelStyle labelStyle,
                       int alignment, float min, float max, float step,
                       float buttonOffset, float labelFontSize) {
        this(buttonOneText,
                buttonTwoText,
                labelText,
                new Vector2(x, y),
                new Vector2(width, height),
                buttonStyle,
                labelStyle,
                alignment,
                min,
                max,
                step,
                buttonOffset,
                labelFontSize);
    }

    public TextSpinner(CharSequence buttonOneText,
                       CharSequence buttonTwoText,
                       CharSequence labelText,
                       float x, float y,
                       float width, float height,
                       TextButton.TextButtonStyle buttonStyle,
                       Label.LabelStyle labelStyle,
                       int alignment, float min, float max, float step,
                       float buttonOffset, float labelFontSize, float holdDelaySeconds, float intervalBetweenIncrements) {
        this(buttonOneText,
                buttonTwoText,
                labelText,
                new Vector2(x, y), new Vector2(width, height),
                buttonStyle, labelStyle, alignment,
                min, max, step, buttonOffset,
                labelFontSize, holdDelaySeconds, intervalBetweenIncrements);
    }

    public void setButtonText(TextButton button, CharSequence buttonText) {
        if (actorBelongs(button))
            button.setText(buttonText.toString());
    }
}