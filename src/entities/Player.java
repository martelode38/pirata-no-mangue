package entities;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import game_states.Play;
import main.Game;

import static utilz.HelpMet.*;

import utilz.LoadSave;

import static utilz.Constants.EnemyConstants.ATTACK;
import static utilz.Constants.PlayarConstants.*;

public class Player extends Entity{
	
	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 15;
	private int playerAction = IDLE;
	private boolean moving = false, attacking = false;
	private boolean left, right, up, down, jump;
	private float playerSpeed = 1.50f;
	private int[][] lvlData;
	private float xDrawOffset = 21 * Game.SCALE;
	private float yDrawOffset = 4 * Game.SCALE;
	private float airSpeed = 0f;
	private float gravity = 0.04f *Game.SCALE;
	private float jumpSpeed = -2.25f * Game.SCALE;
	private float fallSpeed = 0.5f * Game.SCALE;
	private boolean inAir = false;
	private BufferedImage statusBarImg;
	private int statusBarWidth = (int) (192 * Game.SCALE);
	private int statusBarHeight = (int) (58 * Game.SCALE);
	private int statusBarX = (int) (10 * Game.SCALE);
	private int statusBarY = (int) (10 * Game.SCALE);
	private int healthBarWidth = (int) (150 * Game.SCALE);
	private int healthBarHeight = (int) (4 * Game.SCALE);
	private int healthBarXStart = (int) (34 * Game.SCALE);
	private int healthBarYStart = (int) (14 * Game.SCALE);
	private int maxHealth = 10;
	private int currentHealth = maxHealth;
	private int healthWidth = healthBarWidth;
	private Rectangle2D.Float attackBox;
	private int flipX = 0;
	private int flipW = 1;
	private boolean attackChecked;
	private Play playing;
	


	public Player(float x, float y, int width, int heigh, Play playing) {
		super(x,y, width, height);
		this.playing = playing;
		loadAnimation();
		initHitbox(x, y, 20*Game.SCALE, 28*Game.SCALE);
		initAttackBox();
	}

	private void initAttackBox(){
		attackBox = new Rectangle2D.Float(x,y,(int)(20 * Game.SCALE), (int)(20*Game.SCALE));
	}
	
	public void update() {
		updateHealthBar();
		if(currentHealth <= 0){
			playing.setGameOver(true);
			return;
		}

		
		updateAtackBox();
		updatePos();
		if(attacking)
			checkAttack();
		updateAnimationTick();
		setAnimation();
		

	}

	private void checkAttack() {
		if(attackChecked || aniIndex != 1){
			return;
		}
		attackChecked = true;
		playing.checkEnemyHit(attackBox);
	}

	public void updateAtackBox(){
		if(right){
			attackBox.x = hitbox.x + hitbox.width + (int)(Game.SCALE *10);
		}else if(left){
			attackBox.x = hitbox.x - hitbox.width - (int)(Game.SCALE*10);
		}
		attackBox.y = hitbox.y + (Game.SCALE * 10);
	}
	
	private void updateHealthBar() {
		healthBarWidth = (int) (currentHealth / (float)maxHealth) * healthBarWidth;
	}

	public void drawUI(Graphics g){
		g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth,statusBarHeight, null);
		g.setColor(Color.red);
		g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthBarWidth,healthBarHeight);
	
	}	

	public void render(Graphics g, int xLvlOffset) {
		
		g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset) - xLvlOffset + flipX, (int) (hitbox.y - yDrawOffset), width * flipW, height, null);
	
		drawUI(g);
	}

	private void updatePos() {
		
		moving = false;
		
		if(jump){
			jump();
		}

		if(!inAir){
			if((!left && !right) || (right && left)){
				return;
			}
		}

		float xSpeed = 0;
		
		if(left){
			xSpeed -= playerSpeed;
			flipX = width;
			flipW = -1;
		}else if(right && !left){
			xSpeed += playerSpeed;	
			flipX = 0;
			flipW = 1;
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

	public void changeHealth(int value){
		currentHealth += value;	
		if(currentHealth<= 0){
			currentHealth = 0;
			//gameOver();
		}else if(currentHealth >= maxHealth){
			currentHealth = maxHealth;
		}
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

		if(inAir){
			if(airSpeed < 0){
				playerAction = JUMP;
			}else{
				playerAction = FALLING;
			}
		}
		
		if(attacking) {
			playerAction = ATTACK_1;
			if(startAni != ATTACK_1){
				aniIndex = 1;
				aniTick = 0;
				return;
			}
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
				attackChecked = false;
			}
		}
		
	}
	
	
	private void loadAnimation() {
		
		
		
		BufferedImage img = LoadSave.getSprite(LoadSave.PLAYER_SPRITE);
		
		animations =  new BufferedImage[7][8];
		
		for(int i = 0; i < animations.length; i++) {
		for (int j = 0; j < animations[i].length; j++) {
			animations[i][j] = img.getSubimage(j*64, i*40, 64, 40);
			}
		}
		
		statusBarImg = LoadSave.getSprite((LoadSave.STATUS_BAR));
	}

	public void loadlvlData(int[][] lvlData){
		this.lvlData = lvlData;
		if(!IsEntityOnFLoor(hitbox, lvlData)){
			inAir = true;
		}
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

	public void resetAll() {
		resetDirBooleans();
		inAir = false;
		attacking = false;
		moving = false;
		playerAction = IDLE;
		currentHealth = maxHealth;

		hitbox.x = x;
		hitbox.y = y;

		if(!IsEntityOnFLoor(hitbox, lvlData)){
			inAir = true;
		}
	}
	
}
