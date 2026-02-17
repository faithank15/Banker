/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Dashboard;

import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;
import components.SidebarItem;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.util.List;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class sideBar extends javax.swing.JPanel {

    private BoxLayout layout;
    
    public sideBar() {
        initComponents();
        init();
    }
    
    public static Icon resizeIcon(String path, int w, int h){
        ImageIcon icon = new ImageIcon(SidebarItem.class.getResource(path));
        
        Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        
        return new ImageIcon(img);
    }
    
    private void init(){
        setBackground(new Color(46, 92, 127));
        layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
        setBorder(new EmptyBorder(10, 0, 10, 0));
        
        Icon boardWhite = resizeIcon("/ressources/icons/board-white.png", 30, 30);
        Icon boardBlack = resizeIcon("/ressources/icons/board-black.png", 30, 30);
        SidebarItem home = new SidebarItem("Dashboard",boardWhite,boardBlack);
        
        Icon clientWhite = resizeIcon("/ressources/icons/client-white.png", 30, 30);
        Icon clientBlack = resizeIcon("/ressources/icons/client-black.png", 30, 30);
        SidebarItem users = new SidebarItem("Gestion clients",clientWhite,clientBlack);
        
        
        SidebarItem settings = new SidebarItem("Compte bancaires");
        
        JLabel ban1 = new JLabel("PRINCIPAL");
        ban1.setFont(new Font("sansserif", Font.BOLD, 12));
        ban1.setForeground(Color.LIGHT_GRAY);

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        wrapper.setOpaque(false);
        wrapper.add(ban1);
        wrapper.setBorder(new EmptyBorder(0,0,0,0));

        add(Box.createVerticalStrut(20));
        //add(wrapper);
        add(Box.createVerticalStrut(10));
        add(home);
        add(Box.createVerticalStrut(5));
        add(users);
        add(Box.createVerticalStrut(5));
        add(settings);
        add(Box.createVerticalGlue());
        
        List<SidebarItem> items = List.of(home, users, settings);

        for (SidebarItem item : items) {
            item.addPropertyChangeListener("clicked", evt -> {
                items.forEach(i -> i.setActive(false));
                item.setActive(true);

                //cardLayout.show(contentPanel, item.getText());
            });
        }


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
