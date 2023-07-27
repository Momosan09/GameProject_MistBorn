package com.bakpun.mistborn.elementos;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.bakpun.mistborn.utiles.Render;

public class Texto {

	private FreeTypeFontGenerator generador;
	private FreeTypeFontParameter parametros;
	private BitmapFont fuente;
	private GlyphLayout layout;
	private String cadena;
	private float x,y;
	
	public Texto(String rutaFuente,int tamano,Color color) {
		generador = new FreeTypeFontGenerator(Gdx.files.internal(rutaFuente));
		parametros = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parametros.size = tamano;
		parametros.color = color;
		fuente = generador.generateFont(parametros);
		layout = new GlyphLayout();
	}
	
	public void draw() {
		fuente.draw(Render.batch,this.cadena, this.x, this.y);
	}
	
	public void setPosicion(float x,float y) {
		this.x = x;
		this.y = y;
	}
	public void setTexto(String cadena) {
		this.cadena = cadena;
		this.layout.setText(fuente, cadena);
	}
	public void setColor(Color color) {
		this.fuente.setColor(color);
	}
	
	public float getAncho() {
		return this.layout.width;
	}
	public float getAlto() {
		return this.layout.height;
	}
	public Vector2 getDimension() {
		return new Vector2(layout.width,layout.height);
	}
	
	
	
}
			