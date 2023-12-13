package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.PlayarConstants.ATTACK_1;
import static utilz.HelpMet.*;

import java.awt.geom.Rectangle2D;

import static utilz.Constants.Directions.*;
import static utilz.Constants.*;


import main.Game;


public abstract class Enemy extends Entity {
	protected int aniIndex, enemyState, enemyType;
	protected int aniTick, aniSpeed = 25;
	protected boolean firstUpdate = true;
	protected boolean inAir = false; 
	protected float fallSpeed;
	protected float gravity = 0.4f * Game.SCALE;
	protected float walkSpeed = 0.35f * Game.SCALE;
	protected int walkDir = LEFT;
	protected int tileY;
	protected float attackDistance = Game.TILES_SIZE;
	protected int  maxHealth;
	protected int currentHealth;
	protected boolean active = true;
	protected boolean attackChecked;
	

	public Enemy(float x, float y, int width, int height, int enemyType) {
		super(x, y, width, height);
		this.enemyType = enemyType;
		initHitbox(x, y, width, height);
		maxHealth = GetMaxHealth(enemyType);
		currentHealth = maxHealth;


	}

	protected void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(enemyType, enemyState)) {
				aniIndex = 0;

				switch (enemyState) {
					case ATTACK:
					case HIT:
						enemyState = IDLE;
						break;
					case DEAD:
						active = false;
						break;
				}
				
			}
		}
	}
	protected void firstUpdateCheck(int [][] lvlData){
			if(!IsEntityOnFLoor(hitbox, lvlData)){
				inAir = true;
			}
			firstUpdate = false;
	}

	protected void updateInAir(int [][] lvlData){
		if(CanMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, lvlData)){
				hitbox.y += fallSpeed;
				fallSpeed += gravity;
			}else{
				inAir = false;
				hitbox.y = GetEntityPosY(hitbox, fallSpeed);
			}

	}

	protected void move(int [][] lvlData){
			float xSpeed = 0;

						if(walkDir == LEFT){
							xSpeed = -walkSpeed;
						}else{
							xSpeed = walkSpeed;
						}
						
						if(CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)){
							if(IsFloor(hitbox, xSpeed, lvlData)){
								hitbox.x += xSpeed;
								return;
							}
						}

						changeWalkDir();
	}

	protected void turnTowardsPlayer(Player player) {
		if (player.hitbox.x > hitbox.x)
			walkDir = RIGHT;
		else
			walkDir = LEFT;
	}

	protected void newState(int enemyState){
		this.enemyState = enemyState;
		aniTick = 0;
		aniIndex = 0;
	}

	public void hurt(int a){
		currentHealth -= a;
		if(currentHealth <= 0){
			newState(DEAD);
		}else{
			newState(HIT);
		}
	}
	
	protected boolean canSeePlayer(int[][] lvlData, Player player) {
		int playerTileY = (int) (player.getHitbox().y / Game.TILES_SIZE);
		if (playerTileY == tileY)
			if (isPlayerInRange(player)) {
				if (IsSightClear(lvlData, hitbox, player.hitbox, tileY))
					return true;
			}

		return false;
	}
	
	protected void checkEnemyHit(Rectangle2D.Float attackBox, Player player){
		if(attackBox.intersects(player.hitbox)){
			player.changeHealth(-GetEnemyDano(enemyType));
		}
		attackChecked =  true;
	}
	
	protected boolean isPlayerInRange(Player p) {
		int absValue = (int) Math.abs(p.hitbox.x - hitbox.x);
		return absValue <= attackDistance * 5;
	}

	protected boolean isPlayerCloseForAttack(Player p){
		int absValue = (int) Math.abs(p.hitbox.x - hitbox.x);
		return absValue <= attackDistance;
	}
	public void resetEnemy(){
		hitbox.x = x;
		hitbox.y = y;
		firstUpdate = true;
		currentHealth = maxHealth;
		newState(IDLE);
		active = true;
		fallSpeed = 0;
	}

	protected void changeWalkDir(){
		if(walkDir == LEFT){
				walkDir = RIGHT;
		}else{
				walkDir = LEFT;
		}
	}

	public int getAniIndex() {
		return aniIndex;
	}

	public int getEnemyState() {
		return enemyState;
	}

	public boolean isActive(){
		return active;
	}

}