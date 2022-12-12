package com.mygdx.Pong.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
    private Label volumeLabel;
    private CheckBox muteCheckbox;
    private TextButton backButton;
    private com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle textButtonStyle;
    private com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle checkBoxStyle;
    private com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle labelStyle;
    private Vector2 volumeSliderPos, volumeSliderSize;
    private Vector2 volumeLabelPos, volumeLabelSize;
    private Vector2 muteCheckboxPos, muteCheckboxSize;
    private Vector2 backButtonPos, backButtonSize;
    private Game game;
    private Artist2D artist2D;
    private SpriteBatch spriteBatch;
    private Constants constants;
    private FileHandler fileHandler;
    private JsonHandler jsonHandler;

    public SoundScreen(Game game, final TextButton.TextButtonStyle textButtonStyle, final Artist2D artist2D, final SpriteBatch spriteBatch, final Constants constants, final FileHandler fileHandler, final JsonHandler jsonHandler, BitmapFont font) {
        this.game = game;
        this.artist2D = artist2D;
        this.spriteBatch = spriteBatch;
        this.constants = constants;
        this.fileHandler = fileHandler;
        this.jsonHandler = jsonHandler;

        labelStyle = new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle();
        checkBoxStyle = new com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle();
        this.textButtonStyle = new com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle();
        labelStyle.font = font;
        checkBoxStyle.font = font;
        checkBoxStyle.checkedFontColor = Color.BLACK;
        // TODO: Maybe come back and change checkbox style
//        checkBoxStyle.downFontColor = Color.BLACK;
//        checkBoxStyle.checkedDownFontColor = Color.WHITE;
        this.textButtonStyle.font = font;

        volumeLabelPos = new Vector2(Gdx.graphics.getWidth()/2f - 365, Gdx.graphics.getHeight()/2f - 125);
        volumeLabelSize = new Vector2(100, 47.5f);
        volumeSliderPos = new Vector2(Gdx.graphics.getWidth()/2f + 75, volumeLabelPos.y + 5);
        volumeSliderSize = new Vector2(300, 50);
        muteCheckboxPos = new Vector2(Gdx.graphics.getWidth()/2f - 365, Gdx.graphics.getHeight()/2f + 4);
        muteCheckboxSize = new Vector2(100, 47.5f);
        backButtonPos = new Vector2(Gdx.graphics.getWidth()/2f - 365, Gdx.graphics.getHeight() - 63);
        backButtonSize = new Vector2(100, 47.5f);

        volumeLabel = new Label("Volume", labelStyle, volumeLabelPos, volumeLabelSize);
        muteCheckbox = new CheckBox("Mute", checkBoxStyle, muteCheckboxPos, muteCheckboxSize, Color.WHITE);
        volumeSlider = new Slider(0, 10, 1,false, volumeSliderPos, volumeSliderSize,
                new Rectangle(volumeSliderPos.x, volumeSliderPos.y, 20, volumeSliderSize.y));
        backButton = new TextButton("Back", this.textButtonStyle, backButtonPos, backButtonSize);

        muteCheckbox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (muteCheckbox.isChecked()) {
                    constants.setMute(true);
                } else {
                    constants.setMute(false);
                }
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                constants.setVolume(volumeSlider.getValue());
                constants.setStaticFieldsToInstanceFields();
                SoundScreen.this.game.setScreen(new SettingsScreen(SoundScreen.this.game, artist2D, textButtonStyle,
                        spriteBatch, constants, fileHandler, jsonHandler));
            }
        });

        stage = new Stage(new ScreenViewport());
        stage.addActor(volumeLabel);
        stage.addActor(muteCheckbox);
        stage.addActor(backButton);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        drawCheckBoxStyle();
        drawVolumeLabelToSliderStyle();
        drawBackButtonBorderOnHover();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        volumeSlider.draw(artist2D, Color.WHITE);
//        updateVolumeSliderValue();
    }

    private void drawCheckBoxStyle() {
        if (muteCheckbox.isChecked() && !muteCheckbox.isPressed()) {
            artist2D.drawFilledRectangle(muteCheckbox, Color.WHITE);
        }

        /*if (!muteCheckbox.isChecked() && muteCheckbox.isPressed()) {
            artist2D.drawFilledRectangle(muteCheckbox, new Color(0.8f, 0.8f, 0.8f, 1));
        }*/

        /*if (muteCheckbox.isChecked() && muteCheckbox.isPressed()) {
            artist2D.drawFilledRectangle(muteCheckbox, Color.BLACK);
        }*/
    }

    private void drawVolumeLabelToSliderStyle() {
        artist2D.drawFilledRectangle(volumeLabel.getX(), volumeLabel.getY(),
                volumeSlider.getX() + volumeSlider.getWidth() - volumeLabel.getX(), 2.5f, Color.WHITE);
    }

    private void drawBackButtonBorderOnHover() {
        backButton.drawBorderOnHover(artist2D, Color.WHITE);
    }

    private void updateVolumeLevel() {

    }

    @Override
    public void show() {

    }

    private void updateVolumeSliderValue() {
        volumeSlider.setValue(0);
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
}
