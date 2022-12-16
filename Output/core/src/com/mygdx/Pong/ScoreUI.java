package com.mygdx.Pong;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.Pong.Engine.Math.MathUtils;
import com.mygdx.Pong.Engine.Math.Vector2;
import com.mygdx.Pong.Engine.UI.TextButton;
import com.mygdx.Pong.Screens.StartMenu;

public class ScoreUI {
    FreeTypeFontGenerator fontGenerator;
    FreeTypeFontGenerator.FreeTypeFontParameter fontParameters;
    BitmapFont pixelFont;
    SpriteBatch fontBatch;
    ShapeRenderer shapeRenderer;
    float windowHalfWidth, windowHeight, lineWidth, lineHeight;
    Stage stage;
    TextButton backButton;
    TextButtonStyle backButtonStyle;
    float backButtonX, backButtonY, backButtonWidth, backButtonHeight;
    Vector2 backButtonPos, backButtonSize, screenSize;
    Game game;
    ExtendViewport extendViewport;

    public ScoreUI(final Game game, float screenWidth, float screenHeight, Camera camera) {
        this.game = game;

        extendViewport = new ExtendViewport(screenWidth, screenHeight, camera);
        extendViewport.apply(true);

        fontBatch = new SpriteBatch();
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("PixeloidSans-nR3g1.ttf"));
        fontParameters = new FreeTypeFontGenerator.FreeTypeFontParameter();

        shapeRenderer = new ShapeRenderer();

        screenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        fontParameters.borderWidth = 0;
        fontParameters.color = Color.WHITE;
        fontParameters.mono = true;
        fontParameters.size = 90;

        pixelFont = fontGenerator.generateFont(fontParameters);

        pixelFont.setColor(Color.WHITE);

        windowHalfWidth = screenSize.x/2f;
        windowHeight = screenSize.y;
        lineWidth = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, 6f), screenSize.x);
        lineHeight = MathUtils.toValue(MathUtils.toPercentage(screenSize.y, 12.5f), screenSize.y);

        fontParameters.size = 25;
        backButtonStyle = new TextButtonStyle();
        backButtonStyle.font = fontGenerator.generateFont(fontParameters);
        backButtonStyle.fontColor = Color.WHITE;
        fontGenerator.dispose();

        backButtonX = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, screenSize.x/2f - 370), screenSize.x);
        backButtonY = MathUtils.toValue(MathUtils.toPercentage(screenSize.y, screenSize.y - 45), screenSize.y);
        backButtonPos = new Vector2(backButtonX, backButtonY);
        backButtonWidth = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, 45), screenSize.x);
        backButtonHeight = MathUtils.toValue(MathUtils.toPercentage(screenSize.y, 25), screenSize.y);
        backButtonSize = new Vector2(backButtonWidth, backButtonHeight);

        stage = new Stage(extendViewport);
        backButton = new TextButton("Back", backButtonStyle, backButtonPos, backButtonSize);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.graphics.setWindowedMode(800, 480);
                ScoreUI.this.game.setScreen(new StartMenu(ScoreUI.this.game));
                ScoreUI.this.dispose();
            }
        });
        stage.addActor(backButton);
        Gdx.input.setInputProcessor(stage);
    }

    public void drawScores(int leftScore, int rightScore) {
        fontBatch.setProjectionMatrix(stage.getViewport().getCamera().combined);
        shapeRenderer.setProjectionMatrix(stage.getViewport().getCamera().combined);
        shapeRenderer.setColor(Color.WHITE);

        fontBatch.begin();
        pixelFont.draw(fontBatch, Integer.toString(leftScore), screenSize.x/2f - 120, screenSize.y - 50);
        pixelFont.draw(fontBatch, Integer.toString(rightScore), screenSize.x/2f + 80, screenSize.y - 50);
        fontBatch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < 15; i++) {
            shapeRenderer.rect(windowHalfWidth, windowHeight, lineWidth, lineHeight);
            windowHeight -= 32.5f;
        }
        shapeRenderer.end();

        windowHeight = screenSize.y;
    }

    public void resize(int width, int height) {
        extendViewport.update(width, height, true);
        screenSize = new Vector2(extendViewport.getWorldWidth(), extendViewport.getWorldHeight());
        windowHalfWidth = screenSize.x/2f;
        windowHeight = screenSize.y;
        fontBatch.setProjectionMatrix(stage.getViewport().getCamera().combined);
        shapeRenderer.setProjectionMatrix(stage.getViewport().getCamera().combined);
//        resizeShapeRendererVariables();
    }

    private void resizeShapeRendererVariables() {
        windowHalfWidth = screenSize.x/2f;
        windowHeight = screenSize.y;
    }

    private void resizeBackButton() {
        backButtonX = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, screenSize.x/2f - 370), screenSize.x);
        backButtonY = MathUtils.toValue(MathUtils.toPercentage(screenSize.y, screenSize.y - 45), screenSize.y);
        backButtonPos = new Vector2(backButtonX, backButtonY);
        backButtonSize = new Vector2(backButtonWidth, backButtonHeight);
    }

    public void act(float deltaTime) {
        stage.act(deltaTime);
    }

    public void draw() {
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
        pixelFont.dispose();
        shapeRenderer.dispose();
        fontBatch.dispose();
    }
}