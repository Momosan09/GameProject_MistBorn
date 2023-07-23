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
	
	public void draw() {
		s.draw(Render.batch);
	}
	
	public void setTransparencia(float alpha) {
		s.setAlpha(alpha);
	}

	public void actualizar(float x,float y) {
		s.setPosition(x, y);
	}
	public void ajustarTamano(float tamano) {
		s.scale(tamano);
	}

	public void flip(boolean x) {
		s.flip(x, false);	
	}
}
