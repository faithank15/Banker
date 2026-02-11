/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

public class DiagonalRoundedPanel extends JPanel {

    private int radius = 25;

    public DiagonalRoundedPanel() {
        setOpaque(false);
    }

    public DiagonalRoundedPanel(int radius) {
        this.radius = radius;
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

        int w = getWidth();
        int h = getHeight();

        Area area = new Area();

        // Rectangle principal
        area.add(new Area(new Rectangle2D.Double(0, 0, w, h)));

        // Coin supérieur gauche arrondi
        area.subtract(new Area(
            new Rectangle2D.Double(0, 0, radius, radius)
        ));
        area.add(new Area(
            new RoundRectangle2D.Double(
                0, 0, radius * 2, radius * 2, radius * 2, radius * 2
            )
        ));

        // Coin inférieur droit arrondi
        area.subtract(new Area(
            new Rectangle2D.Double(w - radius, h - radius, radius, radius)
        ));
        area.add(new Area(
            new RoundRectangle2D.Double(
                w - radius * 2,
                h - radius * 2,
                radius * 2,
                radius * 2,
                radius * 2,
                radius * 2
            )
        ));

        g2.setColor(getBackground());
        g2.fill(area);

        g2.dispose();
        super.paintComponent(g);
    }
}

