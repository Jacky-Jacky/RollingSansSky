/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeuxihm;

import java.awt.*;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author maxime
 */
public class Barre {
    
    private JPanel globalP;
    private JFrame f;
    public JPanel[] tabBarre = new JPanel[5];
    public int noRectangle;
    public int posY;
    
    public int hitboxLeft;
    public int hitboxRight;
    public int hitboxTop;
    public int hitboxBot;
    
    
    public Barre(JFrame f,JPanel globalP,Random r,int posY){
        this.globalP = globalP;
        this.f = f;
        noRectangle = r.nextInt(5);
        this.posY = posY;
        
        //calcul des hitboxs de la barre
        hitboxLeft = noRectangle*60;
        hitboxRight = hitboxLeft+60;
        hitboxTop = posY; 
        hitboxBot = posY+10;
        
        for(int i = 0;i<5;i++){
            if(i!=noRectangle){
                tabBarre[i] = createRectangle(60*i,posY);
            }
        }
        
    }
    
    public JPanel createRectangle(int posx,int posy){
        JPanel rect = new JPanel();
        rect.setBackground(Color.black);
        rect.setBounds(posx,posy,60,10);
        globalP.add(rect);        
        return rect;
    }
    
    public void updateBarre(){
        posY = posY+1;
        hitboxTop = posY; 
        hitboxBot = posY+10;
        for(JPanel r : tabBarre){
            if(r!=null){
                int posX = (int)(r.getLocation()).getX();
                r.setBounds(posX, posY, 60,10);
            }
            
            
        }
        
    }
    
    public void remove(){
        for(JPanel p : tabBarre){
            if(p!=null){
                f.remove(p);
                f.repaint();
            }
            
        }
    }
    
    public boolean touch(Player p){
        if((p.posX<hitboxLeft || p.posX>hitboxRight) && (p.posY>hitboxTop && p.posY<hitboxBot)){
            return true;
        } 
        return false;
    }
    
}
