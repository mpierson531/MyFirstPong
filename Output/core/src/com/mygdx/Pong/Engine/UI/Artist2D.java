package com.mygdx.Pong.Engine.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.Pong.Engine.Math.Vector2;
import com.mygdx.Pong.Engine.Shapes.Classes.Circle;
import com.mygdx.Pong.Engine.Shapes.Classes.Rectangle;
import com.mygdx.Pong.Engine.Shapes.Interfaces.Shape;

public class Artist2D {
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;

    public Artist2D(Color color) {
        this.shapeRenderer = new ShapeRenderer();
        this.shapeRenderer.setColor(color);
    }

    public Artist2D(ShapeRenderer shapeRenderer, Color color) {
        this.shapeRenderer = shapeRenderer;
        this.shapeRenderer.setColor(color);
    }

    public Artist2D(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }

    public Artist2D() {
        this.shapeRenderer = new ShapeRenderer();
    }

    public void begin() {
        shapeRenderer.begin();
    }

    public void end() {
        shapeRenderer.end();
    }

    public void drawLineRect(float x, float y, float width, float height, Color color) {
        shapeRenderer.setColor(color);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();
    }

    public void drawLineRect(Shape rectangle, Color color) {
        shapeRenderer.setColor(color);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        shapeRenderer.end();
    }

    /**
     * Draws a line rectangle
     * @param rectangle The rectangle to draw
     * @param color The color to draw the rectangle with
     */
    public void drawLineRect(Rectangle rectangle, Color color) {
        shapeRenderer.setColor(color);

            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.rect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
            shapeRenderer.end();
    }

    /**
     * Draws an outline of a rectangle around an actor.
     * @param actor The actor to draw around
     * @param color The color of the rectangle around {@code actor}
     */
    public void drawLineActor(Actor actor, Color color) {
        shapeRenderer.setColor(color);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
        shapeRenderer.end();
    }

    /**
     * Draws a filled rectangle.
     * @param x x position
     * @param y y position
     * @param width width of the rectangle
     * @param height height of the rectangle
     * @param color color of the rectangle
     */
    public void drawFilledActor(float x, float y, float width, float height, Color color) {
        shapeRenderer.setColor(color);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();
    }

    /**
     * Draws a filled rectangle, taking a {@code Rectangle object}
     * @param rectangle the rectangle to draw
     * @param color Color of {@code rectangle}
     */
    public void drawFilledRect(Rectangle rectangle, Color color) {
        shapeRenderer.setColor(color);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.rect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
            shapeRenderer.end();
    }

    public void drawFilledRect(Shape rectangle, Color color) {
        shapeRenderer.setColor(color);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        shapeRenderer.end();
    }

    /**
     * Draws a filled rectangle of an actor
     * @param actor The {@code Actor} to draw
     * @param color Color of the rectangle drawn
     */
    public void drawFilledActor(Actor actor, Color color) {
        shapeRenderer.setColor(color);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
        shapeRenderer.end();
    }

