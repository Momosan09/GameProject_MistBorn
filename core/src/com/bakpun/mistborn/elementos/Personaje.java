package com.bakpun.mistborn.elementos;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bakpun.mistborn.io.Entradas;
import com.bakpun.mistborn.utiles.Recursos;

public class Personaje {
	//Falta que el personaje rote en la direccion opuesta si va a la izq.
	private final float VELOCIDAD_X = 400f, IMPULSO_Y = 12f,GRAVEDAD = -20f;
	private final int ANCHO_QUIETO = 80, ALTO_QUIETO_CORRER = 32,ANCHO_CORRE = 128,FILA_FRAME = 4;
	private Animacion animacionQuieto,animacionCorrer;
	private Imagen spr;
	private float delta = 0f,x=100,y=200,velocidadImpulso = 0f;
	private boolean saltar,puedeMoverse,estaSaltando = false,estaCorriendo,estaQuieto;
	private Entradas entradas;
	private float duracion = 0.2f,duracionCorrer = 0.15f;
	private TextureRegion frameActual;
	
	public Personaje() {
		entradas = new Entradas();
		Gdx.input.setInputProcessor(entradas);
 		spr = new Imagen(Recursos.PERSONAJE_VIN);
 		spr.ajustarTamano(2);
		crearAnimaciones();
	}
	
	private void update() {		//Este metodo updatea que frame de la animacion se va a mostrar actualmente,lo llamo en draw().
		delta = Gdx.graphics.getDeltaTime();
		duracion += delta;
		duracionCorrer += delta;
		
		animacionQuieto.update(delta);
		animacionCorrer.update(delta);
	}
	
	
	public void draw() {
		
		update();
		
		saltar = (entradas.isSaltar() && !estaSaltando);
		puedeMoverse = (entradas.isIrDer() != entradas.isIrIzq());	//Si el jugador toca las 2 teclas a la vez no va a poder moverse.
		estaQuieto = ((!entradas.isIrDer() == !entradas.isIrIzq()) || !puedeMoverse);
		estaCorriendo = ((entradas.isIrDer() || entradas.isIrIzq()) && puedeMoverse);
		
		calcularSalto();	//Calcula el salto con la gravedad.
		calcularMovimiento();	//Calcula el movimiento. Tendria que cambiarlo por la clase Entradas (creo).
		calcularLimites();	//Calcula e impide que el jugador no se salga del plano visible.
		
		spr.setPosicion(x,y);
		
		if(estaQuieto) {	//Si esta quieto muestra el fotograma actual de la animacionQuieto.
			frameActual = animacionQuieto.getCurrentFrame();
			spr.drawAnimacion(frameActual,x,y);
		}else if(estaCorriendo) { 	//Si esta corriendo muestra el fotograma actual de la animacionCorrer.
			frameActual = animacionCorrer.getCurrentFrame();
			spr.drawAnimacion(frameActual,x,y);
		}
	}
	
	private void crearAnimaciones() {
		TextureRegion[][] tempQuieto,tempCorrer;		//Es una matriz porque la funcion split() te los da de esa manera, en este caso que
		TextureRegion[] quietoFrames,correrFrames;		//solo las texturas son de 1 fila, es al pedo, pero no hay otra manera. 
		
		Texture tQuieto = new Texture(Recursos.ANIMACION_QUIETO);		//Cargo la textura que tiene los 4 frames de quieto.
		Texture tCorrer = new Texture(Recursos.ANIMACION_CORRER);		//Cargo la textura que tiene los 4 frames de correr.
		
		tempQuieto = TextureRegion.split(tQuieto , ANCHO_QUIETO/FILA_FRAME, ALTO_QUIETO_CORRER);	//Divido la textura en 4 columnas.
		quietoFrames = new TextureRegion[FILA_FRAME];
		
		tempCorrer = TextureRegion.split(tCorrer, ANCHO_CORRE/FILA_FRAME, ALTO_QUIETO_CORRER);		//Divido la textura en 4 columnas
		correrFrames = new TextureRegion[FILA_FRAME];
		
		for (int i = 0; i < FILA_FRAME; i++) {
			quietoFrames[i] = tempQuieto[0][i];			//Meto los frames separados en un vector 1D.
			correrFrames[i] = tempCorrer[0][i];
		}
		
		animacionQuieto = new Animacion(quietoFrames, duracion);	//Creo la animacion
		animacionCorrer = new Animacion(correrFrames,duracionCorrer);
	}
	
	private void calcularLimites() {
		if(x >= Gdx.graphics.getWidth()) {
			x = Gdx.graphics.getWidth();
		}
		if(x <= 0){
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
			if(entradas.isIrDer()) {
				//Falta saber como rotar el personaje cuando camina izq,der.
				x += VELOCIDAD_X * delta;
			} else if (entradas.isIrIzq()){
				//spr.flip();
				x -= VELOCIDAD_X * delta;
			}
		}
	}
	public float getX() {
		return this.x;
	}
	public float getY() {
		return this.y;
	}
}
