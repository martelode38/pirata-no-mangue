package levels;

import static main.Game.TILES_SIZE;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import game_states.GameState;
import main.Game;
import utilz.LoadSave;

public class LevelManeger {
    
    private Game game;
    private BufferedImage[] levelSprite;
    private  ArrayList<Level> levels;
    private int lvlIndex = 0;
   
    public LevelManeger(Game game){
        this.game = game;
        importFloorSprites();
        levels = new ArrayList<>();
        buildAllLevels();
    }

    private void buildAllLevels() {
        BufferedImage[] allLevels = LoadSave.getAlllevels();
        for(BufferedImage img : allLevels){
            levels.add(new Level(img));
        }
    }

    public void importFloorSprites(){
        BufferedImage img = LoadSave.getSprite(LoadSave.LEVEL_SPRITE);
        levelSprite = new BufferedImage[48];
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 12; j++){
                int index = i * 12 + j;
                levelSprite[index] = img.getSubimage(j*32, i*32, 32, 32);
            }
        }
    }

    public void loadNextLvl(){
        lvlIndex++;
        if(lvlIndex >= levels.size()){
            lvlIndex = 0;
            System.out.println("GAME COMPLETED!");
            GameState.state = GameState.MENU;
        }

        Level newLevel = levels.get(lvlIndex);
        game.getPlay().getEnemyManager().loadEnemies(newLevel);
        game.getPlay().getPlayer().loadlvlData(newLevel.getLevelData());
        game.getPlay().setLvlOffset(newLevel.getLvlOffset());
    }


    public void draw(Graphics g, int xLvlOffset){
        for(int i = 0; i < Game.TILES_IN_HEIGHT; i++){
            for(int j = 0; j < levels.get(lvlIndex).getLevelData()[0].length; j++){
                int index = levels.get(lvlIndex).getSpriteIndex(j,i);
                 g.drawImage(levelSprite[index], Game.TILES_SIZE*j - xLvlOffset, Game.TILES_SIZE*i, Game.TILES_SIZE, Game.TILES_SIZE, null);

            }
        }
    }
    
    public void update(){
        
    }

    public Level getCurrentlvl(){
        return levels.get(lvlIndex);
    }

    public int getAmoutLevels(){
        return levels.size();
    }
}
