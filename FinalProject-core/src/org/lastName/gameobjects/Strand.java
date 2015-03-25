package org.lastName.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.*;
import org.lastName.game.*;

public class Strand extends GameObject implements Updatable {
	
	private float targetXPos;
	
	public Strand (Texture s, float x, float y) {
		
		targetXPos = Gdx.graphics.getWidth();
		
		sprite = new Sprite(s);	
		sprite.setSize(s.getWidth(), s.getHeight());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(x,y);
		
		setIsDrawable(true);
	}
	
	@Override
	public void update(float deltaT) {
		
		if (sprite.getX() > targetXPos)
			sprite.setX(sprite.getX() - Constants.nucleotideWidth * deltaT * 8);
	}
	
	public void moveLeft() {
		targetXPos = (sprite.getX() - Constants.nucleotideWidth);
	}
}
