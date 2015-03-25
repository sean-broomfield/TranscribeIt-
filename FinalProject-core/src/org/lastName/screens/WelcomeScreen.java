package org.lastName.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import  org.lastName.game.FinalGame;

public class WelcomeScreen implements Screen, InputProcessor{

	FinalGame game;
	private SpriteBatch spriteBatch;
	private Texture background;
	
	public WelcomeScreen(FinalGame g){
		super();
		Gdx.input.setInputProcessor(this);
		game = g;
		spriteBatch = new SpriteBatch(); 
		background = new Texture("welcomeScreen.png");
		
	}
	
	@Override
	public void dispose() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void render(float arg0) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		spriteBatch.begin();
		spriteBatch.draw(background, 0, 0);
		spriteBatch.end();
		
	}

	@Override
	public void resize(int arg0, int arg1) {
	}

	@Override
	public void resume() {
	}

	@Override
	public void show() {
	}

	@Override
	public boolean keyDown(int arg0) {
		return false;
	}

	@Override
	public boolean keyTyped(char arg0) {
		return false;
	}

	@Override
	public boolean keyUp(int arg0) {
		return false;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int arg3) {
		// TODO: Magic numbers! :(
		if(x > 280 && x < 640 && y > 230 && y < 320){
			game.setScreen(new GameScreen(game));
			dispose();
		}
		else if(x > 245 && x < 690 && y > 310 && y < 420){
			game.setScreen(new ControlScreen(game));
			
			dispose();
		}
		return false;
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		return false;
	}

}
