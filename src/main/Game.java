package main;

import java.awt.Graphics;


import game_states.GameState;
import game_states.Menu;
import game_states.Play;


public class Game implements Runnable{
	
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;

	private Play playing;
	private Menu menu;


	public final static int TILES_DEFAULT_SIZE = 32;
	public final static float SCALE = 1.5f;
	public final static int TILES_IN_WIDTH = 26;
	public final static int TILES_IN_HEIGHT = 14;
	public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
	
	public Game() {
		initClasses();
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.setFocusable(true);
		gamePanel.requestFocus();

		startGameLoop();
		
	}
	
	private void initClasses() {
		menu = new Menu(this);
		playing = new Play(this);
	}

	private void startGameLoop() {
		
		gameThread = new Thread(this);
		gameThread.start();
		
		
	}
	
	public void update() {


		switch (GameState.state) {
			case MENU:
				menu.update();
				break;
			
			case PLAY:
				playing.update();
				
				break;	
			
			default:
				break;
		}
		
	}
	
	public void render(Graphics g) {
		
		
		switch (GameState.state) {
			case MENU:
				menu.draw(g);
				break;
			
			case PLAY:
				playing.draw(g);
				
				break;	
			
			default:
				break;
		}


	}


	@Override
	public void run() {

		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePErUpdate = 1000000000.0 / UPS_SET;

		
		long previousTime = System.nanoTime();
		
		int updates = 0;
		int frames = 0;
		long lastCheck = System.currentTimeMillis();
		
		double deltaU = 0;
		double deltaF = 0;
		
		
		
		while(true) {
	
			long currentTime = System.nanoTime();
			
			deltaU += (currentTime - previousTime) / timePErUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;
			
			if(deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}
			
			if(deltaF >= 1) {
				gamePanel.repaint();
				frames++;
				deltaF--;
			}
			
 			
			if(System.currentTimeMillis() - lastCheck >= 1000) {
				
				lastCheck = System.currentTimeMillis();	
				System.out.println("FPS: "  + frames + " UPS: " + updates);
				frames = 0;
				updates = 0;
			}
			
		}
	}
	
	public void windowFocusLost() {
		if(GameState.state == GameState.PLAY){
			playing.getPlayer().resetDirBooleans();
		}

	}
	
	public Menu getMenu(){
		return menu;
	}
	
	public Play getPlay(){
		return playing;
	}
	

}//
