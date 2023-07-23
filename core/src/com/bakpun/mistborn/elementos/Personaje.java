package com.bakpun.mistborn.elementos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.bakpun.mistborn.utiles.Render;

public class Personaje {
	//Faltan las animaciones de caminar y que pueda saltar.
	
	private Imagen spr;
	private float velocidad = 2f,delta = Gdx.graphics.getDeltaTime();
	private float x=100,y=200;
	private boolean irDer = Gdx.input.isKeyPressed(Keys.D),irIzq = Gdx.input.isKeyPressed(Keys.A), puedeMoverse = (irDer != irIzq);
	
	public Personaje() {
		spr = new Imagen("esqueletoPj2.png");
		spr.ajustarTamano(3);
	}
	public float getX() {
		return this.x;
	}
	public float getY() {
		return this.y;
	}

	public void update() {
		if(Gdx.input.isKeyPressed(Keys.D)) {
			//Falta saber como rotar el personaje cuando camina izq,der.
			spr.flip(false);
			//Falta agregar el deltaTime,no anda cuando se multiplica por la velocidad.
			x += velocidad;
		} else if (Gdx.input.isKeyPressed(Keys.A)){
			spr.flip(true);
			x -= velocidad;
		}
		spr.actualizar(x,y);
		spr.draw();
	}	
}
