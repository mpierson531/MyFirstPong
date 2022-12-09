package com.mygdx.Pong.Engine.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.Pong.Engine.Math.Vector2;
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

    public void drawLineRectangle(float x, float y, float width, float height, Color color) {
        shapeRenderer.setColor(color);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();
    }

    public void drawLineRectangle(Actor actor, Color color) {
        shapeRenderer.setColor(color);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
        shapeRenderer.end();
    }

    public void drawFilledRectangle(float x, float y, float width, float height, Color color) {
        shapeRenderer.setColor(color);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();
    }

    public void drawFilledRectangle(Actor actor, Color color) {
        shapeRenderer.setColor(color);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
        shapeRenderer.end();
    }

    public void batchDrawFilledRects(Actor[] actors, Color color) {
        shapeRenderer.setColor(color);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Actor actor : actors) {
            shapeRenderer.rect(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
        }
        shapeRenderer.end();
    }

    public void batchDrawFilledRects(Shape[] shapes, Color color) {
        shapeRenderer.setColor(color);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Shape shape : shapes) {
            shapeRenderer.rect(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
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

    public TextureRegionDrawable getFilledRectDrawable(Actor actor, Color color) {
        Pixmap pixmap = new Pixmap((int) actor.getWidth(), (int) actor.getHeight(), Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillRectangle((int) actor.getX(), (int) actor.getY(), (int) actor.getWidth(), (int) actor.getHeight());
        return new TextureRegionDrawable(new Texture(pixmap));
    }

    public TextureRegionDrawable getFilledRectDrawable(Vector2 position, float width, float height, Color color) {
        Pixmap pixmap = new Pixmap((int) position.x, (int) position.y, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillRectangle((int) position.x, (int) position.y, (int) width, (int) height);
        return new TextureRegionDrawable(new Texture(pixmap));
    }

    public TextureRegionDrawable getFilledRectDrawable(Vector2 position, Vector2 size, Color color) {
        Pixmap pixmap = new Pixmap((int) position.x, (int) position.y, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillRectangle((int) position.x, (int) position.y, (int) size.x, (int) size.y);
        return new TextureRegionDrawable(new Texture(pixmap));
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