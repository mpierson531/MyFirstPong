package com.mygdx.Pong;

import com.badlogic.gdx.Game;

public class Main extends Game {

	@Override
	public void create () {
		this.setScreen(new StartMenu(this));
	}

	@Override
	public void render() {
		super.render();
	}
}