package com.mygdx.Pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.mygdx.Pong.Engine.Shapes.Classes.Rectangle;

public class ScoreUI {
    FreeTypeFontGenerator fontGenerator;
    FreeTypeFontGenerator.FreeTypeFontParameter fontParameters;
    BitmapFont pixelFont;
    SpriteBatch fontBatch;
    ShapeRenderer shapeRenderer;
    float windowHalfWidth;
    float windowHeight;
    float lineWidth;
    float lineHeight;

    public ScoreUI() {
        fontBatch = new SpriteBatch();
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.absolute("C:\\Users\\Micah\\MyFirstPong\\Output\\assets\\PixeloidSans-nR3g1.ttf"));
        fontParameters = new FreeTypeFontGenerator.FreeTypeFontParameter();

        shapeRenderer = new ShapeRenderer();

        fontParameters.borderWidth = 0;
        fontParameters.color = Color.WHITE;
        fontParameters.mono = true;
        fontParameters.size = 90;

        pixelFont = fontGenerator.generateFont(fontParameters);
        fontGenerator.dispose();

        pixelFont.setColor(Color.WHITE);

        windowHalfWidth = Gdx.graphics.getWidth()/2f;
        windowHeight = Gdx.graphics.getHeight();
        lineWidth = 6f;
        lineHeight = 12.5f;
    }

    public void drawScores(int leftScore, int rightScore, Camera camera) {
        windowHeight = Gdx.graphics.getHeight();
        fontBatch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.setColor(Color.WHITE);

        fontBatch.begin();
        pixelFont.draw(fontBatch, Integer.toString(leftScore), Gdx.graphics.getWidth()/2f - 120, Gdx.graphics.getHeight() - 50);
        pixelFont.draw(fontBatch, Integer.toString(rightScore), Gdx.graphics.getWidth()/2f + 80, Gdx.graphics.getHeight() - 50);
        fontBatch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < 15; i++) {
            shapeRenderer.rect(windowHalfWidth, windowHeight, lineWidth, lineHeight);
            windowHeight -= 32.5f;
        }
        shapeRenderer.end();
    }
}