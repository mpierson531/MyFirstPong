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
    private CheckBox.CheckBoxStyle checkBoxStyle;
    private CheckBox easyCheckbox, mediumCheckbox, hardCheckbox;
    private TextSpinner ballSpeedSpinner, ballAngleSpinner, playerSpeedSpinner;
    private TextSpinner comPlayerSpeedSpinner, comPlayerSpeedMultiplierSpinner;
    private Vector2 easyCheckboxPos, mediumCheckboxPos, hardCheckboxPos;
    private Vector2 ballSpeedLabelPos, ballAngleLabelPos, playerSpeedLabelPos;
    private Vector2 comPlayerSpeedLabelPos, comPlayerSpeedMultiplierLabelPos;
    private Vector2 ballSpeedLabelSize, ballAngleLabelSize, playerSpeedLabelSize;
    private Vector2 comPlayerSpeedLabelSize, comPlayerSpeedMultiplierLabelSize;
    private float easyCheckboxWidth, easyCheckboxHeight;
    private float mediumCheckboxWidth, mediumCheckboxHeight;
    private float hardCheckboxWidth, hardCheckboxHeight;
    private Vector2 backButtonPos;
    private float backButtonWidth;
    private float backButtonHeight;
    private Vector2 backButtonSize;
    private Vector2 ballSpeedSpinnerPos, ballAngleSpinnerPos, playerSpeedSpinnerPos;
    private Vector2 comPlayerSpeedSpinnerPos, comPlayerSpeedMultiplierSpinnerPos;
    private Vector2 ballSpeedSpinnerSize, ballAngleSpinnerSize, playerSpeedSpinnerSize;
    private Vector2 comPlayerSpeedSpinnerSize, comPlayerSpeedMultiplierSpinnerSize;
    private final Artist2D artist2d;
    private final Constants constants;
    private Vector2 screenSize;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParams;
    private float spinnerX, spinnerWidth, spinnerHeight;
    private float labelWidth, labelHeight, labelX, labelY;
    private float spinnerButtonOffset;
    private float labelSpinnerUnderlineHeight;
    private float resetButtonX, resetButtonY, resetButtonWidth, resetButtonHeight;
    private Vector2 resetButtonPos, resetButtonSize;
    private TextButton resetButton;
    private FileHandler fileHandler;
    private JsonHandler jsonHandler;

    public DifficultyScreen(final Artist2D artist2D, final Game game, final SpriteBatch spriteBatch,
                            Constants constants, FileHandler fileHandler, JsonHandler jsonHandler) {
        this.game = game;
        this.spriteBatch = spriteBatch;
        this.artist2d = artist2D;
        this.constants = constants;
        this.fileHandler = fileHandler;
        this.jsonHandler = jsonHandler;

        labelStyle = new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle();
        textButtonStyle = new TextButtonStyle();
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.local("PixeloidSans-nR3g1.ttf"));
        fontParams = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParams.color = Color.WHITE;
        fontParams.mono = true;
        fontParams.gamma = 1;
        labelStyle.fontColor = Color.WHITE;
        textButtonStyle.fontColor = Color.WHITE;

        /*fontParams.size = 35;
        checkBoxStyle = new CheckBox.CheckBoxStyle();
        checkBoxStyle.font = fontGenerator.generateFont(fontParams);
        checkBoxStyle.checkedFontColor = Color.BLACK;*/

        screenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage = new Stage(new ScreenViewport());

