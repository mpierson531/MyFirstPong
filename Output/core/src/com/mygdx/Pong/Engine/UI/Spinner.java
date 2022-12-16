package com.mygdx.Pong.Engine.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.mygdx.Pong.Engine.Math.Vector2;

public class Spinner extends WidgetGroup {
    private Button buttonOne;
    private Button buttonTwo;
    private Label label;
    private float x, y, width, height;
    private Vector2 position, size;
    private Actor[] actors;
    private float min, max;
    private float step;
    private float buttonOffset;
    private float labelFontSize;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParams;
    private LabelStyle labelStyle;
    private String labelValue;
    private float labelValueFloat;

    /** Using labelValue = String.format("%.2", args) as a workaround for labelFont/labelLength issue and issue of floating-point precision **/

    public Spinner(Button buttonOne, Label label, Button buttonTwo, int alignment, float min, float max, float step, float buttonOffset, float labelFontSize) {
        this.x = buttonOne.getX();
        this.y = buttonOne.getY();
        this.width = buttonOne.getWidth() + label.getWidth() + buttonTwo.getWidth();
        this.height = buttonOne.getHeight();
        this.position = new Vector2(this.x, this.y);
        this.size = new Vector2(this.width, this.height);
        this.min = min;
        this.max = max;
        this.step = step;
        this.buttonOffset = buttonOffset;
        this.buttonOne = buttonOne;
        this.label = label;
        this.buttonTwo = buttonTwo;
        this.labelFontSize = labelFontSize;
        this.labelStyle = getLabelStyle();
        this.labelValue = String.format("%.2f", Float.parseFloat(getLabelValue().toString()));
        this.labelValueFloat = Float.parseFloat(labelValue);
        this.buttonOne.align(alignment);
        this.label.setAlignment(alignment, alignment);
        this.buttonTwo.align(alignment);
        this.fontParams = new FreeTypeFontGenerator.FreeTypeFontParameter();
        this.fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("PixeloidSans-nR3g1.ttf"));
        addActor(buttonOne);
        addActor(label);
        addActor(buttonTwo);
        this.actors = new Actor[] { buttonOne, label, buttonTwo };
    }

    public Spinner(Button buttonOne, Label label, Button buttonTwo, int alignment, float min, float max, float buttonOffset, float labelFontSize) {
        this(buttonOne, label, buttonTwo, alignment, min, max, 1, buttonOffset, labelFontSize);
    }

    public Spinner(Button buttonOne, Label label, Button buttonTwo, int alignment, float range, float buttonOffset, float labelFontSize) {
        this(buttonOne, label, buttonTwo, alignment, 0, range, 1, buttonOffset, labelFontSize);
    }

    public Spinner() {

    }

    public void increment() {
        increment(this.step);
    }

    public void increment(float step) {
        labelValue = getLabelValue().toString();
        labelValueFloat = Float.parseFloat(labelValue) + step;

        if (labelValueFloat != getMax()) {
            this.label.setText(String.format("%.2f", labelValueFloat));
        }

        if (getLabelValue().toString().length() > labelValue.length()) {
            adjustButtons(true);
        } else if (getLabelValue().toString().length() < labelValue.length()) {
            adjustButtons(false);
        }
    }

    public void decrement() {
        decrement(this.step);
    }

    public void decrement(float step) {
        labelValue = getLabelValue().toString();
        labelValueFloat = Float.parseFloat(labelValue) - step;

        if (labelValueFloat != getMin()) {
            this.label.setText(String.format("%.2f", labelValueFloat));
        }

        if (getLabelValue().toString().length() > labelValue.length()) {
            adjustButtons(true);
        } else if (getLabelValue().toString().length() < labelValue.length()) {
            adjustButtons(false);
        }
    }

    // TODO: Figure out how to move buttons dynamically based on label value

    private void adjustButtons(boolean greaterThan) {
        if (greaterThan) {
            for (int i = 0; i < getLabelValue().toString().length(); i++) {
                buttonOne.setX(buttonOne.getX() - buttonOffset);
                buttonTwo.setX(buttonTwo.getX() + buttonOffset);
            }
            labelFontSize -= 3;
        } else {
            for (int i = 0; i < getLabelValue().toString().length(); i++) {
                buttonOne.setX(buttonOne.getX() + buttonOffset);
                buttonTwo.setX(buttonTwo.getX() - buttonOffset);
            }
            labelFontSize += 3;
        }

        fontParams.size = (int) labelFontSize;
        labelStyle.font = fontGenerator.generateFont(fontParams);
        setLabelStyle(labelStyle);
    }

    public void offsetButtons(boolean outwards, float numberOfOffsets) {
        if (outwards) {
            getButtonOne().setPosition(getButtonOne().getX() - (buttonOffset * numberOfOffsets), getY());
            getButtonTwo().setPosition(getButtonTwo().getX() + (buttonOffset * numberOfOffsets), getY());
        } else {
            getButtonOne().setPosition(getButtonOne().getX() + (buttonOffset * numberOfOffsets), getY());
            getButtonTwo().setPosition(getButtonTwo().getX() - (buttonOffset * numberOfOffsets), getY());
        }
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public void setPosition(Vector2 position, boolean snap) {
        setPosition(position.x, position.y, snap);
    }

    public void setPosition(float x, float y, boolean snap) {
        if (snap) {
            setX(x, true);
            setY(y, true);
        } else {
            setX(x, false);
            setY(y, false);
        }
    }

    /**
     * Snaps 'label' and 'buttonTwo' onto 'buttonOne', horizontally.
     * label's x position is buttonOne's maximum x, and buttonTwo's x position is label's maximum x.
     **/
    public void snapX() {
        label.setX(buttonOne.getX() + buttonOne.getWidth());
        buttonTwo.setX(label.getX() + label.getWidth());
    }

    /**
     * Snaps 'label' and buttonTwo's y position onto the same y position of 'buttonOne'.
     **/
    public void snapY() {
        buttonTwo.setY(this.y);
        label.setY(this.y);
    }

    /**
     * Calls snapX() and snapY()
     **/
    public void snap() {
        snapX();
        snapY();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x, boolean snap) {
        if (snap) {
            this.x = x;
            buttonOne.setX(this.x);
            snapX();
        } else {
            this.x = x;
            buttonOne.setX(this.x);
        }
    }

    public void setY(float y, boolean snap) {
        if (snap) {
            this.y = y;
            buttonOne.setY(this.y);
            snapY();
        } else {
            this.y = y;
            buttonOne.setY(this.y);
        }
    }

    public float getWidth() {
        return buttonOne.getWidth() + label.getWidth() + buttonTwo.getWidth();
    }

    public float getHeight() {
        return this.height;
    }

    public Vector2 getSize() {
        return this.size;
    }

    public void setSize(Vector2 size) {
        buttonOne.setSize(size.x / 3f, size.y);
        label.setSize(size.x / 3f, size.y);
        buttonTwo.setSize(size.x / 3f, size.y);
    }

    public void setSize(float width, float height) {
        buttonOne.setSize(width, height);
        label.setSize(width / 3f, height);
        buttonTwo.setSize(width / 3f, height);
    }

    public float getMin() {
        return this.min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return this.max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getStep() {
        return this.step;
    }

    public void setStep(float step) {
        this.step = step;
    }

    public float getRange() {
        return this.max - this.min;
    }

    public void setRange(float min, float max) {
        this.min = min;
        this.max = max;
    }

    public Button getButtonOne() {
        return this.buttonOne;
    }

    public void setButtonOne(TextButton buttonOne) {
        this.buttonOne = buttonOne;
    }

    public Button getButtonTwo() {
        return this.buttonTwo;
    }

    public void setButtonTwo(TextButton buttonTwo) {
        this.buttonTwo = buttonTwo;
    }

    public Label getLabel() {
        return this.label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public void setStyle(com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle labelStyle, Button.ButtonStyle buttonStyle) {
        setLabelStyle(labelStyle);
        setButtonStyle(buttonStyle);
    }

    public Button.ButtonStyle getButtonStyle() {
        if (buttonOne.getStyle() == buttonTwo.getStyle()) {
            return this.buttonOne.getStyle();
        } else {
            throw new IllegalArgumentException("Button styles in class 'Spinner' were not rectified with each other");
        }
    }

    public Actor[] getActors() {
        return actors;
    }

    public void setButtonStyle(Button.ButtonStyle buttonStyle) {
        buttonOne.setStyle(buttonStyle);
        buttonTwo.setStyle(buttonStyle);
    }

    public LabelStyle getLabelStyle() {
        return this.label.getStyle();
    }

    public void setLabelStyle(LabelStyle style) {
        this.label.setStyle(style);
    }

    public void setAlignment(int alignment) {
        this.buttonOne.align(alignment);
        this.label.setAlignment(alignment);
        this.buttonTwo.align(alignment);
    }

    public CharSequence getLabelValue() {
        return this.label.getText();
    }

    public void setLabelValue(CharSequence labelText) {
        this.label.setText(labelText);
    }

    public boolean addListener(EventListener listener) {
        addButtonListeners(listener);
        addLabelListener(listener);
        return false;
    }

    public void addLabelListener(EventListener listener) {
        if (listener != null)
            label.addListener(listener);
    }

    /** Adds one listener to one button that belongs to an instance or heir of class 'Spinner'. **/
    public void addButtonListener(Button button, EventListener listener) {
        if (actorBelongs(button) && listener != null) {
            button.addListener(listener);
        }
    }

    /** Adds one listener to both buttons. Buttons must belong to an instance or heir of class 'Spinner'.  **/
    public void addButtonListeners(EventListener listener) {
        addButtonListener(buttonOne, listener);
        addButtonListener(buttonTwo, listener);
    }

    public void addButtonListeners(EventListener listenerOne, EventListener listenerTwo) {
        addButtonListener(buttonOne, listenerOne);
        addButtonListener(buttonTwo, listenerTwo);
    }

    /** Adds all listeners in 'listenerArray' to 'button'. **/
    public void addListeners(Actor button, EventListener[] listenerArray) {
        if (actorBelongs(button)) {
            for (EventListener listener : listenerArray) {
                if (listener != null) {
                    button.addListener(listener);
                }
            }
        }
    }

    /**
     * Returns all listeners belonging to both 'buttonOne' and 'buttonTwo', with buttonOne's in index 0 and buttonTwo's in index 1.
     **/
    public EventListener[] getButtonListeners() {
        return new EventListener[] {
                (EventListener) buttonOne.getListeners(), (EventListener) buttonTwo.getListeners()
        };
    }

    /** Returns all listeners belonging to 'button'. Parameter 'button' must belong to an instance or heir of class 'Spinner'. **/
    public EventListener[] getButtonListeners(Button button) {
        if (actorBelongs(button))
            return button.getListeners().toArray();
        else
            return null;
    }

    /** Check to make sure 'button' is owned by an instance or heir of class 'Spinner'. Also checks null status. **/
    public Boolean actorBelongs(Actor actor) {
        if (actor == null) {
            throw new NullPointerException("'button' was null");
        }

        return actor == getButtonOne() || actor == getButtonTwo() || actor == getLabel();
    }

    public void drawOutline(Artist2D artist2D, Color color) {
        artist2D.drawLineActor(this, color);
    }

    public void drawFilled(Artist2D artist2D, Color color) {
        artist2D.drawFilledActor(getX(), getY(), getWidth(), getHeight(), color);
    }

    public void drawFilledOnPress(Artist2D artist2d, Color color) {
        if (getButtonOne().isPressed()) {
            artist2d.drawFilledActor(getButtonOne(), color);
        }

        if (getButtonTwo().isPressed()) {
            artist2d.drawFilledActor(getButtonTwo(), color);
        }
    }

    public void drawLineOnPress(Artist2D artist2d, Color color) {
        if (getButtonOne().isPressed()) {
            artist2d.drawLineActor(getButtonOne(), color);
        }

        if (getButtonTwo().isPressed()) {
            artist2d.drawLineActor(getButtonTwo(), color);
        }
    }
}