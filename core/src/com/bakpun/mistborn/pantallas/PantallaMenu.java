package com.bakpun.mistborn.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.bakpun.mistborn.elementos.Imagen;
import com.bakpun.mistborn.elementos.Texto;
import com.bakpun.mistborn.io.Entradas;
import com.bakpun.mistborn.utiles.Recursos;
import com.bakpun.mistborn.utiles.Render;

public class PantallaMenu implements Screen {

	/*
	 Falta poner al rectangulo la opacidad, y tamb la interaccion.
	*/
	private Texto[] opciones = new Texto[3];
	private String textos[] = {"Jugar","Opciones","Salir"};
	private Imagen fondo;
	private ShapeRenderer figura;
	private OrthographicCamera cam;
	private OrthographicCamera camEstatica;
	private final float VELOCIDAD_CAMARA = 1.2f;
	private float tiempoMapa = 150f, contMapa = 0f, opacidad = 1f;
	private int seleccion = 1,alto,ancho;
	private final int MAX_OPC = 3,MIN_OPC = 1;
	private boolean reiniciarCam = false, terminoPrimeraParte = false;
	private Entradas entradas;
	private static float tiempo;
	
	
	public void show() {
		alto = Gdx.graphics.getHeight();ancho = Gdx.graphics.getWidth();
		entradas = new Entradas();
		Gdx.input.setInputProcessor(entradas);
		fondo = new Imagen(Recursos.FONDO_MENU);
		figura = new ShapeRenderer();
		cam = new OrthographicCamera(alto, ancho);	//Camara para el fondo que se mueve.
		camEstatica = new OrthographicCamera(alto,ancho);	//Camara para las opciones del menu (estaticas).
		cargarTexto();
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
		
		seleccion = chequearEntradas(delta);
		colorearOpcion();
		camEstatica.update();
		Render.batch.setProjectionMatrix(camEstatica.combined);	//segundo bloque para hacer la camara estatica.
		mostrarFigura();	//Dibuja un rectangulo que vendria a ser donde estan las opciones.
		Render.batch.begin();
		for (int i = 0; i < opciones.length; i++) {
			opciones[i].draw();		//Dibuja las opciones.
		}
		Render.batch.end();
	}

	private void mostrarFigura() {
		figura.setProjectionMatrix(Render.batch.getProjectionMatrix());	 // Viewport, esto dentro del Render de la camEstatica.
		figura.begin(ShapeType.Filled);
		figura.rect(100, 200, (opciones[1].getAncho()+100), Gdx.graphics.getHeight() - 50);	//No me anda poner alto, tendria que hacer una clase config,															
		figura.setColor(Color.ORANGE);												//que me de el ancho,alto.
		figura.end();
	}

	private void colorearOpcion() {
		for (int i = 0; i < opciones.length; i++) {
			if (i == (seleccion-1)) {
				opciones[i].setColor(Color.YELLOW);
			} else {
				opciones[i].setColor(Color.WHITE);
			}
		}
	}

	private int chequearEntradas(float delta) {
		tiempo += delta;
		if(entradas.isAbajo()) {
			if(tiempo >= 0.2f) {	//Hay un delay para elegir otra opcion.
				tiempo = 0;
				seleccion++;	//Si en este contador se supera el MAX_OPC, la seleccion va a ser igual a la primera opcion.
				if(seleccion > MAX_OPC) {
					seleccion = MIN_OPC;
				}
			}
			return seleccion;
		}
		else if(entradas.isArriba()) {
			if(tiempo >= 0.2f) {
				tiempo = 0;
				seleccion--;	//Si en este contador es menor que el MIX_OPC, la seleccion va a ser igual a la ultima opcion.
				if(seleccion < MIN_OPC) {
					seleccion = MAX_OPC;
				}
			}
		}
		return seleccion;
	}

	private void cargarTexto() {
		for (int i = 0; i < opciones.length; i++) {
			opciones[i] = new Texto(Recursos.FUENTE_MENU,50,Color.WHITE);
			opciones[i].setTexto(textos[i]);
			//opciones[i].setPosicion((ancho/2)-(opciones[i].getAncho()/2),((alto/2)+(opciones[0].getAlto()/2))-((opciones[i].getAlto()*i)+(margen*i)));
			opciones[i].setPosicion(150, ((i==0)?400:(i==1)?340:280));
		}
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
