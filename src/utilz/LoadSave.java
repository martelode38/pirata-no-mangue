package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import static utilz.Constants.EnemyConstants.CRABBY;
import javax.imageio.ImageIO;
import java.net.URL;


import entities.Crabby;
import main.Game;

public class LoadSave {
    public static final String PLAYER_SPRITE = "player_sprites.png";
	public static final String LEVEL_SPRITE = "Terrain.png";

	public static final String CRABBY_SPRITE = "crabby_sprite.png";
	public static final String STATUS_BAR = "health_power_bar.png";
	public static final String COMPLETED = "next.png";




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

	public static BufferedImage[] getAlllevels(){
		URL url = LoadSave.class.getResource("/lvls");
		File file = null;

		try{
			file = new File(url.toURI());
		}catch(URISyntaxException e){
			e.printStackTrace();
		}
		File[] files = file.listFiles();
		File[] fileSorted = new File[files.length];

		for(int i = 0; i < fileSorted.length; i++){
			for(int j = 0; j < files.length; j++){
				if(files[j].getName().equals((i+1)+".png"))
					fileSorted[i] = files[j];
			}

		}
		BufferedImage[] imgs = new BufferedImage[fileSorted.length];

		for(int i = 0; i < imgs.length; i++){
			try {
				imgs[i] = ImageIO.read(fileSorted[i]);
			} catch (IOException e) {
	
				e.printStackTrace();
			}
		}
		return imgs;

	}


  
}

