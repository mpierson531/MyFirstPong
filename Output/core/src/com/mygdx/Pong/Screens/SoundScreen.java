package com.mygdx.Pong.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.Pong.Constants;
import com.mygdx.Pong.Engine.Files.FileHandler;
import com.mygdx.Pong.Engine.Json.JsonHandler;
import com.mygdx.Pong.Engine.Math.Vector2;
import com.mygdx.Pong.Engine.Shapes.Classes.Rectangle;
import com.mygdx.Pong.Engine.UI.Artist2D;
import com.mygdx.Pong.Engine.UI.CheckBox;
import com.mygdx.Pong.Engine.UI.Label;
import com.mygdx.Pong.Engine.UI.Slider;
import com.mygdx.Pong.Engine.UI.TextButton;

public class SoundScreen implements Screen {
    private Stage stage;
    private Slider volumeSlider;
    private Label volumeLabel, muteLabel;
    private CheckBox muteCheckbox;
    private TextButton backButton;
    private com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle textButtonStyle;
    private Game game;
    private Artist2D artist2D;
    private SpriteBatch spriteBatch;
    private Constants constants;
    private FileHandler fileHandler;
    private JsonHandler jsonHandler;
    private Color buttonPressColor;

    public SoundScreen(Game game,
                       final TextButton.TextButtonStyle textButtonStyle,
                       final Artist2D artist2D,
                       final SpriteBatch spriteBatch,
                       Constants constants,
                       final FileHandler fileHandler,
                       final JsonHandler jsonHandler,
                       BitmapFont font,
                       final Color buttonPressColor) {
        this.game = game;
        this.artist2D = artist2D;
        this.spriteBatch = spriteBatch;
        this.constants = constants;
        this.fileHandler = fileHandler;
        this.jsonHandler = jsonHandler;

        this.buttonPressColor = buttonPressColor;

        com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle labelStyle = new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle();
        com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle checkBoxStyle = new com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle();
        this.textButtonStyle = new com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle();
        labelStyle.font = font;
        checkBoxStyle.font = font;
        this.textButtonStyle.font = font;

        Vector2 volumeLabelPos = new Vector2(Gdx.graphics.getWidth() / 2f - 365, Gdx.graphics.getHeight() / 2f - 125);
        Vector2 volumeLabelSize = new Vector2(100, 47.5f);
        Vector2 volumeSliderPos = new Vector2(Gdx.graphics.getWidth() / 2f + 75, volumeLabelPos.y + 5);
        Vector2 volumeSliderSize = new Vector2(300, 50);

        Vector2 muteLabelPos = new Vector2(volumeLabelPos.x, Gdx.graphics.getHeight() / 2f + 5);
        Vector2 muteLabelSize = new Vector2(100, 47.5f);
        Vector2 muteCheckboxPos = new Vector2(volumeSliderPos.x + 190, muteLabelPos.y + 5);
        Vector2 muteCheckboxSize = new Vector2(110, 47.5f);

        Vector2 backButtonPos = new Vector2(volumeLabelPos.x, Gdx.graphics.getHeight() - 63);
        Vector2 backButtonSize = new Vector2(100, 47.5f);

        volumeLabel = new Label("Volume", labelStyle, volumeLabelPos, volumeLabelSize);
        muteCheckbox = new CheckBox("False", checkBoxStyle, muteCheckboxPos, muteCheckboxSize, Color.WHITE);
        muteLabel = new Label("Mute", labelStyle, muteLabelPos, muteLabelSize);
        volumeSlider = new Slider(0, 1, 0.1f,false, volumeSliderPos, volumeSliderSize,
                new Rectangle(volumeSliderPos.x, volumeSliderPos.y, 20, volumeSliderSize.y));
        backButton = new TextButton("Back", this.textButtonStyle, backButtonPos, backButtonSize);

        muteCheckbox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (muteCheckbox.isChecked()) {
                    muteCheckbox.setText("True");
                    SoundScreen.this.constants.setMute(true);
                    volumeSlider.setValue(0);
                } else {
                    muteCheckbox.setText("False");
                    SoundScreen.this.constants.setMute(false);
                }
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SoundScreen.this.constants.setVolume(volumeSlider.getValue());
                SoundScreen.this.constants.setStaticFieldsToInstanceFields();
                SoundScreen.this.game.setScreen(new SettingsScreen(SoundScreen.this.game,
                        SoundScreen.this.artist2D,
                        SoundScreen.this.textButtonStyle,
                        SoundScreen.this.spriteBatch,
                        SoundScreen.this.constants,
                        SoundScreen.this.fileHandler,
                        SoundScreen.this.jsonHandler,
                        SoundScreen.this.buttonPressColor));
                SoundScreen.this.dispose();
            }
        });

        stage = new Stage(new ScreenViewport());
        stage.addActor(volumeLabel);
        stage.addActor(muteCheckbox);
        stage.addActor(muteLabel);
        stage.addActor(backButton);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        drawCheckBoxStyle(buttonPressColor);
        drawLabelUnderlines();
        drawBackButtonBorderOnHover();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        volumeSlider.draw(artist2D, Color.WHITE);
    }

    private void drawCheckBoxStyle(Color muteCheckboxPressColor) {
        artist2D.drawLineActor(muteCheckbox, Color.WHITE);

        if (muteCheckbox.isPressed()) {
            artist2D.drawFilledActor(muteCheckbox, muteCheckboxPressColor);
        }
    }

    private void drawLabelUnderlines() {
        artist2D.drawFilledActor(volumeLabel.getX(), volumeLabel.getY(),
                volumeSlider.getX() + volumeSlider.getWidth() - volumeLabel.getX(), 2.5f, Color.WHITE);
        artist2D.drawFilledActor(muteLabel.getX(), muteLabel.getY(),
                muteCheckbox.getX() + muteCheckbox.getWidth() - muteLabel.getX(), 2.5f, Color.WHITE);
    }

    private void drawBackButtonBorderOnHover() {
        backButton.drawBorderOnHover(artist2D, Color.WHITE);
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
    }
}
