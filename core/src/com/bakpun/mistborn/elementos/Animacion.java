package com.bakpun.mistborn.elementos;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animacion {
	private Animation<TextureRegion> animacion;
	private float tiempo = 0f, duracionTotal;

	public Animacion(TextureRegion[] framesAnimacion, float framesDuracion) {
		animacion = new Animation<>(framesDuracion, framesAnimacion); 	//Creo la animacion.
		duracionTotal = framesAnimacion.length * framesDuracion;
	}

	public void update(float delta) {
		tiempo += delta;
		if (tiempo >= duracionTotal) {		//Si el tiempo es mayor a la duracion total que le asigne , el tiempo se restara por este.
			tiempo -= duracionTotal;
		}
	}
	
	public TextureRegion getCurrentFrame() {
		return animacion.getKeyFrame(tiempo);	//En base a x tiempo te devuelve que frame tendria que ir ahora. 
	}

}
