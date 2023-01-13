package com.mygdx.Pong.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.Pong.Constants;
import com.mygdx.Pong.Defaults;
import com.mygdx.Pong.Engine.Files.FileHandler;
import com.mygdx.Pong.Engine.Json.JsonHandler;
import com.mygdx.Pong.Engine.Math.MathUtils;
import com.mygdx.Pong.Engine.Math.Vector2;
import com.mygdx.Pong.Engine.UI.Artist2D;
import com.mygdx.Pong.Engine.UI.Label;
import com.mygdx.Pong.Engine.UI.TextButton;
import com.mygdx.Pong.Engine.UI.TextSpinner;

public class DifficultyScreen implements Screen {
    private final Game game;
    private final SpriteBatch spriteBatch;
    private final Stage stage;
    private final Label.LabelStyle labelStyle;
    private Label ballSpeedLabel, ballAngleLabel;
    private Label playerSpeedLabel, comPlayerSpeedLabel, comPlayerSpeedMultiplierLabel;
    private final TextButtonStyle textButtonStyle;
    private TextButton backButton;
    private TextSpinner ballSpeedSpinner, ballAngleSpinner, playerSpeedSpinner;
    private TextSpinner comPlayerSpeedSpinner, comPlayerSpeedMultiplierSpinner;
    private Vector2 backButtonPos;
    private Vector2 backButtonSize;
    private final Vector2 screenSize;
    private final Artist2D artist2d;
    private final Constants constants;
    private final FreeTypeFontGenerator fontGenerator;
    private final FreeTypeFontGenerator.FreeTypeFontParameter fontParams;
    private TextButton resetButton;
    private final FileHandler fileHandler;
    private final JsonHandler jsonHandler;
    private final Color buttonPressColor;

    public DifficultyScreen(final Artist2D artist2D,
                            final Game game,
                            final SpriteBatch spriteBatch,
                            Constants constants,
                            FileHandler fileHandler,
                            JsonHandler jsonHandler,
                            Color buttonPressColor) {
        this.game = game;
        this.spriteBatch = spriteBatch;
        this.artist2d = artist2D;
        this.constants = constants;
        this.fileHandler = fileHandler;
        this.jsonHandler = jsonHandler;
        this.buttonPressColor = buttonPressColor;

        labelStyle = new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle();
        textButtonStyle = new TextButtonStyle();
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("PixeloidSans-nR3g1.ttf"));
        fontParams = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParams.color = Constants.FONT_COLOR;
        fontParams.mono = true;
        fontParams.gamma = 1;
        labelStyle.fontColor = Constants.FONT_COLOR;
        textButtonStyle.fontColor = Constants.FONT_COLOR;

        screenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage = new Stage(new ScreenViewport());

        float[] spinnerValues = new float[] { Constants.MAX_BALL_SPEED, Constants.MAX_BALL_ANGLE,
                Constants.PLAYER_MOVEMENT_SPEED, Constants.COM_PLAYER_SPEED, Constants.COM_PLAYER_SPEED_MULTIPLIER };

        setupLabels();
        setupSpinners(spinnerValues);
        setupBackButton();
        setupResetButton();

        stage.addActor(backButton);
        stage.addActor(resetButton);
        stage.addActor(ballSpeedLabel);
        stage.addActor(ballAngleLabel);
        stage.addActor(playerSpeedLabel);
        stage.addActor(comPlayerSpeedLabel);
        stage.addActor(comPlayerSpeedMultiplierLabel);
        stage.addActor(ballSpeedSpinner); stage.addActor(ballAngleSpinner);
        stage.addActor(playerSpeedSpinner); stage.addActor(comPlayerSpeedSpinner);
        stage.addActor(comPlayerSpeedMultiplierSpinner);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        drawSpinnerButtonPresses();
        drawTextButtonStyle();
        drawLabelToSpinnerOutlines();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private void drawSpinnerButtonPresses() {
        ballSpeedSpinner.drawFilledOnPress(artist2d, buttonPressColor);
        ballAngleSpinner.drawFilledOnPress(artist2d, buttonPressColor);
        playerSpeedSpinner.drawFilledOnPress(artist2d, buttonPressColor);
        comPlayerSpeedSpinner.drawFilledOnPress(artist2d, buttonPressColor);
        comPlayerSpeedMultiplierSpinner.drawFilledOnPress(artist2d, buttonPressColor);
    }

