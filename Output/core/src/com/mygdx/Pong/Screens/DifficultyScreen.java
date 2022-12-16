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
import com.mygdx.Pong.Engine.UI.*;
import com.mygdx.Pong.TouchHoldListener;

public class DifficultyScreen implements Screen {
    private final Game game;
    private final SpriteBatch spriteBatch;
    private final Stage stage;
    private Label.LabelStyle labelStyle;
    private Label ballSpeedLabel, ballAngleLabel;
    private Label playerSpeedLabel, comPlayerSpeedLabel, comPlayerSpeedMultiplierLabel;
    private TextButtonStyle textButtonStyle;
    private TextButton backButton;
    private TextSpinner ballSpeedSpinner, ballAngleSpinner, playerSpeedSpinner;
    private TextSpinner comPlayerSpeedSpinner, comPlayerSpeedMultiplierSpinner;
    private Vector2 backButtonPos, backButtonSize, screenSize;
    private float backButtonWidth, backButtonHeight;
    private final Artist2D artist2d;
    private final Constants constants;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParams;
    private float labelWidth, labelHeight, labelX, labelY;
    private float labelSpinnerUnderlineHeight;
    private TextButton resetButton;
    private FileHandler fileHandler;
    private JsonHandler jsonHandler;
    private Color buttonPressColor;

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
        fontParams.color = Color.WHITE;
        fontParams.mono = true;
        fontParams.gamma = 1;
        labelStyle.fontColor = Color.WHITE;
        textButtonStyle.fontColor = Color.WHITE;

        screenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage = new Stage(new ScreenViewport());

        setupLabels();
        setupSpinners();
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
        labelSpinnerUnderlineHeight = MathUtils.toValue(MathUtils.toPercentage(screenSize.y, 2.5f), screenSize.x);
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

    private void resetConstantsFields() {
        constants.setMaxBallSpeed(Defaults.MAX_BALL_SPEED);
        constants.setMaxBallAngle(Defaults.MAX_BALL_ANGLE);
        constants.setPlayerMovementSpeed(Defaults.PLAYER_MOVEMENT_SPEED);
        constants.setComPlayerSpeed(Defaults.COM_PLAYER_SPEED);
        constants.setComPlayerSpeedMultiplier(Defaults.COM_PLAYER_SPEED_MULTIPLIER);
    }

    // Only solution I can think of right now to fix font and button alignment issues
    private void updateSpinnerLabels() {
        float ballSpeed = Float.parseFloat(ballSpeedSpinner.getLabelValue().toString());
        float ballAngle = Float.parseFloat(ballAngleSpinner.getLabelValue().toString());
        float playerSpeed = Float.parseFloat(playerSpeedSpinner.getLabelValue().toString());
        float comPlayerSpeed = Float.parseFloat(comPlayerSpeedSpinner.getLabelValue().toString());
        float comPlayerSpeedMultiplier = Float.parseFloat(comPlayerSpeedMultiplierSpinner.getLabelValue().toString());

        if (ballSpeed > Constants.MAX_BALL_SPEED) {
            for (float i = ballSpeed; i > Constants.MAX_BALL_SPEED; i -= ballSpeedSpinner.getStep()) {
                ballSpeedSpinner.decrement();
            }
        } else if (ballSpeed < Constants.MAX_BALL_SPEED) {
            for (float i = ballSpeed; i < Constants.MAX_BALL_SPEED; i += ballSpeedSpinner.getStep()) {
                ballSpeedSpinner.increment();
            }
        }

        if (ballAngle > Constants.MAX_BALL_ANGLE) {
            for (float i = ballAngle; i > Constants.MAX_BALL_SPEED; i -= ballAngleSpinner.getStep()) {
                ballAngleSpinner.decrement();
            }
        } else if (ballAngle < Constants.MAX_BALL_ANGLE) {
            for (float i = ballAngle; i < Constants.MAX_BALL_ANGLE; i += ballAngleSpinner.getStep()) {
                ballAngleSpinner.increment();
            }
        }

        if (playerSpeed > Constants.PLAYER_MOVEMENT_SPEED) {
            for (float i = playerSpeed; i > Constants.PLAYER_MOVEMENT_SPEED; i -= 0.5f) {
                playerSpeedSpinner.decrement(0.5f);
            }
        } else if (playerSpeed < Constants.PLAYER_MOVEMENT_SPEED) {
            for (float i = playerSpeed; i < Constants.PLAYER_MOVEMENT_SPEED; i += 0.5f) {
                playerSpeedSpinner.increment(0.5f);
            }
        }

        if (comPlayerSpeed > Constants.COM_PLAYER_SPEED) {
            for (float i = comPlayerSpeed; i > Constants.COM_PLAYER_SPEED; i -= comPlayerSpeedSpinner.getStep()) {
                comPlayerSpeedSpinner.decrement();
            }
        } else if (comPlayerSpeed < Constants.COM_PLAYER_SPEED) {
            for (float i = comPlayerSpeed; i < Constants.COM_PLAYER_SPEED; i += comPlayerSpeedSpinner.getStep()) {
                comPlayerSpeedSpinner.increment();
            }
        }

        if (comPlayerSpeedMultiplier > Constants.COM_PLAYER_SPEED_MULTIPLIER) {
            for (float i = comPlayerSpeedMultiplier; i > Constants.COM_PLAYER_SPEED_MULTIPLIER; i -= comPlayerSpeedMultiplierSpinner.getStep()) {
                comPlayerSpeedMultiplierSpinner.decrement();
            }
        } else if (comPlayerSpeedMultiplier < Constants.COM_PLAYER_SPEED_MULTIPLIER) {
            for (float i = comPlayerSpeedMultiplier; i < Constants.COM_PLAYER_SPEED_MULTIPLIER; i += comPlayerSpeedMultiplierSpinner.getStep()) {
                comPlayerSpeedMultiplierSpinner.increment();
            }
        }
    }

