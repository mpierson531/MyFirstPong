package com.mygdx.Pong.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.Pong.Constants;
import com.mygdx.Pong.Engine.Files.FileHandler;
import com.mygdx.Pong.Engine.Json.JsonHandler;
import com.mygdx.Pong.Engine.UI.Artist2D;
import com.mygdx.Pong.Engine.UI.TextButton;
import com.mygdx.Pong.OnePlayerPong;
import com.mygdx.Pong.TwoPlayerPong;

import static com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class StartMenu implements Screen {
    private Stage stage;
    private Label titleLabel;
    private TextButton startButton, twoPlayerButton, settingsButton;
    private final BitmapFont pixelButtonFont;
    private final BitmapFont pixelLabelFont;
    private final Game game;
    private final SpriteBatch spriteBatch;
    private TextButtonStyle textButtonStyle;
    private Artist2D artist2D;
    private JsonHandler jsonHandler;
    private FileHandler fileHandler;
    private Constants constants;
    private Color buttonPressColor;

    public StartMenu(final Game game) {
        this.game = game;

        buttonPressColor = new Color(0.8f, 0.8f, 0.8f, 1);

        stage = new Stage(new ScreenViewport());

        jsonHandler = new JsonHandler();
        fileHandler = new FileHandler(Gdx.files.internal("configFile.json").toString());

        artist2D = new Artist2D();

        constants = new Constants();

        spriteBatch = new SpriteBatch();

        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("PixeloidSans-nR3g1.ttf"));

        FreeTypeFontParameter fontParams = new FreeTypeFontParameter();
        fontParams.gamma = 1;
        fontParams.size = 35;
        fontParams.mono = true;
        fontParams.color = Color.WHITE;
        pixelButtonFont = fontGenerator.generateFont(fontParams);

        fontParams = new FreeTypeFontParameter();
        fontParams.gamma = 1;
        fontParams.size = 50;
        fontParams.mono = true;
        fontParams.color = Color.WHITE;
        pixelLabelFont = fontGenerator.generateFont(fontParams);
        fontGenerator.dispose();

        setupTitleLabel();
        setupTextButtons();
        setupStage();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        drawButtonStyles();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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
        startButton.setTouchable(Touchable.disabled);
        twoPlayerButton.setTouchable(Touchable.disabled);
        settingsButton.setTouchable(Touchable.disabled);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void setupStage() {
        stage.addActor(titleLabel);
        stage.addActor(startButton);
        stage.addActor(twoPlayerButton);
        stage.addActor(settingsButton);
    }

    private void setupTitleLabel() {
        LabelStyle labelStyle = new LabelStyle();
        labelStyle.fontColor = Color.WHITE;
        labelStyle.font = pixelLabelFont;

        titleLabel = new Label("My First Pong", labelStyle);
        titleLabel.setPosition(Gdx.graphics.getWidth()/2f - 200, Gdx.graphics.getHeight() - 100);
        titleLabel.setSize(50, 50);
    }

    private void setupTextButtons() {
        BaseDrawable defaultDrawable = new BaseDrawable();
        defaultDrawable.setTopHeight(5);
        defaultDrawable.setBottomHeight(5);
        defaultDrawable.setLeftWidth(5);
        defaultDrawable.setRightWidth(5);

        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = pixelButtonFont;

        startButton = new TextButton("Start", textButtonStyle);
        twoPlayerButton = new TextButton("2 Player", textButtonStyle);
        settingsButton = new TextButton("Settings", textButtonStyle);

        startButton.setSize(177, 47.5f);
        startButton.setPosition(Gdx.graphics.getWidth()/2f - 105, Gdx.graphics.getHeight() - 280.5f);
        startButton.getLabel().setAlignment(Align.center, Align.center);

        twoPlayerButton.setSize(200, 47.5f);
        twoPlayerButton.setPosition(Gdx.graphics.getWidth()/2f - 117.5f, Gdx.graphics.getHeight() - 346);
        twoPlayerButton.getLabel().setAlignment(Align.center, Align.center);

        settingsButton.setSize(200, 47.5f);
        settingsButton.setPosition(Gdx.graphics.getWidth()/2f - 117.5f, Gdx.graphics.getHeight() - 411.5f);
        settingsButton.getLabel().setAlignment(Align.center, Align.center);

        setupTextButtonListeners();
    }

    private void setupTextButtonListeners() {
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                    if (!StartMenu.this.fileHandler.fileExists()) {
                        StartMenu.this.fileHandler.createFile();
                        StartMenu.this.jsonHandler.serialize(StartMenu.this.constants, StartMenu.this.fileHandler.getFile());
                        StartMenu.this.constants.setStaticFieldsToInstanceFields();
                    } else {
                        StartMenu.this.constants = (Constants) jsonHandler.deserialize(fileHandler.getFile(), StartMenu.this.constants);
                        StartMenu.this.constants.setStaticFieldsToInstanceFields();
                    }

                StartMenu.this.game.setScreen(new OnePlayerPong(StartMenu.this.game));
                StartMenu.this.dispose();
            }
        });

        twoPlayerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                StartMenu.this.game.setScreen(new TwoPlayerPong(StartMenu.this.game));
                StartMenu.this.dispose();
            }
        });

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!StartMenu.this.fileHandler.fileExists()) {
                    StartMenu.this.fileHandler.createFile();
                    StartMenu.this.jsonHandler.serialize(StartMenu.this.constants, StartMenu.this.fileHandler.getFile());
                    StartMenu.this.constants.setStaticFieldsToInstanceFields();
                } else {
                    StartMenu.this.constants = (Constants) jsonHandler.deserialize(StartMenu.this.fileHandler.getFile(), StartMenu.this.constants);
                    StartMenu.this.constants.setStaticFieldsToInstanceFields();
                }

                StartMenu.this.game.setScreen(new SettingsScreen(StartMenu.this.game,
                        StartMenu.this.artist2D,
                        StartMenu.this.textButtonStyle,
                        StartMenu.this.spriteBatch,
                        StartMenu.this.constants,
                        StartMenu.this.fileHandler,
                        StartMenu.this.jsonHandler,
                        StartMenu.this.buttonPressColor));
                StartMenu.this.dispose();
            }
        });
    }

    private void drawButtonStyles() {
        startButton.drawBorderOnHover(artist2D, Color.WHITE);
        twoPlayerButton.drawBorderOnHover(artist2D, Color.WHITE);
        settingsButton.drawBorderOnHover(artist2D, Color.WHITE);

        if (startButton.isPressed()) {
            artist2D.drawFilledActor(startButton, buttonPressColor);
        }

        if (twoPlayerButton.isPressed()) {
            artist2D.drawFilledActor(twoPlayerButton, buttonPressColor);
        }

        if (settingsButton.isPressed()) {
            artist2D.drawFilledActor(settingsButton, buttonPressColor);
        }
    }
}