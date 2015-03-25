package org.lastName.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;

public class TranscriptionFactor extends GameObject implements Updatable {
	
	public boolean remove;
	private Animation anim;
	private float TFStateTime;
	private float tfX, tfY;
	
	public TranscriptionFactor (Animation a, float x, float y) {
		
		anim = a;
		TFStateTime = 0;
		tfX = x;
		tfY = y;
		setSprite(anim.getKeyFrame(TFStateTime, false));
		setIsDrawable(true);
	}
	
	public void setSprite (TextureRegion tr) {
		
		sprite = new Sprite(tr);
		sprite.setSize(sprite.getWidth(), sprite.getHeight());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(tfX,tfY);
	}
	
	@Override
	public void update(float deltaT) {
		
		if (anim.isAnimationFinished(TFStateTime)) {
			
			TFStateTime = 0;
			setSprite(anim.getKeyFrame(TFStateTime, false));
		}
		
		else {
			
			TFStateTime += Gdx.graphics.getDeltaTime();
			setSprite(anim.getKeyFrame(TFStateTime, false));	
		}
		
		sprite.setX(sprite.getX() - 200 * deltaT);
		tfX = sprite.getX();
		tfY = sprite.getY();
	}
	
}