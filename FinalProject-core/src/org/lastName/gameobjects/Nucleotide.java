package org.lastName.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;

public class Nucleotide extends GameObject implements Updatable {
	
	private float targetXPos;
	
	public Nucleotide (Sprite s) {
		
		targetXPos = Gdx.graphics.getWidth();
		
		sprite = new Sprite(s);	
		sprite.setSize(s.getWidth(), s.getHeight());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(s.getWidth() *4 ,Gdx.graphics.getHeight());
		sprite.setRotation(180);
		
		setIsDrawable(true);
	}
	
	@Override
	public void update(float deltaT) {

		if (sprite.getY() > Gdx.graphics.getHeight()/3)
			sprite.setY(sprite.getY() - 500 * deltaT * 3);
		
		if (sprite.getX() > targetXPos)
			sprite.setX(sprite.getX() - sprite.getWidth() * deltaT * 8);
	}
	
	public void moveLeft() {
		targetXPos = sprite.getX() - sprite.getWidth() + 5;
	}
}
