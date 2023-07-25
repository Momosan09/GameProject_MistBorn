package com.bakpun.mistborn.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.bakpun.mistborn.elementos.Imagen;
import com.bakpun.mistborn.utiles.Render;

public class PantallaMenu implements Screen{

	/*Falta poner un rectangulo en el menu que te diga "Jugar" por ahora. Y hacer la transparencia y que la imagen vuelva,
	como el menu de oblivion*/
	
	private Imagen fondo;
	private OrthographicCamera cam;
	private final float VELOCIDAD_CAMARA = 1.2f;
	private float tiempoMapa,contMapa;

	public void show() {
		tiempoMapa = 150f;contMapa = 0f;
		int alto = Gdx.graphics.getHeight(),ancho = Gdx.graphics.getWidth();
		fondo = new Imagen("backgroundMenu.jpg");
		cam = new OrthographicCamera(alto,ancho);
	}

	public void render(float delta) {
		Render.limpiarPantalla();
		cam.update();
		Render.batch.setProjectionMatrix(cam.combined);
		Render.batch.begin();
		calcularMovCamara();
		fondo.draw();
		Render.batch.end();
	}

	private void calcularMovCamara() {
		if(tiempoMapa > contMapa) {
			moverCamara();
		}else if(tiempoMapa <= contMapa){
			reinciarCamara();
			contMapa = 0f;
		}
		contMapa += 0.1f;	
	}

	private void reinciarCamara() {
		//cam.viewportWidth es lo que estaba buscando. Sino no me andaba y tenia que ponerlo por mi cuenta.
		cam.position.set(cam.viewportWidth/2,cam.viewportHeight/2,0);
	}

	private void moverCamara() {
		cam.translate(VELOCIDAD_CAMARA, VELOCIDAD_CAMARA);
	}

	@Override
	public void resize(int width, int height) {
		cam.setToOrtho(false, width, height);
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
