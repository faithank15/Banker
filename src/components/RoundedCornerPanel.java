/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import javax.swing.JPanel;

/**
 *
 * @author faithan15
 */
public class RoundedCornerPanel extends JPanel {

    private int rTL, rTR, rBR, rBL;

    public RoundedCornerPanel(){
        this.rTL = 0;
        this.rTR = 0;
        this.rBR = 0;
        this.rBL = 0;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        
        GradientPaint gp = new GradientPaint(
            0, 0, new Color(255, 255, 255),
            w, h, new Color(245, 248, 252) // gris bleuté léger
        );
        
        g2.setPaint(gp);

        Path2D path = new Path2D.Double();
        path.moveTo(rTL, 0);

        path.lineTo(w - rTR, 0);
        path.quadTo(w, 0, w, rTR);

        path.lineTo(w, h - w);
        path.quadTo(w, h, 0, h);

        path.lineTo(rBL, h);
        path.quadTo(0, h, 0, h - rBL);

        path.lineTo(0, rTL);
        path.quadTo(0, 0, rTL, 0);
        path.closePath();

        
        g2.fill(path);

        g2.dispose();
    }
}

