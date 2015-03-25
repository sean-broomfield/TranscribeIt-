package org.lastName.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.*;

public class Missile extends GameObject implements Updatable {

	private Vector2 dirAndVel;
	
	private final float VELOCITY = 300;
	int screenWidth;
	int screenHeight;
	public boolean remove;
	
	public Missile(Texture texture, Vector2 dir, Vector2 pos) {
		sprite = new Sprite(texture);
		sprite.setOrigin(texture.getWidth()/2, texture.getHeight()/2);
		sprite.setPosition(pos.x, pos.y);
		dirAndVel = new Vector2(dir.x, -dir.y);
		dirAndVel.scl(VELOCITY);
		sprite.rotate(dirAndVel.angle()-90);
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
	}
	
	public void update(float deltaTime) {
		sprite.translate(dirAndVel.x*deltaTime, dirAndVel.y*deltaTime);
		
		if(sprite.getX() > (screenWidth - 32) || sprite.getX() < -32 || sprite.getY() < -32 || sprite.getY() > (screenHeight + 32)) {
			remove = true;
		} 	
	}
}
