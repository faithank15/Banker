/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package login;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;
import swing.EventLogin;
import javax.swing.*;
import java.awt.*;
import components.RoundedCornerPanel;
import java.io.IOException;
import javax.swing.border.EmptyBorder;

public class LoginAndRegister extends javax.swing.JPanel {

    
    private MigLayout layout;
    private Register register;
    private Login login;
    private Animator animator;
    private JLabel title;
    private JLabel subtitle;
    private boolean isLogin;
    private RoundedCornerPanel backTitle;
    
    
    public static Color mainColor = new Color(46, 92, 127);
    
    public void setAnimate(int animate) {
        layout.setComponentConstraints(register, "pos (50%)-290px-" + animate + " 0.5al n n");
        layout.setComponentConstraints(login, "pos (50%)-10px+" + animate + " 0.5al n n");
        if (animate == 30) {
            if (isLogin) {
                setComponentZOrder(login, 0);
            } else {
                setComponentZOrder(register, 0);
            }
        }
        revalidate();
    }

  
    public LoginAndRegister() {
        initComponents();
        init();
        initAnimator();
    }
    
    private void init(){
        setBackground(mainColor);
        layout = new MigLayout("insets 0,fill", "[grow]", "[40px][15px][grow]");
        setLayout(layout);

        title = new JLabel("GK");
        
        backTitle = new RoundedCornerPanel();
        backTitle.setBackground(Color.white);
        
        subtitle = new JLabel("Système de Gestion Bancaire");

        try {
            Font font = Font.createFont(
                Font.TRUETYPE_FONT,
                getClass().getResourceAsStream("/ressources/fonts/ChakraPetch-Bold.ttf")
            ).deriveFont(58f);
            title.setFont(font);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        
        subtitle.setFont(new Font("Catarell", Font.PLAIN, 24));
        title.setForeground(Color.white);
        
        title.setBackground(new Color(46,92, 127));
        
        title.setBorder(new EmptyBorder(5,5,20,30));
        subtitle.setForeground(new Color(160,160,160));
        
        backTitle.add(title);

        add(title, "cell 0 0, alignx left");
        add(subtitle, "cell 0 1, span, align center, gaptop 50,gapbottom 20");

        register = new Register();
        login = new Login();
        add(register,    "pos (50%)-290px 0.5al n n");
        add(login, "pos (50%)-10px 0.5al n n");
        register.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                if (SwingUtilities.isLeftMouseButton(me)) {
                    showLogin(false);
                }
            }
        });
        login.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                if (SwingUtilities.isLeftMouseButton(me)) {
                    showLogin(true);
                }
            }
        });
        
    }
    
    private void initAnimator() {
        animator = new Animator(1000, new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                if (isLogin) {
                    register.setAlpha(fraction);
                    login.setAlpha(1f - fraction);
                } else {
                    register.setAlpha(1f - fraction);
                    login.setAlpha(fraction);
                }
            }
        });
        animator.addTarget(new PropertySetter(this, "animate", 0, 30, 0));
        animator.setResolution(0);
    }
    
    public void showLogin(boolean show) {
        if (show != isLogin) {
            if (!animator.isRunning()) {
                isLogin = show;
                animator.start();
            }
        }
    }

    private void applyEvent(JComponent panel, boolean login) {
        for (Component com : panel.getComponents()) {
            com.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent me) {
                    showLogin(login);
                }
            });
        }
    }

    public void setEventLogin(EventLogin event) {
        login.setEventLogin(event);
    }
    
    private final Color bg = new Color(46, 92, 127);
    private final Color dot = new Color(255, 255, 255, 50);
    private final int spacing = 20;
    private final int radius = 2;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fond
        GradientPaint gp = new GradientPaint(
            0, 0, new Color(46, 92, 127),
            0, getHeight(), new Color(30, 60, 90)
        );
        g2.setPaint(gp);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Motif
        g2.setColor(dot);
        for (int y = 0; y < getHeight(); y += spacing) {
            for (int x = 0; x < getWidth(); x += spacing) {
                g2.fillOval(x, y, radius * 2, radius * 2);
            }
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setToolTipText("");

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
