/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Dashboard;

import static Dashboard.sideBar.resizeIcon;
import net.miginfocom.swing.MigLayout;
import components.PanelCustom;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import javax.swing.border.EmptyBorder;

public class topBar extends javax.swing.JPanel {

    private MigLayout layout;
    private PanelCustom blCon;
    private PanelCustom roundCon;
    private JPanel logo;
    private JLabel title;
    
    
    public topBar() {
        initComponents();
        init();
    }
    
    /*@Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            GradientPaint gradient = new GradientPaint(
                0, 0, new Color(46, 92, 127),       // coin supérieur gauche
                getWidth(), getHeight(), Color.WHITE // coin inférieur droit
            );

            g2.setPaint(gradient);
            g2.fillRect(0, 0, getWidth(), getHeight());
    }*/

    private void init(){
        
        layout = new MigLayout("fill","[pref][grow][pref]","[fill]");
        setLayout(layout);
        
        title = new JLabel(" GK");

        try {
            Font font = Font.createFont(
                Font.TRUETYPE_FONT,
                getClass().getResourceAsStream("/ressources/fonts/ChakraPetch-Bold.ttf")
            ).deriveFont(50f);
            title.setFont(font);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        title.setForeground(new Color(255, 255, 255));
        
        logo = new JPanel();
        logo.setOpaque(false);
        logo.setLayout(new BoxLayout(logo, BoxLayout.Y_AXIS));
        logo.add(title);
        logo.setBorder(new EmptyBorder(0,0,0,0));
        
        roundCon = new PanelCustom(30);
        roundCon.setBackground(new Color(228, 239, 239));
        JLabel Con = new JLabel(" JK ");
        Con.setFont(new Font("sansserif", Font.BOLD, 22));
        Con.setForeground(new Color(46, 92, 127));
 
        FlowLayout FLayout = new FlowLayout(FlowLayout.CENTER);
        roundCon.setLayout(FLayout);
        roundCon.add(Con);
        
        JLabel Name = new JLabel("Bob L'Eponge");
        Name.setFont(new Font("Catarell", Font.PLAIN, 18));
        Name.setForeground(Color.black);    
        
        blCon = new PanelCustom(20);
        blCon.setBackground(Color.white);
        blCon.setBorder(new EmptyBorder(4,8,4,8));
        
        BoxLayout BLayout =  new BoxLayout(blCon, BoxLayout.X_AXIS);
        
        blCon.setLayout(BLayout);
        blCon.add(roundCon);
        blCon.add(Box.createHorizontalStrut(5));
        blCon.add(Name);
        
        PanelCustom btnOut = new PanelCustom(20);
        btnOut.setOpaque(true); // très important
        btnOut.setBackground(new Color(46, 92, 127));
        btnOut.setPreferredSize(new Dimension(45, 45)); // ou selon ton icône
        btnOut.setLayout(new BorderLayout()); // pour centrer le JLabel

        Icon logout = resizeIcon("/ressources/icons/logout.png", 40, 38);
        JLabel iconLabel = new JLabel(logout);
        iconLabel.setHorizontalAlignment(JLabel.CENTER);
        iconLabel.setVerticalAlignment(JLabel.CENTER);
        iconLabel.setBorder(new EmptyBorder(0,0,0,0));
        
        btnOut.setBorder(new EmptyBorder(1,3,1,3));
        btnOut.add(iconLabel, BorderLayout.CENTER);

        // Panel background et padding
        setBackground(new Color(46, 92, 127));
        setBorder(new EmptyBorder(0,0,0,0));
        
        add(logo, "cell 0 0, align left center");
        add(btnOut, "cell 1 0, align right center");
        //add(blCon, "align right");
        
        
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
