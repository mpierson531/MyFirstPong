package com.mygdx.Pong.Engine.Audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.Pong.Engine.Files.FileHandler;

public class AudioHandler {
    private Sound sound;
    private FileHandler fileHandler;
    private float startTime;
    private float endTime;
    private boolean soundPlayed;

    public AudioHandler() {
        soundPlayed = false;
        startTime = System.nanoTime();
    }

    public AudioHandler(Sound sound) {
        this.sound = sound;
        soundPlayed = false;
        startTime = System.nanoTime();
        checkSoundNull();
    }

    public void playSound() {
        sound.play();
    }

    public void playSound(float volume) {
        sound.play(volume);
    }

    public void playSound(float volume, float delayBetweenPlays) {
        updateStartTime(delayBetweenPlays);
        if (!soundPlayed) {
            sound.play(volume);
            soundPlayed = true;
            startTime = System.nanoTime();
        }
    }

    public void playSound(float volume, float pitch, float delayBetweenPlays) {
        updateStartTime(delayBetweenPlays);
        if (!soundPlayed()) {
            sound.play(volume, pitch, 0);
            soundPlayed = true;
            startTime = System.nanoTime();
        }
    }

    private void checkSoundNull() {
        if (isSoundNull()) {
            throw new GdxRuntimeException("Sound 'sound' in AudioHandler was null.");
        }
    }

    public boolean isSoundNull() {
        return sound == null;
    }

    public boolean soundPlayed() {
        return this.soundPlayed;
    }

    private void updateStartTime(float delayBetweenPlays) {
        if (System.nanoTime() - startTime >= delayBetweenPlays * 1_000_000_000) {
            soundPlayed = false;
            startTime = System.nanoTime();
        } else {
            soundPlayed = true;
        }
    }

    public static void playSound(Sound sound) {
        if (sound != null) {
            sound.play();
        } else {
            throw new GdxRuntimeException("Sound 'sound' in AudioHandler was null.");
        }
    }

    public static void playSound(FileHandle fileHandle) {
        Gdx.audio.newSound(fileHandle).play();
    }

    public static void playSound(String filePath) {
        Gdx.audio.newSound(new FileHandle(filePath)).play();
    }

    public static Sound newSound(String filePath) {
        return Gdx.audio.newSound(new FileHandle(filePath));
    }

    public static Sound newSound(FileHandle fileHandle) {
        return Gdx.audio.newSound(fileHandle);
    }
}