    private void drawTextButtonStyle() {
        backButton.drawBorderOnHover(artist2d, backButtonPos, backButtonSize.x, backButtonSize.y, Color.WHITE);
        resetButton.drawBorderOnHover(artist2d, Color.WHITE);

        if (backButton.isPressed()) {
            artist2d.drawFilledActor(backButton, buttonPressColor);
        }

        if (resetButton.isPressed()) {
            artist2d.drawFilledActor(resetButton, buttonPressColor);
        }
    }

    private void drawLabelToSpinnerOutlines() {
        float labelSpinnerUnderlineHeight = MathUtils.toValue(MathUtils.toPercentage(screenSize.y, 2.5f), screenSize.x);
        artist2d.drawFilledActor(ballSpeedLabel.getX(), ballSpeedLabel.getY(),
                ballSpeedSpinner.getX() + ballSpeedSpinner.getWidth() - ballSpeedLabel.getX(), labelSpinnerUnderlineHeight, Color.WHITE);
        artist2d.drawFilledActor(ballAngleLabel.getX(), ballAngleLabel.getY(),
                ballAngleSpinner.getX() + ballAngleSpinner.getWidth() - ballAngleLabel.getX(), labelSpinnerUnderlineHeight, Color.WHITE);
        artist2d.drawFilledActor(playerSpeedLabel.getX(), playerSpeedLabel.getY(),
                playerSpeedSpinner.getX() + playerSpeedSpinner.getWidth() - playerSpeedLabel.getX(), labelSpinnerUnderlineHeight, Color.WHITE);
        artist2d.drawFilledActor(comPlayerSpeedLabel.getX(), comPlayerSpeedLabel.getY(),
                comPlayerSpeedSpinner.getX() + comPlayerSpeedSpinner.getWidth() - comPlayerSpeedLabel.getX(), labelSpinnerUnderlineHeight,
                Color.WHITE);
        artist2d.drawFilledActor(comPlayerSpeedMultiplierLabel.getX(), comPlayerSpeedMultiplierLabel.getY(),
                comPlayerSpeedMultiplierSpinner.getX() + comPlayerSpeedMultiplierSpinner.getWidth() - comPlayerSpeedMultiplierLabel.getX(),
                labelSpinnerUnderlineHeight,
                Color.WHITE);
    }

    private void setConstantsFields() {
        constants.setMaxBallSpeed(Float.parseFloat(ballSpeedSpinner.getLabelValue().toString()));
        constants.setMaxBallAngle(Float.parseFloat(ballAngleSpinner.getLabelValue().toString()));
        constants.setPlayerMovementSpeed(Float.parseFloat(playerSpeedSpinner.getLabelValue().toString()));
        constants.setComPlayerSpeed(Float.parseFloat(comPlayerSpeedSpinner.getLabelValue().toString()));
        constants.setComPlayerSpeedMultiplier(Float.parseFloat(comPlayerSpeedMultiplierSpinner.getLabelValue().toString()));
    }

    private void reset() {
        constants.setMaxBallSpeed(Defaults.MAX_BALL_SPEED);
        constants.setMaxBallAngle(Defaults.MAX_BALL_ANGLE);
        constants.setPlayerMovementSpeed(Defaults.PLAYER_MOVEMENT_SPEED);
        constants.setComPlayerSpeed(Defaults.COM_PLAYER_SPEED);
        constants.setComPlayerSpeedMultiplier(Defaults.COM_PLAYER_SPEED_MULTIPLIER);

        ballSpeedSpinner.setLabelValue(String.valueOf(Defaults.MAX_BALL_SPEED));
        ballAngleSpinner.setLabelValue(String.valueOf(Defaults.MAX_BALL_ANGLE));
        playerSpeedSpinner.setLabelValue(String.valueOf(Defaults.PLAYER_MOVEMENT_SPEED));
        comPlayerSpeedSpinner.setLabelValue(String.valueOf(Defaults.COM_PLAYER_SPEED));
        comPlayerSpeedMultiplierSpinner.setLabelValue(String.valueOf(Defaults.COM_PLAYER_SPEED_MULTIPLIER));
    }