    private void setupLabels() {
        labelWidth = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, 100), screenSize.x);
        labelHeight = MathUtils.toValue(MathUtils.toPercentage(screenSize.y, 47.5f), screenSize.y);
        labelX = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, screenSize.x/2f - 365), screenSize.x);

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

    private void setupSpinners() {
        float spinnerButtonOffset = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, 1), screenSize.x);
        float spinnerX = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, screenSize.x - 115), screenSize.x);
        float spinnerWidth = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, 100), screenSize.x);
        float spinnerHeight = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, 47.5f), screenSize.x);

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

        ballSpeedSpinner = new TextSpinner("-", "+", "0", ballSpeedSpinnerPos, ballSpeedSpinnerSize,
                textButtonStyle, labelStyle, alignment, -1000f, 1000f, 5f, spinnerButtonOffset, (float) fontParams.size);
        ballAngleSpinner = new TextSpinner("-", "+", "0", ballAngleSpinnerPos, ballAngleSpinnerSize,
                textButtonStyle, labelStyle, alignment, -500f, 500f, 2.5f, spinnerButtonOffset, (float) fontParams.size);
        playerSpeedSpinner = new TextSpinner("-", "+", "0", playerSpeedSpinnerPos, playerSpeedSpinnerSize,
                textButtonStyle, labelStyle, alignment, -1000f, 1000f, 5f, spinnerButtonOffset, (float) fontParams.size);
        comPlayerSpeedSpinner = new TextSpinner("-", "+", "0", comPlayerSpeedSpinnerPos, comPlayerSpeedSpinnerSize,
                textButtonStyle, labelStyle, alignment, -500f, 500f, 0.025f, spinnerButtonOffset, (float) fontParams.size);
        comPlayerSpeedMultiplierSpinner = new TextSpinner("-", "+", "0",
                comPlayerSpeedMultiplierSpinnerPos, comPlayerSpeedMultiplierSpinnerSize,
                textButtonStyle, labelStyle, alignment, -1000f, 1000f, 0.1f, spinnerButtonOffset, (float) fontParams.size);
        updateSpinnerLabels();
        addSpinnerListeners();
    }

    private void setupBackButton() {
        fontParams.size = 35;
        textButtonStyle.font = fontGenerator.generateFont(fontParams);

        backButtonPos = new Vector2(MathUtils.toValue(MathUtils.toPercentage(screenSize.x, screenSize.x/2f - 365), screenSize.x),
                MathUtils.toValue(MathUtils.toPercentage(screenSize.y, screenSize.y - 63f), screenSize.y));
        backButtonWidth = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, 100), screenSize.x);
        backButtonHeight = MathUtils.toValue(MathUtils.toPercentage(screenSize.y, 47.5f), screenSize.y);
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
                DifficultyScreen.this.resetConstantsFields();
                DifficultyScreen.this.constants.setStaticFieldsToInstanceFields();
                DifficultyScreen.this.updateSpinnerLabels();
            }
        });
    }

    private void addSpinnerListeners() {
        float holdDelay = 0.16f;
        float intervalSeconds = 0.05f;

        ballSpeedSpinner.getButtonOne().addListener(new TouchHoldListener(holdDelay, intervalSeconds, new Runnable() {
            @Override
            public void run() {
                ballSpeedSpinner.decrement();
            }
        }));

        ballSpeedSpinner.getButtonTwo().addListener(new TouchHoldListener(holdDelay, intervalSeconds, new Runnable() {
            @Override
            public void run() {
                ballSpeedSpinner.increment();
            }
        }));

        ballAngleSpinner.getButtonOne().addListener(new TouchHoldListener(holdDelay, intervalSeconds, new Runnable() {
            @Override
            public void run() {
                ballAngleSpinner.decrement();
            }
        }));

        ballAngleSpinner.getButtonTwo().addListener(new TouchHoldListener(holdDelay, intervalSeconds, new Runnable() {
            @Override
            public void run() {
                ballAngleSpinner.increment();
            }
        }));

        playerSpeedSpinner.getButtonOne().addListener(new TouchHoldListener(holdDelay, intervalSeconds, new Runnable() {
            @Override
            public void run() {
                playerSpeedSpinner.decrement();
            }
        }));

        playerSpeedSpinner.getButtonTwo().addListener(new TouchHoldListener(holdDelay, intervalSeconds, new Runnable() {
            @Override
            public void run() {
                playerSpeedSpinner.increment();
            }
        }));

        comPlayerSpeedSpinner.getButtonOne().addListener(new TouchHoldListener(holdDelay, intervalSeconds, new Runnable() {
            @Override
            public void run() {
                comPlayerSpeedSpinner.decrement();
            }
        }));

        comPlayerSpeedSpinner.getButtonTwo().addListener(new TouchHoldListener(holdDelay, intervalSeconds, new Runnable() {
            @Override
            public void run() {
                comPlayerSpeedSpinner.increment();
            }
        }));

        comPlayerSpeedMultiplierSpinner.getButtonOne().addListener(new TouchHoldListener(holdDelay, intervalSeconds, new Runnable() {
            @Override
            public void run() {
                comPlayerSpeedMultiplierSpinner.decrement();
            }
        }));

        comPlayerSpeedMultiplierSpinner.getButtonTwo().addListener(new TouchHoldListener(holdDelay, intervalSeconds, new Runnable() {
            @Override
            public void run() {
                comPlayerSpeedMultiplierSpinner.increment();
            }
        }));
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
        // TODO: Dispose objects
    }
}