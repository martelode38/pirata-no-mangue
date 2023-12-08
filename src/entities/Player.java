package entities;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import static utilz.Constants.Directions.*;
import static utilz.Constants.PlayarConstants.*;

public class Player extends Entity{
	
	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 15;
	private int playerAction = IDLE;
	private boolean moving = false, attacking = false;
	private boolean left, right, up, down;
	private float playerSpeed = 2.0f;
	
	public Player(float x, float y) {
		super(x,y);
		loadAnimation();
	}
	
	public void update() {
		updatePos();
		updateAnimationTick();
		setAnimation();

	}
	
	public void render(Graphics g) {
		
		g.drawImage(animations[playerAction][aniIndex], (int)x, (int)y, 128, 80, null);

	}
	


	private void updatePos() {
		
		moving = false;
		
		if(left && !right) {
			x-= playerSpeed;
			moving = true;
		}else if(right && !left) {
			x+= playerSpeed;
			moving = true;
			
		}
		
		if(up && !down) {
			y-= playerSpeed;
			moving = true;
		}else if(down && !up) {
			y+= playerSpeed;
			moving = true;
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
		
		InputStream is = getClass().getResourceAsStream("/player_sprites.png");
		try {
		BufferedImage img = ImageIO.read(is);
		
		animations =  new BufferedImage[9][6];
		
		for(int i = 0; i < animations.length; i++) {
		for (int j = 0; j < animations[i].length; j++) {
			animations[i][j] = img.getSubimage(j*64, i*40, 64, 40);
			}
		}
		
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				is.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
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
	
	
}
