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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import static javax.swing.JOptionPane.*;
import static jeuxihm.Menu1.con;


public class Jeux {
    JFrame f = new JFrame("Rolling Sans Sky");
    JPanel globalP = (JPanel)f.getContentPane();
    
    int timeTimer = 0; 
    JLabel timer = new JLabel(Integer.toString(timeTimer));
    JFrame frameM;
    Random r = new Random();
    
    ArrayList<Barre> listBarre = new ArrayList<>();    
    
    Player p = new Player(globalP,f);
    private Timer newBarreTimer;
    private Timer timerTimer;
    private Timer updateBarreTimer;
    
    public Jeux (JFrame frameM){
        f.setSize(310, 500);
        globalP.setLayout(null);
        this.frameM = frameM;
        globalP.setBackground(Color.red);
        
        globalP.add(timer);
        listBarre.add(new Barre(f,globalP,r,50));//ajout de la premiere barre
        
        
        timer.setBounds(0, 0, 60, 30);

        f.setVisible(true);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        timerTimer = new Timer(1000,timerAdd);
        timerTimer.start();
        newBarreTimer = new Timer(1800,newBarre);
        newBarreTimer.start();
        updateBarreTimer = new Timer(10,updateBarre);
        updateBarreTimer.start();
        
        
    }
    
    
    ActionListener timerAdd = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            timeTimer ++;
            timer.setText(Integer.toString(timeTimer));
            
        }
    };
    ActionListener newBarre = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            newBarreTimer.setDelay(1000+r.nextInt(1000));
            listBarre.add(new Barre(f,globalP,r,50));
        }
    };
    ActionListener updateBarre = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            for(int i=0;i<listBarre.size();i++){
                Barre b = listBarre.get(i);
                if(b.posY > 460){
                    b.remove();
                    listBarre.remove(i);
                }else{
                    b.updateBarre();
                }
            }
            if(listBarre.get(0).touch(p)){
                newBarreTimer.stop();
                timerTimer.stop();
                updateBarreTimer.stop();
                p.death();
                JOptionPane d = new JOptionPane();
                String textes[] = {"Try Again","Partage","Menu"};
                int b = d.showOptionDialog(globalP, "GameOver","Perdu",0,PLAIN_MESSAGE,new ImageIcon(".\\src\\jeuxihm\\dead1.gif"),textes,null);
                //OptionPane du gameover
                switch(b){
                    case 0://nouvelle partie
                        new Jeux(frameM);
                        f.dispose();
                        break;
                    case 1: //partage du score du joueur
                        JOptionPane o = new JOptionPane();
                        String s = (String)JOptionPane.showInputDialog(globalP,"Pseudo:","Partage",JOptionPane.QUESTION_MESSAGE,null,null,"trois");
                        try {
                            Class.forName("oracle.jdbc.OracleDriver");
                            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@iutdoua-oracle.univ-lyon1.fr:1521:orcl","p1803502","366882");
                            Statement stmt = con.createStatement(
                                           ResultSet.TYPE_SCROLL_INSENSITIVE,
                                           ResultSet.CONCUR_READ_ONLY);   
                            
                            stmt.executeQuery("insert into Jeu (pseudo,score) values('"+s+"',"+timeTimer+")");
                            
                            } catch (Exception ex) {
                                System.err.println("Erreur :"+ex.getMessage());          
                            }
                        f.dispose();
                        frameM.setVisible(true);
                        break;
                    default://retour au menu
                        frameM.setVisible(true);
                        f.dispose();
                        
                }
                
                
            }
                
            
            
        }
    };
    
    
    
    
}
