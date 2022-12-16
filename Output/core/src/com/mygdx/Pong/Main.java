package com.mygdx.Pong;

import com.badlogic.gdx.Game;
import com.mygdx.Pong.Screens.StartMenu;

public class Main extends Game {

	@Override
	public void create () {
		super.setScreen(new StartMenu(this));
	}

	@Override
	public void render() {
		super.render();
	}
}