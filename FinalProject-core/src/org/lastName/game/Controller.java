package org.lastName.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.*;

import org.lastName.gameobjects.*;


public class Controller {
	
	ArrayList<GameObject> drawableObjectsMove, drawableObjectsTrans;
	private float screenHeight, screenWidth, DNATime;
	private Music backgroundNoise;
	private Sound enemy, correctTrans, wrongTrans, shot;
	private RNAPoly poly;
	private DNA dna;
	private AlphaAm alpha;
	private Sprite alphaTex;
	
	private Texture DNATexture, topStrand, bottomStrand, virusTexture;
	
	Strand topS, bottomS;
	long spawnTime = System.currentTimeMillis();
	
	private TranscriptionFactor tf;
	
	private TextureAtlas atlas1, atlas2;
	private int ADENINE = 0, URACIL = 1, GUANINE = 2, CYTOSINE = 3;
	private String [] bases = {"adenine","uracil","guanine","cytosine"};
	private String bottomSequence;
	
	int currentNuc;
	long time;
	
	private int lives, score, next, shakeOff, numViruses;
	
	private Random random;
	
	private boolean paused, isMoleculeNear, isDNAOnScreen, 
			isTFOnScreen, escape, isAlphaOnScreen, transcribed;

	final int STATE_MOVING_AROUND = 0;
	final int STATE_ENEMY_ATTACK = 1;
	final int STATE_TRANSCRIBING = 2;
	int state = STATE_MOVING_AROUND;
		
	
	public Controller() {
		
		paused = false;
		isDNAOnScreen = false;
		isTFOnScreen = false;
		lives = 3;
		score = 0;
		shakeOff = 0;
		escape = false;
		transcribed = false;
		bottomSequence = "ATGGGAACCATGGGAACCATG";
		screenHeight = Gdx.graphics.getHeight();
		screenWidth = Gdx.graphics.getWidth();
		
		random = new Random();
		DNATime = 0;
		numViruses = 0;
		
		DNATexture = new Texture(Gdx.files.internal("DNA.png"));
		
		atlas1 = new TextureAtlas(
				Gdx.files.internal("spritesheet1.txt")
				);
		atlas2 = new TextureAtlas(
				Gdx.files.internal("spritesheet2.txt")
				);
		alphaTex = atlas2.createSprite("a-amanitin");
		
		topS = new Strand(
				new Texture(Gdx.files.internal("topstrand.png")), 
				0, screenHeight - 177);
		
		bottomS = new Strand(
				new Texture(Gdx.files.internal("bottomstrand.png")), 
				0, 0);
		
		virusTexture = new Texture(
				Gdx.files.internal("Viral_DNA.png")
				);
		
		isMoleculeNear=false;
		//Load array with objects coming from side of screen
		drawableObjectsMove = new ArrayList<GameObject>();
		drawableObjectsTrans = new ArrayList<GameObject>();
		initRNAPoly();
		initSound();		
		time = System.currentTimeMillis();
	
	}
	
	private void initSound() {
		backgroundNoise = Gdx.audio.newMusic(Gdx.files.internal("finalBG.mp3"));
		enemy = Gdx.audio.newSound(Gdx.files.internal("enemy.mp3"));
		shot = Gdx.audio.newSound(Gdx.files.internal("shot.mp3"));
		correctTrans = Gdx.audio.newSound(Gdx.files.internal("correctTrans.mp3"));
		wrongTrans = Gdx.audio.newSound(Gdx.files.internal("wrongTrans.mp3"));
		
		backgroundNoise.setLooping(true);
		backgroundNoise.play();
		backgroundNoise.setVolume(1.0f);
	}
	
	private void initRNAPoly() {
		
		poly = new RNAPoly(atlas1.createSprite("poly2"), 100, 100);
		drawableObjectsMove.add(poly);
		
	}
	
	private void initMissile() {
		int w = 10;
		int h = 10;
		Pixmap pmap = new Pixmap(w, h, Format.RGB565);
		pmap.setColor(Color.RED);
		pmap.drawLine(w/2,  0,  w/2,  h);
		drawableObjectsMove.add(new Missile(new Texture(pmap), poly.getDirection(), poly.getPosition()));
		
	}
	
