package com.mygdx.Pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Timer;

public class TouchHoldListener extends InputListener {
    private boolean down;
    private boolean held;
    private final float holdDelaySeconds;
    private float inputTime;
    private float intervalSeconds;
    private Timer.Task delayedHoldTask;
    private Runnable runnable;

    /**
     * @param holdDelaySeconds Delay before held is set to true, in seconds.
     */
    public TouchHoldListener(float holdDelaySeconds, float intervalSeconds, Runnable runnable) {
        this.down = false;
        this.held = false;
        this.holdDelaySeconds = holdDelaySeconds;
        this.intervalSeconds = intervalSeconds;
        this.runnable = runnable;
    }

    public boolean isDown() {
        return this.down;
    }

    public boolean isHeld() {
        return this.held;
    }

    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        runnable.run();

        float deltaTime = Gdx.graphics.getDeltaTime();
        inputTime += deltaTime;

        delayedHoldTask = Timer.schedule(getDelayedHoldTask(), holdDelaySeconds, intervalSeconds, 1000);

        return true;
    }

    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        delayedHoldTask.cancel();
        inputTime = 0f;
        this.down = false;
        this.held = false;
    }

    private Timer.Task getDelayedHoldTask() {
        return new Timer.Task() {
            @Override
            public void run() {
                runnable.run();
            }
        };
    }
}