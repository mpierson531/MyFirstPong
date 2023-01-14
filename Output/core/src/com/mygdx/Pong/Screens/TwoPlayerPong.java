package com.mygdx.Pong.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.Pong.Constants;
import com.mygdx.Pong.Engine.Audio.AudioHandler;
import com.mygdx.Pong.Engine.Math.Vector2;
import com.mygdx.Pong.Engine.Shapes.Circle;
import com.mygdx.Pong.Engine.Shapes.Collisions;
import com.mygdx.Pong.Engine.UI.Artist2D;
import com.mygdx.Pong.Player;
import com.mygdx.Pong.ScoreUI;

public class TwoPlayerPong implements Screen {
	private final Player playerOne;
	private final Player playerTwo;
	private final Circle ball;
	private final Collisions collisions;
	private final OrthographicCamera camera;
	private final ScoreUI scoreUI;
	private Game game;
	private ExtendViewport extendViewport;
	private AudioHandler audioHandler;
	private SpriteBatch spriteBatch;
	private final Sprite paddleSprite, ballSprite;

	public TwoPlayerPong(Game game, SpriteBatch spriteBatch, Artist2D artist2D) {
		this.game = game;
		this.spriteBatch = spriteBatch;

		audioHandler = new AudioHandler(AudioHandler.newSound(Gdx.files.internal("Text 1.mp3")));

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		extendViewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				0, Gdx.graphics.getHeight(), camera);

		playerOne = new Player(50f, 50f, 30f, 100f);
		playerTwo = new Player(Gdx.graphics.getWidth() - 50, 50, 30f, 100f);
		ball = new Circle(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f, 10f,
				new Vector2(-Constants.MAX_BALL_SPEED, 10));

		scoreUI = new ScoreUI(this.game, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera, artist2D);

		collisions = new Collisions();

		paddleSprite = new Sprite(new Texture(artist2D.getPixmap(30, 100, Pixmap.Format.RGBA8888,
				Constants.PADDLE_COLOR, "rectangle", "filled")));
		ballSprite = new Sprite(new Texture(artist2D.getPixmap(20, 20, Pixmap.Format.RGBA8888,
				Constants.BALL_COLOR, "circle", "filled")));
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0, 1);
		camera.update(true);

		scoreUI.act(Gdx.graphics.getDeltaTime());
		scoreUI.draw();
		spriteBatch.begin();
		scoreUI.drawScores(playerOne.getScore(), playerTwo.getScore(), spriteBatch);
		spriteBatch.setColor(Constants.PADDLE_COLOR);
		spriteBatch.draw(paddleSprite, playerOne.getRectangle().getX(), playerOne.getRectangle().getY());
		spriteBatch.draw(paddleSprite, playerTwo.getRectangle().getX(), playerTwo.getRectangle().getY());
		spriteBatch.setColor(Constants.BALL_COLOR);
		spriteBatch.draw(ballSprite, ball.getX() - ball.getRadius(), ball.getY() - ball.getRadius());
		spriteBatch.end();

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

		// Checking to see if player has escaped the ball from being stuck
		if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
			if (ball.getVelocity().x > 0) {
				ball.setVelocityX(-ball.getVelocity().x - playerTwo.getRectangle().getWidth());
			} else {
				ball.setVelocityX(-ball.getVelocity().x + playerOne.getRectangle().getWidth());
			}
			ball.setVelocityY(MathUtils.randomSign() == -1 ? -5 : 5);
		}
	}

	private void moveBall() {
		ball.setX(ball.getX() + ball.getVelocity().x * Gdx.graphics.getDeltaTime());
		ball.setY(ball.getY() + ball.getVelocity().y * Gdx.graphics.getDeltaTime());

		if (collisions.isCollidingCircleRect(playerOne.getRectangle(), ball)) {
			float theta = collisions.calculateDeflectionAngle(playerOne.getRectangle(), ball);
			float newBallSpeedX = Math.abs((MathUtils.cos(theta)) * Constants.MAX_BALL_SPEED);
			float newBallSpeedY = (-MathUtils.sin(theta) * Constants.MAX_BALL_SPEED);
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
			float newBallSpeedY = (-MathUtils.sin(theta) * Constants.MAX_BALL_SPEED);
			float oldSign = Math.signum(ball.getVelocity().x);
			ball.setVelocityX(newBallSpeedX * (-1 * oldSign));
			ball.setVelocityY(newBallSpeedY);
			collisions.reset();

			audioHandler.playSound(Constants.VOLUME, 0.3f);

			System.out.println("Ball velocityX: " + ball.getVelocity().x);
			System.out.println("Ball velocityY: " + ball.getVelocity().y);
		}
	}

	@Override
	public void resize(int width, int height) {
		extendViewport.update(width, height, false);
		scoreUI.resize(width, height);
	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
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
	public void show() {

	}
}