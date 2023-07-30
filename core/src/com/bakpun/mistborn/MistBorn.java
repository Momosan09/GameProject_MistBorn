package com.bakpun.mistborn;

import com.bakpun.mistborn.elementos.*;



import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bakpun.mistborn.pantallas.PantallaCarga;
//import com.bakpun.mistborn.pantallas.PantallaPvP;
import com.bakpun.mistborn.utiles.Render;

public class MistBorn extends Game {
	Personaje pj;
	
	public void create() {
		pj = new Personaje();
		Render.cancionMenu.play();
		Render.app = this;
		Render.batch = new SpriteBatch();
		this.setScreen(new PantallaCarga());
		//this.setScreen(new PantallaPvP());
	}

	public void render () {
		super.render();
	}
	
	public void dispose () {
		Render.batch.dispose();
	}
}
