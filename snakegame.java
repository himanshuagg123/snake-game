import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;




public class snakegame extends JPanel implements ActionListener, KeyListener{
    public class tile {

        int x;
        int y;

        tile(int x, int y){
            this.x = x;
            this.y = y; 
        }
    }
    int boardheight;
    int boardWidth;
    int tilesize = 25;
    // snake
    tile snakehead;
    ArrayList<tile> snakebody;
    // food
    tile food;

    Random random;
    // game logic

    Timer gameloop;
    int velocityx;
    int velocityy;
    boolean gameover =  false;


    snakegame(int boardWidth, int boardheight){
        

        this.boardWidth =  boardWidth;
        this.boardheight = boardheight;
        setPreferredSize(new Dimension(this.boardWidth,this.boardheight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);
        //    snake
        snakehead = new tile(5,5);
        snakebody  =  new ArrayList<>();
        food = new tile(10,10);

        random = new Random();
        placefood();

        gameloop = new Timer(100, this);
        gameloop.start();
        velocityx = 0;
        velocityy = 0;
        
    }

    public void  paint(Graphics g){
        super.paint(g);
        draw(g);
    }

    public void draw(Graphics g){

        // grid
        for(int i=0; i<boardWidth/tilesize; i++){
            // x1/x2/y1/y2;
            g.drawLine(i*tilesize, 0, i*tilesize,boardheight);
            g.drawLine(0, i*tilesize,boardheight,i*tilesize);
        }
        // snake
        g.setColor(Color.GREEN);
        g.fillRect(snakehead.x*tilesize, snakehead.y*tilesize, tilesize, tilesize);

        // food
        g.setColor(Color.BLUE);
        g.fillRect(food.x*tilesize, food.y*tilesize, tilesize, tilesize);

        // snake body
        for(int i =0; i<snakebody.size(); i++){
            tile snakepart = snakebody.get(i);
            g.fillRect(snakepart.x*tilesize, snakepart.y*tilesize, tilesize, tilesize);

        }
        // score
        g.setFont(new Font("Arial",Font.PLAIN,16));
        // g.setFont(Font.createFont(, fontFile)ont);
        if(gameover){
            g.setColor(Color.red);
            g.drawString("game over " + String.valueOf(snakebody.size()), tilesize -16, tilesize);
            
        }

        else{
            g.drawString("score " + String.valueOf(snakebody.size()), tilesize -16, tilesize); 
        }
    }

    public void placefood(){
        food.x = random.nextInt(boardWidth/tilesize);
        food.y = random.nextInt(boardWidth/tilesize);
    }

    public boolean collision(tile tile1, tile tile2){
        return tile1.x==tile2.x && tile1.y==tile2.y;
    }

    public void move(){
        //eat food
        if(collision(snakehead, food)){
            snakebody.add(new tile(food.x, food.y));
            placefood();
        }

        //snake body

        for(int i = snakebody.size()-1; i>=0; i--){
            tile snakepart = snakebody.get(i);
            if(i==0){
                snakepart.x= snakehead.x;
                snakepart.y=snakehead.y;
            }

            else{
                tile presnakepart = snakebody.get(i-1);
                snakepart.x = presnakepart.x;
                snakepart.y= presnakepart.y;
            }
        }

        snakehead.x += velocityx;
        snakehead.y += velocityy;

        // gameover
        for(int i = 0; i<snakebody.size(); i++){
            tile snakepart = snakebody.get(i);
            // collision  between snakepart and head;
            if(collision(snakepart, snakehead)){
                gameover=true;
            }
        }

        if(snakehead.x*tilesize <0 || snakehead.x*tilesize>boardWidth || 
           snakehead.y*tilesize<0 || snakehead.y*tilesize>boardheight){
            gameover= true;
           }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();

        repaint();
        if(gameover){
            gameloop.stop();
        }

    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP && velocityy != 1){
            velocityx = 0;
            velocityy = -1;
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN && velocityy != -1){
            velocityx = 0;
            velocityy = 1;
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT && velocityx != 1){
            velocityx = -1;
            velocityy = 0;
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT && velocityx !=-1){
            velocityx = 1;
            velocityy = 0;
        }

    }
// no need
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }
    
}
