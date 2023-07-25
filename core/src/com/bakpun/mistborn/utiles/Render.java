package com.bakpun.mistborn.utiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bakpun.mistborn.MistBorn;

public class Render {
	public static SpriteBatch batch;	
	public static MistBorn app;
	
	public static void limpiarPantalla() {
		Gdx.gl.glClearColor(228,198,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
}
