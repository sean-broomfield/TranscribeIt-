package org.lastName.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;

import org.lastName.game.Controller;
import org.lastName.game.Constants;
import org.lastName.game.FinalGame;
import org.lastName.game.Renderer; 
import org.lastName.gameobjects.RNAPoly;

public class GameScreen implements Screen, InputProcessor{

	private Controller control; 
	private Renderer render;
	private RNAPoly poly;
	
	FinalGame game;
	private float screenHeight;
	
	public GameScreen(FinalGame g){
		super();
		screenHeight = Gdx.graphics.getHeight();
		control = new Controller();
		render = new Renderer(control); 
		Gdx.input.setInputProcessor(this);
		game = g;
		
	}
	
	@Override
	public void dispose() {
		control.dispose();
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void render(float arg0) {
		if(Gdx.input.isKeyJustPressed(Keys.R) && Constants.GAMEOVER) {
			Constants.GAMEOVER = false;
			control.dispose();
			game.setScreen(new GameScreen(game));
		}
		

		

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		//IF P is Press then game is paused
				if(Gdx.input.isKeyJustPressed(Keys.P) && !Constants.GAMEOVER) {
					if(Constants.PAUSED == false) {
						Constants.PAUSED = true;
					   
					}
					else if (Constants.PAUSED == true)
						Constants.PAUSED = false;
				}
				
				//Don't process any more updates to the world
				if(Constants.PAUSED == false)
					control.update(); // Process inputs and update game world.
				
				//Continue to render objects on the screen.
				render.render();
				
			//if player wants to be reminded of controls
				if(Gdx.input.isKeyJustPressed(Keys.ESCAPE))
					 game.setScreen(new ControlScreen(game));

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
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			poly.face(new Vector2(Gdx.input.getX()-poly.sprite.getX(), 
					-(screenHeight - Gdx.input.getY()-poly.sprite.getY())));
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
