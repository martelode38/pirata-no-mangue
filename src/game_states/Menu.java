package game_states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import main.Game;
import main.GamePanel;

public class Menu extends State implements StateMethods{

    private GamePanel gamePanel;

    public Menu(Game game) {
        super(game);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void draw(Graphics g) {
      g.setColor(Color.black);
      g.drawString("ENTER TO PLAY!!!!!!!!", Game.GAME_WIDTH / 4, 200);
       g.drawString("CONTROLS(A == RIGHT, W == JUMP, D == LEFT, CLICK == ATTACK)", Game.GAME_WIDTH / 4, 250);
        g.drawString("Mate TODOS os caraguejos para passar de fase!", Game.GAME_WIDTH / 4, 300);
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
         
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            GameState.state = GameState.PLAY;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        
    }
  
}
