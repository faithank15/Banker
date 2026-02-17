/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JTextField;

public class RoundedTextField extends JTextField {

    private int radius = 50;

    public RoundedTextField() {
        setOpaque(false);
    }

    public void setRadius(int radius) {
        this.radius = radius;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Double(
                0, 0, getWidth(), getHeight(), radius, radius));

        super.paintComponent(g);
        g2.dispose();
    }
}

