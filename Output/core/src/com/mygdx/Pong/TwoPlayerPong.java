package com.mygdx.Pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.Pong.Engine.Shapes.Classes.Circle;
import com.mygdx.Pong.Engine.Shapes.Classes.Collisions;

public class TwoPlayerPong implements Screen {
	private final ShapeRenderer shapeRenderer;
	private final Player playerOne;
	private final Player playerTwo;
	private final Circle ball;
	private final Collisions collisions;
	private final OrthographicCamera camera;
	private final ScoreUI scoreUI;

	public TwoPlayerPong() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		shapeRenderer = new ShapeRenderer();

		// Shape inheritor initialization
		playerOne = new Player(50f, 50f, 30f, 100f);
		playerTwo = new Player(Gdx.graphics.getWidth() - 50, 50, 30f, 100f);
		ball = new Circle(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f, 10f, new Vector2(-Constants.MAX_BALL_SPEED, 10));

		scoreUI = new ScoreUI();

		collisions = new Collisions();
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0,0,0,1);

		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.setColor(Color.WHITE);
		camera.update();

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

		shapeRenderer.rect(playerOne.getRectangle().getX(), playerOne.getRectangle().getY(), playerOne.getRectangle().getWidth(), playerOne.getRectangle().getHeight());
		shapeRenderer.rect(playerTwo.getRectangle().getX(), playerTwo.getRectangle().getY(), playerTwo.getRectangle().getWidth(), playerTwo.getRectangle().getHeight());
		shapeRenderer.circle(ball.x, ball.y, ball.radius);

		shapeRenderer.end();

		scoreUI.drawScores(playerOne.getScore(), playerTwo.getScore(), camera);

//		scoreUI.getStage().act(Gdx.graphics.getDeltaTime());

		moveBall();
		keepBallInBounds();
		checkPlayerScored();
		playerOne.wasdMove();
		playerTwo.arrowKeyMove();
		playerOne.keepInBounds();
		playerTwo.keepInBounds();
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

			System.out.println("Ball velocityX: " + ball.getVelocity().x);
			System.out.println("Ball velocityY: " + ball.getVelocity().y);
		}
	}

	private void checkPlayerScored() {
		if (ball.getX() > Gdx.graphics.getWidth() + 2.5f) {
			playerOne.updateScore();
			resetBall();
		}

		if (ball.getX() < 0 - 2.5f) {
			playerTwo.updateScore();
			resetBall();
		}
	}

	private void keepBallInBounds() {
		if ((ball.getY() > Gdx.graphics.getHeight() || (ball.getY() < 0)) && (ball.getVelocity().x > 0)) {
			float theta = collisions.calculateDeflectionAngle(Gdx.graphics.getHeight()/2f, ball);
			float newBallSpeedX = Math.abs((MathUtils.cos(theta)) * Constants.MAX_BALL_SPEED);
			float newBallSpeedY = -(MathUtils.sin(theta) * -Constants.MAX_BALL_SPEED);
			float oldSign = Math.signum(ball.getVelocity().x);
			ball.setVelocityX(-newBallSpeedX * (-1 * oldSign));
			ball.setVelocityY(newBallSpeedY);
			collisions.reset();
		} else if ((ball.getY() > Gdx.graphics.getHeight() || (ball.getY() < 0)) && (ball.getVelocity().x < 0)) {
			float theta = collisions.calculateDeflectionAngle(Gdx.graphics.getHeight()/2f, ball);
			float newBallSpeedX = Math.abs((MathUtils.cos(theta)) * Constants.MAX_BALL_SPEED);
			float newBallSpeedY = -(MathUtils.sin(theta) * -Constants.MAX_BALL_SPEED);
			float oldSign = Math.signum(ball.getVelocity().x);
			ball.setVelocityX(-newBallSpeedX * (-1 * oldSign));
			ball.setVelocityY(newBallSpeedY);
			collisions.reset();
		}
	}

	private void resetBall() {
		ball.setCenter(new Vector2(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f));

		float randomX = MathUtils.random(0, 1);
		ball.setVelocityX((randomX == 0) ? -Constants.MAX_BALL_SPEED : Constants.MAX_BALL_SPEED);
		ball.setVelocityY(10);
		System.out.println("Ball velocityX: " + ball.getVelocity().x);
		System.out.println("Ball velocityY: " + ball.getVelocity().y);
	}
}