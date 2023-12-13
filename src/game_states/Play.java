package game_states;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import UI.GameOver;
import entities.EnemyManage;
import entities.Player;
import levels.LevelManeger;
import main.Game;
import utilz.LoadSave;
import UI.LevelCompleted;

public class Play extends State implements StateMethods{

    private Player player;
    private LevelManeger levelManeger;
    private EnemyManage enemyManager;
    private GameOver gameOverLay;
    private LevelCompleted levelCompleted;
    private int xLvlOffset;
    private int leftBorder = (int) (0.2* Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
    private int maxLvlOffset;
    private boolean lvlCompleted;
    private boolean gameOver;

    public Play(Game game){
        super(game);
        initClasses();

        clcLvlOffset();
        loadStartLvl();

    }

    public void loadNextLvl(){
        resetAll();
        levelManeger.loadNextLvl();
    }

    private void loadStartLvl() {
        enemyManager.loadEnemies(levelManeger.getCurrentlvl());
    }

    private void clcLvlOffset() {
        maxLvlOffset = levelManeger.getCurrentlvl().getLvlOffset();
    }

    private void initClasses() {
		levelManeger = new LevelManeger(game);
        enemyManager = new EnemyManage(this);
		player = new Player(200,200, (int) (64 * Game.SCALE), 
        (int) (40 * Game.SCALE),this);
		player.loadlvlData(levelManeger.getCurrentlvl().getLevelData());
        gameOverLay = new GameOver(this);
        levelCompleted = new LevelCompleted(this);
	}


    @Override
    public void update() {

        if(lvlCompleted){
            levelCompleted.update();
        }else if(!gameOver){
            levelManeger.update();
            player.update();
            enemyManager.update(levelManeger.getCurrentlvl().getLevelData(),
            player);
            checkCloseToBorder();
        }
        

    }
    

    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - xLvlOffset;

        if(diff > rightBorder){
            xLvlOffset += diff - rightBorder;
        }else if(diff < leftBorder){
            xLvlOffset += diff - leftBorder;
        }

        if(xLvlOffset > maxLvlOffset){
            xLvlOffset = maxLvlOffset;

        }else if(xLvlOffset < 0){
            xLvlOffset = 0;
        }
    }

    @Override
    public void draw(Graphics g) {
        levelManeger.draw(g, xLvlOffset);
        player.render(g, xLvlOffset);
        enemyManager.draw(g, xLvlOffset);

        if(gameOver){
            gameOverLay.draw(g);
        }else if(lvlCompleted)

        levelCompleted.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(!gameOver){
        if(e.getButton() == MouseEvent.BUTTON1){
			player.setAtack(true);
		}}
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(gameOver){
        gameOverLay.keyPressed(e);
        }else{
        	switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                player.setJump(true);
                break;
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_BACK_SPACE:
                    GameState.state = GameState.MENU;

            }}
	}

    

    @Override
    public void keyReleased(KeyEvent e) {
         if(!gameOver){
               switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                player.setJump(false);
                break;
            case KeyEvent.VK_A:
                player.setLeft(false);
                break;
            case KeyEvent.VK_D:
                player.setRight(false);
                break;

        }}
    }

    public void setLvlOffset(int LvlOffset){
        this.maxLvlOffset = LvlOffset;
    }

    public EnemyManage getEnemyManager(){
        return enemyManager;
    }

    public void resetAll(){
        //resetar tudo
        gameOver = false;
        lvlCompleted = false;
        player.resetAll();
        enemyManager.resetAllEnemies();
    }
    public void setGameOver(boolean GO){
        this.gameOver = GO;
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox){
        enemyManager.checkEnemyHit(attackBox);
    }
    
    public void windowFocusLost() {
		player.resetDirBooleans();
	}
	
	public Player getPlayer() {
		return player;
	}

    public void setLevelCompleted(boolean l){
        this.lvlCompleted = l;
    }
}

	