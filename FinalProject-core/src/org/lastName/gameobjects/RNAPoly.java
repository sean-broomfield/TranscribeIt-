package org.lastName.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.*;


public class RNAPoly extends GameObject implements Updatable {

	private Vector2 targetDirection;
	private Vector2 direction;
	private float screenW;
	private float screenH;
	private int speed;
	
	public RNAPoly(Sprite spr, int x, int y) {
		
		screenW = Gdx.graphics.getWidth();
		screenH = Gdx.graphics.getHeight();
		
		sprite = new Sprite(spr);
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(x, y);
		sprite.setRotation(90);
		
		speed = 30;
		direction = new Vector2(0, -1);
		targetDirection = new Vector2(0, -1);
		setIsDrawable(true);
	}
	
	@Override
	public void update(float deltaTime) {
		
		double cosTheta = (direction.dot(targetDirection))/targetDirection.len();
		if(cosTheta >1) {
			cosTheta = 1;
		}
		
		double deg = Math.acos(cosTheta);
		deg = Math.toDegrees(deg) * deltaTime;
		if(direction.crs(targetDirection) > 0) {
			deg = -deg;
		}
		
		sprite.rotate((float) deg * 15);
		direction.rotate(-(float) deg * 15);
	}
	
	public void face(Vector2 vect) {
		
		targetDirection = vect;
	}
	
	public void moveUp(float deltaTime) {
		
		if(sprite.getY() < screenH - 65) {
			sprite.translateY(5 * deltaTime*speed);
		}
	}
	
	public void moveLeft(float deltaTime) {
		
		if(sprite.getX() > 3) {
			sprite.translateX(-5 * deltaTime*speed);
		}
	}
	
	public void moveRight(float deltaTime) {
		
		if(sprite.getX() < screenW - 20) {
			sprite.translateX(5 * deltaTime*speed);
		}
	}
	
	public void moveDown(float deltaTime) {
		
		if(sprite.getY() > 3) {
			sprite.translateY(-5 * deltaTime*speed);
		}
	}
	
	public Vector2 getPosition() {
		
		return new Vector2(sprite.getX(), sprite.getY());
	}
	
	public Vector2 getDirection() {
		
		return direction;
	}
	
	public void slowDown() {
		
		speed = 10;
	}
	
	public void normalSpeed() {
		
		speed = 30;
	}

}