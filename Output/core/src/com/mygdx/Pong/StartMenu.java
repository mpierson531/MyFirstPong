package com.mygdx.Pong;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class StartMenu implements Screen {
    private final Stage stage;
    private Label titleLabel;
    private TextButton startButton;
    private TextButton twoPlayerButton;
    private final FreeTypeFontGenerator fontGenerator;
    private final Game _game;

    public StartMenu(final Game game) {
        _game = game;

        stage = new Stage(new ScreenViewport());

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.absolute("C:\\Users\\Micah\\MyFirstPong\\Output\\assets\\PixeloidSans-nR3g1.ttf"));

        setupTitleLabel();
        setupTextButtons();
        setupStage();

        Gdx.input.setInputProcessor(stage);

        fontGenerator.dispose();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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

    }

    private void setupStage() {
        stage.addActor(titleLabel);
        stage.addActor(startButton);
        stage.addActor(twoPlayerButton);
    }

    private void setupTitleLabel() {
        FreeTypeFontParameter fontParams = new FreeTypeFontParameter();
        fontParams.size = 50;
        fontParams.mono = true;
        fontParams.color = Color.WHITE;
        fontParams.gamma = 5;

        BitmapFont labelFont = fontGenerator.generateFont(fontParams);
        LabelStyle labelStyle = new LabelStyle();
        labelStyle.fontColor = Color.WHITE;
        labelStyle.font = labelFont;

        titleLabel = new Label("My First Pong", labelStyle);
        titleLabel.setPosition(Gdx.graphics.getWidth()/2f - 200, Gdx.graphics.getHeight() - 100);
        titleLabel.setSize(50, 50);
    }

    private void setupTextButtons() {
        FreeTypeFontParameter buttonFontParams = new FreeTypeFontParameter();
        buttonFontParams.gamma = 5;
        buttonFontParams.size = 35;
        buttonFontParams.mono = true;
        buttonFontParams.color = Color.WHITE;
        BitmapFont buttonFont = fontGenerator.generateFont(buttonFontParams);

        BaseDrawable defaultDrawable = new BaseDrawable();
        defaultDrawable.setTopHeight(5);
        defaultDrawable.setBottomHeight(5);
        defaultDrawable.setLeftWidth(5);
        defaultDrawable.setRightWidth(5);
        TextButtonStyle textButtonStyle = new TextButtonStyle();
//        textButtonStyle.checked = null;
        textButtonStyle.font = buttonFont;

//        textButtonStyle.up = defaultDrawable;

        startButton = new TextButton("Start", textButtonStyle);
        twoPlayerButton = new TextButton("2 Player", textButtonStyle);

        startButton.setPosition(Gdx.graphics.getWidth()/2f - 35, Gdx.graphics.getHeight() - 275);
        startButton.setSize(35, 35);
        twoPlayerButton.setPosition(Gdx.graphics.getWidth()/2f - 33, Gdx.graphics.getHeight() - 340);
        twoPlayerButton.setSize(35, 35);

        setupTextButtonListeners();
    }

    private void setupTextButtonListeners() {
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                _game.setScreen(new OnePlayerPong());
                dispose();
            }
        });

        twoPlayerButton.addListener(new ClickListener() {
           @Override
           public void clicked(InputEvent event, float x, float y) {
               _game.setScreen(new TwoPlayerPong());
               dispose();
           }
        });
    }

    public Stage getStage() {
        return stage;
    }
}