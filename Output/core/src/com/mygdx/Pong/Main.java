package com.mygdx.Pong;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.Pong.Shapes.Classes.Rectangle;
import com.mygdx.Pong.Shapes.Classes.Circle;
import com.mygdx.Pong.Shapes.Classes.Utils;

public class Main extends ApplicationAdapter {
	private ShapeRenderer shapeRenderer;
	private Rectangle player, comPlayer;
	private Circle ball;
	private boolean playerPastBoundaries;
	private boolean compPlayerPastBoundaries;
	private boolean ballPastBoundaries;
	private boolean playerAboveXAxis, comPlayerAboveXAxis;
	private boolean ballIsAboveXAxis, ballIsPastYAxis;
	private Vector2 distancePlayer, distanceComPlayer;
	private float ballSpeedX, ballSpeedY;
	private OrthographicCamera camera;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		shapeRenderer = new ShapeRenderer();

		// Shape initialization
		player = new Rectangle(50f, 50f, 50f, 100f);
		player = new Rectangle(50f, 50f, 50f, 100f);
		comPlayer = new Rectangle(750f, 50f, 50f, 100f);
		ball = new Circle(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 10f);

		playerPastBoundaries = false;
		compPlayerPastBoundaries = false;
		ballPastBoundaries = false;

		ballSpeedX = -100;
		ballSpeedY = 0;
	}

	@Override
	public void render() {
		ScreenUtils.clear(0,0,0,1);
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.setColor(Color.WHITE);
		camera.update();

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

		shapeRenderer.rect(player.x, player.y, player.width, player.height);
		shapeRenderer.rect(comPlayer.x, comPlayer.y, comPlayer.width, comPlayer.height);
		shapeRenderer.circle(ball.x, ball.y, ball.radius);

		shapeRenderer.end();

		moveBall();
		checkForInput();
		updatePlayerCoordBools();
		updateBallCoordBools();
		keepPlayersInBounds();
		checkBallPastBounds();
	}
	
	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}

	private void moveBall() {
		ball.x += ballSpeedX * Gdx.graphics.getDeltaTime();
		ball.y += ballSpeedY * Gdx.graphics.getDeltaTime();

		Vector2 closestPointPlayer = new Vector2(ball.getCenter());

		if (ball.getCenterX() < player.getMinX()) {
			closestPointPlayer.x = player.getMinX();
		} else if (ball.getCenterX() > player.getMaxX()) {
			closestPointPlayer.x = player.getMaxX();
		}

		if (ball.getCenterY() < player.getMinY()) {
			closestPointPlayer.y = player.getMinY();
		} else if (ball.getCenterY() > player.getMaxY()) {
			closestPointPlayer.y = player.getMaxY();
		}

		distancePlayer = new Vector2(ball.getCenter().sub(closestPointPlayer));

		if (Utils.isColliding(player, ball))
			ballSpeedX = -ballSpeedX;


//		moveBallIfCollision();
	}

	private void checkForInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) player.y += 175 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) player.y -= 175 * Gdx.graphics.getDeltaTime();

		if (Gdx.input.isKeyPressed(Input.Keys.W)) player.y += 175 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Input.Keys.S)) player.y -= 175 * Gdx.graphics.getDeltaTime();
	}

	public void keepPlayersInBounds() {
		if (player.y < 20f) player.y = 20f;
		if (player.y > 360f) player.y = 360f;

		if (comPlayer.y < 20f) comPlayer.y = 20f;
		if (comPlayer.y > 360f) comPlayer.y = 360f;
	}

	private void checkBallPastBounds() {
		if (ball.x > 800f || ball.x < 0f || ball.y > 480f || ball.y < 0f)
			resetBall();
	}

	private void moveBallIfCollision() { // Super method for collision
		if (ball.isColliding(player) && playerAboveXAxis) {
			ballSpeedX += -100f;
			ballSpeedY += -100f;
		}
		else if (ball.isColliding(player) && !playerAboveXAxis) {
			ball.x += (ball.x * 2) * Gdx.graphics.getDeltaTime();
			ball.y += (ball.x * 2) * Gdx.graphics.getDeltaTime();
		}
		else if (ball.isColliding(comPlayer) && comPlayerAboveXAxis) {
			ball.x -= (ball.x * 2) * Gdx.graphics.getDeltaTime();
			ball.y -= (ball.y * 2) * Gdx.graphics.getDeltaTime();
		}
		else if (ball.isColliding(comPlayer) && !comPlayerAboveXAxis) {
			ball.x -= (ball.x * 2) * Gdx.graphics.getDeltaTime();
			ball.y += (ball.y * 2) * Gdx.graphics.getDeltaTime();
		}
	}

	private void updatePlayerCoordBools() {
		if (player.y > Gdx.graphics.getHeight() / 2)
			playerAboveXAxis = true;
		else if (player.y < Gdx.graphics.getHeight() / 2)
			playerAboveXAxis = false;

		if (comPlayer.y > Gdx.graphics.getHeight() / 2)
			comPlayerAboveXAxis = true;
		else if (comPlayer.y < Gdx.graphics.getHeight() / 2)
			comPlayerAboveXAxis = false;
	}

	private void updateBallCoordBools() {
		if (ball.y > Gdx.graphics.getHeight() / 2) playerAboveXAxis = true;
		else ballIsAboveXAxis = false;

		if (ball.x > Gdx.graphics.getWidth() / 2) ballIsPastYAxis = true;
		else ballIsPastYAxis = false;
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