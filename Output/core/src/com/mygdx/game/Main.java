package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
	private ShapeRenderer shapeRenderer;
	private Rectangle player;
	private Rectangle compPlayer;
	private Circle ball;
	private boolean playerPastBoundaries;
	private boolean compPlayerPastBoundaries;
	private boolean ballPastBoundaries;
	private float ballSpeed;
	private OrthographicCamera camera;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		shapeRenderer = new ShapeRenderer();

		player = new Rectangle(50f, 50f, 50f, 100f);
		compPlayer = new Rectangle(750f, 50f, 50f, 100f);
		ball = new Circle(375f, 50f, 10f);

		playerPastBoundaries = false;
		compPlayerPastBoundaries = false;
		ballPastBoundaries = false;

		ballSpeed = 50f;
	}

	@Override
	public void render() {
		ScreenUtils.clear(0,0,0,1);
		shapeRenderer.setProjectionMatrix(camera.combined);
		camera.update();

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

		shapeRenderer.setColor(Color.WHITE);
		shapeRenderer.rect(player.x, player.y, player.width, player.height);
		shapeRenderer.rect(compPlayer.x, compPlayer.y, compPlayer.width, compPlayer.height);
		shapeRenderer.circle(ball.x, ball.y, ball.radius);

		shapeRenderer.end();

		checkForInput();
		checkPlayerPastBounds();
		checkBallPastBounds();
	}
	
	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}

	private void checkForInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) player.y += 175 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) player.y -= 175 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Input.Keys.W)) player.y += 175 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Input.Keys.S)) player.y -= 175 * Gdx.graphics.getDeltaTime();
	}

	public void checkPlayerPastBounds() {
		if (player.y > 395f || player.y < 5f) {
			keepPlayerInBounds(player);
		}

		if (compPlayer.y > 395f || compPlayer.y < 5f) {
			keepPlayerInBounds(compPlayer);
		}
	}

	private void checkBallPastBounds() {
		if (ball.x > 800f || ball.x < 0f || ball.y > 480f || ball.y < 0f)
			resetBall();
	}

	private void keepPlayerInBounds(Rectangle whatPlayer) {
		whatPlayer.x = whatPlayer.x;
		whatPlayer.y = whatPlayer.y;
	}

	private void resetPlayer(Rectangle whatPlayer) {
		if (whatPlayer == player)
			whatPlayer.x = 50f;
		else
			whatPlayer.x = 750f;

		whatPlayer.y = 50f;
	}

	private void resetBall() {
		ball.x = Gdx.graphics.getWidth() / 2;
		ball.y = Gdx.graphics.getHeight() / 2;
	}
}