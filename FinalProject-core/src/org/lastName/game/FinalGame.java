package org.lastName.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;

public class FinalGame extends ApplicationAdapter {
	private Controller control; 
	private Renderer render;
	
	@Override
	public void create () {
		control = new Controller();
		render = new Renderer(control);
	}
	
	@Override
	public void dispose() {
		control.dispose();
	}
	
	@Override
	public void render () {
		if(Gdx.input.isKeyJustPressed(Keys.R) && Constants.GAMEOVER) {
			Constants.GAMEOVER = false;
			dispose();
			create();
		}
		
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		//IF P is Press then game is paused
		if(Gdx.input.isKeyJustPressed(Keys.P) && !Constants.GAMEOVER) {
			if(Constants.PAUSED == false)
				Constants.PAUSED = true;
			else if (Constants.PAUSED == true)
				Constants.PAUSED = false;
		}
		
		//Don't process any more updates to the world
		if(Constants.PAUSED == false)
			control.update(); // Process inputs and update game world.
		
		//Continue to render objects on the screen.
		render.render();

	}
}


/*
 package org.lastName.game;
 

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import org.lastName.screens.WelcomeScreen;

public class FinalGame extends Game{//extends ApplicationAdapter {

	@Override
	public void create () {

		setScreen(new WelcomeScreen(this));
	}
	@Override
	public void render () {

		getScreen().render(Gdx.graphics.getDeltaTime());
	}
	

	@Override
	public void dispose(){
		getScreen().dispose();

	}	
	
}
*/