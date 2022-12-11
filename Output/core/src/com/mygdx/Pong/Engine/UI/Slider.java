package com.mygdx.Pong.Engine.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.mygdx.Pong.Engine.Math.Vector2;
import com.mygdx.Pong.Engine.Shapes.Classes.Circle;
import com.mygdx.Pong.Engine.Shapes.Classes.Rectangle;
import com.mygdx.Pong.Engine.Shapes.Interfaces.Shape;

public class Slider {
    private Shape stepShape;
    private Array<Shape> steps, tempSteps;
    private Array<Boolean> stepsOn;
    private float min, max, stepValue;
    private boolean isVertical;
    private Vector2 position, size;
    private float value;

    public Slider(float min, float max, float stepValue, boolean isVertical, Shape stepShape) {
        this(min, max, stepValue, isVertical, null, null, stepShape, 5);
    }

    public Slider(float min, float max, boolean isVertical) {
        this(min, max, 1, isVertical, null, null, null, 5);
    }

    public Slider(float min, float max, float stepValue, boolean isVertical, Vector2 position, Vector2 size, Shape stepShape, float widthBetweenSteps) {
        this.min = min;
        this.max = max;
        this.stepValue = stepValue;
        this.isVertical = isVertical;
        this.position = position;
        this.size = size;
        this.stepShape = stepShape;
        this.value = 0;

        steps = new Array<>();
        tempSteps = new Array<>();
        stepsOn = new Array<>();

        float increasingXPos = position.x;

        if (stepShape instanceof Rectangle) {
            for (int i = 0; i < max; i++) {
                tempSteps.add(new Rectangle(increasingXPos, stepShape.getY(), stepShape.getWidth(), stepShape.getHeight()));
                stepsOn.add(false);
                steps.add(new Rectangle(0, 0, 0,0));
                increasingXPos += widthBetweenSteps;
            }
        } else if (stepShape instanceof Circle) {
            for (int i = 0; i < max; i++) {
                tempSteps.add(new Circle(increasingXPos, stepShape.getY(), ((Circle) stepShape).getRadius(), null));
                steps.add(new Rectangle(0, 0,0,0));
                stepsOn.add(false);
                increasingXPos += widthBetweenSteps;
            }
        }
    }

    public Slider(float min, float max, float stepValue, boolean isVertical, Vector2 position, Vector2 size, Shape stepShape) {
        this(min, max, stepValue, isVertical, position, size, stepShape, size.x/max);
    }

    public void draw(Artist2D artist2D, Color color) {
        checkForInput(artist2D);
        artist2D.batchDrawFilledRects(steps, color);
        artist2D.batchDrawLineRectangles(tempSteps, color);
        for (int i = 0; i < getMax(); i++) {
            if (stepsOn.get(i)) {
                artist2D.drawFilledRectangle(tempSteps.get(i), color);
            } else {
                artist2D.drawLineRectangle(tempSteps.get(i), color);
            }
        }
    }

    private void checkForInput(Artist2D artist2D) {
        float rectangleIndex;

        for (int i = 0; i < steps.size; i++) {
            if (tempSteps.get(i).justTouched()) {
                rectangleIndex = i;
                System.out.println(rectangleIndex);
                if (rectangleIndex < 0 || rectangleIndex >= getMax() / getStepValue()) {
                    return;
                }

                if (!stepsOn.get((int) rectangleIndex)) {
                    for (int j = 0; j <= rectangleIndex; j++) {
                        stepsOn.set(j, true);
                    }
                } else {
                    // Otherwise, turn it and all rectangles after it off.
                    for (int j = (int) rectangleIndex; j < (getMax() / getStepValue()); j++) {
                        stepsOn.set(j, false);
                    }
                }

                /*for (int j = 0; j < getValue() + 1; j++) {
                    if (j == i) break;
                    setValue(getValue() + getStepValue());
                    steps.set(j, tempSteps.get(j));
                }*/
//                setValue(getValue() + getStepValue());
//                steps.set(i, tempSteps.get(i));
//                tempSteps.set(i, new Rectangle(0,0,0,0));
            } /*else if (steps.get(i).justTouched()) {
                rectangleIndex = (Gdx.input.getX() / (tempSteps.get(i).getWidth() + (getWidth() / max)));
                *//*for (float j = getValue(); j > getMin() - 1; j--) {
                    if (j == i) break;
                    setValue(getValue() - getStepValue());
                    tempSteps.set((int) j, steps.get((int) j));
                }*//*
                for (int j = 0; j < getValue(); j++) {
                    if (j == i) break;
                    setValue(getValue() - getStepValue());
                    tempSteps.set(j, steps.get(j));
                }
//                setValue(getValue() - getStepValue());
//                tempSteps.set(i, steps.get(i));
                steps.set(i, new Rectangle(0,0,0,0));
            }*/
        }




        /*for (int i = 0; i < tempSteps.size; i++) {
            if (!stepsOn.get(i) && tempSteps.get(i).justTouched()) {
                setValue((tempSteps.get(i).getX()/getWidth()) * getMax());
                stepsOn.set(i, true);
                for (int k = 0; k < steps.size; k++) {
                    steps.set(k, new Rectangle(tempSteps.get(k).getX(), steps.get(k).getY(), tempSteps.get(k).getWidth(), tempSteps.get(k).getHeight()));
                    if (k == i) break;
                }
                tempSteps.set(i, new Rectangle(0,0,0,0));
                for (int j = 0; j < stepsOn.size; j++) {
                    stepsOn.set(j, true);
                    if (j == i) break;
                }
            } else if (stepsOn.get(i) && steps.get(i).justTouched()) {
                setValue((steps.get(i).getX()/getWidth()) * getMax());
                stepsOn.set(i, false);
                for (int k = 0; k < steps.size; k++) {
                    tempSteps.set(k, new Rectangle(steps.get(k).getX(), steps.get(k).getY(), steps.get(k).getWidth(), steps.get(k).getHeight()));
                    if (k == i) break;
                }
                steps.set(i, new Rectangle(0,0,0,0));
                for (int j = 0; j < stepsOn.size; j++) {
                    stepsOn.set(j, false);
                    if (j == i) break;
                }
            }
        }*/

        /*for (int i = 0; i < steps.size; i++) {
            if (stepsOn.get(i)) {
                steps.set(i, tempSteps.get(i));
                tempSteps.set(i, new Rectangle(0,0,0,0));
            } else if (!stepsOn.get(i)) {
                tempSteps.set(i, steps.get(i));
                steps.set(i, new Rectangle(0,0,0,0));
            }
        }*/

        /*for (int i = 0; i < getMax(); i++) {
            float currentRectX = (value / getMax()) * getWidth();

            if (steps.get(i).getX() <= currentRectX) {
                artist2D.drawFilledRectangle(steps.get(i), Color.WHITE);
            } else {
                artist2D.drawLineRectangle(steps.get(i), Color.WHITE);
            }
        }*/
    }

    public float getValue() {
        return this.value;
    }

    private void setValue(float value) {
        this.value = value;
    }

    public float getStepValue() {
        return this.stepValue;
    }

    private void setStepValue(float stepValue) {
        this.stepValue = stepValue;
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

    public float getMax() {
        return this.max;
    }
}
