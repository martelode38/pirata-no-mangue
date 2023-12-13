package entities;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


import game_states.Play;
import levels.Level;
import utilz.LoadSave;
import static utilz.Constants.EnemyConstants.*;


public class EnemyManage {

	private Play playing;
	private BufferedImage[][] crabbyArr;
	private ArrayList<Crabby> crabbies = new ArrayList<>();

	public EnemyManage(Play playing) {
		this.playing = playing;
		loadEnemyImgs();
		
	}

	public void loadEnemies(Level level) {
		crabbies = level.getCrabs();
		
	}

	public void update(int [][] lvlData, Player p) {
		boolean isAnyActive = false;
		for (Crabby c : crabbies)
		if(c.isActive()){
			c.update(lvlData, p);
			isAnyActive = true;
		}
		if(!isAnyActive){
			playing.setLevelCompleted(true);
		}
	}

	public void draw(Graphics g, int xLvlOffset) {
		drawCrabs(g, xLvlOffset);
	}

	private void drawCrabs(Graphics g, int xLvlOffset) {
		for (Crabby c : crabbies)
		if (c.isActive())
		{
			g.drawImage(crabbyArr[c.getEnemyState()][c.getAniIndex()], (int) c.getHitbox().x - xLvlOffset, (int) c.getHitbox().y, CRABBY_WIDTH, CRABBY_HEIGHT, null);
		}
	}

	public void checkEnemyHit(Rectangle2D.Float attackBox){
		for(Crabby c : crabbies)
			if (c.isActive()){
			if(attackBox.intersects(c.getHitbox())){
				c.hurt(10);
				return; 
			}
		}	
	}

	private void loadEnemyImgs() {
		crabbyArr = new BufferedImage[5][9];
		BufferedImage temp = LoadSave.getSprite(LoadSave.CRABBY_SPRITE);
		for (int j = 0; j < crabbyArr.length; j++)
			for (int i = 0; i < crabbyArr[j].length; i++)
				crabbyArr[j][i] = temp.getSubimage(i * CRABBY_WIDTH_DEFAULT, j * CRABBY_HEIGHT_DEFAULT, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT);
	}

	public void resetAllEnemies() {
		for(Crabby c : crabbies){
			c.resetEnemy();
		}
	}
}