package com.mygdx.Pong.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.Pong.Constants;
import com.mygdx.Pong.Engine.Files.FileHandler;
import com.mygdx.Pong.Engine.Json.JsonHandler;
import com.mygdx.Pong.Engine.Math.Vector2;
import com.mygdx.Pong.Engine.UI.Artist2D;
import com.mygdx.Pong.Engine.UI.Label;
import com.mygdx.Pong.Engine.UI.Spinner;
import com.mygdx.Pong.Engine.UI.TextSpinner;
import com.mygdx.Pong.Engine.UI.TextButton;

public class ColorsScreen implements Screen {
    private final Game game;
    private final Artist2D artist2D;
    private final TextButton.TextButtonStyle textButtonStyle;
    private final SpriteBatch spriteBatch;
    private final Constants constants;
    private final FileHandler fileHandler;
    private final JsonHandler jsonHandler;
    private final Color buttonPressColor;
    private final TextButton backButton, fontButton;
    private Spinner paddleR, paddleG, paddleB;
    private Spinner ballR, ballG, ballB;
    private Spinner fontR, fontG, fontB;
    private final Label paddleRLabel, paddleGLabel, paddleBLabel;
    private final Label ballRLabel, ballGLabel, ballBLabel;
    private final Label fontRLabel, fontGLabel, fontBLabel;
    private final Stage stage;
    private final com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle labelStyle;
    private final Vector2 screenSize;

