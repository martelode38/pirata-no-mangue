package levels;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import static utilz.HelpMet.GetLevelData;
import static utilz.HelpMet.GetCrabs;
import entities.Crabby;
import main.Game;

public class Level {

    private BufferedImage img;
    private int[][] lvlData;
    private ArrayList<Crabby> crabs;
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffset;
    
    public Level(BufferedImage img){
        
        this.img = img;
        createLvlData();
        createEnemies();
        calcLvl();
    }

    private void calcLvl() {
        lvlTilesWide = img.getWidth();
        maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
        maxLvlOffset = Game.TILES_SIZE * maxTilesOffset;
    }

    private void createEnemies() {
        crabs = GetCrabs(img);
    }

    private void createLvlData() {
        lvlData = GetLevelData(img);
    }

    public int getSpriteIndex(int x, int y){
        return lvlData[y][x];
    }

    public int[][] getLevelData(){
        return lvlData;
    }
    public int getLvlOffset(){
        return maxLvlOffset;
    }
    public ArrayList<Crabby> getCrabs(){
        return crabs;
    }
    
}
