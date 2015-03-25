package org.lastName.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.lastName.gameobjects.*;
import org.lastName.game.*;

public class Renderer {
	
	private Controller control;
	private SpriteBatch spriteBatch;
	BitmapFont font, pauseFont;
	Texture bg1, bg2, bg3;
	float bg1XPos, bg2XPos, bg3XPos;
	
	public Renderer(Controller c) {
		control = c;
		font = new BitmapFont();
		pauseFont = new BitmapFont();
		spriteBatch = new SpriteBatch(); 
		bg1 = new Texture("dna.jpg");
		bg2 = new Texture("dna.jpg");
		bg3 = new Texture("polybg.png");
		bg1XPos = 0;
		bg2XPos = bg1.getWidth();
		bg3XPos = -10;
		font.setColor(Color.RED);
		pauseFont.setColor(Color.GREEN);
		pauseFont.scale(4);
	}
	
	public void renderBackground() {
		spriteBatch.draw(bg1, bg1XPos, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		spriteBatch.draw(bg2, bg2XPos, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		if (bg1XPos + bg1.getWidth() < Gdx.graphics.getWidth()) {
			bg2XPos = (bg1XPos + bg1.getWidth());
		}
	
		if (bg2XPos + bg2.getWidth() < Gdx.graphics.getWidth()) {
			bg1XPos = (bg2XPos + bg2.getWidth());
	    }
	
		if (control.getState() == 2) {
			spriteBatch.draw(bg3, bg3XPos, 0);

		}
	 
		if(!Constants.PAUSED) {
			bg1XPos -= 0.3;
			bg2XPos -= 0.3;
		} 
		
		else if (Constants.PAUSED && !Constants.GAMEOVER) {
			pauseFont.draw(spriteBatch, "PAUSED", (Gdx.graphics.getWidth()/2) - 115, Gdx.graphics.getHeight()/2);
		//	font.draw(spriteBatch, "Press 'W' to move up.", 0, 15);
		//	font.draw(spriteBatch, "Press 'S' to move down.", 0, (Gdx.graphics.getHeight()/2) - 30);
		//	font.draw(spriteBatch, "Press 'A' to move left.", 0, (Gdx.graphics.getHeight()/2) - 45);
		//	font.draw(spriteBatch, "Press 'D' to move right.", 0, (Gdx.graphics.getHeight()/2) - 60);
		//	font.draw(spriteBatch, "Click 'LEFT BUTTON' on your Mouse to shoot missiles.", 0, (Gdx.graphics.getHeight()/2) - 75);
		} 
	}
	
	public void render(){
		
		spriteBatch.begin();
		renderBackground();
		
		//font.draw(spriteBatch, "Lives: " + control.getLives(), 0, Gdx.graphics.getHeight() - 5);
		font.draw(spriteBatch,  "Score: " + control.getScore(),  0,  Gdx.graphics.getHeight() - 25);
		//font.draw(spriteBatch, "DrawableObjects size: " + control.getDrawableObjects().size(), 0, Gdx.graphics.getHeight() - 45);
		for(GameObject gObj : control.getDrawableObjects()) {
			gObj.sprite.draw(spriteBatch);
		}
		
		if (Constants.GAMEOVER) {
			pauseFont.draw(spriteBatch, "GAME OVER", (Gdx.graphics.getWidth()/2) - 200, Gdx.graphics.getHeight()/2);
			font.draw(spriteBatch,  "Press R to Restart the Game", Gdx.graphics.getWidth()/2 - 115, Gdx.graphics.getHeight()/2 - 100);
		}
		
		spriteBatch.end();
	}
}