	private void generateRandom(int n) {
		if(n >= 0 && n <=30 && drawableObjectsMove.size() < 15 && ((spawnTime  + random.nextInt(6000) + 5000) <= System.currentTimeMillis()) && isAlphaOnScreen == false) {
			spawnTime = System.currentTimeMillis();
			createAlpha();
			isAlphaOnScreen = true;
		} else if (n >= 31 && n <=70 && drawableObjectsMove.size() < 15 && ((spawnTime  + random.nextInt(6000) + 5000) <= System.currentTimeMillis()) && numViruses < 2){
			spawnTime = System.currentTimeMillis();
			initViral_DNA();
		} else if (n > 71 && n < 101 && drawableObjectsMove.size() < 15 && ((spawnTime  + random.nextInt(6000) + 5000) <= System.currentTimeMillis())) {
			spawnTime = System.currentTimeMillis();
			if(!isDNAOnScreen)
				createDNA();
		}
	}
	
	private void createAlpha() {
			alpha = new AlphaAm(alphaTex);
			drawableObjectsMove.add(alpha);		
	}
	
	private void createDNA() {
		dna = new DNA(DNATexture);
		drawableObjectsMove.add(dna);
		tf = new TranscriptionFactor(new Animation((float)0.1, atlas1.findRegions("transcription_factor")), 
				dna.sprite.getWidth()/2 + screenWidth, 
				dna.sprite.getHeight() + dna.sprite.getY() - 20);
		drawableObjectsMove.add(tf);
		isDNAOnScreen = true;

	}
	
	/* use the boolean isDNAOnScreen to determine whether to draw a DNA object
	 * on the screen. If so, create it and add it to the GameObject arraylist
	 * and set the boolean to true to ensure only one DNA object can be
	 * on screen at a time.
	 */
	/*
	 * 
	private void initDNA() {
		
		if (isDNAOnScreen == false && isAlphaOnScreen == false) {
			alpha = new AlphaAm(alphaTex);
			drawableObjectsMove.add(alpha);
			isAlphaOnScreen = true;
		}
		
		if (isDNAOnScreen == false && random.nextInt(2) == 1 && DNATime > 5) {
			
			dna = new DNA(DNATexture);
			drawableObjectsMove.add(dna);
			isDNAOnScreen = true;
		}
		
		else
			DNATime += Gdx.graphics.getDeltaTime();
		
		if (dna != null && dna.sprite.getX() <= -dna.sprite.getWidth()) {
			
			dna = null;
			drawableObjectsMove.remove(dna);
			isDNAOnScreen = false;
			DNATime = 0;
			
			if (transcribed == false)
				score -= 20;
			else
				transcribed = false;
		}
		
	};
	
	private void initTF () {
		
		if (isDNAOnScreen && !isTFOnScreen) {
			tf = new TranscriptionFactor(new Animation((float)0.1, atlas1.findRegions("transcription_factor")), 
					dna.sprite.getWidth()/2 + screenWidth, 
					dna.sprite.getHeight() + dna.sprite.getY() - 20);
		
			drawableObjectsMove.add(tf);
			isTFOnScreen = true;

		}
		
		else if (isTFOnScreen && !isDNAOnScreen) {
			
			tf = null;
			drawableObjectsMove.remove(tf);
			isTFOnScreen = false;
		}
	}
	*/
	
	private void initNucleotide(String base) {
		
		drawableObjectsTrans.add(
				new Nucleotide(atlas2.createSprite(base))
		);
		
		GameObject obj;
		for(int i = 0; i < drawableObjectsTrans.size(); i++) {
			
			obj = drawableObjectsTrans.get(i);
			
			if (obj instanceof Nucleotide)
				((Nucleotide) obj).moveLeft();
			else if (obj instanceof Strand)
				((Strand) obj).moveLeft();
		}
		
		
		// end addition to drawableOjects
	}
	
	private void initViral_DNA() {
		
		drawableObjectsMove.add(new Viral_DNA(virusTexture));
		numViruses++;
	}
	
	private void alphaMove () {
				
		if (poly.sprite.getX() < alpha.sprite.getX() && poly.sprite.getY() < alpha.sprite.getY())
			alpha.setMovement(false, false);
		else if (poly.sprite.getX() < alpha.sprite.getX() && poly.sprite.getY() > alpha.sprite.getY())
			alpha.setMovement(false, true);
		else if (poly.sprite.getX() > alpha.sprite.getX() && poly.sprite.getY() < alpha.sprite.getY())
			alpha.setMovement(true, false);
		else
			alpha.setMovement(true, true);
	}
	
