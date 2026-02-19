/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GestionPrets;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import DBManager.DBManager;

/**
 *
 * @author CJ
 */
public class IPret extends javax.swing.JFrame {
    private JPanel mainPanel;
    private JTable pretTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JLabel countBadge;
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(IPret.class.getName());

    /**
     * Creates new form IPret
     */
    public IPret() {
        
        initUI();
        setTitle("Gestion des Prets");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    private void initUI(){
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 242, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel headerPanel = createHeader();
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        JPanel tablePanel = createTablePanel();
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        
        add(mainPanel);
        
        loadPrets();
    }
    
    private JPanel createHeader(){
        JPanel headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setBackground(new Color(240, 242, 245));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 32, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(new Color(240, 242, 245));
        
        JLabel titleLabel = new JLabel("Gestion des Prets");
        titleLabel.setFont(new Font("Century Gothic", Font.BOLD, 32));
        titleLabel.setForeground(new Color(28, 30, 33));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Historique complet des prets des clients");
        subtitleLabel.setFont(new Font("Century Gothic", Font.PLAIN, 15));
        subtitleLabel.setForeground(new Color(101, 103, 107));
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 6)));
        titlePanel.add(subtitleLabel);
        
        headerPanel.add(titlePanel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(new Color(240, 242, 245));
        
        searchField = new JTextField(){
            @Override
            protected void paintComponent(Graphics g){
                if(!isOpaque () && getBorder() instanceof RoundedBorder){
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(getBackground());
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                    g2.dispose();
                }
                super.paintComponent(g);
            }
        };
        
        searchField.setPreferredSize(new Dimension(300, 36));
        searchField.setMinimumSize(new Dimension(300, 36));
        searchField.setMaximumSize(new Dimension(300, 36));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setOpaque(false);
        searchField.setBorder(new RoundedBorder(6));
        
        searchField.setText("Rechercher un pret...");
        searchField.setForeground(new Color(153, 153, 153));
        
        searchField.addFocusListener(new FocusAdapter(){
            @Override
            public void focusGained(FocusEvent e){
                if(searchField.getText().equals("Rechercher un pret...")){
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e){
                if(searchField.getText().isEmpty()){
                    searchField.setText("Rechercher un pret...");
                    searchField.setForeground(new Color(153, 153, 153));
                }
            }
        });
        
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                searchPrets();
            }
        });
        
        searchPanel.add(searchField, BorderLayout.EAST);
        
        headerPanel.add(searchPanel, gbc);
        
        return headerPanel;
    }
    
    class RoundedBorder implements Border {
        private int radius;
        
        RoundedBorder(int radius){
            this.radius = radius;
        }
        
        @Override
        public Insets getBorderInsets(Component c){
            return new Insets(8, 12, 8, 12);
        }
        
        @Override
        public boolean isBorderOpaque(){
            return false;
        }
        
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height){
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(228, 230, 235));
            g2.drawRoundRect(x, y, width  -1, height  -1, radius, radius);
        }
    }
    
    private JPanel createTablePanel(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(228, 230, 235), 1),
            BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        
        JPanel cardHeader = createCardHeader();
        panel.add(cardHeader, BorderLayout.NORTH);
        
        pretTable = createPretTable();
        JScrollPane scrollPane = new JScrollPane(pretTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createCardHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(228, 230, 235)),
            BorderFactory.createEmptyBorder(20, 24, 20, 24)
        ));
        
        JLabel title = new JLabel("Liste des prets");
        title.setFont(new Font("Century Gothic", Font.BOLD, 18));
        title.setForeground(new Color(28, 30, 33));
        
        countBadge = new JLabel("0 pret");
        countBadge.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        countBadge.setForeground(new Color(101, 103, 107));
        countBadge.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.add(title);
        titlePanel.add(countBadge);
        
        header.add(titlePanel, BorderLayout.WEST);
        
        return header;
    }
    
    private JTable createPretTable(){
        String[] columns = {"ID", "Montant", "Devise", "Date", "Duree", "Statut", "N° Compte"};
        
        tableModel = new DefaultTableModel(columns, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        JTable table = new JTable(tableModel);
        table.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        table.setRowHeight(45);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(false);
        table.setGridColor(new Color(228, 230, 235));
        table.setSelectionBackground(new Color(173, 216, 230)); 
        table.setSelectionForeground(Color.BLACK);
        
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Century Gothic", Font.BOLD, 13));
        header.setBackground(new Color(248, 249, 250));
        header.setForeground(new Color(101, 103, 107));
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(228, 230, 235)),
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        
        int[] widths = {60, 120, 80, 150, 100, 100, 250};
        for (int i = 0; i < widths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }
        
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        table.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);  
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);  
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); 
        
        
        table.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer(){
           @Override
           public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
              JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
              panel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());  
              
              JLabel badge = new JLabel(value != null ? value.toString() : "");
              badge.setFont(new Font("Century Gothic", Font.BOLD, 12));
              badge.setOpaque(true);
              badge.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));
              
              String statut = value != null ? value.toString().toLowerCase() : "";
              
              if (statut.contains("rembousé") || statut.contains("rembourse")) {
                    badge.setBackground(new Color(212, 237, 218));
                    badge.setForeground(new Color(21, 87, 36));
                } else if (statut.contains("impayé") || statut.contains("impaye")) {
                    badge.setBackground(new Color(248, 215, 218));
                    badge.setForeground(new Color(114, 28, 36));
                } else if (statut.contains("en cours") || statut.contains("cours")) {
                    badge.setBackground(new Color(255, 243, 205));
                    badge.setForeground(new Color(133, 100, 4));
                } else {
                    badge.setBackground(Color.LIGHT_GRAY);
                    badge.setForeground(Color.DARK_GRAY);
                }
              
              panel.add(badge);
              return panel;
           }
        });
        
        return table;
    }
    
    private void loadPrets(){
        
        tableModel.setRowCount(0);
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try{
            conn = DBManager.link();
            if (conn == null) return;
            
            String sql = "SELECT idPret, montPret, devPret, datePret, duree, statutPret, numCompte " +
                     "FROM pret ORDER BY duree DESC";
            
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            int count = 0;
            while(rs.next()){
              count++;
              int id = rs.getInt("idPret");
              double montant = rs.getDouble("montPret");
              String devise = rs.getString("devPret");
              Timestamp datePret = rs.getTimestamp("datePret");
              int duree = rs.getInt("duree");
              String statut = rs.getString("statutPret");
              String numCompte = rs.getString("numCompte");
              
              SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
              String dateFormatee = sdf.format(datePret);
              
              String montantFormate;
              if (devise != null && !devise.isEmpty()) {
                    if (devise.equals("€") || devise.equals("EURO")) {
                        montantFormate = String.format("%,.2f €", montant);
                    } else if (devise.equals("$") || devise.equals("USD")) {
                        montantFormate = String.format("%,.2f $", montant);
                    } else if (devise.equals("£") || devise.equals("GBP")) {
                        montantFormate = String.format("%,.2f £", montant);
                    } else if (devise.equals("FCFA") || devise.equals("CFA")) {
                        montantFormate = String.format("%,.2f FCFA", montant);
                    } else {
                        montantFormate = String.format("%,.2f %s", montant, devise);
                        }
                } else {
                    montantFormate = String.format("%,.2f", montant);
                }
              
                String dureeFormatee = duree + " mois";
              
                tableModel.addRow(new Object[]{
                    id,
                    montantFormate,
                    devise,
                    dateFormatee,
                    dureeFormatee,
                    statut,
                    numCompte
                });
                }
            updatePretCount(count);

            }catch(SQLException e){
                e.printStackTrace();
                
                JOptionPane.showMessageDialog(this,
                "Erreur lors du chargement des transactions: " + e.getMessage(),
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
            }finally{
                try{
                   if(rs != null) rs.close();
                   if(pstmt != null) pstmt.close();
                   if(conn != null) conn.close(); 
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    private void updatePretCount(int count){
        if(countBadge != null){
            String text = count + "pret" + (count > 1 ? "s" : "");
            countBadge.setText("(" + text + ")");
            countBadge.revalidate();
            countBadge.repaint();
        }
    }
    
    private void searchPrets(){
        
        String searchText = searchField.getText().toLowerCase().trim();
        
        if (searchText.isEmpty() || searchText.equals("rechercher un pret...")) {
            loadPrets();
            return;
        }
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try{
            
            conn = DBManager.link();
            if (conn == null) return;
            
            String sql = "SELECT idPret, montPret, devPret, datePret, duree, statutPret, numCompte " +
                     "FROM pret WHERE LOWER(statutPret) LIKE ? OR numCompte LIKE ? " +
                     "OR CAST(idPret AS CHAR) LIKE ? OR LOWER(devPret) LIKE ? " +
                     "OR CAST(duree AS CHAR) LIKE ? " +
                     "ORDER BY datePret DESC";
            
            pstmt = conn.prepareStatement(sql);
            String pattern = "%" + searchText + "%";
            
            for (int i = 1; i <= 5; i++) {
                pstmt.setString(i, pattern);
            }
            
            rs = pstmt.executeQuery(); 
            
            tableModel.setRowCount(0);
            
            int count = 0;
            while(rs.next()){
              count++;
              int id = rs.getInt("idPret");
              double montant = rs.getDouble("montPret");
              String devise = rs.getString("devPret");
              Timestamp datePret = rs.getTimestamp("datePret");
              int duree = rs.getInt("duree");
              String statut = rs.getString("statutPret");
              String numCompte = rs.getString("numCompte");
              
              SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
              String dateFormatee = sdf.format(datePret);
              
              String montantFormate;
              if (devise != null && !devise.isEmpty()) {
                    if (devise.equals("€") || devise.equals("EUR")) {
                        montantFormate = String.format("%,.2f €", montant);
                    } else if (devise.equals("$") || devise.equals("USD")) {
                        montantFormate = String.format("%,.2f $", montant);
                    } else if (devise.equals("£") || devise.equals("GBP")) {
                        montantFormate = String.format("%,.2f £", montant);
                    } else if (devise.equals("FCFA") || devise.equals("CFA")) {
                        montantFormate = String.format("%,.2f FCFA", montant);
                    } else {
                        montantFormate = String.format("%,.2f %s", montant, devise);
                        }
                } else {
                    montantFormate = String.format("%,.2f", montant);
                }
              
                String dureeFormatee = duree + " mois";
              
                tableModel.addRow(new Object[]{
                    id,
                    montantFormate,
                    devise,
                    dateFormatee,
                    dureeFormatee,
                    statut,
                    numCompte
                });
                }
                updatePretCount(count);
        }catch(SQLException e){
                e.printStackTrace();
                
                JOptionPane.showMessageDialog(this,
                "Erreur lors du chargement des prets: " + e.getMessage(),
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
            }finally{
                try{
                   if(rs != null) rs.close();
                   if(pstmt != null) pstmt.close();
                   if(conn != null) conn.close(); 
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
    } 

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new IPret().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
