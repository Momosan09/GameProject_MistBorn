package com.bakpun.mistborn.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.bakpun.mistborn.elementos.Imagen;
import com.bakpun.mistborn.elementos.Texto;
import com.bakpun.mistborn.io.Entradas;
import com.bakpun.mistborn.utiles.Recursos;
import com.bakpun.mistborn.utiles.Render;

public class PantallaMenu implements Screen {

	/*
	 Falta poner un rectangulo en el menu que te diga "Jugar" por ahora. Y la interaccion.
	*/
	private Texto t;
	private Imagen fondo;
	private OrthographicCamera cam;
	private OrthographicCamera camEstatica;
	private final float VELOCIDAD_CAMARA = 1.2f;
	private float tiempoMapa = 150f, contMapa = 0f, opacidad = 1f;
	private boolean reiniciarCam = false, terminoPrimeraParte = false;
	private Entradas entradas;
	
	public void show() {
		int alto = Gdx.graphics.getHeight(), ancho = Gdx.graphics.getWidth();
		entradas = new Entradas();
		Gdx.input.setInputProcessor(entradas);
		fondo = new Imagen(Recursos.FONDO_MENU);
		cam = new OrthographicCamera(alto, ancho);	//Camara para el fondo que se mueve.
		camEstatica = new OrthographicCamera(alto,ancho);	//Camara para las opciones del menu (estaticas).
		t = new Texto(Recursos.FUENTE_MENU,80,Color.CYAN);	//Cree un texto para probar.
		t.setTexto("Jugar");
		t.setPosicion(200,200);
	}

	public void render(float delta) {
		Render.limpiarPantalla((float) 212 / 255, (float) 183 / 255, (float) 117 / 255);	//limpiarPantalla() ahora le tenes que pasar rgb.
		calcularMovCamara();																//lo divido por 255 porque es del 0 al 1.
		if (reiniciarCam) {		// Esta va a reinciar la camara cuando haya superado el tiempo de muestra. Va a volver al punto de inicio.
			reiniciarCam = reinciarCamara();
		}
		cam.update();
		Render.batch.setProjectionMatrix(cam.combined);  //primer bloque para hacer la camara que se mueve.
		Render.batch.begin();
		fondo.draw();
		Render.batch.end();
		
		camEstatica.update();
		Render.batch.setProjectionMatrix(camEstatica.combined);	//segundo bloque para hacer la camara estatica.
		Render.batch.begin();
		t.draw();							
		Render.batch.end();
	}

	private void calcularMovCamara() { //Este metodo va a hacer que la camara se mueva constantemente y va a calcular cuando hay que reiniciar la camara.
		moverCamara();
		if (tiempoMapa <= contMapa) {
			reiniciarCam = true;
			contMapa = 0f;
		}
		contMapa += 0.1f;
	}

	private boolean reinciarCamara() {		//Este metodo hace el fade para que no se vea el cambio de posicion de la camara.
		fondo.setTransparencia(opacidad);
		if (!terminoPrimeraParte) {
			if (opacidad < 0f) {
				terminoPrimeraParte = true;
				// cam.viewportWidth es lo que estaba buscando. Sino me andaba, tenia que
				// ponerlo por mi cuenta.
				cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);
			} else {
				opacidad -= 0.007f;
			}									
		}
		if (terminoPrimeraParte) {
			opacidad += 0.007f;
		}
		if (terminoPrimeraParte && opacidad >= 1f) {
			terminoPrimeraParte = false;
			return false;
		}
		return true;
	}

	private void moverCamara() {
		cam.translate(VELOCIDAD_CAMARA, VELOCIDAD_CAMARA);	//Esto mueve la camara en base a una velocidad introducida x,y.
	}

	@Override
	public void resize(int width, int height) {
		cam.setToOrtho(false, width, height);	// Viewport
		camEstatica.setToOrtho(false, width, height);	//Viewport
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