	private void processKeyboardInput() {
		
		if(state != STATE_TRANSCRIBING) {
			//moving around
			if(Gdx.input.isKeyPressed(Keys.D)) {
				poly.moveRight(Gdx.graphics.getDeltaTime());
			}
			if (Gdx.input.isKeyPressed(Keys.S)) {
				poly.moveDown(Gdx.graphics.getDeltaTime());
			}
			if (Gdx.input.isKeyPressed(Keys.W)) {
				poly.moveUp(Gdx.graphics.getDeltaTime());
			}
			if (Gdx.input.isKeyPressed(Keys.A)) {
				poly.moveLeft(Gdx.graphics.getDeltaTime());
			}
			
		}
		
		if (state == STATE_MOVING_AROUND) {
			if(Gdx.input.isKeyJustPressed(Keys.Q)) {
				
				if (tf != null) {
					if (tf.sprite.getBoundingRectangle().overlaps(poly.sprite.getBoundingRectangle())) {
						drawableObjectsTrans.add(topS);
						drawableObjectsTrans.add(bottomS);
						state = STATE_TRANSCRIBING;
						currentNuc = 0;
					}
				}
			}
			
			if(Gdx.input.isKeyPressed(Keys.SPACE)) {
				
				if(System.currentTimeMillis() >= time + 100) {
					
					time = System.currentTimeMillis();
						initMissile();
						shot.play(1.0f);
				}	
			}
		}
		
		else if (state == STATE_ENEMY_ATTACK) {
			
			if (shakeOff == 0) {
				
				escape = false;
				alpha.getOff(Gdx.graphics.getDeltaTime());
				
				if (Math.abs(poly.sprite.getX() - alpha.sprite.getX()) > 5) {
					state = STATE_MOVING_AROUND;
					poly.normalSpeed();
				}
			}
			
			else if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
				
				shakeOff --;
			}
		}
		
