package org.lastName.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Gdx;

import org.lastName.gameobjects.*;

import com.badlogic.gdx.math.*;


public class DNA extends GameObject implements Updatable {
	
	public boolean remove;
	
	public DNA (Texture t) {
		
		sprite = new Sprite(t);
		sprite.setSize(t.getWidth(), t.getHeight());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(Gdx.graphics.getWidth(), 
			Gdx.graphics.getHeight()/10);
		setIsDrawable(true);
	}
	
	@Override
	public void update(float deltaT) {
		
		if (this.sprite.getX() <= -this.sprite.getWidth()) {
			remove = true;
		}
		
		
		sprite.setX(sprite.getX() - 200 * deltaT);
	}
}
