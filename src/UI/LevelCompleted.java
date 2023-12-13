package UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import game_states.GameState;
import game_states.Play;
import main.Game;
import utilz.LoadSave;

public class LevelCompleted implements KeyListener {
    private Play playing;
    private BufferedImage img;

    private int BgX, BgY, BgW, BgH;
    
    public LevelCompleted(Play playing){
        this.playing = playing;
        initImg();
    }

    private void initImg() {
        img = LoadSave.getSprite(LoadSave.COMPLETED);
        BgW = (int) (img.getWidth() * Game.SCALE);
        BgH = (int) (img.getHeight() * Game.SCALE);
        BgX = Game.GAME_WIDTH / 2 - BgW / 2;
        BgY = (int) (75 * Game.SCALE);
    }

    public void draw(Graphics g){
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
        g.drawImage(img, BgX, BgY, BgW, BgH, null);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            playing.loadNextLvl();
            GameState.state = GameState.PLAY;
        }
    }

    // Métodos obrigatórios da interface KeyListener
    @Override
    public void keyTyped(KeyEvent e) {
        // Implementação opcional
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Implementação opcional
    }

    public void update() {
        // Implementação opcional
    }
}
