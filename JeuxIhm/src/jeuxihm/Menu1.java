package jeuxihm;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

public class Menu1 implements ActionListener {
    static Connection con;
    ResultSet rs;
    Statement stmt;
    
    
    JButton bJouer = new JButton(new ImageIcon("build/classes/Images/2.jpg"));
    JButton bQ = new JButton("Quitter");
    JButton bR = new JButton("Retour");
    JButton bClassement = new JButton("Actualiser");
    JFrame frame = new JFrame("Projet");
    JFrame frameS = new JFrame("Classement");
    JMenu menuM= new JMenu("Menu");
    JMenuItem classementM = new JMenuItem("Classement");
    JPanel p = (JPanel)frame.getContentPane();
    JPanel p2 = (JPanel)frameS.getContentPane();
    JMenuBar pHeader = new JMenuBar();
    JPanel pFooter = new JPanel();

    Font myFont = new Font("Arial", Font.BOLD, 20);
    Font myFont2 = new Font("Arial", Font.BOLD, 13);
    
    public Menu1() throws SQLException {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@iutdoua-oracle.univ-lyon1.fr:1521:orcl","p1803502","366882");
            stmt = con.createStatement(
                           ResultSet.TYPE_SCROLL_INSENSITIVE,
                           ResultSet.CONCUR_READ_ONLY);   
        } catch (Exception ex) {
            System.err.println("Erreur :"+ex.getMessage());          
        }
        // ResultSet joueur1 = stmt.executeQuery("Select pseudo from Jeu WHERE score > 3000 Order By score DESC");
        // ResultSet score1 = stmt.executeQuery("Select score from Jeu WHERE score > 3000 Order By score DESC"); 
        
        
        
        Object[][] donnees = new Object[20][2];
        String[] entetes = {"Prénom","Score"};
        ResultSet rs = stmt.executeQuery("select * from Jeu Order By Score DESC");
        int j = 0;
        while(rs.next()){
            donnees[j][0] = rs.getString(1);
            donnees[j][1] = rs.getInt(2);
            j++;
        }
        JTable tableau = new JTable(donnees, entetes);
        
        frame.setSize(400,610);
        frameS.setSize(400,610);
        p.setLayout(new BorderLayout());
        p2.setLayout(new BorderLayout());
        pFooter.setLayout(new BorderLayout());
        
        p.add("North",pHeader);
        p.add("Center",bJouer);
        p.add("South",pFooter);
        pFooter.add("North",bClassement);
        pFooter.add("South",bQ);
        pHeader.add(menuM);
        menuM.add(classementM);
        
        p2.add("North",tableau.getTableHeader());
        p2.add("South",bR);
        p2.add("Center",tableau);
        
        tableau.getTableHeader().setFont(myFont);
        tableau.getTableHeader().setForeground(Color.red); 
        tableau.setFont(myFont2);

        tableau.setRowHeight(26);
        
        bClassement.addActionListener(this);
	bQ.addActionListener(this);
        bR.addActionListener(this);
        bJouer.addActionListener(this);
        classementM.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                frameS.setVisible(true);
            try {
                con = DriverManager.getConnection("jdbc:oracle:thin:@iutdoua-oracle.univ-lyon1.fr:1521:orcl","p1803502","366882");
                stmt = con.createStatement(
                           ResultSet.TYPE_SCROLL_INSENSITIVE,
                           ResultSet.CONCUR_READ_ONLY);
                Object[][] donnees = new Object[20][2];
                String[] entetes = {"Prénom","Score"};
                ResultSet rs = stmt.executeQuery("select * from Jeu Order By Score DESC");
                int j = 0;
                while(rs.next()){
                    donnees[j][0] = rs.getString(1);
                    donnees[j][1] = rs.getInt(2);
                    j++;
                }
                con.close();
                JTable tableau = new JTable(donnees, entetes);
                
            } catch (SQLException ex) {
                System.err.println("Erreur :"+ex.getMessage());
            }
            }
            
        });//barremenu du classement
         
        DefaultTableCellRenderer custom = new DefaultTableCellRenderer(); 
        custom.setHorizontalAlignment(JLabel.CENTER); // centre les données de ton tableau
        for (int i=0 ; i < tableau.getColumnCount() ; i++) { // centre chaque cellule de ton tableau
            tableau.getColumnModel().getColumn(i).setCellRenderer(custom); 
        }
                
        frame.setVisible(true);
        frameS.setVisible(false);
        frame.setLocationRelativeTo(null);
        frameS.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent evt) {
        if (((JButton)evt.getSource())==bQ) {
            System.exit(0);
        }
        if (((JButton)evt.getSource())==bClassement) {
            frameS.setVisible(false);
            try {
                con = DriverManager.getConnection("jdbc:oracle:thin:@iutdoua-oracle.univ-lyon1.fr:1521:orcl","p1803502","366882");
                
                stmt = con.createStatement(
                           ResultSet.TYPE_SCROLL_INSENSITIVE,
                           ResultSet.CONCUR_READ_ONLY);
                Object[][] donnees = new Object[20][2];
                String[] entetes = {"Pseudo","Score"};
                ResultSet rs = stmt.executeQuery("select * from Jeu Order By Score DESC");
                int j = 0;
                while(rs.next()){
                    donnees[j][0] = rs.getString(1);
                    donnees[j][1] = rs.getInt(2);
                    j++;
                }// Acutalisation du tableau des classements à chaque fois que l'on clique
                con.close();
                JTable tableau = new JTable(donnees, entetes);
                
            } catch (SQLException ex) {
                System.err.println("Erreur :"+ex.getMessage());
            }
        
        }
        if (((JButton)evt.getSource())==bR) {
            frameS.setVisible(false);
        }
        if (((JButton)evt.getSource())==bJouer) {
            new Jeux(frame);
            frame.setVisible(false);
        }
        
    }

    
}
