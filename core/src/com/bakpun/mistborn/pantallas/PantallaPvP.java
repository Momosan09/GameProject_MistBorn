package com.bakpun.mistborn.pantallas;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.bakpun.mistborn.elementos.Imagen;
import com.bakpun.mistborn.elementos.Personaje;
import com.bakpun.mistborn.utiles.Render;

public class PantallaPvP implements Screen{
	//Faltan las plataformas.
	Imagen fondo;
	Personaje pj;
	OrthographicCamera cam;
	public void show() {
		int alto = Gdx.graphics.getHeight(),ancho = Gdx.graphics.getWidth();
		fondo = new Imagen("background.png");
		pj = new Personaje();
		cam = new OrthographicCamera(alto,ancho);
	}

	public void render(float delta) {
		Render.limpiarPantalla();
		cam.update();
		Render.batch.setProjectionMatrix(cam.combined);
		Render.batch.begin();
		fondo.draw();
		pj.update();
		Render.batch.end();
		//Para hacer que la camara siga al pj, en este juego no la necesitamos, pero ahi esta.
		//cam.position.set(pj.getX(), pj.getY(), 0);
	}

	@Override
	public void resize(int width, int height) {
		cam.setToOrtho(false, width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	
	
	
}