		else {
			if (currentNuc < 20) {
				
				//transcribing
				if(Gdx.input.isKeyJustPressed(Keys.A)) {
					
					initNucleotide(bases[ADENINE]);
					if (bottomSequence.charAt(currentNuc) == 'T') {
						//transcribe with U
						

						correctTrans.play();
						score++;
					}
					else {
						wrongTrans.play();
						score--;	
					}
					currentNuc ++;
				}
				
				else if(Gdx.input.isKeyJustPressed(Keys.U)) {
					
					initNucleotide(bases[URACIL]);
					if (bottomSequence.charAt(currentNuc) == 'A') {
						//transcribe with U
						

						correctTrans.play();
						score++;
					}
					else {
						wrongTrans.play();
						score--;	
					}
					currentNuc ++;
				}
			
				if(Gdx.input.isKeyJustPressed(Keys.G)) { 
					
					initNucleotide(bases[GUANINE]);
					if (bottomSequence.charAt(currentNuc) == 'C') {
						//transcribe with U
						

						correctTrans.play();
						score++;
					}
					else {
						wrongTrans.play();
						score--;	
					}
					currentNuc ++;
				}
				
				if(Gdx.input.isKeyJustPressed(Keys.C)) { 	
					
					initNucleotide(bases[CYTOSINE]);
					if (bottomSequence.charAt(currentNuc) == 'G') {
						//transcribe with U
						
						correctTrans.play();
						score++;
					}
					else {
						wrongTrans.play();
						score--;	
					}
					currentNuc ++;
				}
			}
			else{
				
				topS = new Strand(
						new Texture(Gdx.files.internal("topstrand.png")), 
						0, screenHeight - 177);
				bottomS = new Strand(
						new Texture(Gdx.files.internal("bottomstrand.png")), 
						0, 0);
				drawableObjectsTrans.clear();
				state = STATE_MOVING_AROUND;
			}
		}
	}
	
	private void processMouseInput() {
		
		Vector2 v = new Vector2(Gdx.input.getX()-poly.sprite.getX(), -(screenHeight-Gdx.input.getY()-poly.sprite.getY()));
		//enemy attacking
		if(state == STATE_MOVING_AROUND){
		
			poly.face(new Vector2(v));

			if (Gdx.input.getX() < poly.sprite.getX() && poly.getDirection().x > 0) {
				poly.sprite.flip(false, true);
			}
			
			else if (Gdx.input.getX() > poly.sprite.getX() && poly.getDirection().x < 0) {
				poly.sprite.flip(false, true);
			}
			
		}	
	}
	
	public void update() {
		
		if(!Constants.GAMEOVER) {
			scoreCheck();
			processKeyboardInput();
			processMouseInput();
			boolean [] removalArray = new boolean[drawableObjectsMove.size()];
			float deltaT = Gdx.graphics.getDeltaTime();	
		
			if (state != STATE_TRANSCRIBING) {	
				
				generateRandom(random.nextInt(100));
				poly.update(deltaT);
				
				if (numViruses < 2)
					initViral_DNA();
				
				if (alpha != null) {
					
					if (!escape)
						alphaMove();
			
					if ((Math.abs(alpha.sprite.getX() - poly.sprite.getX()) < 15) && (Math.abs(alpha.sprite.getY() - poly.sprite.getY()) < 15)) {
						if (state != STATE_ENEMY_ATTACK) {
							poly.slowDown();
							shakeOff = 5;
							escape = false;
							state = STATE_ENEMY_ATTACK;
						}
					}
					else
						escape = false;
			}
			
					//CHECK FOR COLLISIONS BETWEEN OBJECTS
		
				for(int i = 0; i < drawableObjectsMove.size(); i++) {
					GameObject obj = drawableObjectsMove.get(i);
					if(obj instanceof Missile) {
						((Missile) obj).update(deltaT);
						if( ((Missile)obj).remove)
							removalArray[i] = true;
						for (GameObject g : drawableObjectsMove) {
							if(((g instanceof AlphaAm)) && !(((Missile) obj).remove) &&   (((Missile) obj).sprite.getBoundingRectangle().overlaps((((AlphaAm) g).sprite.getBoundingRectangle())))) {
								((Missile) obj).remove = true;
								removalArray[i] = true;
								((AlphaAm)g).subHp();
								if( ((AlphaAm)g).getHp() <= 0) {
									((AlphaAm)g).remove = true;
									alpha = null;
									isAlphaOnScreen = false;
								}
							}
							if(((g instanceof Viral_DNA)) &&   (((Missile) obj).sprite.getBoundingRectangle().overlaps((((Viral_DNA) g).sprite.getBoundingRectangle())))) {
								((Missile) obj).remove = true;
								removalArray[i] = true;
								((Viral_DNA)g).remove = true;
							}
						}
					}
				
					if (obj instanceof DNA) {
						((DNA) obj).update(deltaT);
						//If dna goes off screen, player loses points
						if ( ((DNA)obj).sprite.getX() <= -((DNA)obj).sprite.getWidth() ) {
							score-=25;
						}
						if( ((DNA)obj).remove )  {
							removalArray[i] = true;
							isDNAOnScreen = false;
						}
					}
					if (obj instanceof TranscriptionFactor) {
						((TranscriptionFactor) obj).update(deltaT);
						if(!isDNAOnScreen)
							removalArray[i] = true;
					}
					
					if (obj instanceof AlphaAm) {
						((AlphaAm) obj).update(deltaT);
						if ( ((AlphaAm)obj).remove)
							removalArray[i] = true;
					}
					
					if (obj instanceof Viral_DNA) {
						((Viral_DNA)obj).update(deltaT);
						if ( ((Viral_DNA)obj).sprite.getX() <= -((Viral_DNA)obj).sprite.getWidth() ) {
							score-=25;
						}
						if( ((Viral_DNA)obj).remove ) {
							removalArray[i] = true;
							numViruses--;
						}
					}
				}
			
				//Checks objects that need to be erased
				for(int i = removalArray.length - 1; i >= 0; i--) {
					if(removalArray[i]) {
						drawableObjectsMove.remove(i);
					}
				}
			}
		
			else if (state == STATE_TRANSCRIBING){
			
			//Remove objects from the current state of the game except the RNAPoly
				for(int i = 0; i < drawableObjectsMove.size(); i++) {
					GameObject obj = drawableObjectsMove.get(i);
					if( !(obj instanceof RNAPoly)) {
						removalArray[i] = true;
					}
				}
			
				for(int i = removalArray.length - 1; i >= 0; i--) {
					if(removalArray[i]) {
						drawableObjectsMove.remove(i);
					}
				}
			
				for(int i = 0; i < drawableObjectsTrans.size(); i++) {
					GameObject obj = drawableObjectsTrans.get(i);
					obj = drawableObjectsTrans.get(i);
				
					if (obj instanceof Nucleotide)
						((Nucleotide) obj).update(deltaT);
				
					else if (obj instanceof Strand)
						((Strand) obj).update(deltaT);
				
				}
				
			}
		}
	}
	
	public ArrayList<GameObject> getDrawableObjects() {
		
		if (state != STATE_TRANSCRIBING)
			return drawableObjectsMove;
		else
			return drawableObjectsTrans;
	}
	
	public int getState() {
		return state;
	}
	
	public int getLives() {
		return lives;
	}
	
	public int getScore() {
		return score;
	}
	
	public void scoreCheck() {
		//If score less than -250 game is over
		if(score <= -250) {
			Constants.GAMEOVER = true;
		}
	}
	
	public void dispose() {
		if (enemy != null) {
			enemy.dispose();
		}
		if (shot != null) {
			shot.dispose();
		}		
		if (correctTrans != null) {
			correctTrans.dispose();
		}		
		if (wrongTrans != null) {
			wrongTrans.dispose();
		}
		if (backgroundNoise != null) {
			backgroundNoise.dispose();
		}		

	}

	
}
