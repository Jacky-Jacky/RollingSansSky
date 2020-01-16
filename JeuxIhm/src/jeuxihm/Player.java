/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeuxihm;

/**
 *
 * @author maxime
 */
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class Player implements KeyListener{
    
    private JPanel globalP;
    private JFrame f;
    
    public JPanel player;
    
    public int posX = 140;
    public int posY = 430;
    
    public Player(JPanel globalP, JFrame f){
        this.globalP = globalP;
        this.f = f;
        f.addKeyListener(this);
        
        player = new JPanel();
        player.setBackground(Color.blue);
        
        player.setBounds(posX,posY,20,20);
        globalP.add(player);
        
    }
    
    public void death(){
        f.removeKeyListener(this);
    }
    

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'q'){
            if (posX>20){
                posX = posX-60;
                player.setBounds(posX, posY, 20,20);
            }
        }else if(e.getKeyChar() == 'd'){
            if (posX<260){
                posX = posX+60;
                player.setBounds(posX, posY, 20,20);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
}
