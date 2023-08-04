package com.bakpun.mistborn.elementos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bakpun.mistborn.utiles.Render;

public class Imagen {

	private Texture t;
	private Sprite s;
	
	public Imagen(String ruta) {
		t = new Texture(ruta);
		s = new Sprite(t);
	}
	
	public void draw() {	//Lo dejo para que dibuje otras imagenes que no necesiten animacion.
		s.draw(Render.batch);
	}
	
	public void drawAnimacion(TextureRegion frameActual,float x,float y) { //Dibuja el Sprite con la animacion.
		s.setRegion(frameActual);	//Cambia la textura por el frameActual que le paso.
		s.draw(Render.batch);	
	}
	
	public void setTransparencia(float alpha) {	//Opacidad.
		s.setAlpha(alpha);
	}
	
	public void setPosicion(float x,float y) {	//Posicion x,y.
		s.setPosition(x, y);
	}
	public void ajustarTamano(float tamano) {	//Tamano.
		s.scale(tamano);
	}
	public Texture getTexture() {
		return this.t;
	}

	public void flip() {	//Rotar sprite. Esto para utilizarlo con el personaje.
		s.setSize(-t.getWidth(), 0);
	}
}
