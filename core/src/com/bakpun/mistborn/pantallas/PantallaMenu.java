package com.bakpun.mistborn.pantallas;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.bakpun.mistborn.elementos.Imagen;
import com.bakpun.mistborn.elementos.Texto;
import com.bakpun.mistborn.io.Entradas;
import com.bakpun.mistborn.utiles.Config;
import com.bakpun.mistborn.utiles.Recursos;
import com.bakpun.mistborn.utiles.Render;

//Tengo que ver la forma de anadir el sonido cuando se selecciona opcion con el mouse y buscar un sonido para cuando toca enter o click.

public class PantallaMenu implements Screen {
	private final String textos[] = {"Jugar","Opciones","Salir"};
	private final float VELOCIDAD_CAMARA = 1.2f;
	private final int MAX_OPC = 3,MIN_OPC = 1;
	private Texto[] opciones = new Texto[3];
	private Imagen fondo;
	private ShapeRenderer figuraMenu/*,colision*/;
	private OrthographicCamera cam;
	private OrthographicCamera camEstatica;
	private Sound sfxOpcion;
	private Entradas entradas;
	private float tiempoMapa = 150f, contMapa = 0f, opacidad = 1f,tiempo;
	private int seleccion = 1;
	private boolean reiniciarCam = false, terminoPrimeraParte = false,estaSobreOpcion = false;
	
	public void show() {
		sfxOpcion = Gdx.audio.newSound(Gdx.files.internal(Recursos.SONIDO_OPCION_MENU));
		entradas = new Entradas();
		fondo = new Imagen(Recursos.FONDO_MENU);
		figuraMenu = new ShapeRenderer();
		//colision = new ShapeRenderer();
		cam = new OrthographicCamera(Config.ALTO,Config.ANCHO);	//Camara para el fondo que se mueve.
		camEstatica = new OrthographicCamera(Config.ALTO,Config.ANCHO);	//Camara para las opciones del menu (estaticas).
		Gdx.input.setInputProcessor(entradas);
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
		dibujarFigura();	//Dibuja un rectangulo que vendria a ser donde estan las opciones.
		Render.batch.begin();
		for (int i = 0; i < opciones.length; i++) {
			opciones[i].draw();		//Dibuja las opciones.
		}
		Render.batch.end();
		calcularColisionMouse();
		calcularEntradasConOpciones();
	}

	/*Este metodo calcula cuando tocara una opcion, hay mucho lio en el segundo if porque pasaba que si una opcion estaba seleccionada 
	pero el mouse no estaba sobre la misma y vos clickeabas,la opcion se accionaba,cosa que esta mal.*/
	private void calcularEntradasConOpciones() {													
		if(entradas.isEnter() || entradas.isMouseClick()) { 
			if((seleccion==1 && entradas.isEnter()) || (seleccion == 1 && (entradas.isMouseClick() && estaSobreOpcion))) {
				Render.cancionMenu.pause();		//Pauso la cancion del menu.
				Render.app.setScreen(new PantallaPvP());
			}else if(seleccion == 3) {
				Gdx.app.exit();
			}
		}
	}

	private void calcularColisionMouse() {
		/*colision.setProjectionMatrix(camEstatica.combined);
		colision.begin(ShapeType.Line);
		colision.setColor(Color.RED);
		for (int i = 0; i < opciones.length; i++) {
			colision.rect(opciones[i].getX(), opciones[i].getY()-opciones[i].getAlto(), opciones[i].getAncho(), opciones[i].getAlto());
		}
		colision.end();*/
		//Lo dejo comentado, porque son los rectangulos de las opciones (colisiones).
		//Me surge un problema que es cuando amplio la ventana, las colision se muestran bien pero los colores de las opciones se buguean.
		
		int contSobreOpcion = 0;
		
		//Dentro de este for hay un if que es largo, sirve para calcular cuando el mouse esta sobre el rectangulo (osea la opcion).
		// Se podria usar me parece el metodo overlaps con los rectangulos de lo comentado arriba, pero la verdad que no indaque.
		for (int i = 0; i < opciones.length; i++) {
			if((entradas.getMouseX() >= opciones[i].getX() && entradas.getMouseX() <= (opciones[i].getX()+opciones[i].getAncho())) && 
					(entradas.getMouseY() >= (opciones[i].getY() - opciones[i].getAlto()) && entradas.getMouseY() <= opciones[i].getY())) {
				seleccion = (i+1);
				contSobreOpcion++;
			}
			if(contSobreOpcion > 0) {
				estaSobreOpcion = true;
			}else {
				estaSobreOpcion = false;
			}
		}
	}

	private void dibujarFigura() {
		Gdx.gl.glEnable(GL30.GL_BLEND);		//Esto para que funcione el canal alpha de figuraMenu.setColor();
	    Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
		figuraMenu.setProjectionMatrix(camEstatica.combined);	 // Viewport, esto dentro del Render de la camEstatica.
		figuraMenu.begin(ShapeType.Filled);
		figuraMenu.rect(100, 200, (opciones[1].getAncho()+100), Config.ANCHO - 50);	//No me anda poner alto, tendria que hacer una clase config,															
		figuraMenu.setColor(0,0,0,0.7f);												//que me de el ancho,alto.
		figuraMenu.end();
	}

	private void colorearOpcion() {
		for (int i = 0; i < opciones.length; i++) {		//Este metodo hace que la opcion elegida se pinte de x color, diferenciandose de los demas.
			if (i == (seleccion-1)) {					
				opciones[i].setColor(Color.RED);
			} else {
				opciones[i].setColor(Color.WHITE);
			}
		}
	}

	private int chequearEntradas(float delta) {
		tiempo += delta;
		if(entradas.isAbajo()) {
			if(tiempo >= 0.2f) {	//Hay un delay para elegir otra opcion.
				sfxOpcion.play();
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
				sfxOpcion.play();
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
