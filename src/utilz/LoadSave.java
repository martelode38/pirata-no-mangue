package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.Game;

public class LoadSave {
    public static final String PLAYER_SPRITE = "player_sprites.png";
	public static final String LEVEL_SPRITE = "Terrain.png";
	public static final String LEVEL_ONE_DATA= "level_one_data.png";
	public static final String LEVEL_TESTE = "DeusExiste1.png";



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

	public static int[][] GetLevelData(){
		int[][] lvlData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
		BufferedImage img = getSprite(LEVEL_TESTE);

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

