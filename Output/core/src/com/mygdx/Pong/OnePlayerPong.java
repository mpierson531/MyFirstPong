package com.mygdx.Pong;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.Pong.Engine.Math.Vector2;
import com.mygdx.Pong.Engine.Shapes.Classes.Circle;
import com.mygdx.Pong.Engine.Shapes.Classes.Collisions;
import com.mygdx.Pong.Engine.Shapes.Classes.Rectangle;

public class OnePlayerPong implements Screen {
	private final ShapeRenderer shapeRenderer;
	private final Player playerOne;
	private final ComPlayer comPlayer;
	private final Circle ball;
	private final Collisions collisions;
	private final OrthographicCamera camera;
	private final ScoreUI scoreUI;
	private Circle ballClone;
	private Game game;
	private ExtendViewport extendViewport;
	private Rectangle topBound, bottomBound;

	public OnePlayerPong(Game game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		extendViewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				0, Gdx.graphics.getHeight(), camera);

		shapeRenderer = new ShapeRenderer();

		// Shape inheritor initialization
		playerOne = new Player(50f, 50f, 30f, 100f);
		comPlayer = new ComPlayer();
		ball = new Circle(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f, 10f, new Vector2(-Constants.MAX_BALL_SPEED, 10));
		ballClone = ball.clone();
		topBound = new Rectangle(0, Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), 2);
		bottomBound = new Rectangle(0, 0, Gdx.graphics.getWidth(), 2);

		scoreUI = new ScoreUI(this.game, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

		collisions = new Collisions();
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0, 1);
//		extendViewport.apply(true);
		camera.update(true);

		scoreUI.drawScores(playerOne.getScore(), comPlayer.getScore(), camera);
		scoreUI.act(Gdx.graphics.getDeltaTime());
		scoreUI.draw();

		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.setColor(Color.WHITE);

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.rect(playerOne.getRectangle().getX(), playerOne.getRectangle().getY(), playerOne.getRectangle().getWidth(),
				playerOne.getRectangle().getHeight());
		shapeRenderer.rect(comPlayer.getRectangle().getX(), comPlayer.getRectangle().getY(), comPlayer.getRectangle().getWidth(),
				comPlayer.getRectangle().getHeight());
		shapeRenderer.circle(ball.x, ball.y, ball.radius);
