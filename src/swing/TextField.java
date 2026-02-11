package swing;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TextField extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTextField field;
    private String hint = "";

    public TextField() {
        setOpaque(false);
        setLayout(new BorderLayout());

        field = new JTextField();
        field.setBorder(new EmptyBorder(10, 10, 10, 10));
        field.setOpaque(false);
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setSelectionColor(new Color(246, 207, 104));

        add(field, BorderLayout.CENTER);
    }

    /* ===== JavaBean properties ===== */

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
        repaint();
    }

    public String getText() {
        return field.getText();
    }

    public void setText(String text) {
        field.setText(text);
    }

    /* ===== Custom painting ===== */

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth() - 1;
        int h = getHeight() - 1;

        g2.setColor(Color.WHITE);
        g2.draw(new RoundRectangle2D.Double(0, 0, w, h, h, h));

        if (!hint.isEmpty() && field.getText().isEmpty() && !field.isFocusOwner()) {
            drawHint(g2);
        }

        g2.dispose();
    }

    private void drawHint(Graphics2D g2) {
        FontMetrics fm = g2.getFontMetrics();
        g2.setColor(new Color(255, 255, 255, 120));

        int x = (getWidth() - fm.stringWidth(hint)) / 2;
        int y = (getHeight() + fm.getAscent()) / 2 - 2;

        g2.drawString(hint, x, y);
    }
}
