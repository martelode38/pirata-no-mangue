package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import static utilz.Constants.EnemyConstants.CRABBY;
import javax.imageio.ImageIO;

import entities.Crabby;
import main.Game;

public class LoadSave {
    public static final String PLAYER_SPRITE = "player_sprites.png";
	public static final String LEVEL_SPRITE = "Terrain.png";
	public static final String LEVEL_ONE_DATA= "level_one_data.png";
	public static final String LEVEL_TESTE = "teste4.png";
	public static final String CRABBY_SPRITE = "crabby_sprite.png";



    public static BufferedImage getSprite(String fileName){

        BufferedImage img = null;

        InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
		try {
		img = ImageIO.read(is);
		
	
		
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				is.close();	
				}catch(IOException e) {
					e.printStackTrace();
				}
		}
        return img;
    }

		public static ArrayList<Crabby> GetCrabs() {
		BufferedImage img = getSprite(LEVEL_TESTE);
		ArrayList<Crabby> list = new ArrayList<>();
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if (value == CRABBY)
					list.add(new Crabby(i * Game.TILES_SIZE, j * Game.TILES_SIZE));

			}
		return list;

	}
	

	public static int[][] GetLevelData(){
		BufferedImage img = getSprite(LEVEL_TESTE);
		int[][] lvlData = new int[img.getHeight()][img.getWidth()];


		for(int i = 0; i < img.getHeight(); i++){
			for(int j = 0; j < img.getWidth(); j++){
				Color color = new Color(img.getRGB(j, i));
				int value = color.getRed();
				if(value >= 48){
					value = 0;
				}
				lvlData[i][j] = value;
			}
			
		}
		 return lvlData; 
	}
         
}

