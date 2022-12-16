package com.mygdx.Pong.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.Pong.Constants;
import com.mygdx.Pong.Engine.Files.FileHandler;
import com.mygdx.Pong.Engine.Json.JsonHandler;
import com.mygdx.Pong.Engine.Math.MathUtils;
import com.mygdx.Pong.Engine.Math.Vector2;
import com.mygdx.Pong.Engine.UI.Artist2D;
import com.mygdx.Pong.Engine.UI.TextButton;

public class SettingsScreen implements Screen {
    private TextButton backButton, difficultyButton, soundButton;
    private Vector2 backButtonPos, difficultyButtonPos, soundButtonPos;
    private Vector2 backButtonSize, difficultyButtonSize, soundButtonSize;
    private Vector2 screenSize;
    private float difficultyButtonWidth, difficultyButtonHeight;
    private float soundButtonWidth, soundButtonHeight;
    private float backButtonWidth, backButtonHeight;
    private TextButtonStyle textButtonStyle;
    private final SpriteBatch spriteBatch;
    private final Stage stage;
    private final Game game;
    private Constants constants;
    private final Artist2D artist2D;
    private FileHandler fileHandler;
    private JsonHandler jsonHandler;
    private Color buttonPressColor;

    public SettingsScreen(final Game game,
                          Artist2D artist2D,
                          final TextButtonStyle textButtonStyle,
                          final SpriteBatch spriteBatch,
                          Constants constants,
                          FileHandler fileHandler,
                          JsonHandler jsonHandler,
                          Color buttonPressColor) {
        this.fileHandler = fileHandler;
        this.jsonHandler = jsonHandler;
        this.game = game;
        this.spriteBatch = spriteBatch;
        this.textButtonStyle = textButtonStyle;
        this.buttonPressColor = buttonPressColor;

        screenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        this.constants = constants;
        this.artist2D = artist2D;

        stage = new Stage(new ScreenViewport());

        setupTextButtons();

        stage.addActor(backButton);
        stage.addActor(soundButton);
        stage.addActor(difficultyButton);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        drawButtonStyles();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private void drawButtonStyles() {
        difficultyButton.drawBorderOnHover(artist2D, Color.WHITE);
        soundButton.drawBorderOnHover(artist2D, Color.WHITE);
        backButton.drawBorderOnHover(artist2D, Color.WHITE);

        if (difficultyButton.isPressed()) {
            artist2D.drawFilledActor(difficultyButton, buttonPressColor);
        }

        if (soundButton.isPressed()) {
            artist2D.drawFilledActor(soundButton, buttonPressColor);
        }

        if (backButton.isPressed()) {
            artist2D.drawFilledActor(backButton, buttonPressColor);
        }
    }

    private void setupTextButtons() {
        difficultyButtonWidth = MathUtils.toValue(MathUtils.toPercentage(screenSize.x,  200f), screenSize.x);
        difficultyButtonHeight = MathUtils.toValue(MathUtils.toPercentage(screenSize.y, 47.5f), screenSize.y);
        difficultyButtonSize = new Vector2(difficultyButtonWidth, difficultyButtonHeight);
        difficultyButtonPos = new Vector2(MathUtils.toValue(MathUtils.toPercentage(screenSize.x, screenSize.x/2f - 120f), screenSize.x),
                MathUtils.toValue(MathUtils.toPercentage(screenSize.y, screenSize.y/2f - 75f), screenSize.y));

        soundButtonWidth = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, 200), screenSize.x);
        soundButtonHeight = MathUtils.toValue(MathUtils.toPercentage(screenSize.y, 47.5f), screenSize.y);
        soundButtonSize = new Vector2(soundButtonWidth, soundButtonHeight);
        soundButtonPos = new Vector2(MathUtils.toValue(MathUtils.toPercentage(screenSize.x, screenSize.x/2f - 120), screenSize.x),
                MathUtils.toValue(MathUtils.toPercentage(screenSize.y, screenSize.y/2f - 20), screenSize.y));

        difficultyButton = new TextButton("Difficulty", textButtonStyle, difficultyButtonPos.x, difficultyButtonPos.y,
                difficultyButtonSize.x, difficultyButtonSize.y);
        difficultyButton.getLabel().setAlignment(Align.center, Align.center);

        soundButton = new TextButton("Sound", textButtonStyle, soundButtonPos.x, soundButtonPos.y, soundButtonSize.x, soundButtonSize.y);
        soundButton.getLabel().setAlignment(Align.center, Align.center);

        soundButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                game.setScreen(new SoundScreen(SettingsScreen.this.game,
                        SettingsScreen.this.textButtonStyle,
                        SettingsScreen.this.artist2D,
                        SettingsScreen.this.spriteBatch,
                        SettingsScreen.this.constants,
                        SettingsScreen.this.fileHandler,
                        SettingsScreen.this.jsonHandler,
                        SettingsScreen.this.soundButton.getStyle().font,
                        SettingsScreen.this.buttonPressColor));
                SettingsScreen.this.dispose();
            }
        });

        difficultyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new DifficultyScreen(SettingsScreen.this.artist2D,
                        SettingsScreen.this.game,
                        SettingsScreen.this.spriteBatch,
                        SettingsScreen.this.constants,
                        SettingsScreen.this.fileHandler,
                        SettingsScreen.this.jsonHandler,
                        SettingsScreen.this.buttonPressColor));
                SettingsScreen.this.dispose();
            }
        });

        backButtonPos = new Vector2(MathUtils.toValue(MathUtils.toPercentage(screenSize.x, screenSize.x/2f - 365), screenSize.x),
                MathUtils.toValue(MathUtils.toPercentage(screenSize.y, screenSize.y - 63f), screenSize.y));
        backButtonWidth = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, 100), screenSize.x);
        backButtonHeight = MathUtils.toValue(MathUtils.toPercentage(screenSize.y, 47.5f), screenSize.y);
        backButtonSize = new Vector2(backButtonWidth, backButtonHeight);
        backButton = new TextButton("Back", textButtonStyle, backButtonPos.x, backButtonPos.y, backButtonSize.x, backButtonSize.y);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SettingsScreen.this.jsonHandler.serialize(constants, fileHandler.getFile());
                SettingsScreen.this.constants.setStaticFieldsToInstanceFields();
                SettingsScreen.this.game.setScreen(new StartMenu(game));
                SettingsScreen.this.dispose();
            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        resizeSoundButton();
        resizeDifficultyButton();
        resizeBackButton();
    }

    private void resizeSoundButton() {
        soundButtonWidth = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, 200), screenSize.x);
        soundButtonHeight = MathUtils.toValue(MathUtils.toPercentage(screenSize.y, 47.5f), screenSize.y);
        soundButtonSize = new Vector2(soundButtonWidth, soundButtonHeight);
        soundButtonPos = new Vector2(MathUtils.toValue(MathUtils.toPercentage(screenSize.x, screenSize.x/2f - 120), screenSize.x),
                MathUtils.toValue(MathUtils.toPercentage(screenSize.y, screenSize.y/2f - 20), screenSize.y));
        soundButton.setPosition(soundButtonPos.x, soundButtonPos.y);
        soundButton.setSize(soundButtonSize.x, soundButtonSize.y);
    }

    private void resizeDifficultyButton() {
        difficultyButtonWidth = MathUtils.toValue(MathUtils.toPercentage(screenSize.x,  200f), screenSize.x);
        difficultyButtonHeight = MathUtils.toValue(MathUtils.toPercentage(screenSize.y, 47.5f), screenSize.y);
        difficultyButtonSize = new Vector2(difficultyButtonWidth, difficultyButtonHeight);
        difficultyButtonPos = new Vector2(MathUtils.toValue(MathUtils.toPercentage(screenSize.x, screenSize.x/2f - 120f), screenSize.x),
                MathUtils.toValue(MathUtils.toPercentage(screenSize.y, screenSize.y/2f - 75f), screenSize.y));
        difficultyButton.setPosition(difficultyButtonPos.x, difficultyButtonPos.y);
        difficultyButton.setSize(difficultyButtonSize.x, difficultyButtonSize.y);
    }

    private void resizeBackButton() {
        backButtonPos = new Vector2(MathUtils.toValue(MathUtils.toPercentage(screenSize.x, screenSize.x/2f - 388f), screenSize.x),
                MathUtils.toValue(MathUtils.toPercentage(screenSize.y, screenSize.y - 63f), screenSize.y));
        backButtonWidth = MathUtils.toValue(MathUtils.toPercentage(screenSize.x, 100), screenSize.x);
        backButtonHeight = MathUtils.toValue(MathUtils.toPercentage(screenSize.y, 47.5f), screenSize.y);
        backButtonSize = new Vector2(backButtonWidth, backButtonHeight);
        backButton.setPosition(backButtonPos.x, backButtonPos.y);
        backButton.setSize(backButtonSize.x, backButtonSize.y);
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
    }
}