package com.mygdx.Pong.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.Pong.Constants;
import com.mygdx.Pong.Engine.Files.FileHandler;
import com.mygdx.Pong.Engine.Json.JsonHandler;
import com.mygdx.Pong.Engine.Math.Vector2;
import com.mygdx.Pong.Engine.Shapes.Classes.Rectangle;
import com.mygdx.Pong.Engine.UI.Artist2D;
import com.mygdx.Pong.Engine.UI.Slider;

public class SoundScreen implements Screen {
    private Stage stage;
    private Slider volumeSlider;
    private Vector2 volumeSliderPos, volumeSliderSize;
    private Game game;
    private Artist2D artist2D;
    private SpriteBatch spriteBatch;
    private Constants constants;
    private FileHandler fileHandler;
    private JsonHandler jsonHandler;

    public SoundScreen(Game game, Artist2D artist2D, SpriteBatch spriteBatch, Constants constants, FileHandler fileHandler, JsonHandler jsonHandler) {
        this.game = game;
        this.artist2D = artist2D;
        this.spriteBatch = spriteBatch;
        this.constants = constants;
        this.fileHandler = fileHandler;
        this.jsonHandler = jsonHandler;

        volumeSliderPos = new Vector2(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f);
        volumeSliderSize = new Vector2(200, 100);

        stage = new Stage(new ScreenViewport());
        volumeSlider = new Slider(0, 10, 1,false, volumeSliderPos, volumeSliderSize,
                new Rectangle(volumeSliderPos.x, volumeSliderPos.y, 10, 25));
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        volumeSlider.draw(artist2D, Color.WHITE);
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
