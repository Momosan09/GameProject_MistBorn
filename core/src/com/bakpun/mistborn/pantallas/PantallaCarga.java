package com.bakpun.mistborn.pantallas;

import com.badlogic.gdx.Screen;
import com.bakpun.mistborn.elementos.Imagen;
import com.bakpun.mistborn.utiles.Render;

public class PantallaCarga implements Screen {
	Imagen fondo;
	float a = 0f,contEspera = 0f;
	int espera = 3;
	boolean terminoFadeIn = false,termina = false;
	
	public void show() {
		fondo = new Imagen("logoMistborn.jpg");
		fondo.setTransparencia(0.5f);
	}
	public void render(float delta) {
		Render.limpiarPantalla();
		fondo.setTransparencia(a);
		termina = procesarFadePantalla();
		Render.batch.begin();
		fondo.draw();
		Render.batch.end();
		if(termina) {
			Render.app.setScreen(new PantallaPvP());
		}
		
	}
	private boolean procesarFadePantalla() {
		if (!terminoFadeIn) {
			a += 0.007f;
			if (a > 1) {
				a = 1;
				terminoFadeIn = true;
			}
		} else {
			if (contEspera > espera) {
				a -= 0.007f;
				if(a < 0) {
					a = 0;
					termina = true;
				}
			} else {
				contEspera += 0.01;
			}
		}
		return termina;
	}
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
