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
import com.mygdx.Pong.Engine.Math.Vector2;
import com.mygdx.Pong.Engine.UI.Artist2D;
import com.mygdx.Pong.Engine.UI.TextButton;

public class SettingsScreen implements Screen {
    private TextButton backButton, difficultyButton, soundButton, colorsButton;
    private Vector2 backButtonPos, difficultyButtonPos, soundButtonPos, colorsButtonPos;
    private Vector2 backButtonSize, difficultyButtonSize, soundButtonSize, colorsButtonSize;
    private final Vector2 screenSize;
    private float difficultyButtonWidth, difficultyButtonHeight;
    private float soundButtonWidth, soundButtonHeight;
    private float backButtonWidth, backButtonHeight;
    private TextButtonStyle textButtonStyle;
    private final SpriteBatch spriteBatch;
    private final Stage stage;
    private Game game;
    private Constants constants;
    private final Artist2D artist2D;
    private FileHandler fileHandler;
    private JsonHandler jsonHandler;
    private final Color buttonPressColor;

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
        this.textButtonStyle.fontColor = Constants.FONT_COLOR;
        this.buttonPressColor = buttonPressColor;

        screenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        this.constants = constants;
        this.artist2D = artist2D;

        stage = new Stage(new ScreenViewport());

        setupTextButtons();

        stage.addActor(soundButton);
        stage.addActor(difficultyButton);
        stage.addActor(backButton);
        stage.addActor(colorsButton);
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
        colorsButton.drawBorderOnHover(artist2D, Color.WHITE);

        difficultyButton.drawFilledRectOnPress(artist2D, buttonPressColor);
        soundButton.drawFilledRectOnPress(artist2D, buttonPressColor);
        backButton.drawFilledRectOnPress(artist2D, buttonPressColor);
        colorsButton.drawFilledRectOnPress(artist2D, buttonPressColor);
    }

    private void setupTextButtons() {
        difficultyButtonWidth = 200f;
        difficultyButtonHeight = 47.5f;
        difficultyButtonSize = new Vector2(difficultyButtonWidth, difficultyButtonHeight);
        difficultyButtonPos = new Vector2(screenSize.x/2f - 120f, screenSize.y/2f - 75f);

        soundButtonWidth = 200f;
        soundButtonHeight = 47.5f;
        soundButtonSize = new Vector2(soundButtonWidth, soundButtonHeight);
        soundButtonPos = new Vector2(screenSize.x/2f - 120, screenSize.y/2f - 20);

        backButtonPos = new Vector2(screenSize.x/2f - 365, screenSize.y - 63f);
        backButtonWidth = 100f;
        backButtonHeight = 47.5f;
        backButtonSize = new Vector2(backButtonWidth, backButtonHeight);

        colorsButtonPos = new Vector2(screenSize.x/2f - 120, screenSize.y/2 + 35);
        colorsButtonSize = new Vector2(200f, 47.5f);

        difficultyButton = new TextButton("Difficulty", textButtonStyle, difficultyButtonPos.x, difficultyButtonPos.y,
                difficultyButtonSize.x, difficultyButtonSize.y);
        difficultyButton.getLabel().setAlignment(Align.center, Align.center);

        soundButton = new TextButton("Sound", textButtonStyle, soundButtonPos.x, soundButtonPos.y, soundButtonSize.x, soundButtonSize.y);
        soundButton.getLabel().setAlignment(Align.center, Align.center);

        backButton = new TextButton("Back", textButtonStyle, backButtonPos.x, backButtonPos.y, backButtonSize.x, backButtonSize.y);

        colorsButton = new TextButton("Colors", textButtonStyle, colorsButtonPos, colorsButtonSize);

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

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SettingsScreen.this.jsonHandler.serialize(constants, fileHandler.getFile());
                SettingsScreen.this.constants.setStaticFieldsToInstanceFields();
                SettingsScreen.this.game.setScreen(new StartMenu(game));
                SettingsScreen.this.dispose();
            }
        });

        colorsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SettingsScreen.this.game.setScreen(new ColorsScreen(SettingsScreen.this.game,
                        SettingsScreen.this.artist2D,
                        SettingsScreen.this.textButtonStyle,
                        SettingsScreen.this.spriteBatch, SettingsScreen.this.constants,
                        SettingsScreen.this.fileHandler, SettingsScreen.this.jsonHandler, SettingsScreen.this.buttonPressColor));
                dispose();
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
        soundButtonWidth = 200f;
        soundButtonHeight = 47.5f;
        soundButtonSize = new Vector2(soundButtonWidth, soundButtonHeight);
        soundButtonPos = new Vector2(screenSize.x / 2 - 120, screenSize.y / 2 - 20);
        soundButton.setPosition(soundButtonPos.x, soundButtonPos.y);
        soundButton.setSize(soundButtonSize.x, soundButtonSize.y);
    }

    private void resizeDifficultyButton() {
        difficultyButtonWidth = 200f;
        difficultyButtonHeight = 47.5f;
        difficultyButtonSize = new Vector2(difficultyButtonWidth, difficultyButtonHeight);
        difficultyButtonPos = new Vector2(screenSize.x / 2 - 120f, screenSize.y / 2 - 75f);
        difficultyButton.setPosition(difficultyButtonPos.x, difficultyButtonPos.y);
        difficultyButton.setSize(difficultyButtonSize.x, difficultyButtonSize.y);
    }

    private void resizeBackButton() {
        backButtonPos = new Vector2(screenSize.x/2f - 388f, screenSize.y - 63f);
        backButtonWidth = 100f;
        backButtonHeight = 47.5f;
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