package com.mygdx.Pong.Engine.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.mygdx.Pong.Engine.Math.Vector2;
import com.mygdx.Pong.Engine.Shapes.Classes.Circle;
import com.mygdx.Pong.Engine.Shapes.Classes.Rectangle;
import com.mygdx.Pong.Engine.Shapes.Interfaces.Shape;

public class Slider {
    private Array<Shape> steps;
    private Array<Boolean> stepsOn;
    private float min, max, stepValue;
    private boolean isVertical;
    private Vector2 position, size;
    private float value;
    private int numOfSteps;
    private int currentStep;

    public Slider(float min, float max, float stepValue, boolean isVertical, Shape stepShape) {
        this(min, max, stepValue, isVertical, new Vector2(0, 0), new Vector2(0, 0), stepShape, 5);
    }

    public Slider(float min, float max, boolean isVertical) {
        this(min, max, 1, isVertical, new Vector2(0, 0), new Vector2(0, 0), null, 5);
    }

    public Slider(float min, float max, float stepValue, boolean isVertical, Vector2 position, Vector2 size, Shape stepShape, float widthBetweenSteps) {
        this.min = min;
        this.max = max;
        this.stepValue = stepValue;
        this.isVertical = isVertical;
        this.position = position;
        this.size = size;
        this.value = 0;
        this.numOfSteps = (int) (getMax()/getStepValue());
        this.currentStep = 0;

        steps = new Array<>();
        stepsOn = new Array<>();

        float increasingXPos = position.x;

        if (stepShape != null) {
            if (stepShape instanceof Rectangle) {
                for (int i = 0; i < max / stepValue; i++) {
                    steps.add(new Rectangle(increasingXPos, stepShape.getY(), stepShape.getWidth(), stepShape.getHeight()));
                    stepsOn.add(false);
                    increasingXPos += widthBetweenSteps;
                }
            } else if (stepShape instanceof Circle) {
                for (int i = 0; i < max / stepValue; i++) {
                    steps.add(new Circle(increasingXPos, stepShape.getY(), ((Circle) stepShape).getRadius(), null));
                    stepsOn.add(false);
                    increasingXPos += widthBetweenSteps;
                }
            }
        }
    }

    public Slider(float min, float max, float stepValue, boolean isVertical, Vector2 position, Vector2 size, Shape stepShape) {
        this(min, max, stepValue, isVertical, position, size, stepShape, size.x / (max / stepValue));
    }

    public void draw(Artist2D artist2D, Color color) {
        checkForInput();
        artist2D.batchDrawLineRectangles(steps, color);
        for (int i = 0; i < getNumOfSteps(); i++) {
            if (stepsOn.get(i)) {
                artist2D.drawFilledRect(steps.get(i), color);
            } else {
                artist2D.drawLineRect(steps.get(i), color);
            }
        }
    }

    private void checkForInput() {
        System.out.println("Current step: " + getCurrentStep());
        for (int i = 0; i < getNumOfSteps(); i++) {
            if (steps.get(i).justTouched()) {
                if (i >= getNumOfSteps()) {
                    return;
                }

                if (!stepsOn.get(i)) {
                    currentStep = i;
                    for (int j = 0; j <= i; j++) {
                        stepsOn.set(j, true);
                        if (value + getStepValue() > getMax()) {
                            setValue(getMax());
                        } else {
                            setValue(value + getStepValue());
                        }
                    }
                } else {
                    for (int j = i; j < getNumOfSteps(); j++) {
                        stepsOn.set(j, false);
                        setValue(Math.max(getMin(), (int) (value - getStepValue())));
                    }
                }
            }
        }
    }

    public float getValue() {
        return this.value;
    }

    public void setValue(float value) {
        if (value > getMax()) {
            this.value = getMax();
            return;
        } else if (value < getMin()) {
            this.value = getMin();
            return;
        }

        this.value = value;

        /*if ((int) (value + getStepValue()) >= getMax()) {
            this.value = getMax();
        } else if ((int) (value - getStepValue()) <= getMin()) {
            this.value = getMin();
        }*/

        // TODO: Keep trying to fix this
        /*if (value != this.value) {
            this.value = value;
            if (value > this.value) {
                this.value = Math.min(value, getValue() + getStepValue());
            } else if (value <= getValue()) {
                this.value = Math.max(value, getValue() + getStepValue());
            }*/

        /*if ((int) (value / getStepValue()) > getCurrentStep()) {
            for (int i = 0; i < getCurrentStep(); i++) {
                stepsOn.set(i, true);
            }
        } else if ((int) (value / getStepValue()) < getCurrentStep()) {
            for (int i = getCurrentStep(); i < getNumOfSteps(); i++) {
                stepsOn.set(i, false);
            }
        }
        setCurrentStep((int) (value / getStepValue()));*/
    }

    public float getStepValue() {
        return this.stepValue;
    }

    public void setStepValue(float stepValue) {

    }

    public int getCurrentStep() {
        return this.currentStep;
    }

    private void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
    }

    public int getNumOfSteps() {
        return this.numOfSteps;
    }

    public float getX() {
        return getPosition().x;
    }

    public void setX(float x) {
        position.x = x;
    }

    public float getY() {
        return getPosition().y;
    }

    public void setY(float y) {
        position.y = y;
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setPosition(float x, float y) {
        position.x = x;
        position.y = y;
    }

    public float getWidth() {
        return getSize().x;
    }

    public void setWidth(float width) {
        size.x = width;
    }

    public float getHeight() {
        return getSize().y;
    }

    public void setHeight(float height) {
        size.y = height;
    }

    public Vector2 getSize() {
        return this.size;
    }

    public void setSize(Vector2 size) {
        this.size = size;
    }

    public void setSize(float width, float height) {
        size.x = width;
        size.y = height;
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
}
