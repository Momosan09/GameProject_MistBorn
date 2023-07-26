package com.bakpun.mistborn.elementos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.bakpun.mistborn.utiles.Render;

public class Imagen {

	private Texture t;
	private Sprite s;
	
	public Imagen(String ruta) {
		t = new Texture(ruta);
		s = new Sprite(t);
	}
	
	public void draw() {	//Dibuja Sprite.
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

	public void flip(boolean x) {	//Rotar sprite. Esto para utilizarlo con el personaje.
		s.flip(x, false);	
	}
}
