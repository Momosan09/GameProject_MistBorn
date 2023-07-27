package com.bakpun.mistborn.io;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class Entradas implements InputProcessor{
	//Hay que desarrollar esta clase para las entradas del usuario.

	private boolean abajo,arriba,irDer,irIzq,saltar;
	
	public boolean keyDown(int keycode) {
		
		if(keycode == Keys.D || keycode == Keys.RIGHT) {
			irDer = true;
		}
		if(keycode == Keys.A || keycode == Keys.LEFT) {
			irIzq = true;
		}
		if(keycode == Keys.SPACE) {
			saltar = true;
		}
		if(keycode == Keys.DOWN) {
			abajo = true;
		}
		if(keycode == Keys.UP) {
			arriba = true;
		}
		return false;
	}

	public boolean keyUp(int keycode) {
		if(keycode == Keys.D || keycode == Keys.RIGHT) {
			irDer = false;
		}
		if(keycode == Keys.A || keycode == Keys.LEFT) {
			irIzq = false;
		}
		if(keycode == Keys.SPACE) {
			saltar = false;
		}
		if(keycode == Keys.DOWN) {
			abajo = false;
		}
		if(keycode == Keys.UP) {
			arriba = false;
		}
		return false;
	}
	
	public boolean isIrDer() {
		return this.irDer;
	}
	public boolean isIrIzq() {
		return this.irIzq;
	}
	public boolean isSaltar() {
		return this.saltar;
	}
	public boolean isAbajo() {
		return this.abajo;
	}
	public boolean isArriba() {
		return this.arriba;
	}
	

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		// TODO Auto-generated method stub
		return false;
	}

}