    public void batchDrawFilledRects(Array<Shape> shapes, Color color) {
        shapeRenderer.setColor(color);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < shapes.size; i++) {
            shapeRenderer.rect(shapes.get(i).getX(),
                    shapes.get(i).getY(),
                    shapes.get(i).getWidth(),
                    shapes.get(i).getHeight());
        }
        shapeRenderer.end();
    }

    public void batchDrawFilledShapes(Array<Shape> shapes, Color color) {
        shapeRenderer.setColor(color);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < shapes.size; i++) {
            if (shapes.get(i) instanceof Rectangle) {
                shapeRenderer.rect(shapes.get(i).getX(),
                        shapes.get(i).getY(),
                        shapes.get(i).getWidth(),
                        shapes.get(i).getHeight());
            } else if (shapes.get(i) instanceof Circle) {
                shapeRenderer.circle(shapes.get(i).getX(), shapes.get(i).getY(), ((Circle) shapes.get(i)).getRadius());
            }
        }
        shapeRenderer.end();
    }

    public void batchDrawFilledActors(Array<Actor> actors, Color color) {
        shapeRenderer.setColor(color);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < actors.size; i++) {
            shapeRenderer.rect(actors.get(i).getX(), actors.get(i).getY(), actors.get(i).getWidth(), actors.get(i).getHeight());
        }
        shapeRenderer.end();
    }

    public void batchDrawFilledActors(Actor[] actors, Color color) {
        shapeRenderer.setColor(color);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < actors.length; i++) {
            shapeRenderer.rect(actors[i].getX(), actors[i].getY(), actors[i].getWidth(), actors[i].getHeight());
        }
        shapeRenderer.end();
    }

    public void batchDrawFilledShapes(Shape[] shapes, Color color) {
        shapeRenderer.setColor(color);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < shapes.length; i++) {
            shapeRenderer.rect(shapes[i].getX(), shapes[i].getY(), shapes[i].getWidth(), shapes[i].getHeight());
        }
        shapeRenderer.end();
    }

    public void batchDrawLineRectangles(Array<Shape> shapes, Color color) {
            shapeRenderer.setColor(color);

            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            for (int i = 0; i < shapes.size; i++) {
                if (shapes.get(i) instanceof Rectangle) {
                    shapeRenderer.rect(shapes.get(i).getX(), shapes.get(i).getY(), shapes.get(i).getWidth(), shapes.get(i).getHeight());
                } else if (shapes.get(i) instanceof Circle) {
                    shapeRenderer.circle(shapes.get(i).getX(), shapes.get(i).getY(), ((Circle) shapes.get(i)).getRadius());
                }
            }
            shapeRenderer.end();
    }

    public void batchDrawLineRectActors(Array<Actor> actors, Color color) {
        shapeRenderer.setColor(color);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0; i < actors.size; i++) {
            shapeRenderer.rect(actors.get(i).getX(), actors.get(i).getY(), actors.get(i).getWidth(), actors.get(i).getHeight());
        }
        shapeRenderer.end();
    }

    public void batchDrawLineRects(Array<Rectangle> shapes, Color color) {
        shapeRenderer.setColor(color);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0; i < shapes.size; i++) {
            shapeRenderer.rect(shapes.get(i).getX(), shapes.get(i).getY(), shapes.get(i).getWidth(), shapes.get(i).getHeight());
        }
        shapeRenderer.end();
    }

    public void batchDrawLineRectangles(Shape[] shapes, Color color) {
        shapeRenderer.setColor(color);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0; i < shapes.length; i++) {
            shapeRenderer.rect(shapes[i].getX(), shapes[i].getY(), shapes[i].getWidth(), shapes[i].getHeight());
        }
        shapeRenderer.end();
    }

    public void batchDrawLineRectangles(Actor[] actors, Color color) {
        shapeRenderer.setColor(color);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0; i < actors.length; i++) {
            shapeRenderer.rect(actors[i].getX(), actors[i].getY(), actors[i].getWidth(), actors[i].getHeight());
        }
        shapeRenderer.end();
    }

    public void batchDrawLineRectangles(Vector2[] vectorArray, float[] widthArray, float[] heightArray, Color color) {
        shapeRenderer.setColor(color);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0; i < vectorArray.length; i++) {
            shapeRenderer.rect(vectorArray[i].x, vectorArray[i].y, widthArray[i], heightArray[i]);
        }
        shapeRenderer.end();
    }

    public void batchDrawLineRectangles(float[] xPositions, float[] yPositions, float[] widthArray, float[] heightArray, Color color) {
        shapeRenderer.setColor(color);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0; i < xPositions.length; i++) {
            shapeRenderer.rect(xPositions[i], yPositions[i], widthArray[i], heightArray[i]);
        }
        shapeRenderer.end();
    }

    public Pixmap getPixmap(int width, int height, Pixmap.Format colorFormat, Color color, String shape, String shapeType) {
        Pixmap pixmap = new Pixmap(width, height, colorFormat);
        pixmap.setColor(color);
        pixmap.setFilter(Pixmap.Filter.BiLinear);

        if ((shape.equalsIgnoreCase("rect") || shape.equalsIgnoreCase("rectangle"))
                && shapeType.equalsIgnoreCase("filled")) {
            pixmap.fillRectangle(0, 0, width, height);
        } else if ((shape.equalsIgnoreCase("rect") || shape.equalsIgnoreCase("rectangle"))
                && shapeType.equalsIgnoreCase("line")) {
            pixmap.drawRectangle(0, 0, width, height);
        } else if (shape.equalsIgnoreCase("circle") && shapeType.equalsIgnoreCase("filled")) {
            pixmap.fillCircle(width / 2, height / 2, width / 2 - 1);
        } else if (shape.equalsIgnoreCase("circle") && shapeType.equalsIgnoreCase("line")) {
            pixmap.drawCircle(width / 2, height / 2, width / 2 - 1);
        }

        return pixmap;
    }

    public TextureRegionDrawable getTextureRegionDrawable(Actor actor, Color color, String shape, String shapeType) {
        Pixmap pixmap = new Pixmap((int) actor.getWidth(), (int) actor.getHeight(), Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.setFilter(Pixmap.Filter.BiLinear);

        if ((shape.equalsIgnoreCase( "rect") || shape.equalsIgnoreCase("rectangle"))
                && shapeType.equalsIgnoreCase("filled")) {
            pixmap.fillRectangle(0, 0, (int) actor.getWidth(), (int) actor.getHeight());
        } else if ((shape.equalsIgnoreCase( "rect") || shape.equalsIgnoreCase("rectangle"))
                && shapeType.equalsIgnoreCase("line")) {
            pixmap.drawRectangle(0, 0, (int) actor.getWidth(), (int) actor.getHeight());
        } else if (shape.equalsIgnoreCase( "circle") && shapeType.equalsIgnoreCase("filled")) {
            pixmap.fillCircle((int) actor.getWidth() / 2, (int) actor.getHeight() / 2, (int) actor.getWidth() / 2);
        } else if (shape.equalsIgnoreCase("circle") && shapeType.equalsIgnoreCase("line")) {
            pixmap.drawCircle((int) actor.getWidth() / 2, (int) actor.getHeight() / 2, (int) actor.getWidth() / 2);
        }

        TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(new Texture(pixmap, true));
        pixmap.dispose();
        return textureRegionDrawable;
    }

    public TextureRegionDrawable getTextureRegionDrawable(Vector2 position, float width, float height, Color color, String shape, String shapeType) {
        Pixmap pixmap = new Pixmap((int) width, (int) height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.setFilter(Pixmap.Filter.BiLinear);

        if ((shape.equalsIgnoreCase( "rect") || shape.equalsIgnoreCase("rectangle"))
                && shapeType.equalsIgnoreCase("filled")) {
            pixmap.fillRectangle(0, 0, (int) width, (int) height);
        } else if ((shape.equalsIgnoreCase( "rect") || shape.equalsIgnoreCase("rectangle"))
                && shapeType.equalsIgnoreCase("line")) {
            pixmap.drawRectangle(0, 0, (int) width, (int) height);
        } else if (shape.equalsIgnoreCase( "circle") && shapeType.equalsIgnoreCase("filled")) {
            pixmap.fillCircle((int) width / 2, (int) height / 2, (int) width / 2);
        } else if (shape.equalsIgnoreCase("circle") && shapeType.equalsIgnoreCase("line")) {
            pixmap.drawCircle((int) width / 2, (int) height / 2, (int) width / 2);
        }

        TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(new Texture(pixmap, true));
        pixmap.dispose();
        return textureRegionDrawable;
    }

    public ShapeRenderer getShapeRenderer() {
        return this.shapeRenderer;
    }

    public void setShapeRenderer(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }

    public SpriteBatch getSpriteBatch() {
        return this.spriteBatch;
    }

    public void setSpriteBatch(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }
}