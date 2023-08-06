package com.bakpun.mistborn.pantallas;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bakpun.mistborn.elementos.Imagen;
import com.bakpun.mistborn.elementos.Personaje;
import com.bakpun.mistborn.utiles.Config;
import com.bakpun.mistborn.utiles.Recursos;
import com.bakpun.mistborn.utiles.Render;

public class PantallaPvP implements Screen{
	//Faltan las plataformas.
	private Imagen fondo;
	private Personaje pj;
	private OrthographicCamera cam;
	private Viewport vw;
	
	public void show() {
		fondo = new Imagen(Recursos.FONDO_PVP);
		pj = new Personaje(Recursos.PERSONAJE_VIN);
		cam = new OrthographicCamera();	//Creo una camara para hacer el viewport.
		cam.position.set(new Vector2(fondo.getTexture().getWidth()/2,fondo.getTexture().getHeight()/2), 0);	//Para posicionar la camara en el centro del fondo. La camara como default esta con coordenadas negativas.
		vw = new FillViewport(Config.ANCHO,Config.ALTO,cam);
	}

	public void render(float delta) {
		Render.limpiarPantalla(1,1,1);
		cam.update();	//Updateo la camara.
		Render.batch.setProjectionMatrix(vw.getCamera().combined);
		Render.batch.begin();
		fondo.draw();	//Dibujo el fondo.
		pj.draw(); 	//Updateo los movimientos del jugador.
		Render.batch.end();
		//Para hacer que la camara siga al pj, en este juego no la necesitamos, pero ahi esta.
		//cam.position.set(pj.getX(), pj.getY(), 0);
	}

	@Override
	public void resize(int width, int height) {
		vw.update(width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		fondo.getTexture().dispose();	//Texture
		pj.dispose();	//Texture.
		Render.batch.dispose();		//SpriteBatch.
		this.dispose();
	}
}