//        setupCheckBoxes();
        setupLabels();
        setupSpinners();
        setupBackButton();
        setupResetButton();

        /*stage.addActor(easyCheckbox);
        stage.addActor(mediumCheckbox);
        stage.addActor(hardCheckbox);*/
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
//        drawCheckboxHoverAndChecked();
        drawSpinnerButtonPresses();
        drawTextButtonOutlines();
        drawLabelToSpinnerOutlines();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private void drawCheckboxHoverAndChecked() {
        if (easyCheckbox.isChecked()) {
            artist2d.drawFilledRectangle(easyCheckbox.getX(), easyCheckbox.getY(),
                    easyCheckbox.getWidth(), easyCheckbox.getHeight(), Color.WHITE);
        }

        if (easyCheckbox.isOver()) {
            artist2d.drawLineRectangle(easyCheckbox.getX(), easyCheckbox.getY(),
                    easyCheckbox.getWidth(), easyCheckbox.getHeight(), Color.WHITE);
        }

        if (mediumCheckbox.isChecked()) {
            artist2d.drawFilledRectangle(mediumCheckbox.getX(), mediumCheckbox.getY(),
                    mediumCheckbox.getWidth(), mediumCheckbox.getHeight(), Color.WHITE);
        }

        if (mediumCheckbox.isOver()) {
            artist2d.drawLineRectangle(mediumCheckbox.getX(), mediumCheckbox.getY(),
                    mediumCheckbox.getWidth(), mediumCheckbox.getHeight(), Color.WHITE);
        }

        if (hardCheckbox.isChecked()) {
            artist2d.drawFilledRectangle(hardCheckbox.getX(), hardCheckbox.getY(),
                    hardCheckbox.getWidth(), hardCheckbox.getHeight(), Color.WHITE);
        }

        if (hardCheckbox.isOver()) {
            artist2d.drawLineRectangle(hardCheckbox.getX(), hardCheckbox.getY(),
                    hardCheckbox.getWidth(), hardCheckbox.getHeight(), Color.WHITE);
        }
    }

    private void drawSpinnerButtonPresses() {
        Color color = new Color(0.825f, 0.825f, 0.825f, 0.825f);
        ballSpeedSpinner.drawFilledRectOnPress(artist2d, color);
        ballAngleSpinner.drawFilledRectOnPress(artist2d, color);
        playerSpeedSpinner.drawFilledRectOnPress(artist2d, color);
        comPlayerSpeedSpinner.drawFilledRectOnPress(artist2d, color);
        comPlayerSpeedMultiplierSpinner.drawFilledRectOnPress(artist2d, color);
    }

    private void drawTextButtonOutlines() {
        backButton.drawBorderOnHover(artist2d, backButtonPos, backButtonSize.x, backButtonSize.y, Color.WHITE);
        resetButton.drawBorderOnHover(artist2d, Color.WHITE);
    }

    private void drawLabelToSpinnerOutlines() {
        labelSpinnerUnderlineHeight = MathUtils.toValue(MathUtils.toPercentage(screenSize.y, 2.5f), screenSize.x);
        artist2d.drawFilledRectangle(ballSpeedLabel.getX(), ballSpeedLabel.getY(),
                ballSpeedSpinner.getX() + ballSpeedSpinner.getWidth() - ballSpeedLabel.getX(), labelSpinnerUnderlineHeight, Color.WHITE);
        artist2d.drawFilledRectangle(ballAngleLabel.getX(), ballAngleLabel.getY(),
                ballAngleSpinner.getX() + ballAngleSpinner.getWidth() - ballAngleLabel.getX(), labelSpinnerUnderlineHeight, Color.WHITE);
        artist2d.drawFilledRectangle(playerSpeedLabel.getX(), playerSpeedLabel.getY(),
                playerSpeedSpinner.getX() + playerSpeedSpinner.getWidth() - playerSpeedLabel.getX(), labelSpinnerUnderlineHeight, Color.WHITE);
        artist2d.drawFilledRectangle(comPlayerSpeedLabel.getX(), comPlayerSpeedLabel.getY(),
                comPlayerSpeedSpinner.getX() + comPlayerSpeedSpinner.getWidth() - comPlayerSpeedLabel.getX(), labelSpinnerUnderlineHeight,
                Color.WHITE);
        artist2d.drawFilledRectangle(comPlayerSpeedMultiplierLabel.getX(), comPlayerSpeedMultiplierLabel.getY(),
                comPlayerSpeedMultiplierSpinner.getX() + comPlayerSpeedMultiplierSpinner.getWidth() - comPlayerSpeedMultiplierLabel.getX(),
                labelSpinnerUnderlineHeight,
                Color.WHITE);
    }

    private void drawSpinnerOutlines() {
        artist2d.drawLineRectangle(ballSpeedSpinner.getX(), ballSpeedSpinner.getY(),
                ballSpeedSpinner.getWidth(), ballSpeedSpinner.getHeight(), Color.WHITE);
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

    private void setupCheckBoxes() {
        easyCheckboxPos = new Vector2(Gdx.graphics.getWidth()/2f - 100f, Gdx.graphics.getHeight()/2f - 200);
        mediumCheckboxPos = new Vector2(Gdx.graphics.getWidth()/2f - 150f, Gdx.graphics.getHeight()/2f - 300f);
        hardCheckboxPos = new Vector2(Gdx.graphics.getWidth()/2f - 200f, Gdx.graphics.getHeight()/2f - 300f);

        easyCheckboxWidth = 100;
        easyCheckboxHeight = 47.5f;
        mediumCheckboxWidth = 145f;
        mediumCheckboxHeight = 47.5f;
        hardCheckboxWidth = 100f;
        hardCheckboxHeight = 47.5f;

        easyCheckbox = new CheckBox("Easy", checkBoxStyle, easyCheckboxPos, easyCheckboxWidth, easyCheckboxHeight, Color.WHITE);
        mediumCheckbox = new CheckBox("Medium", checkBoxStyle, mediumCheckboxPos, mediumCheckboxWidth, mediumCheckboxHeight, Color.WHITE);
        hardCheckbox = new CheckBox("Hard", checkBoxStyle, hardCheckboxPos, hardCheckboxWidth, hardCheckboxHeight, Color.WHITE);
        // TODO: add ClickListeners to checkboxes; figure out how to style them

        easyCheckbox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mediumCheckbox.setChecked(false);
                hardCheckbox.setChecked(false);
            }
        });

        mediumCheckbox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                easyCheckbox.setChecked(false);
                hardCheckbox.setChecked(false);
            }
        });

        hardCheckbox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                easyCheckbox.setChecked(false);
                mediumCheckbox.setChecked(false);
            }
        });
    }

    private void setupLabels() {
        labelWidth = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, 100), screenSize.x);
        labelHeight = MathUtils.toValue(MathUtils.toPercentage(screenSize.y, 47.5f), screenSize.y);
        labelX = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, screenSize.x/2f - 365), screenSize.x);

        fontParams.size = 32;
        labelStyle.font = fontGenerator.generateFont(fontParams);

        ballSpeedLabelPos = new Vector2(labelX, MathUtils.toValue(MathUtils.toPercentage(screenSize.y, screenSize.y - 185),
                screenSize.y));
        ballAngleLabelPos = new Vector2(labelX, MathUtils.toValue(MathUtils.toPercentage(screenSize.y, ballSpeedLabelPos.y - 62.5f),
                screenSize.y));
        playerSpeedLabelPos = new Vector2(labelX, MathUtils.toValue(MathUtils.toPercentage(screenSize.y, ballAngleLabelPos.y - 62.5f),
                screenSize.y));
        comPlayerSpeedLabelPos = new Vector2(labelX, MathUtils.toValue(MathUtils.toPercentage(screenSize.y, playerSpeedLabelPos.y - 62.5f),
                screenSize.y));
        comPlayerSpeedMultiplierLabelPos = new Vector2(labelX, MathUtils.toValue(MathUtils.toPercentage(screenSize.y, comPlayerSpeedLabelPos.y - 62.5f),
                screenSize.y));
        ballSpeedLabelSize = new Vector2(labelWidth, labelHeight);
        ballAngleLabelSize = new Vector2(labelWidth, labelHeight);
        playerSpeedLabelSize = new Vector2(labelWidth, labelHeight);
        comPlayerSpeedLabelSize = new Vector2(labelWidth, labelHeight);
        comPlayerSpeedMultiplierLabelSize = new Vector2(MathUtils.toValue(MathUtils.toPercentage(screenSize.x, 120), screenSize.x), labelHeight);

        ballSpeedLabel = new Label("Max Ball Speed", labelStyle, ballSpeedLabelPos, ballSpeedLabelSize);
        ballAngleLabel = new Label("Max Ball Angle", labelStyle, ballAngleLabelPos, ballAngleLabelSize);
        playerSpeedLabel = new Label("Player Speed", labelStyle, playerSpeedLabelPos, playerSpeedLabelSize);
        comPlayerSpeedLabel = new Label("ComPlayer Speed", labelStyle, comPlayerSpeedLabelPos, comPlayerSpeedLabelSize);
        comPlayerSpeedMultiplierLabel = new Label("ComPlayer Speed Multiplier", labelStyle, comPlayerSpeedMultiplierLabelPos, comPlayerSpeedMultiplierLabelSize);
    }

    private void setupSpinners() {
        spinnerButtonOffset = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, 1), screenSize.x);
        spinnerX = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, screenSize.x - 115), screenSize.x);
        spinnerWidth = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, 100), screenSize.x);
        spinnerHeight = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, 47.5f), screenSize.x);

        ballSpeedSpinnerPos = new Vector2(spinnerX, ballSpeedLabel.getY());
        ballSpeedSpinnerSize = new Vector2(spinnerWidth, spinnerHeight);
        ballAngleSpinnerPos = new Vector2(spinnerX, ballAngleLabel.getY());
        ballAngleSpinnerSize = new Vector2(spinnerWidth, spinnerHeight);
        playerSpeedSpinnerPos = new Vector2 (spinnerX, playerSpeedLabel.getY());
        playerSpeedSpinnerSize = new Vector2(spinnerWidth, spinnerHeight);
        comPlayerSpeedSpinnerPos = new Vector2(spinnerX, comPlayerSpeedLabel.getY());
        comPlayerSpeedSpinnerSize = new Vector2(spinnerWidth, spinnerHeight);
        comPlayerSpeedMultiplierSpinnerPos = new Vector2(spinnerX, comPlayerSpeedMultiplierLabel.getY());
        comPlayerSpeedMultiplierSpinnerSize = new Vector2(spinnerWidth, spinnerHeight);

        int alignment = Align.center;

        // 32: font size I originally tested with, on values of 0.0

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
                DifficultyScreen.this.game.setScreen(new SettingsScreen(game, artist2d, textButtonStyle, DifficultyScreen.this.spriteBatch,
                        constants, fileHandler, jsonHandler));
            }
        });
    }

    private void setupResetButton() {
        resetButtonX = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, 660), screenSize.x);
        resetButtonY = backButton.getY();
        resetButtonWidth = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, 120), screenSize.x);
        resetButtonHeight = MathUtils.toValue(MathUtils.toPercentage(screenSize.y, 47.5f), screenSize.y);
        resetButtonPos = new Vector2(resetButtonX, resetButtonY);
        resetButtonSize = new Vector2(resetButtonWidth, resetButtonHeight);
        resetButton = new TextButton("Reset", textButtonStyle, resetButtonPos, resetButtonSize);
        resetButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resetConstantsFields();
                constants.setStaticFieldsToInstanceFields();
                updateSpinnerLabels();
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
        /*screenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        resizeLabels();
        resizeSpinners();
        resizeBackButton();
        resizeResetButton();*/
        // TODO: Finish setting up resizing
    }

    void resizeLabels() {
        labelWidth = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, 100), screenSize.x);
        labelHeight = MathUtils.toValue(MathUtils.toPercentage(screenSize.y, 47.5f), screenSize.y);

        ballSpeedLabelPos = new Vector2(screenSize.x/2f - 375, screenSize.y - 150f);
        ballAngleLabelPos = new Vector2(screenSize.x/2f - 375, screenSize.y - 200f);
        playerSpeedLabelPos = new Vector2(screenSize.x/2f - 375, screenSize.y - 250f);
        comPlayerSpeedLabelPos = new Vector2(screenSize.x/2f - 375, screenSize.y - 300f);
        comPlayerSpeedMultiplierLabelPos = new Vector2(screenSize.x/2f - 375, screenSize.y - 350);
        ballSpeedLabelSize = new Vector2(labelWidth, labelHeight);
        ballAngleLabelSize = new Vector2(labelWidth, labelHeight);
        playerSpeedLabelSize = new Vector2(labelWidth, labelHeight);
        comPlayerSpeedLabelSize = new Vector2(labelWidth, labelHeight);
        comPlayerSpeedMultiplierLabelSize = new Vector2(MathUtils.toValue(MathUtils.toPercentage(screenSize.x, 120), screenSize.x), labelHeight);

        ballSpeedLabel.setPosition(ballSpeedLabelPos);
        ballAngleLabel.setPosition(ballAngleLabelPos);
        playerSpeedLabel.setPosition(playerSpeedLabelPos);
        comPlayerSpeedLabel.setPosition(comPlayerSpeedLabelPos);
        comPlayerSpeedMultiplierLabel.setPosition(comPlayerSpeedMultiplierLabelPos);
        ballSpeedLabel.setSize(ballSpeedLabelSize);
        ballAngleLabel.setSize(ballAngleLabelSize);
        playerSpeedLabel.setSize(playerSpeedLabelSize);
        comPlayerSpeedLabel.setSize(comPlayerSpeedLabelSize);
        comPlayerSpeedMultiplierLabel.setSize(comPlayerSpeedMultiplierLabelSize);
    }

    void resizeSpinners() {
        spinnerX = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, screenSize.x - 115), screenSize.x);
        spinnerWidth = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, 100), screenSize.x);
        spinnerHeight = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, 47.5f), screenSize.x);

        ballSpeedSpinnerPos = new Vector2(spinnerX, ballSpeedLabel.getY());
        ballSpeedSpinnerSize = new Vector2(spinnerWidth, spinnerHeight);
        ballAngleSpinnerPos = new Vector2(spinnerX, ballAngleLabel.getY());
        ballAngleSpinnerSize = new Vector2(spinnerWidth, spinnerHeight);
        playerSpeedSpinnerPos = new Vector2 (spinnerX, playerSpeedLabel.getY());
        playerSpeedSpinnerSize = new Vector2(spinnerWidth, spinnerHeight);
        comPlayerSpeedSpinnerPos = new Vector2(spinnerX, comPlayerSpeedLabel.getY());
        comPlayerSpeedSpinnerSize = new Vector2(spinnerWidth, spinnerHeight);
        comPlayerSpeedMultiplierSpinnerPos = new Vector2(spinnerX, comPlayerSpeedMultiplierLabel.getY());
        comPlayerSpeedMultiplierSpinnerSize = new Vector2(spinnerWidth, spinnerHeight);

        ballSpeedSpinner.setPosition(ballSpeedSpinnerPos, true);
        ballAngleSpinner.setPosition(ballAngleSpinnerPos, true);
        playerSpeedSpinner.setPosition(playerSpeedSpinnerPos, true);
        comPlayerSpeedSpinner.setPosition(comPlayerSpeedSpinnerPos, true);
        comPlayerSpeedMultiplierSpinner.setPosition(comPlayerSpeedMultiplierSpinnerPos, true);
        ballSpeedSpinner.setSize(ballSpeedSpinnerSize);
        ballAngleSpinner.setSize(ballAngleSpinnerSize);
        playerSpeedSpinner.setSize(playerSpeedSpinnerSize);
        comPlayerSpeedSpinner.setSize(comPlayerSpeedSpinnerSize);
        comPlayerSpeedMultiplierSpinner.setSize(comPlayerSpeedMultiplierSpinnerSize);
    }

    void resizeBackButton() {
        backButtonPos = new Vector2(ballSpeedLabel.getX(),
                MathUtils.toValue(ballSpeedLabelPos.x, screenSize.y));
        backButtonWidth = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, 100), screenSize.x);
        backButtonHeight = MathUtils.toValue(MathUtils.toPercentage(screenSize.y, 47.5f), screenSize.y);
        backButtonSize = new Vector2(backButtonWidth, backButtonHeight);
        backButton.setPosition(backButtonPos.x, backButtonPos.y);
        backButton.setSize(backButtonSize.x, backButtonSize.y);
    }

    private void resizeResetButton() {
        resetButtonX = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, 700), screenSize.x);
        resetButtonY = backButton.getY();
        resetButtonWidth = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, 50), screenSize.x);
        resetButtonHeight = MathUtils.toValue(MathUtils.toPercentage(screenSize.y, 47.5f), screenSize.y);
        resetButtonPos = new Vector2(resetButtonX, resetButtonY);
        resetButtonSize = new Vector2(resetButtonWidth, resetButtonHeight);
        resetButton = new TextButton("Reset", textButtonStyle, resetButtonPos, resetButtonSize);
        resetButton.setPosition(resetButtonPos);
        resetButton.setSize(resetButtonSize);
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