    private void setupLabels() {
        float labelWidth = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, 100), screenSize.x);
        float labelHeight = MathUtils.toValue(MathUtils.toPercentage(screenSize.y, 47.5f), screenSize.y);
        float labelX = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, screenSize.x / 2f - 365), screenSize.x);

        fontParams.size = 32;
        labelStyle.font = fontGenerator.generateFont(fontParams);

        Vector2 ballSpeedLabelPos = new Vector2(labelX, MathUtils.toValue(MathUtils.toPercentage(screenSize.y, screenSize.y - 185),
                screenSize.y));
        Vector2 ballAngleLabelPos = new Vector2(labelX, MathUtils.toValue(MathUtils.toPercentage(screenSize.y, ballSpeedLabelPos.y - 62.5f),
                screenSize.y));
        Vector2 playerSpeedLabelPos = new Vector2(labelX, MathUtils.toValue(MathUtils.toPercentage(screenSize.y, ballAngleLabelPos.y - 62.5f),
                screenSize.y));
        Vector2 comPlayerSpeedLabelPos = new Vector2(labelX, MathUtils.toValue(MathUtils.toPercentage(screenSize.y, playerSpeedLabelPos.y - 62.5f),
                screenSize.y));
        Vector2 comPlayerSpeedMultiplierLabelPos = new Vector2(labelX, MathUtils.toValue(MathUtils.toPercentage(screenSize.y, comPlayerSpeedLabelPos.y - 62.5f),
                screenSize.y));
        Vector2 ballSpeedLabelSize = new Vector2(labelWidth, labelHeight);
        Vector2 ballAngleLabelSize = new Vector2(labelWidth, labelHeight);
        Vector2 playerSpeedLabelSize = new Vector2(labelWidth, labelHeight);
        Vector2 comPlayerSpeedLabelSize = new Vector2(labelWidth, labelHeight);
        Vector2 comPlayerSpeedMultiplierLabelSize = new Vector2(MathUtils.toValue(MathUtils.toPercentage(screenSize.x, 120), screenSize.x), labelHeight);

        ballSpeedLabel = new Label("Max Ball Speed", labelStyle, ballSpeedLabelPos, ballSpeedLabelSize);
        ballAngleLabel = new Label("Max Ball Angle", labelStyle, ballAngleLabelPos, ballAngleLabelSize);
        playerSpeedLabel = new Label("Player Speed", labelStyle, playerSpeedLabelPos, playerSpeedLabelSize);
        comPlayerSpeedLabel = new Label("ComPlayer Speed", labelStyle, comPlayerSpeedLabelPos, comPlayerSpeedLabelSize);
        comPlayerSpeedMultiplierLabel = new Label("ComPlayer Speed Multiplier", labelStyle, comPlayerSpeedMultiplierLabelPos, comPlayerSpeedMultiplierLabelSize);
    }

    private void setupSpinners(float[] spinnerValues) {
        float spinnerButtonOffset = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, 1), screenSize.x);
        float spinnerX = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, screenSize.x - 115), screenSize.x);
        float spinnerWidth = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, 100), screenSize.x);
        float spinnerHeight = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, 47.5f), screenSize.x);

        String ballSpeedValue = String.valueOf(spinnerValues[0]);
        String ballAngleValue = String.valueOf(spinnerValues[1]);
        String playerSpeedValue = String.valueOf(spinnerValues[2]);
        String comPlayerSpeedValue = String.valueOf(spinnerValues[3]);
        String comPlayerMultiplierValue = String.valueOf(spinnerValues[4]);

        Vector2 ballSpeedSpinnerPos = new Vector2(spinnerX, ballSpeedLabel.getY());
        Vector2 ballSpeedSpinnerSize = new Vector2(spinnerWidth, spinnerHeight);
        Vector2 ballAngleSpinnerPos = new Vector2(spinnerX, ballAngleLabel.getY());
        Vector2 ballAngleSpinnerSize = new Vector2(spinnerWidth, spinnerHeight);
        Vector2 playerSpeedSpinnerPos = new Vector2(spinnerX, playerSpeedLabel.getY());
        Vector2 playerSpeedSpinnerSize = new Vector2(spinnerWidth, spinnerHeight);
        Vector2 comPlayerSpeedSpinnerPos = new Vector2(spinnerX, comPlayerSpeedLabel.getY());
        Vector2 comPlayerSpeedSpinnerSize = new Vector2(spinnerWidth, spinnerHeight);
        Vector2 comPlayerSpeedMultiplierSpinnerPos = new Vector2(spinnerX, comPlayerSpeedMultiplierLabel.getY());
        Vector2 comPlayerSpeedMultiplierSpinnerSize = new Vector2(spinnerWidth, spinnerHeight);

        int alignment = Align.center;

        fontParams.size = 32;
        textButtonStyle.font = fontGenerator.generateFont(fontParams);
        labelStyle.font = fontGenerator.generateFont(fontParams);

        float holdDelay = 0.16f;
        float intervalSeconds = 0.05f;

        ballSpeedSpinner = new TextSpinner("-", "+", ballSpeedValue, ballSpeedSpinnerPos, ballSpeedSpinnerSize,
                textButtonStyle, labelStyle, alignment, -1000f, 1000f, 5f,
                spinnerButtonOffset, (float) fontParams.size, holdDelay, intervalSeconds);
        ballAngleSpinner = new TextSpinner("-", "+", ballAngleValue, ballAngleSpinnerPos, ballAngleSpinnerSize,
                textButtonStyle, labelStyle, alignment, -500f, 500f, 2.5f,
                spinnerButtonOffset, (float) fontParams.size, holdDelay, intervalSeconds);
        playerSpeedSpinner = new TextSpinner("-", "+", playerSpeedValue, playerSpeedSpinnerPos, playerSpeedSpinnerSize,
                textButtonStyle, labelStyle, alignment, -1000f, 1000f, 5f,
                spinnerButtonOffset, (float) fontParams.size, holdDelay, intervalSeconds);
        comPlayerSpeedSpinner = new TextSpinner("-", "+", comPlayerSpeedValue, comPlayerSpeedSpinnerPos, comPlayerSpeedSpinnerSize,
                textButtonStyle, labelStyle, alignment, -500f, 500f, 0.025f,
                spinnerButtonOffset, (float) fontParams.size, holdDelay, intervalSeconds);
        comPlayerSpeedMultiplierSpinner = new TextSpinner("-", "+", comPlayerMultiplierValue,
                comPlayerSpeedMultiplierSpinnerPos, comPlayerSpeedMultiplierSpinnerSize,
                textButtonStyle, labelStyle, alignment, -1000f, 1000f, 0.1f,
                spinnerButtonOffset, (float) fontParams.size, holdDelay, intervalSeconds);
    }

    private void setupBackButton() {
        fontParams.size = 35;
        textButtonStyle.font = fontGenerator.generateFont(fontParams);

        backButtonPos = new Vector2(MathUtils.toValue(MathUtils.toPercentage(screenSize.x, screenSize.x/2f - 365), screenSize.x),
                MathUtils.toValue(MathUtils.toPercentage(screenSize.y, screenSize.y - 63f), screenSize.y));
        float backButtonWidth = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, 100), screenSize.x);
        float backButtonHeight = MathUtils.toValue(MathUtils.toPercentage(screenSize.y, 47.5f), screenSize.y);
        backButtonSize = new Vector2(backButtonWidth, backButtonHeight);
        backButton = new TextButton("Back", textButtonStyle, backButtonPos.x, backButtonPos.y, backButtonSize.x, backButtonSize.y);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setConstantsFields();
                constants.setStaticFieldsToInstanceFields();
                DifficultyScreen.this.game.setScreen(new SettingsScreen(DifficultyScreen.this.game,
                        DifficultyScreen.this.artist2d,
                        DifficultyScreen.this.textButtonStyle,
                        DifficultyScreen.this.spriteBatch,
                        DifficultyScreen.this.constants,
                        DifficultyScreen.this.fileHandler,
                        DifficultyScreen.this.jsonHandler,
                        DifficultyScreen.this.buttonPressColor));
            }
        });
    }

    private void setupResetButton() {
        float resetButtonX = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, 660), screenSize.x);
        float resetButtonY = backButton.getY();
        float resetButtonWidth = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, 120), screenSize.x);
        float resetButtonHeight = MathUtils.toValue(MathUtils.toPercentage(screenSize.y, 47.5f), screenSize.y);
        Vector2 resetButtonPos = new Vector2(resetButtonX, resetButtonY);
        Vector2 resetButtonSize = new Vector2(resetButtonWidth, resetButtonHeight);
        resetButton = new TextButton("Reset", textButtonStyle, resetButtonPos, resetButtonSize);
        resetButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                DifficultyScreen.this.reset();
                DifficultyScreen.this.constants.setStaticFieldsToInstanceFields();
            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        fontGenerator.dispose();
    }
}