package org.lastName.gameobjects;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.*;

public class Viral_DNA extends GameObject implements Updatable {

	public boolean remove;

public Viral_DNA (Texture t) {
		
		Random rand = new Random();
		
		sprite = new Sprite(t);	
		sprite.setSize(t.getWidth(), t.getHeight());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(Gdx.graphics.getWidth(), 
				rand.nextInt(Gdx.graphics.getHeight() - t.getHeight() * 2)
				);
		sprite.setRotation(180);
		
		setIsDrawable(true);
	}
	
	@Override
	public void update(float deltaT) {
		if (this.sprite.getX() <= -this.sprite.getWidth()) {
			remove = true;
		}
		moveLeft(deltaT);
	}
	
	public void moveLeft(float deltaT) {
		sprite.setX(sprite.getX() - 200 * deltaT);
	}
}