    public ColorsScreen(Game game,
                        Artist2D artist2D,
                        TextButton.TextButtonStyle textButtonStyle,
                        final SpriteBatch spriteBatch,
                        Constants constants,
                        FileHandler fileHandler,
                        JsonHandler jsonHandler,
                        final Color buttonPressColor) {
        this.game = game;
        this.artist2D = artist2D;
        this.textButtonStyle = textButtonStyle;
        this.spriteBatch = spriteBatch;
        this.constants = constants;
        this.fileHandler = fileHandler;
        this.jsonHandler = jsonHandler;
        this.buttonPressColor = buttonPressColor;

        screenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("PixeloidSans-nR3g1.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParams = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParams.color = Constants.FONT_COLOR;
        fontParams.mono = true;
        fontParams.gamma = 1;
        fontParams.size = 30;

        labelStyle = new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle();
        labelStyle.font = fontGenerator.generateFont(fontParams);
        labelStyle.fontColor = Constants.FONT_COLOR;
        fontGenerator.dispose();

        Vector2 backButtonPos = new Vector2(screenSize.x / 2f - 365, screenSize.y - 63);
        Vector2 backButtonSize = new Vector2(100, 47.5f);

        float labelX = screenSize.x/2f - 365;
        float labelHeight = 40;

        paddleRLabel = new Label("Paddle R", labelStyle, labelX, screenSize.y - 180, 80, labelHeight);
        paddleGLabel = new Label("Paddle G", labelStyle, labelX, paddleRLabel.getY() - 60, 80, labelHeight);
        paddleBLabel = new Label("Paddle B", labelStyle, labelX, paddleGLabel.getY() - 60, 80, labelHeight);
        ballRLabel = new Label("Ball R", labelStyle, labelX, paddleBLabel.getY() - 60, 80, labelHeight);
        ballGLabel = new Label("Ball G", labelStyle, labelX, ballRLabel.getY() - 60, 80, labelHeight);
        ballBLabel = new Label("Ball B", labelStyle, labelX, ballGLabel.getY() - 60, 80, labelHeight);
        fontRLabel = new Label("Font R", labelStyle, labelX, ballRLabel.getY() + 50, 80, labelHeight);
        fontGLabel = new Label("Font G", labelStyle, labelX, ballGLabel.getY() + 50, 80, labelHeight);
        fontBLabel = new Label("Font B", labelStyle, labelX, ballBLabel.getY() + 50, 80, labelHeight);
        setupSpinners();
        backButton = new TextButton("Back", textButtonStyle, backButtonPos, backButtonSize);
        fontButton = new TextButton("Font", textButtonStyle, paddleR.getX(), backButton.getY(), backButton.getWidth(), backButton.getHeight());

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (fontR.isVisible()) {
                    ColorsScreen.this.fontButton.setVisible(true);
                    showPaddleBallColors();
                    return;
                }

                updateConstantsValues();
                ColorsScreen.this.constants.setStaticFieldsToInstanceFields();
                ColorsScreen.this.game.setScreen(new SettingsScreen(ColorsScreen.this.game, ColorsScreen.this.artist2D,
                        ColorsScreen.this.textButtonStyle, ColorsScreen.this.spriteBatch, ColorsScreen.this.constants,
                        ColorsScreen.this.fileHandler, ColorsScreen.this.jsonHandler, ColorsScreen.this.buttonPressColor));
                dispose();
            }
        });

        fontButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showFontColors();
                ColorsScreen.this.fontButton.setVisible(false);
            }
        });

        stage = new Stage(new ScreenViewport());
        stage.addActor(backButton);
        stage.addActor(fontButton);
        stage.addActor(paddleRLabel); stage.addActor(paddleGLabel); stage.addActor(paddleBLabel);
        stage.addActor(ballRLabel); stage.addActor(ballGLabel); stage.addActor(ballBLabel);
        stage.addActor(fontRLabel); stage.addActor(fontGLabel); stage.addActor(fontBLabel);
        stage.addActor(paddleR); stage.addActor(paddleG); stage.addActor(paddleB);
        stage.addActor(ballR); stage.addActor(ballG); stage.addActor(ballB);
        stage.addActor(fontR); stage.addActor(fontG); stage.addActor(fontB);
        Gdx.input.setInputProcessor(stage);

        fontRLabel.setVisible(false);
        fontGLabel.setVisible(false);
        fontBLabel.setVisible(false);
        fontR.setVisible(false);
        fontG.setVisible(false);
        fontB.setVisible(false);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        drawStyles();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        float squareColorDisplayWidth = 100;
        float squareColorDisplayHeight = 100;

        // drawing color sample displays
        if (fontR.isVisible()) {
            float fontRValue = (1f/255) * Float.parseFloat(fontR.getLabelValue().toString());
            float fontGValue = (1f/255) * Float.parseFloat(fontG.getLabelValue().toString());
            float fontBValue = (1f/255) * Float.parseFloat(fontB.getLabelValue().toString());
            artist2D.drawFilledActor(screenSize.x / 2f - 50, screenSize.y / 2f,
                    squareColorDisplayWidth, squareColorDisplayHeight, new Color(fontRValue, fontGValue, fontBValue, 1));
        } else {
            float paddleRValue = (1f/255) * Float.parseFloat(paddleR.getLabelValue().toString());
            float paddleGValue = (1f/255) * Float.parseFloat(paddleG.getLabelValue().toString());
            float paddleBValue = (1f/255) * Float.parseFloat(paddleB.getLabelValue().toString());
            float ballRValue = (1f/255) * Float.parseFloat(ballR.getLabelValue().toString());
            float ballGValue = (1f/255) * Float.parseFloat(ballG.getLabelValue().toString());
            float ballBValue = (1f/255) * Float.parseFloat(ballB.getLabelValue().toString());

            artist2D.drawFilledActor(screenSize.x / 2f - 115, backButton.getY() - 65,
                    squareColorDisplayWidth, squareColorDisplayHeight, new Color(paddleRValue, paddleGValue, paddleBValue, 1));
            artist2D.drawFilledActor(screenSize.x / 2f + 70, backButton.getY() - 65,
                    squareColorDisplayWidth, squareColorDisplayHeight, new Color(ballRValue, ballGValue, ballBValue, 1));
        }
    }

    private void drawStyles() {
        float underlineHeight = 2.5f;
        backButton.drawBorderOnHover(artist2D, Color.WHITE);
        backButton.drawFilledRectOnPress(artist2D, buttonPressColor);

        if (!fontR.isVisible()) { // drawing styles for paddle and ball color spinners
            artist2D.drawFilledActor(paddleRLabel.getX(), paddleRLabel.getY(),
                    (paddleR.getX() + paddleR.getWidth()) - paddleRLabel.getX(), underlineHeight, Color.WHITE);
            artist2D.drawFilledActor(paddleGLabel.getX(), paddleGLabel.getY(),
                    (paddleG.getX() + paddleG.getWidth()) - paddleGLabel.getX(), underlineHeight, Color.WHITE);
            artist2D.drawFilledActor(paddleBLabel.getX(), paddleBLabel.getY(),
                    (paddleB.getX() + paddleB.getWidth()) - paddleBLabel.getX(), underlineHeight, Color.WHITE);

            artist2D.drawFilledActor(ballRLabel.getX(), ballRLabel.getY(),
                    (ballR.getX() + ballR.getWidth()) - ballRLabel.getX(), underlineHeight, Color.WHITE);
            artist2D.drawFilledActor(ballGLabel.getX(), ballGLabel.getY(),
                    (ballG.getX() + ballG.getWidth()) - ballGLabel.getX(), underlineHeight, Color.WHITE);
            artist2D.drawFilledActor(ballBLabel.getX(), ballBLabel.getY(),
                    (ballB.getX() + ballB.getWidth()) - ballBLabel.getX(), underlineHeight, Color.WHITE);

            paddleR.drawFilledOnPress(artist2D, buttonPressColor);
            paddleG.drawFilledOnPress(artist2D, buttonPressColor);
            paddleB.drawFilledOnPress(artist2D, buttonPressColor);
            ballR.drawFilledOnPress(artist2D, buttonPressColor);
            ballG.drawFilledOnPress(artist2D, buttonPressColor);
            ballB.drawFilledOnPress(artist2D, buttonPressColor);
            fontButton.drawBorderOnHover(artist2D, Color.WHITE);
            fontButton.drawFilledRectOnPress(artist2D, buttonPressColor);
        } else { // drawing styles for font color spinners
            artist2D.drawFilledActor(fontRLabel.getX(), fontRLabel.getY(),
                    (fontR.getX() + fontR.getWidth()) - fontRLabel.getX(), underlineHeight, Color.WHITE);
            artist2D.drawFilledActor(fontGLabel.getX(), fontGLabel.getY(),
                    (fontG.getX() + fontG.getWidth()) - fontGLabel.getX(), underlineHeight, Color.WHITE);
            artist2D.drawFilledActor(fontBLabel.getX(), fontBLabel.getY(),
                    (fontB.getX() + fontB.getWidth()) - fontBLabel.getX(), underlineHeight, Color.WHITE);
            fontR.drawFilledOnPress(artist2D, buttonPressColor);
            fontG.drawFilledOnPress(artist2D, buttonPressColor);
            fontB.drawFilledOnPress(artist2D, buttonPressColor);
        }
    }

    private void updateConstantsValues() {
        float paddleRValue = (1f/255) * Float.parseFloat(paddleR.getLabelValue().toString());
        float paddleGValue = (1f/255) * Float.parseFloat(paddleG.getLabelValue().toString());
        float paddleBValue = (1f/255) * Float.parseFloat(paddleB.getLabelValue().toString());
        float ballRValue = (1f/255) * Float.parseFloat(ballR.getLabelValue().toString());
        float ballGValue = (1f/255) * Float.parseFloat(ballG.getLabelValue().toString());
        float ballBValue = (1f/255) * Float.parseFloat(ballB.getLabelValue().toString());
        float fontRValue = (1f/255) * Float.parseFloat(fontR.getLabelValue().toString());
        float fontGValue = (1f/255) * Float.parseFloat(fontG.getLabelValue().toString());
        float fontBValue = (1f/255) * Float.parseFloat(fontB.getLabelValue().toString());

        // Need to divide 1 by 255, then multiply by spinner values, to represent RGB values for libGDX's Color constructor
        constants.setPaddleColor(new Color(paddleRValue, paddleGValue, paddleBValue, 1));
        constants.setBallColor(new Color(ballRValue, ballGValue, ballBValue, 1));
        constants.setFontColor(new Color(fontRValue, fontGValue, fontBValue, 1));
        System.out.println(constants.paddleColor.r + ", " + constants.paddleColor.g + ", " + constants.paddleColor.b);

        constants.setStaticFieldsToInstanceFields();

        if (paddleRValue <= (1f/255) * 5 && paddleGValue <= (1f/255) * 5 && paddleBValue <= (1f/255) * 5) {
            constants.setPaddleColor(new Color(1, 1, 1, 1));
            Constants.PADDLE_COLOR = constants.paddleColor;
        }

        if (ballRValue <= (1f/255) * 5 && ballGValue <= (1f/255) * 5 && ballBValue <= (1f/255) * 5) {
            constants.setBallColor(new Color(5, 5, 5, 1));
            Constants.BALL_COLOR = constants.ballColor;
        }

        if (fontRValue <= (1f/255) * 5 && fontGValue <= (1f/255) * 5 && fontBValue <= (1f/255) * 5) {
            constants.setFontColor(new Color(5, 5, 5, 1));
            Constants.FONT_COLOR = constants.fontColor;
        }
    }

    private void showFontColors() {
        fontRLabel.setVisible(true);
        fontGLabel.setVisible(true);
        fontBLabel.setVisible(true);
        fontR.setVisible(true);
        fontG.setVisible(true);
        fontB.setVisible(true);

        paddleRLabel.setVisible(false);
        paddleGLabel.setVisible(false);
        paddleBLabel.setVisible(false);
        ballRLabel.setVisible(false);
        ballGLabel.setVisible(false);
        ballBLabel.setVisible(false);
        paddleR.setVisible(false);
        paddleG.setVisible(false);
        paddleB.setVisible(false);
        ballR.setVisible(false);
        ballG.setVisible(false);
        ballB.setVisible(false);
    }

    private void showPaddleBallColors() {
        paddleRLabel.setVisible(true);
        paddleGLabel.setVisible(true);
        paddleBLabel.setVisible(true);
        ballRLabel.setVisible(true);
        ballGLabel.setVisible(true);
        ballBLabel.setVisible(true);
        paddleR.setVisible(true);
        paddleG.setVisible(true);
        paddleB.setVisible(true);
        ballR.setVisible(true);
        ballG.setVisible(true);
        ballB.setVisible(true);

        fontRLabel.setVisible(false);
        fontGLabel.setVisible(false);
        fontBLabel.setVisible(false);
        fontR.setVisible(false);
        fontG.setVisible(false);
        fontB.setVisible(false);
    }

    private void setupSpinners() {
        float spinnerX = screenSize.x - 115;
        float spinnerWidth = 100f;
        float spinnerHeight = 47.5f;
        float holdDelay = 0.16f;
        float intervalSeconds = 0.0125f;
        float labelFontSize = 30;
        float buttonOffset = 0.85f;

        String paddleRValue = String.valueOf(Constants.PADDLE_COLOR.r * 255);
        System.out.println(Constants.PADDLE_COLOR.r);
        String paddleGValue = String.valueOf(Constants.PADDLE_COLOR.g * 255);
        String paddleBValue = String.valueOf(Constants.PADDLE_COLOR.b * 255);
        String ballRValue = String.valueOf(Constants.BALL_COLOR.r * 255);
        String ballGValue = String.valueOf(Constants.BALL_COLOR.g * 255);
        String ballBValue = String.valueOf(Constants.BALL_COLOR.b * 255);
        String fontRValue = String.valueOf(Constants.FONT_COLOR.r * 255);
        String fontGValue = String.valueOf(Constants.FONT_COLOR.g * 255);
        String fontBValue = String.valueOf(Constants.FONT_COLOR.b * 255);

        paddleR = new TextSpinner("-",
                "+",
                paddleRValue,
                spinnerX, paddleRLabel.getY(),
                spinnerWidth, spinnerHeight, textButtonStyle, labelStyle, Align.center,
                0, 255, 1, buttonOffset, labelFontSize, holdDelay, intervalSeconds);
        paddleG = new TextSpinner("-",
                "+",
                paddleGValue,
                spinnerX, paddleGLabel.getY(),
                spinnerWidth, spinnerHeight, textButtonStyle, labelStyle, Align.center,
                0, 255, 1, buttonOffset, labelFontSize, holdDelay, intervalSeconds);
        paddleB = new TextSpinner("-",
                "+",
                paddleBValue,
                spinnerX, paddleBLabel.getY(),
                spinnerWidth, spinnerHeight, textButtonStyle, labelStyle, Align.center,
                0, 255, 1, buttonOffset, labelFontSize, holdDelay, intervalSeconds);

        ballR = new TextSpinner("-",
                "+",
                ballRValue,
                spinnerX, ballRLabel.getY(),
                spinnerWidth, spinnerHeight, textButtonStyle, labelStyle, Align.center,
                0, 255, 1, buttonOffset, labelFontSize, holdDelay, intervalSeconds);
        ballG = new TextSpinner("-",
                "+",
                ballGValue,
                spinnerX, ballGLabel.getY(),
                spinnerWidth, spinnerHeight, textButtonStyle, labelStyle, Align.center,
                0, 255, 1, buttonOffset, labelFontSize, holdDelay, intervalSeconds);
        ballB = new TextSpinner("-",
                "+",
                ballBValue,
                spinnerX, ballBLabel.getY(),
                spinnerWidth, spinnerHeight, textButtonStyle, labelStyle, Align.center,
                0, 255, 1, buttonOffset, labelFontSize, holdDelay, intervalSeconds);

        fontR = new TextSpinner("-",
                "+",
                fontRValue,
                spinnerX, fontRLabel.getY(),
                spinnerWidth, spinnerHeight, textButtonStyle, labelStyle, Align.center,
                0, 255, 1, buttonOffset, labelFontSize, holdDelay, intervalSeconds);
        fontG = new TextSpinner("-",
                "+",
                fontGValue,
                spinnerX, fontGLabel.getY(),
                spinnerWidth, spinnerHeight, textButtonStyle, labelStyle, Align.center,
                0, 255, 1, buttonOffset, labelFontSize, holdDelay, intervalSeconds);
        fontB = new TextSpinner("-",
                "+",
                fontBValue,
                spinnerX, fontBLabel.getY(),
                spinnerWidth, spinnerHeight, textButtonStyle, labelStyle, Align.center,
                0, 255, 1, buttonOffset, labelFontSize, holdDelay, intervalSeconds);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }
}
