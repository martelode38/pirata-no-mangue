package game_states;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import entities.EnemyManage;
import entities.Player;
import levels.LevelManeger;
import main.Game;
import utilz.LoadSave;

public class Play extends State implements StateMethods{

    private Player player;
    private LevelManeger levelManeger;
    private EnemyManage enemyManager;
    private int xLvlOffset;
    private int leftBorder = (int) (0.2* Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
    private int lvlTilesWide = LoadSave.GetLevelData()[0].length;
    private int maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
    private int maxLvlOffset = maxTilesOffset * Game.TILES_SIZE;


    public Play(Game game){
        super(game);
        initClasses();
    }

    private void initClasses() {
		levelManeger = new LevelManeger(game);
        enemyManager = new EnemyManage(this);
		player = new Player(200,200, (int) (64 * Game.SCALE), (int) (40 * Game.SCALE));
		player.loadlvlData(levelManeger.getCurrentlvl().GetLevelData() );
	}


    @Override
    public void update() {
        levelManeger.update();
        player.update();
        enemyManager.update(levelManeger.getCurrentlvl().GetLevelData(), player);
        checkCloseToBorder();

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
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
			player.setAtack(true);
		}
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
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

		}
	}

    

    @Override
    public void keyReleased(KeyEvent e) {
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

        }
    }
    
    public void windowFocusLost() {
		player.resetDirBooleans();
	}
	
	public Player getPlayer() {
		return player;
	}
}

	