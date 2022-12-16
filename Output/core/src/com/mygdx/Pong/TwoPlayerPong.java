package com.mygdx.Pong;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.Pong.Engine.Audio.AudioHandler;
import com.mygdx.Pong.Engine.Math.Vector2;
import com.mygdx.Pong.Engine.Shapes.Classes.Circle;
import com.mygdx.Pong.Engine.Shapes.Classes.Collisions;
import com.mygdx.Pong.Screens.StartMenu;

public class TwoPlayerPong implements Screen {
	private final ShapeRenderer shapeRenderer;
	private final Player playerOne;
	private final Player playerTwo;
	private final Circle ball;
	private final Collisions collisions;
	private final OrthographicCamera camera;
	private final ScoreUI scoreUI;
	private Game game;
	private ExtendViewport extendViewport;
	private AudioHandler audioHandler;

	public TwoPlayerPong(Game game) {
		this.game = game;

		audioHandler = new AudioHandler(AudioHandler.newSound(Gdx.files.internal("Text 1.mp3")));

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		extendViewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				0, Gdx.graphics.getHeight(), camera);

		shapeRenderer = new ShapeRenderer();

		playerOne = new Player(50f, 50f, 30f, 100f);
		playerTwo = new Player(Gdx.graphics.getWidth() - 50, 50, 30f, 100f);
		ball = new Circle(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f, 10f,
				new Vector2(-Constants.MAX_BALL_SPEED, 10));

		scoreUI = new ScoreUI(this.game, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

		collisions = new Collisions();
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0, 1);
		camera.update(true);

		scoreUI.drawScores(playerOne.getScore(), playerTwo.getScore());
		scoreUI.act(Gdx.graphics.getDeltaTime());
		scoreUI.draw();

		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.setColor(Color.WHITE);

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.rect(playerOne.getRectangle().getX(), playerOne.getRectangle().getY(), playerOne.getRectangle().getWidth(),
				playerOne.getRectangle().getHeight());
		shapeRenderer.rect(playerTwo.getRectangle().getX(), playerTwo.getRectangle().getY(), playerTwo.getRectangle().getWidth(),
				playerTwo.getRectangle().getHeight());
		shapeRenderer.circle(ball.getX(), ball.getY(), ball.getRadius());
		shapeRenderer.end();

		shapeRenderer.setColor(Color.RED);

		moveBall();
		keepBallInBounds();
		checkPlayerScored();
		playerOne.wasdMove();
		playerTwo.arrowKeyMove();
		playerOne.keepInBounds();
		playerTwo.keepInBounds();

		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			game.setScreen(new StartMenu(game));
			dispose();
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
			if (ball.getVelocity().x > 0) {
				ball.setVelocityX(-ball.getVelocity().x - playerTwo.getRectangle().getWidth());
			} else {
				ball.setVelocityX(-ball.getVelocity().x + playerOne.getRectangle().getWidth());
			}
		}
	}

	@Override
	public void show() {

	}

	@Override
	public void resize(int width, int height) {
		extendViewport.update(width, height, false);
		scoreUI.resize(width, height);
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
		shapeRenderer.dispose();
	}

	private void moveBall() {
		ball.x +=  ball.getVelocity().x * Gdx.graphics.getDeltaTime();
		ball.y += ball.getVelocity().y * Gdx.graphics.getDeltaTime();

		if (collisions.isCollidingCircleRect(playerOne.getRectangle(), ball)) {
			float theta = collisions.calculateDeflectionAngle(playerOne.getRectangle(), ball);
			float newBallSpeedX = Math.abs((MathUtils.cos(theta)) * Constants.MAX_BALL_SPEED);
			float newBallSpeedY = -(MathUtils.sin(theta) * Constants.MAX_BALL_SPEED);
			float oldSign = Math.signum(ball.getVelocity().x);
			ball.setVelocityX(newBallSpeedX * (-1 * oldSign));
			ball.setVelocityY(newBallSpeedY);
			collisions.reset();

			audioHandler.playSound(Constants.VOLUME, 0.3f);

			System.out.println("Ball velocityX: " + ball.getVelocity().x);
			System.out.println("Ball velocityY: " + ball.getVelocity().y);
		} else if (collisions.isCollidingCircleRect(playerTwo.getRectangle(), ball)) {

			float theta = collisions.calculateDeflectionAngle(playerTwo.getRectangle(), ball);
			float newBallSpeedX = Math.abs((MathUtils.cos(theta)) * Constants.MAX_BALL_SPEED);
			float newBallSpeedY = -(MathUtils.sin(theta) * Constants.MAX_BALL_SPEED);
			float oldSign = Math.signum(ball.getVelocity().x);
			ball.setVelocityX(newBallSpeedX * (-1 * oldSign));
			ball.setVelocityY(newBallSpeedY);
			collisions.reset();

			audioHandler.playSound(Constants.VOLUME, 0.3f);

			System.out.println("Ball velocityX: " + ball.getVelocity().x);
			System.out.println("Ball velocityY: " + ball.getVelocity().y);
		}
	}

	private void checkPlayerScored() {
		if (ball.getX() > Constants.RIGHT_GOAL_X) {
			playerOne.updateScore(1);
			resetBall();
		}

		if (ball.getX() < Constants.LEFT_GOAL_X) {
			playerTwo.updateScore(1);
			resetBall();
		}
	}

	private void keepBallInBounds() {
		if (ball.getY() > (extendViewport.getWorldHeight() - 5) || ball.getY() <= 0) {
			float theta = collisions.calculateDeflectionAngle(extendViewport.getWorldHeight()/2f, ball);
			float newBallSpeedX = Math.abs((MathUtils.cos(theta)) * Constants.MAX_BALL_SPEED);
			float newBallSpeedY = -(MathUtils.sin(theta) * -Constants.MAX_BALL_SPEED);
			float oldSign = Math.signum(ball.getVelocity().x);
			ball.setVelocityX(-newBallSpeedX * (-1 * oldSign));
			ball.setVelocityY(newBallSpeedY);
			collisions.reset();
		}
	}

	private void resetBall() {
		ball.setCenter(Constants.CENTER_SCREEN);

		float randomX = MathUtils.random(0, 1);
		ball.setVelocityX((randomX == 0) ? -Constants.MAX_BALL_SPEED : Constants.MAX_BALL_SPEED);
		ball.setVelocityY(10);
		System.out.println("Ball velocityX: " + ball.getVelocity().x);
		System.out.println("Ball velocityY: " + ball.getVelocity().y);
	}
}