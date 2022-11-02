package com.mygdx.Pong;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.Pong.Engine.Shapes.Classes.Rectangle;
import com.mygdx.Pong.Engine.Shapes.Classes.Circle;
import com.mygdx.Pong.Engine.Shapes.Classes.Collisions;

public class Main extends ApplicationAdapter {
	private ShapeRenderer shapeRenderer;
	private Rectangle playerOne, comPlayer;
	private Circle ball;
	private Collisions collisions;

	private float movementSpeed;
	private OrthographicCamera camera;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		shapeRenderer = new ShapeRenderer();

		com.badlogic.gdx.graphics.

		// Shape inheritor initialization
		playerOne = new Rectangle(50f, 50f, 30f, 100f);
		comPlayer = new Rectangle(750f, 50f, 30f, 100f);
		ball = new Circle(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f, 10f, new Vector2(-Constants.MAX_BALL_SPEED, 10));

		collisions = new Collisions();

		movementSpeed = 115f;
	}

	@Override
	public void render() {
		ScreenUtils.clear(0,0,0,1);
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.setColor(Color.WHITE);
		camera.update();

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

		shapeRenderer.rect(playerOne.getX(), playerOne.getY(), playerOne.width, playerOne.height);
		shapeRenderer.rect(comPlayer.getX(), comPlayer.getY(), comPlayer.width, comPlayer.height);
		shapeRenderer.circle(ball.x, ball.y, ball.radius);

		shapeRenderer.end();

		moveBall();
		keepBallInBounds();
		checkBallPastGoals();
		moveComOpponent();
		checkInput();
		keepPlayersInBounds();
	}
	
	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}

	private void moveBall() {
		ball.x +=  ball.getVelocity().x * Gdx.graphics.getDeltaTime();
		ball.y += ball.getVelocity().y * Gdx.graphics.getDeltaTime();

		if (collisions.isCollidingCircleRect(playerOne, ball)) {
			float theta = collisions.calculateDeflectionAngle(playerOne, ball);
			float newBallSpeedX = Math.abs((MathUtils.cos(theta)) * Constants.MAX_BALL_SPEED);
			float newBallSpeedY = -(MathUtils.sin(theta) * Constants.MAX_BALL_SPEED);
			float oldSign = Math.signum(ball.getVelocity().x);
			ball.setVelocityX(newBallSpeedX * (-1 * oldSign));
			ball.setVelocityY(newBallSpeedY);
			collisions.reset();

			System.out.println("Ball velocityX: " + ball.getVelocity().x);
			System.out.println("Ball velocityY: " + ball.getVelocity().y);
		} else if (collisions.isCollidingCircleRect(comPlayer, ball)) {

			float theta = collisions.calculateDeflectionAngle(comPlayer, ball);
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

	private void moveComOpponent() {
		comPlayer.y += (ball.y - comPlayer.y) - 10f;
	}

	private void checkInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) playerOne.y += movementSpeed * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) playerOne.y -= movementSpeed * Gdx.graphics.getDeltaTime();

		if (Gdx.input.isKeyPressed(Input.Keys.W)) playerOne.y += movementSpeed * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Input.Keys.S)) playerOne.y -= movementSpeed * Gdx.graphics.getDeltaTime();
	}

	public void keepPlayersInBounds() {
		if (playerOne.y < 10f) playerOne.y = 10f;
		if (playerOne.y > 370f) playerOne.y = 370f;

		if (comPlayer.y < 10f) comPlayer.y = 10f;
		if (comPlayer.y > 370f) comPlayer.y = 370f;
	}

	private void checkBallPastGoals() {
		if (ball.getX() > Gdx.graphics.getWidth() || ball.getX() < 0)
			resetBall();
	}

	private void keepBallInBounds() {
		boolean ballPastHalfWidth = ball.getX() > Gdx.graphics.getWidth()/2f;
		boolean ballAboveBelowHeight = (ball.getY() > Gdx.graphics.getHeight()) || (ball.getY() < 0) && ballPastHalfWidth;

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

	private void resetPlayer(Rectangle whatPlayer) {
		if (whatPlayer == playerOne)
			whatPlayer.x = 50f;
		else
			whatPlayer.x = 750f;

		whatPlayer.y = 50f;
	}

	private void resetBall() {
		ball.setCenter(new Vector2(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f));

		float randomX = MathUtils.random(0, 1);
		ball.setVelocityX((randomX == 0) ? -200 : 200);
		ball.setVelocityY(10);
		System.out.println("Ball velocityX: " + ball.getVelocity().x);
		System.out.println("Ball velocityY: " + ball.getVelocity().y);
	}
}