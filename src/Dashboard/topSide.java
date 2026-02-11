/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Dashboard;

import java.awt.Color;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author faithan15
 */
public class topSide extends javax.swing.JPanel {

    private topBar top;
    private sideBar side;
    private MigLayout layout;
    
    
    public topSide() {
        init();
    }

    
    private void init(){
        setBackground(new Color(255, 255, 255));
        layout = new MigLayout("insets 0,fill","[30%][grow]","[18%][grow]");
        setLayout(layout);
        
        top = new topBar();
        side = new sideBar();
        
        add(top, "cell 0 0 2 1,growx, align center");
        add(side,"cell 0 1,growx,growy, align center");
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
