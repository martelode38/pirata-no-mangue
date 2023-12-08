package main;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;


public class GamePanel extends JPanel{
	
	private MouseInputs mI;
	private Game game;

	public GamePanel(Game game) {
		mI = new MouseInputs(this);
		
		this.game = game;
		setFocusable(true);
		addKeyListener(new KeyboardInputs(this));
		setPanelSize();
		addMouseListener(mI);
		addMouseMotionListener(mI);
	}
	


	private void setPanelSize() {
		Dimension size = new Dimension(1280,800);
		setPreferredSize(size);

		
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.render(g);
	}

	public Game getGame() {
		return game;
	}
}
