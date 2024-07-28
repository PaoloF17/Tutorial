package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

public class gamePanel extends JPanel implements Runnable{

    //SCREEN SETTINGS (p= pixels)
    final int originalTileSize=16; //16x16 p tile
    final int scale = 3;

    final int tileSize= originalTileSize*scale;// 48*48 p
    public final int maxScreenCol= 16;
    public final int maxScreenRow= 12;
    public final int screenWidth= tileSize* maxScreenCol; //768 p
    public final int screenHeight= tileSize* maxScreenRow; //576 p

    // WORLD SETTINGS 
    public final int maxWorldCol=50;
    public final int maxWorldRow=50;
    public final int worldWidth= tileSize*maxWorldCol;
    public final int worldHeight=tileSize*maxScreenRow;

    int FPS= 60;

    TileManager tileM = new TileManager(this);

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public Player player = new Player(this,keyH);

    //set player's default position
    int playerX=100;
    int playerY=100;
    int playerSpeed= 4;

    

    public gamePanel(){

        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);//better rendering performancce-
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }


    public void startGameThread(){
        gameThread= new Thread(this);
        gameThread.start();//calls run method

    }

    @Override
    public void run() {
        //da capire bene il looppaggio
        double drawInterval= 1000000000/FPS;//.166 sec
        double nextDrawTime= System.nanoTime() + drawInterval;
        
        while(gameThread!=null){
            
            // 1)  UPDATE info
            update();
            
            // 2) DRAW on screen updated info 
            repaint();// calls paintComponent

            try {
                double remainingTime= nextDrawTime-System.nanoTime();
                remainingTime= remainingTime/1000000;

                if(remainingTime<0){
                    remainingTime=0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime +=drawInterval;

            } catch (InterruptedException e) {
                
                e.printStackTrace();
            }
        }
    
    }
    public void update(){

           player.update();
    }
    //repaint();
    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2 =(Graphics2D)g;

        tileM.draw(g2);//before [player.draw(g2);]!

        player.draw(g2);
        g2.dispose();//works better


    }

    public int getTileSize() {
        return tileSize;
    }
}
