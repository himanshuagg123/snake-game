// package src;

import javax.swing.JFrame;

/**
 * snake
 */

public class snake {

    public static void main(String[] args) {
        int boardWidth =  600;
        int boardheight = boardWidth;
        // int tilesize = 25;

        JFrame frame = new JFrame("snake");
        frame.setVisible(true);
        frame.setSize(boardWidth,boardheight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        snakegame Snakegame =  new snakegame(boardWidth,boardheight);
        frame.add(Snakegame);
        frame.pack();
        Snakegame.requestFocus();
    }
}