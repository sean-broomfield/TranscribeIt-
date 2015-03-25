package org.lastName.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.*;

public class AlphaAm extends GameObject implements Updatable {
	
	private int hp;
	private boolean movementRight, movementUp;
	public boolean remove;
	
	
	public AlphaAm (Sprite t) {
		
		hp = 5;
		sprite = new Sprite(t);
		sprite.setSize(t.getWidth(), t.getHeight());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(Gdx.graphics.getWidth(), 
			Gdx.graphics.getHeight()/2);
		setIsDrawable(true);
	}
	
	public void setMovement(boolean moveRight, boolean moveUp) {
		
		movementRight = moveRight;
		movementUp = moveUp;
	}
	
	@Override
	public void update (float deltaT) {
		
		if (movementRight)
			sprite.setX(sprite.getX() + 100 * deltaT);
		else
			sprite.setX(sprite.getX() - 100 * deltaT);
		
		if (movementUp)
			sprite.setY(sprite.getY() + 100 * deltaT);
		else
			sprite.setY(sprite.getY() - 100 * deltaT);
		
	}
	
	public int getHp () {
		
		return hp;
	}
	
	public void subHp () {
		
		hp--;
	}
	
	public void getOff(float deltaT) {
		
		sprite.setX(sprite.getX() - 1000 * deltaT);
		sprite.setY(sprite.getY() - 1000 * deltaT);
	}
	
	public boolean getRemove() {
		return remove;
	}
}