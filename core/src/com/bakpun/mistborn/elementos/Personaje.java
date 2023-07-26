package com.bakpun.mistborn.elementos;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input.Keys;
import com.bakpun.mistborn.utiles.Recursos;

public class Personaje {
	//Faltan las animaciones de correr y quieto.
	
	private Imagen spr;
	private float delta = 0f,x=100,y=200,velocidadImpulso = 0f;
	private final float VELOCIDAD_X = 400f, IMPULSO_Y = 12f,GRAVEDAD = -20f;
	private boolean irDer,irIzq,saltar,puedeMoverse,estaSaltando = false;
	
	public Personaje() {
		spr = new Imagen(Recursos.PERSONAJE_VIN);
		spr.ajustarTamano(3);
	}
	public float getX() {
		return this.x;
	}
	public float getY() {
		return this.y;
	}

	public void update() {
		delta = Gdx.graphics.getDeltaTime();
		irDer = Gdx.input.isKeyPressed(Keys.D);
		irIzq = Gdx.input.isKeyPressed(Keys.A);
		saltar = (Gdx.input.isKeyJustPressed(Keys.SPACE) && !estaSaltando);
		puedeMoverse = (irDer != irIzq);	//Si el jugador toca las 2 teclas a la vez no va a poder moverse.
		
		calcularSalto();	//Calcula el salto con la gravedad.
		calcularMovimiento();	//Calcula el movimiento. Tendria que cambiarlo por la clase Entradas (creo).
		calcularLimites();	//Calcula e impide que el jugador no se salga del plano visible.

		spr.setPosicion(x,y);
		spr.draw();
	}
	private void calcularLimites() {
		if(x >= Gdx.graphics.getWidth()) {
			x = Gdx.graphics.getWidth();
		}
		if(x <= 0) {
			x = 0;
		}
	}
	private void calcularSalto() {
		if(saltar) {
			velocidadImpulso += IMPULSO_Y;
			y += velocidadImpulso * delta;
			estaSaltando = true;
		}	
		if(estaSaltando) {
			velocidadImpulso += GRAVEDAD * delta;
			y += velocidadImpulso;
			if(y <= 200) {
				y = 200;
				estaSaltando = false;
				velocidadImpulso = 0;
			}
		}
	}
	private void calcularMovimiento() {
		if(puedeMoverse) {
			if(irDer) {
				//Falta saber como rotar el personaje cuando camina izq,der.
				//spr.flip(false);
				x += VELOCIDAD_X * delta;
			} else if (irIzq){
				//spr.flip(true);
				x -= VELOCIDAD_X * delta;
			}
		}
	}	
}
