package com.mygdx.Pong;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(800,400);
		config.setTitle("MyFirstPong");
		config.setForegroundFPS(60);
		config.useVsync(true);
		new Lwjgl3Application(new Main(), config);
	}
}
