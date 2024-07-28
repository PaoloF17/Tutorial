package tile;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import Main.gamePanel;


public class TileManager {
    
    gamePanel gp;
    Tile[] tile ;
    int mapTileNum[][];

    public TileManager(gamePanel gp){
        this.gp= gp;
        tile= new Tile[10];
        mapTileNum= new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/maps/world01.txt");

    }
    public void getTileImage(){

        try {
            tile[0]= new Tile();
            tile[0].image= ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
            
            tile[1]= new Tile();
            tile[1].image= ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
            
            tile[2]= new Tile();
            tile[2].image= ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
            
            tile[3]= new Tile();
            tile[3].image= ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));
            
            tile[4]= new Tile();
            tile[4].image= ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
            
            tile[5]= new Tile();
            tile[5].image= ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
            

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    public void loadMap(String filePath){

        
        try {
            InputStream is= getClass().getResourceAsStream(filePath);
            BufferedReader br= new BufferedReader( new InputStreamReader(is));
            
            int col=0;
            int row=0;
            while(col<gp.maxWorldCol&& row<gp.maxWorldRow){

                String line= br.readLine();

                while(col<gp.maxWorldCol){
                    //copies first line 
                    String numbers[] =line.split(" ");
                    //into int
                    int num=Integer.parseInt(numbers[col]);

                    mapTileNum[col][row]= num;
                    col++;
                }
                if(col==gp.maxWorldCol){
                    col=0;
                    row++;
                }
            }
            br.close();

        
        } catch (Exception e) {
            // TODO: handle exception
        }

    }
    public void draw(Graphics2D g2){
  
        int worldCol=0;
        int worldRow=0;
      
        while( worldCol<gp.maxWorldCol && worldRow <gp.maxWorldRow){
            int tileNum = mapTileNum[worldCol][worldRow];
            int worldX= worldCol*gp.getTileSize();
            int worldY= worldRow*gp.getTileSize();
            int screenX= worldX-gp.player.worldX+gp.player.screenX;
            int screenY= worldY-gp.player.worldY+gp.player.screenY;

            if( worldX + gp.getTileSize() >gp.player.worldX-gp.player.screenX && 
                worldX - gp.getTileSize()<gp.player.worldX+gp.player.screenX &&
                worldY + gp.getTileSize() >gp.player.worldY-gp.player.screenY&&
                worldY - gp.getTileSize() <gp.player.worldY+gp.player.screenY)
                {
                g2.drawImage(tile[tileNum].image, screenX, screenY,gp.getTileSize(),gp.getTileSize(),null);
           

                }
            worldCol++;
            
            if ( worldCol== gp.maxWorldCol){
                worldCol=0;
                
                worldRow++;
                
            }
    
    
        }
        
        
        
       
    }
}


