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
import com.mygdx.Pong.Engine.Math.Vector2;

import static com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class StartMenu implements Screen {
    private Stage stage;
    private Label titleLabel;
    private TextButton startButton, twoPlayerButton, settingsButton;
    private final BitmapFont pixelButtonFont;
    private final BitmapFont pixelLabelFont;
    private final Game game;
    private final SpriteBatch spriteBatch;
    private final Vector2 startButtonBorderPos;
    private final float startButtonBorderWidth, startButtonBorderHeight;
    private final Vector2 twoPlayerBorderPos;
    private final float twoPlayerBorderWidth, twoPlayerBorderHeight;
    private final Vector2 settingsBorderPos;
    private final float settingsBorderWidth, settingsBorderHeight;
    private TextButtonStyle textButtonStyle;
    private Artist2D artist2D;
    private JsonHandler jsonHandler;
    private FileHandler fileHandler;
    private Constants constants;

    public StartMenu(final Game game) {
        this.game = game;

        jsonHandler = new JsonHandler();
        fileHandler = new FileHandler(Gdx.files.local("configFile.json").toString());

        artist2D = new Artist2D();

        constants = new Constants();

        startButtonBorderWidth = 177;
        startButtonBorderHeight = 47.5f;
        startButtonBorderPos = new Vector2(Gdx.graphics.getWidth()/2f - 105, Gdx.graphics.getHeight() - 280.5f);

        twoPlayerBorderWidth = 200;
        twoPlayerBorderHeight = 47.5f;
        twoPlayerBorderPos = new Vector2(Gdx.graphics.getWidth()/2f - 117.5f, Gdx.graphics.getHeight() - 346);

        settingsBorderWidth = 200;
        settingsBorderHeight = 47.5f;
        settingsBorderPos = new Vector2(Gdx.graphics.getWidth()/2f - 117.5f, Gdx.graphics.getHeight() - 411.5f);

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
        drawButtonBordersOnHover();
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
        pixelLabelFont.dispose();
        pixelButtonFont.dispose();
        stage.dispose();
    }

    private void setupStage() {
        stage = new Stage(new ScreenViewport());
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
                    if (!fileHandler.fileExists()) {
                        fileHandler.createFile();
                        jsonHandler.serialize(constants, fileHandler.getFile());
                    } else {
                        constants = (Constants) jsonHandler.deserialize(fileHandler.getFile(), constants);
                        constants.setStaticFieldsToInstanceFields();
                    }

                game.setScreen(new OnePlayerPong(game));
                dispose();
            }
        });

        twoPlayerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TwoPlayerPong(game));
                dispose();
            }
        });

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!fileHandler.fileExists()) {
                    fileHandler.createFile();
                    jsonHandler.serialize(constants, fileHandler.getFile());
                    constants.setStaticFieldsToInstanceFields();
                } else {
                    constants = (Constants) jsonHandler.deserialize(fileHandler.getFile(), constants);
                    constants.setStaticFieldsToInstanceFields();
                }

                game.setScreen(new SettingsScreen(game, artist2D, textButtonStyle, spriteBatch, constants, fileHandler, jsonHandler));
                hide();
            }
        });
    }

    private void drawButtonBordersOnHover() {
        startButton.drawBorderOnHover(artist2D, startButtonBorderPos, startButtonBorderWidth, startButtonBorderHeight, Color.WHITE);
        twoPlayerButton.drawBorderOnHover(artist2D, twoPlayerBorderPos, twoPlayerBorderWidth, twoPlayerBorderHeight, Color.WHITE);
        settingsButton.drawBorderOnHover(artist2D, settingsBorderPos, settingsBorderWidth, settingsBorderHeight, Color.WHITE);
    }
}