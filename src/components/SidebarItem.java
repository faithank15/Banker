/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.EmptyBorder;

public class SidebarItem extends JLabel {

    private boolean active = false;

    private final Color bgNormal = new Color(46, 92, 127);
    private final Color bgHover  = new Color(105, 147, 179);
    private final Color bgActive = new Color(255,255,255);
    private Icon Ficon;
    private Icon Sicon;

    public SidebarItem(String text, Icon icon1, Icon icon2) {
        super(text, icon1, LEFT);
        if (icon1 == null || icon2 == null) {
            System.err.println("Icon not loaded for: " + text);
        }
        Ficon = icon1;
        Sicon = icon2;
        init();
    }

    public SidebarItem(String text) {
        super(text, LEFT);
        init();
    }

    private void init() {
        setOpaque(true);
        setBackground(bgNormal);
        setForeground(Color.white);

        setFont(new Font("Segoe UI", Font.PLAIN, 16));
        setIconTextGap(12);
        setBorder(new EmptyBorder(10, 15, 10, 15));

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                if (!active) setBackground(bgHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!active) setBackground(bgNormal);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                firePropertyChange("clicked", false, true);
            }
        });
    }

    public void setActive(boolean value) {
        active = value;
        setBackground(active ? bgActive : bgNormal);
        setForeground(active ? Color.BLACK : Color.white);
        setIcon(active ? Sicon : Ficon);
        repaint();
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(Integer.MAX_VALUE, 48);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (active) {
            g.setColor(new Color(46, 92, 127));
            g.fillRect(0, 0, 4, getHeight());
        }
    }

}
