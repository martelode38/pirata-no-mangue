package game_states;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import entities.Player;
import levels.LevelManeger;
import main.Game;

public class Play extends State implements StateMethods{

    private Player player;
    private LevelManeger levelManeger;

    public Play(Game game){
        super(game);
        initClasses();
    }

    private void initClasses() {
		levelManeger = new LevelManeger(game);
		player = new Player(200,200, (int) (64 * Game.SCALE), (int) (40 * Game.SCALE));
		player.loadlvlData(levelManeger.getCurrentlvl().GetLevelData() );
	}


    @Override
    public void update() {
        levelManeger.update();
        player.update();

    }

    @Override
    public void draw(Graphics g) {
        levelManeger.draw(g);
        player.render(g);
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

	