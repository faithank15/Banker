package swing;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import login.LoginAndRegister;

public class Password extends JPasswordField {

    private String hint = "";

    private JPasswordField field;
    
    public Password() {
    init();
    }

    private void init() {
        setOpaque(false);
        setLayout(new BorderLayout());

        setPreferredSize(new Dimension(300, 45));
        setMinimumSize(new Dimension(300, 45));

        field = new JPasswordField();
        field.setBorder(new EmptyBorder(10, 10, 10, 10));
        field.setBackground(new Color(0, 0, 0, 0));
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setSelectionColor(LoginAndRegister.mainColor);
        field.setForeground(Color.WHITE);

        add(field, BorderLayout.CENTER);
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth() - 1;
        int height = getHeight() - 1;

        // contour arrondi
        g2.setColor(getForeground());
        g2.draw(new RoundRectangle2D.Double(0, 0, width, height, height, height));

        // hint
        if (!hint.isEmpty() && getPassword().length == 0) {
            drawHint(g2);
        }

        g2.dispose();
        super.paintComponent(g);
    }

    private void drawHint(Graphics2D g) {
        FontMetrics fm = g.getFontMetrics();
        int c0 = getBackground().getRGB();
        int c1 = getForeground().getRGB();
        int m = 0xfefefefe;
        int c2 = ((c0 & m) >>> 1) + ((c1 & m) >>> 1);

        g.setColor(new Color(c2, true));
        g.drawString(
            hint,
            (getWidth() - fm.stringWidth(hint)) / 2,
            getHeight() / 2 + fm.getAscent() / 2 - 2
        );
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(250, 45);
    }
}
