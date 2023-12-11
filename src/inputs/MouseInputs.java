package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import game_states.GameState;
import main.GamePanel;

public class MouseInputs implements MouseListener, MouseMotionListener {
	
	private GamePanel gamePanel;
	
	public MouseInputs(GamePanel gamePanel) {
		
		this.gamePanel = gamePanel;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
//		gamePanel.setRectPos(e.getX(), e.getY());
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		        switch ((GameState.state)) {
            case MENU:
                gamePanel.getGame().getMenu().mouseClicked(e);
                break;
            case PLAY:
                gamePanel.getGame().getPlay().mouseClicked(e);
                break;
            default:
                break;
				}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {

		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



}//
