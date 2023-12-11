package entities;


import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;

import static utilz.HelpMet.*;

import utilz.LoadSave;

import static utilz.Constants.PlayarConstants.*;

public class Player extends Entity{
	
	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 15;
	private int playerAction = IDLE;
	private boolean moving = false, attacking = false;
	private boolean left, right, up, down, jump;
	private float playerSpeed = 2.0f;
	private int[][] lvlData;
	private float xDrawOffset = 21 * Game.SCALE;
	private float yDrawOffset = 4 * Game.SCALE;
	private float airSpeed = 0f;
	private float gravity = 0.04f *Game.SCALE;
	private float jumpSpeed = -2.25f * Game.SCALE;
	private float fallSpeed = 0.5f * Game.SCALE;
	private boolean inAir = false;



	public Player(float x, float y, int width, int height) {
		super(x,y, width, height);
		loadAnimation();
		initHitbox(x, y, 20*Game.SCALE, 28*Game.SCALE);
	}
	
	public void update() {
		updatePos();
		updateAnimationTick();
		setAnimation();

	}
	
	public void render(Graphics g) {
		
		g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), width, height, null);
		drawHixbox(g);
	}
	


	private void updatePos() {
		
		moving = false;
		
		if(jump){
			jump();
		}

		if(!left && !right && !inAir){
			return;	
		}

		float xSpeed = 0;
		
		if(left){
			xSpeed -= playerSpeed;
		}else if(right && !left){
			xSpeed += playerSpeed;	
		}
		if(!inAir){
			if(!IsEntityOnFLoor(hitbox, lvlData)){
				inAir = true;
			}
		}

		if(inAir){
			if(CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)){
			hitbox.y += airSpeed;
			airSpeed += gravity;
			updateXPos(xSpeed);
			}else{
				hitbox.y = GetEntityPosY(hitbox, airSpeed);
				if(airSpeed > 0){
					resetInAir();
				}else{
					airSpeed = fallSpeed;
				}
				updateXPos(xSpeed);
			}
		
		}else{
			updateXPos(xSpeed);
		}

		moving = true;
	
	}


	
	private void resetInAir(){
		inAir = false;
		airSpeed = 0;
	}

	private void jump(){
		if(inAir){
			return;
		}
		inAir = true;
		airSpeed = jumpSpeed;
	}

	private void updateXPos(float xSpeed){
		
			if(CanMoveHere(hitbox.x+xSpeed,hitbox.y,hitbox.width,hitbox.height,lvlData)){
			hitbox.x += xSpeed;
			}else{
				hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
			}

	}


	private void setAnimation() {
		int startAni = playerAction;
		if(moving) {
			playerAction = RUNNING;
		}else {
			playerAction = IDLE;
		}
		
		if(attacking) {
			playerAction = ATTACK_1;
		}
		
		if(startAni != playerAction) {
			resetAniTick();
		}
		
	}
	
	

	private void resetAniTick() {
		aniTick = 0;
		aniIndex = 0;
		
	}

	private void updateAnimationTick() {
		aniTick++;
		if(aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= getSpriteAmount(playerAction)) {
				aniIndex = 0;
				attacking = false;
			}
		}
		
	}
	
	
	private void loadAnimation() {
		
		
		
		BufferedImage img = LoadSave.getSprite(LoadSave.PLAYER_SPRITE);
		
		animations =  new BufferedImage[9][6];
		
		for(int i = 0; i < animations.length; i++) {
		for (int j = 0; j < animations[i].length; j++) {
			animations[i][j] = img.getSubimage(j*64, i*40, 64, 40);
			}
		}	
	}

	public void loadlvlData(int[][] lvlData){
		this.lvlData = lvlData;
	}
	
	public void setAtack(boolean a) {
		this.attacking = a;
	}
	
	public void resetDirBooleans() {
		
		left = false;
		right = false;
		up = false;
		down = false;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}
	
	public void setJump(boolean jump){
		this.jump = jump;
	}
	
}