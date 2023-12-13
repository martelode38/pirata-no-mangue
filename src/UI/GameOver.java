package UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import game_states.GameState;
import game_states.Play;
import main.Game;

public class GameOver {
    
    private Play play;
    public GameOver(Play p){
        this.play = p;
    }

    public void draw(Graphics g){
        g.setColor(new Color(0,0,0,200));
        g.fillRect(0,0,Game.GAME_WIDTH, Game.GAME_HEIGHT);
       
        g.setColor(Color.white);
        g.drawString("GameOver",Game.GAME_WIDTH / 2, 150);
        g.drawString("press esc to enter menu", Game.GAME_WIDTH/2, 300);
    }

    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            play.resetAll();
            GameState.state = GameState.MENU;
        }
    }
}