//		shapeRenderer.rect(topBound.getX(), topBound.getY(), topBound.getWidth(), topBound.getHeight());
//		shapeRenderer.rect(bottomBound.getX(), bottomBound.getY(), bottomBound.getWidth(), bottomBound.getHeight());
		shapeRenderer.end();

		shapeRenderer.setColor(Color.RED);

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.circle(ballClone.getX(), ballClone.getY(), ballClone.getRadius());
		shapeRenderer.end();

		moveBall();
		keepBallInBounds();
		comPlayer.calculateNewBallPosition(playerOne.getRectangle(), ballClone = ball.clone());
		comPlayer.move(ballClone, 10f);
		playerOne.wasdArrowMove();
		comPlayer.keepInBounds();
		playerOne.keepInBounds();
		checkPlayerScored();
		comPlayer.calculateNewBallPosition(playerOne.getRectangle(), ballClone = ball.clone());
		comPlayer.move(ballClone, 5f);
	}

	@Override
	public void resize(int width, int height) {
		extendViewport.update(width, height, false);
		System.out.println("Screen height after resizing: " + extendViewport.getScreenHeight());
		System.out.println("World height after resizing: " + extendViewport.getWorldHeight());
//		com.badlogic.gdx.math.Vector2 worldToScreenCoords = extendViewport.toScreenCoordinates(new Vector2(width, height), camera.combined);
//		extendViewport.setScreenBounds(extendViewport.getScreenX(), extendViewport.getScreenY(), width, height);
//		extendViewport.apply(true);
		scoreUI.resize(width, height);
	}

	@Override
	public void dispose() {
		shapeRenderer.dispose();
	}

	private void moveBall() {
		ball.x += ball.getVelocity().x * Gdx.graphics.getDeltaTime();
		ball.y += ball.getVelocity().y * Gdx.graphics.getDeltaTime();

		if (collisions.isCollidingCircleRect(playerOne.getRectangle(), ball)) {
			comPlayer.calculateNewBallPosition(playerOne.getRectangle(), ballClone = ball.clone());
			float theta = collisions.calculateDeflectionAngle(playerOne.getRectangle(), ball);
			float newBallSpeedX = Math.abs((MathUtils.cos(theta)) * Constants.MAX_BALL_SPEED);
			float newBallSpeedY = -(MathUtils.sin(theta) * Constants.MAX_BALL_SPEED);
			float oldSign = Math.signum(ball.getVelocity().x);
			ball.setVelocityX(newBallSpeedX * (-1 * oldSign));
			ball.setVelocityY(newBallSpeedY);
			collisions.reset();

			System.out.println("Ball velocityX: " + ball.getVelocity().x);
			System.out.println("Ball velocityY: " + ball.getVelocity().y);
		} else if (collisions.isCollidingCircleRect(comPlayer.getRectangle(), ball)) {
			float theta = collisions.calculateDeflectionAngle(comPlayer.getRectangle(), ball);
			float newBallSpeedX = Math.abs((MathUtils.cos(theta)) * Constants.MAX_BALL_SPEED);
			float newBallSpeedY = -(MathUtils.sin(theta) * Constants.MAX_BALL_SPEED);
			float oldSign = Math.signum(ball.getVelocity().x);
			ball.setVelocityX(newBallSpeedX * (-1 * oldSign));
			ball.setVelocityY(-newBallSpeedY);
			collisions.reset();

			System.out.println("Ball velocityX: " + ball.getVelocity().x);
			System.out.println("Ball velocityY: " + ball.getVelocity().y);
		}

		if (ball.getY() > (extendViewport.getWorldHeight() - 10)  || ball.getY() <= 0) {
			float theta = collisions.calculateDeflectionAngle(extendViewport.getWorldHeight()/2f, ball);
			float newBallSpeedX = Math.abs((MathUtils.cos(theta)) * Constants.MAX_BALL_SPEED);
			float newBallSpeedY = -(MathUtils.sin(theta) * -Constants.MAX_BALL_SPEED);
			float oldSign = Math.signum(ball.getVelocity().x);
			ball.setVelocityX(-newBallSpeedX * (-1 * oldSign));
			ball.setVelocityY(newBallSpeedY);
			collisions.reset();
		}

		/*if (collisions.isCollidingCircleRect(topBound, ball) ||
				collisions.isCollidingCircleRect(bottomBound, ball)) {
			float theta = collisions.calculateDeflectionAngle(Gdx.graphics.getHeight()/2f, ball);
			float newBallSpeedX = Math.abs((MathUtils.cos(theta)) * Constants.MAX_BALL_SPEED);
			float newBallSpeedY = -(MathUtils.sin(theta) * -Constants.MAX_BALL_SPEED);
			float oldSign = Math.signum(ball.getVelocity().x);
			ball.setVelocityX(-newBallSpeedX * (-1 * oldSign));
			ball.setVelocityY(newBallSpeedY);
			collisions.reset();
		}*/
	}

	private void checkPlayerScored() {
		if (ball.getX() > Constants.RIGHT_GOAL_X) {
			playerOne.updateScore(1);
			resetBall();
		}

		if (ball.getX() < Constants.LEFT_GOAL_X) {
			comPlayer.updateScore(1);
			resetBall();
		}
	}

	private void keepBallInBounds() {
		/*if (ball.getY() >= Gdx.graphics.getHeight() - 3 || ball.getY() < 0) {
			float theta = collisions.calculateDeflectionAngle(Gdx.graphics.getHeight()/2f, ball);
			float newBallSpeedX = Math.abs((MathUtils.cos(theta)) * Constants.MAX_BALL_SPEED);
			float newBallSpeedY = -(MathUtils.sin(theta) * -Constants.MAX_BALL_SPEED);
			float oldSign = Math.signum(ball.getVelocity().x);
			ball.setVelocityX(-newBallSpeedX * (-1 * oldSign));
			ball.setVelocityY(newBallSpeedY);
			collisions.reset();
		}*/

		/*if (collisions.isCollidingCircleRect(new Rectangle(0, 0, Gdx.graphics.getWidth(), 1), ball) ||
				collisions.isCollidingCircleRect(new Rectangle(0, Gdx.graphics.getHeight() - 3, Gdx.graphics.getWidth(), 1), ball)) {
			float theta = collisions.calculateDeflectionAngle(Gdx.graphics.getHeight()/2f, ball);
			float newBallSpeedX = Math.abs((MathUtils.cos(theta)) * Constants.MAX_BALL_SPEED);
			float newBallSpeedY = -(MathUtils.sin(theta) * -Constants.MAX_BALL_SPEED);
			float oldSign = Math.signum(ball.getVelocity().x);
			ball.setVelocityX(-newBallSpeedX * (-1 * oldSign));
			ball.setVelocityY(newBallSpeedY);
			collisions.reset();
		}*/

		/*if ((ball.getY() > Gdx.graphics.getHeight() || (ball.getY() < 0)) && (ball.getVelocity().x > 0)) {
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
		}*/

		/*if (collisions.isCollidingCircleRect(topBound, ball)) {
			float theta = collisions.calculateDeflectionAngle(Gdx.graphics.getHeight() / 2f, ball);
			float newBallSpeedX = Math.abs((MathUtils.cos(theta)) * Constants.MAX_BALL_SPEED);
			float newBallSpeedY = -(MathUtils.sin(theta) * -Constants.MAX_BALL_SPEED);
			float oldSign = Math.signum(ball.getVelocity().x);
			ball.setVelocityX(-newBallSpeedX * (-1 * oldSign));
			ball.setVelocityY(newBallSpeedY);
			collisions.reset();
		}

		if (collisions.isCollidingCircleRect(bottomBound, ball)) {
			float theta = collisions.calculateDeflectionAngle(Gdx.graphics.getHeight() / 2f, ball);
			float newBallSpeedX = Math.abs((MathUtils.cos(theta)) * Constants.MAX_BALL_SPEED);
			float newBallSpeedY = -(MathUtils.sin(theta) * -Constants.MAX_BALL_SPEED);
			float oldSign = Math.signum(ball.getVelocity().x);
			ball.setVelocityX(-newBallSpeedX * (-1 * oldSign));
			ball.setVelocityY(newBallSpeedY);
			collisions.reset();
		}*/

		/*if ((collisions.isCollidingCircleRect(new Rectangle(0, Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), 1), ball) ||
				collisions.isCollidingCircleRect(new Rectangle(0, 0, Gdx.graphics.getWidth(), 1), ball)) && (ball.getVelocity().x > 0)) {
			float theta = collisions.calculateDeflectionAngle(Gdx.graphics.getHeight()/2f, ball);
			float newBallSpeedX = Math.abs((MathUtils.cos(theta)) * Constants.MAX_BALL_SPEED);
			float newBallSpeedY = -(MathUtils.sin(theta) * -Constants.MAX_BALL_SPEED);
			float oldSign = Math.signum(ball.getVelocity().x);
			ball.setVelocityX(-newBallSpeedX * (-1 * oldSign));
			ball.setVelocityY(newBallSpeedY);
			collisions.reset();
		} else if ((collisions.isCollidingCircleRect(new Rectangle(0, Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), 1), ball) ||
				collisions.isCollidingCircleRect(new Rectangle(0, 0, Gdx.graphics.getWidth(), 1), ball)) && ball.getVelocity().x < 0) {
			float theta = collisions.calculateDeflectionAngle(Gdx.graphics.getHeight()/2f, ball);
			float newBallSpeedX = Math.abs((MathUtils.cos(theta)) * Constants.MAX_BALL_SPEED);
			float newBallSpeedY = -(MathUtils.sin(theta) * -Constants.MAX_BALL_SPEED);
			float oldSign = Math.signum(ball.getVelocity().x);
			ball.setVelocityX(-newBallSpeedX * (-1 * oldSign));
			ball.setVelocityY(newBallSpeedY);
			collisions.reset();
		}*/
	}

	private void resetBall() {
		ball.setCenter(Constants.CENTER_SCREEN);

		float randomX = MathUtils.random(0, 1);
		ball.setVelocityX((randomX == 0) ? -Constants.MAX_BALL_SPEED : Constants.MAX_BALL_SPEED);
		ball.setVelocityY(10);
		comPlayer.calculateNewBallPosition(playerOne.getRectangle(), ballClone = ball.clone());
		System.out.println("Ball velocityX: " + ball.getVelocity().x);
		System.out.println("Ball velocityY: " + ball.getVelocity().y);
	}

	@Override
	public void show() {

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
}