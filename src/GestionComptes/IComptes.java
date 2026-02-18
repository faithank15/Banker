/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GestionComptes;


import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.*;
import DBManager.DBManager;
import javax.swing.table.TableCellEditor;
import javax.swing.AbstractCellEditor;
import java.awt.image.BufferedImage;
import javax.swing.border.Border;
import java.awt.Graphics;


/**
 *
 * @author CJ
 */
public class IComptes extends javax.swing.JFrame {
    
    private JPanel mainPanel;
    
    
    private JTable compteTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JLabel countBadge;
    
    private JTextField numCompteField;
    private JComboBox<String> typeCombo;
    private JTextField soldeField;
    private JTextField dateField;
    private JComboBox<String> statutCombo;
    private JTextField depotInitField;
    private JTextField cleRIBField;
    private JTextField deviseField;
    private JComboBox<String> clientCombo;
    
    private DBManager dbManager;
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(IComptes.class.getName());

    /**
     * Creates new form IComptes
     */
    public IComptes() {
        initUI();
        setTitle("Gestion des Comptes Bancaires");
        setSize(1500, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        
    }
    
    private void initUI(){
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 242, 245));
        
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        
        JPanel header = createHeader();
        gbc.insets = new Insets(20, 20, 10, 20);
        mainPanel.add(header, gbc);
        
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 20, 10, 20);
        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel, gbc);
        
        gbc.gridy = 2;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel tablePanel = createTablePanel();
        gbc.insets = new Insets(10, 20, 20, 20);
        mainPanel.add(tablePanel, gbc);
        
        dbManager = new DBManager();
        
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        
        setSize(1300, 900);
        setLocationRelativeTo(null);
        
        loadClientsForCombo();
        loadComptes();
    }
    
    private JPanel headerPanel;
    private JPanel createHeader(){
        headerPanel = new JPanel(new GridBagLayout());
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
        titlePanel.setBackground(new Color(240, 242, 245));
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        
        JLabel titleLabel = new JLabel("Gestion des Comptes");
        titleLabel.setFont(new Font("Century Gothic", Font.BOLD, 32));
        titleLabel.setForeground(new Color(28, 30, 33));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        
        JLabel subtitleLabel = new JLabel("Liste complète des comptes bancaires");
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
        
        searchField.setText("Rechercher un compte...");
        searchField.setForeground(new Color(153, 153, 153));
        
        searchField.addFocusListener(new FocusAdapter(){
            @Override
            public void focusGained(FocusEvent evt){
                if (searchField.getText().equals("Rechercher un compte...")){
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent evt){
                if (searchField.getText().isEmpty()){
                    searchField.setText("Rechercher un compte...");
                    searchField.setForeground(new Color(153, 153, 153));
                }
            }
        });
        
                searchField.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent e){
                String text = searchField.getText().trim();
                
                if (text.equals("Rechercher un compte...")|| text.isEmpty()){
                    if(text.isEmpty() && !searchField.hasFocus()){
                        return;
                    }
                    loadComptes();
                    return;
                }
                searchComptes();
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
    
    private JPanel createFormPanel(){
        JPanel formCard = new JPanel(new BorderLayout());
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(228, 230, 235), 1),
        BorderFactory.createEmptyBorder(0, 0, 0, 0)
            ));
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(228, 230, 235)),
        BorderFactory.createEmptyBorder(20, 24, 20, 24)
            ));
        
        JLabel titleLabel = new JLabel("Ajout rapide de compte");
        titleLabel.setFont(new Font("Century Gothic", Font.BOLD, 18));
        titleLabel.setForeground(new Color(28, 30, 33));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        JPanel bodyPanel = new JPanel(new GridBagLayout());
        bodyPanel.setBackground(Color.WHITE);
        bodyPanel.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.insets = new Insets(5, 5, 15, 5);
        
        formGbc.weightx = 0.33;
        initializeFormFields();
        
        addFormField(bodyPanel, formGbc, 0, 0, "N° Compte *", numCompteField);
        addFormCombo(bodyPanel, formGbc, 1, 0, "Type *", typeCombo);
        addFormField(bodyPanel, formGbc, 2, 0, "Solde *", soldeField);
        
        addFormField(bodyPanel, formGbc, 0, 1, "Date ouverture *", dateField);
        addFormCombo(bodyPanel, formGbc, 1, 1, "Statut *", statutCombo);
        addFormField(bodyPanel, formGbc, 2, 1, "Dépôt initial", depotInitField);
        
        addFormField(bodyPanel, formGbc, 0, 2, "Clé RIB", cleRIBField);
        addFormField(bodyPanel, formGbc, 1, 2, "Devise *", deviseField);
        addFormCombo(bodyPanel, formGbc, 2, 2, "Client *", clientCombo);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 24, 24, 24));
        
        JButton saveButton = new JButton("Enregistrer le compte");
        saveButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        saveButton.setBackground(new Color(0, 102, 204));
        saveButton.setForeground(Color.WHITE);
        saveButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveButton.addActionListener(e -> saveCompte());
        
        JButton resetButton = new JButton("Réinitialiser");
        resetButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        resetButton.setBackground(Color.WHITE);
        resetButton.setForeground(new Color(0, 102, 204));
        resetButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        resetButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        resetButton.addActionListener(e -> resetForm());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(resetButton);
        
        formCard.add(headerPanel, BorderLayout.NORTH);
        formCard.add(bodyPanel, BorderLayout.CENTER);
        formCard.add(buttonPanel, BorderLayout.SOUTH);
        
        return formCard;
    }
    
    private void initializeFormFields(){
        
        numCompteField = createTextField();
        soldeField = createTextField();
        dateField = createTextField();
        depotInitField = createTextField();
        cleRIBField = createTextField();
        deviseField = createTextField();
        
        typeCombo = new JComboBox<>(new String[]{"", "Courant", "Epargne", "Titre", "Joint"});
        statutCombo = new JComboBox<>(new String[]{"", "Actif", "Inactif", "Gele", "Bloque"});
        clientCombo = new JComboBox<>();
        
        styleComboBox(typeCombo);
        styleComboBox(statutCombo);
        styleComboBox(clientCombo);
        
        loadClientsForCombo();
    }
    
private JTextField createTextField() {

    JTextField field = new JTextField();
    field.setFont(new Font("Century Gothic", Font.PLAIN, 14));
    field.setBackground(Color.WHITE);

   
    Border normalBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(228, 230, 235), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
    );

   
    Border focusBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 102, 204), 2),
            BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(0, 102, 204, 60), 4),
                    BorderFactory.createEmptyBorder(6, 10, 6, 10)
            )
    );

    field.setBorder(normalBorder);
    field.setPreferredSize(new Dimension(150, 40));

    
    field.addFocusListener(new FocusAdapter() {
        @Override
        public void focusGained(FocusEvent e) {
            field.setBorder(focusBorder);
        }

        @Override
        public void focusLost(FocusEvent e) {
            field.setBorder(normalBorder);
        }
    });

    return field;
    }
private void styleComboBox(JComboBox<String> combo){
    combo.setFont(new Font("Century Gothic", Font.PLAIN, 14));
    combo.setBackground(Color.WHITE);
    combo.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(228, 230, 235), 1),
        BorderFactory.createEmptyBorder(8, 12, 8, 12)
    ));
    combo.setPreferredSize(new Dimension(150, 40));
}

private void addFormField(JPanel panel, GridBagConstraints gbc,
                          int x, int y, String label, JTextField field) {

    
    gbc.gridx = x * 2;
    gbc.gridy = y;
    gbc.insets = new Insets(6, 10, 6, 10);

    JLabel jlabel = new JLabel(label);
    jlabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
    jlabel.setForeground(new Color(28, 30, 33));
    panel.add(jlabel, gbc);

    
    gbc.gridx = x * 2 + 1;

    field.setFont(new Font("Century Gothic", Font.PLAIN, 14));
    field.setBackground(Color.WHITE);
    field.setPreferredSize(new Dimension(170, 40));

    Border normalBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(228, 230, 235), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
    );

    Border focusBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 102, 204), 2),
            BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(0, 102, 204, 60), 3),
                    BorderFactory.createEmptyBorder(7, 11, 7, 11)
            )
    );

    field.setBorder(normalBorder);

    field.addFocusListener(new FocusAdapter() {
        @Override
        public void focusGained(FocusEvent e) {
            field.setBorder(focusBorder);
        }

        @Override
        public void focusLost(FocusEvent e) {
            field.setBorder(normalBorder);
        }
    });

    panel.add(field, gbc);
}
private void addFormCombo(JPanel panel, GridBagConstraints gbc,
                          int x, int y, String label, JComboBox<String> combo) {

    
    gbc.gridx = x * 2;
    gbc.gridy = y;
    gbc.insets = new Insets(6, 10, 6, 10);

    JLabel jlabel = new JLabel(label);
    jlabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
    jlabel.setForeground(new Color(28, 30, 33));
    panel.add(jlabel, gbc);

    
    gbc.gridx = x * 2 + 1;

    combo.setFont(new Font("Century Gothic", Font.PLAIN, 14));
    combo.setBackground(Color.WHITE);
    combo.setPreferredSize(new Dimension(170, 40));
    combo.setFocusable(true);

    
    Border normalBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(228, 230, 235), 1),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
    );

    
    Border focusBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 102, 204), 2),
            BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(0, 102, 204, 60), 3),
                    BorderFactory.createEmptyBorder(5, 9, 5, 9)
            )
    );

    combo.setBorder(normalBorder);

    combo.addFocusListener(new FocusAdapter() {
        @Override
        public void focusGained(FocusEvent e) {
            combo.setBorder(focusBorder);
        }

        @Override
        public void focusLost(FocusEvent e) {
            combo.setBorder(normalBorder);
        }
    });

    
    combo.setRenderer(new DefaultListCellRenderer() {
        @Override
        public Component getListCellRendererComponent(
                JList<?> list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {

            JLabel lbl = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);

            lbl.setFont(new Font("Century Gothic", Font.PLAIN, 14));
            lbl.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

            if (isSelected) {
                lbl.setBackground(new Color(0, 102, 204));
                lbl.setForeground(Color.WHITE);
            }

            return lbl;
        }
    });

    panel.add(combo, gbc);
}

private void loadClientsForCombo() {
    clientCombo.removeAllItems();
    clientCombo.addItem(""); 
    
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    try {
        conn = DBManager.link();
        String sql = "SELECT idCli, nomCli, preCli FROM client ORDER BY nomCli";
        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();
        
        while (rs.next()) {
            int id = rs.getInt("idCli");
            String nom = rs.getString("nomCli");
            String prenom = rs.getString("preCli");
            clientCombo.addItem(id + " - " + nom + " " + prenom);
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

private void saveCompte() {
  
    String numCompte = numCompteField.getText().trim();
    String type = (String) typeCombo.getSelectedItem();
    String soldeText = soldeField.getText().trim();
    String dateText = dateField.getText().trim();
    String statut = (String) statutCombo.getSelectedItem();
    String devise = deviseField.getText().trim().toUpperCase();
    String depotInitText = depotInitField.getText().trim();
    String cleRIB = cleRIBField.getText().trim();
    int clientId = getSelectedClientId();
    
    
    if (numCompte.isEmpty()) {
        showWarning("Le numéro de compte est obligatoire");
        return;
    }
    
    if (type == null || type.isEmpty()) {
        showWarning("Le type de compte est obligatoire");
        return;
    }
    
    if (soldeText.isEmpty()) {
        showWarning("Le solde est obligatoire");
        return;
    }
    
    if (dateText.isEmpty()) {
        showWarning("La date d'ouverture est obligatoire");
        return;
    }
    
    if (statut == null || statut.isEmpty()) {
        showWarning("Le statut est obligatoire");
        return;
    }
    
    if (devise.isEmpty()) {
        showWarning("La devise est obligatoire");
        return;
    }
    
    if (clientId == -1) {
        showWarning("Veuillez sélectionner un client");
        return;
    }
    
    
    double solde;
    double depotInit = 0;
    
    try {
        solde = Double.parseDouble(soldeText);
        
        if (!depotInitText.isEmpty()) {
            depotInit = Double.parseDouble(depotInitText);
        }
    } catch (NumberFormatException e) {
        showWarning("Le solde et le dépôt initial doivent être des nombres valides");
        return;
    }
    
    
    String dateMySQL = formatDateForMySQL(dateText);
    if (dateMySQL == null) {
        showWarning("Format de date invalide. Utilisez JJ/MM/AAAA");
        return;
    }
    
    
    Connection conn = null;
    PreparedStatement pstmt = null;
    
    try {
        conn = DBManager.link();
        if (conn == null) {
            showError("Impossible de se connecter à la base de données");
            return;
        }
        
       
        if (compteExists(conn, numCompte)) {
            showWarning("Ce numéro de compte existe déjà");
            return;
        }
        
        
        String sql = "INSERT INTO compte (numCompte, type, solde, dateCrea, statutCompte, " +
                     "depotInit, cleRIB, devise, idCli) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, numCompte);
        pstmt.setString(2, type);
        pstmt.setDouble(3, solde);
        pstmt.setString(4, dateMySQL);
        pstmt.setString(5, statut);
        pstmt.setDouble(6, depotInit);
        pstmt.setString(7, cleRIB.isEmpty() ? null : cleRIB);
        pstmt.setString(8, devise);
        pstmt.setInt(9, clientId);
        
        int rowsAffected = pstmt.executeUpdate();
        
        if (rowsAffected > 0) {
            System.out.println("Compte ajouté avec succès");
            
            JOptionPane.showMessageDialog(this,
                "Compte ajouté avec succès!\n" +
                "N° Compte: " + numCompte + "\n" +
                "Type: " + type + "\n" +
                "Solde: " + solde + " " + devise,
                "Succès",
                JOptionPane.INFORMATION_MESSAGE);
            
            
            loadComptes();
            
            
            resetForm();
            
        } else {
            showError("Échec de l'ajout du compte");
        }
        
    } catch (SQLException e) {
        System.out.println(" Erreur SQL: " + e.getMessage());
        e.printStackTrace();
        
        showError("Erreur base de données: " + e.getMessage());
    } finally {
        try {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

private boolean compteExists(Connection conn, String numCompte) throws SQLException {
    String sql = "SELECT COUNT(*) FROM compte WHERE numCompte = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, numCompte);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
    }
    return false;
}

private String formatDateForMySQL(String dateStr) {
    if (dateStr == null || dateStr.trim().isEmpty()) return null;
    
    dateStr = dateStr.trim();
    
    
    if (dateStr.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
        String[] parts = dateStr.split("/");
        int jour = Integer.parseInt(parts[0]);
        int mois = Integer.parseInt(parts[1]);
        int annee = Integer.parseInt(parts[2]);
        
        
        if (jour < 1 || jour > 31) return null;
        if (mois < 1 || mois > 12) return null;
        if (annee < 1900 || annee > 2100) return null;
        
        return String.format("%04d-%02d-%02d", annee, mois, jour);
    }
    
    
    if (dateStr.matches("\\d{4}-\\d{2}-\\d{2}")) {
        return dateStr;
    }
    
    return null;
}

private void showWarning(String message) {
    JOptionPane.showMessageDialog(this, message, "Attention", JOptionPane.WARNING_MESSAGE);
}

private void showError(String message) {
    JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
}

private void resetForm() {
    numCompteField.setText("");
    typeCombo.setSelectedIndex(0);
    soldeField.setText("");
    dateField.setText("");
    statutCombo.setSelectedIndex(0);
    depotInitField.setText("");
    cleRIBField.setText("");
    deviseField.setText("");
    clientCombo.setSelectedIndex(0);
}

public void loadComptes() {
    
    tableModel.setRowCount(0);
    
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    try {
        conn = DBManager.link();
        if (conn == null) {
            System.out.println("Connexion échouée");
            return;
        }
        
        
        String sql = "SELECT c.numCompte, c.type, c.solde, c.dateCrea, c.statutCompte, " +
                     "c.depotInit, c.cleRIB, c.devise, cl.idCli, cl.nomCli, cl.preCli " +
                     "FROM compte c " +
                     "LEFT JOIN client cl ON c.idCli = cl.idCli " +
                     "ORDER BY c.dateCrea DESC";
        
        System.out.println("SQL: " + sql);
        
        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();
        
        int count = 0;
        while (rs.next()) {
            count++;
            addCompteRow(rs);
        }
        
        System.out.println("" + count + " compte(s) chargé(s)");
        
        
        updateCompteCount();
        
    } catch (SQLException e) {
        System.out.println("Erreur SQL: " + e.getMessage());
        e.printStackTrace();
        
        JOptionPane.showMessageDialog(this,
            "Erreur lors du chargement des comptes: " + e.getMessage(),
            "Erreur",
            JOptionPane.ERROR_MESSAGE);
    } finally {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

private void addCompteRow(ResultSet rs) throws SQLException {
    String numCompte = rs.getString("numCompte");
    String type = rs.getString("type");
    double solde = rs.getDouble("solde");
    String dateCrea = rs.getString("dateCrea");
    String statut = rs.getString("statutCompte");
    double depotInit = rs.getDouble("depotInit");
    String cleRIB = rs.getString("cleRIB");
    String devise = rs.getString("devise");
    String nomCli = rs.getString("nomCli");
    String preCli = rs.getString("preCli");
    
    
    String soldeFormate = String.format("%,.2f %s", solde, devise);
    String depotInitFormate = (depotInit > 0) ? String.format("%,.2f %s", depotInit, devise) : "-";
    
    
    String clientNom = (nomCli != null && preCli != null) ? preCli + " " + nomCli : "Non assigné";
    
    tableModel.addRow(new Object[]{
        numCompte,
        type,
        soldeFormate,
        dateCrea,
        statut,
        depotInitFormate,
        cleRIB,
        clientNom
    });
}

private void updateCompteCount() {
    if (countBadge != null && tableModel != null) {
        int count = tableModel.getRowCount();
        countBadge.setText("(" + count + " compte" + (count > 1 ? "s" : "") + ")");
        countBadge.revalidate();
        countBadge.repaint();
        System.out.println("Compteur mis à jour: " + count + " compte(s)");
    }
}

private void searchComptes() {
    String searchText = searchField.getText().toLowerCase().trim();
    
    if (searchText.isEmpty() || searchText.equals("rechercher un compte...")) {
        loadComptes();
        return;
    }
    
    System.out.println("Recherche de comptes: '" + searchText + "'");
    
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    try {
        conn = DBManager.link();
        if (conn == null) return;
        
        String sql = "SELECT c.numCompte, c.type, c.solde, c.dateCrea, c.statutCompte, " +
                     "c.depotInit, c.cleRIB, c.devise, cl.idCli, cl.nomCli, cl.preCli " +
                     "FROM compte c " +
                     "LEFT JOIN client cl ON c.idCli = cl.idCli " +
                     "WHERE LOWER(c.numCompte) LIKE ? OR LOWER(c.type) LIKE ? " +
                     "OR LOWER(cl.nomCli) LIKE ? OR LOWER(cl.preCli) LIKE ? " +
                     "ORDER BY c.dateCrea DESC";
        
        pstmt = conn.prepareStatement(sql);
        String pattern = "%" + searchText + "%";
        
        for (int i = 1; i <= 4; i++) {
            pstmt.setString(i, pattern);
        }
        
        rs = pstmt.executeQuery();
        
        tableModel.setRowCount(0);
        
        int count = 0;
        while (rs.next()) {
            count++;
            addCompteRow(rs);
        }
        
        System.out.println(" " + count + " résultat(s) trouvé(s)");
        updateCompteCount();
        
    } catch (SQLException e) {
        System.out.println("Erreur recherche: " + e.getMessage());
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
    
private int getSelectedClientId() {
    String selected = (String) clientCombo.getSelectedItem();
    if (selected == null || selected.isEmpty()) return -1;
    
   
    String[] parts = selected.split(" - ");
    try {
        return Integer.parseInt(parts[0]);
    } catch (NumberFormatException e) {
        return -1;
    }
}
private JPanel createTablePanel(){
    JPanel card = new JPanel(new BorderLayout());
    card.setBackground(Color.WHITE);
    card.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(228, 230, 235), 1),
        BorderFactory.createEmptyBorder(0, 0, 0, 0)
            ));
    JPanel cardHeader = createCardHeader();
    card.add(cardHeader, BorderLayout.NORTH);
    
    JTable table = createCompteTable();
    
    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    scrollPane.getViewport().setBackground(Color.WHITE);
    
    setupTableActions();
    
    card.add(scrollPane, BorderLayout.CENTER);
    
    
    
    return card;
}

private JPanel createCardHeader() {
    JPanel header = new JPanel(new BorderLayout());
    header.setBackground(Color.WHITE);
    header.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(228, 230, 235)),
        BorderFactory.createEmptyBorder(20, 24, 20, 24)
    ));
    
    
    JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
    titlePanel.setBackground(Color.WHITE);
    
    JLabel title = new JLabel("Liste des comptes");
    title.setFont(new Font("Century Gothic", Font.BOLD, 18));
    title.setForeground(new Color(28, 30, 33));
    
    int compteCount = (tableModel != null) ? tableModel.getRowCount() : 0;
    countBadge = new JLabel("(" + compteCount + " compte" + (compteCount > 1 ? "s" : "") + ")");
    countBadge.setFont(new Font("Century Gothic", Font.PLAIN, 14));
    countBadge.setForeground(new Color(101, 103, 107));
    countBadge.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
    
    titlePanel.add(title);
    titlePanel.add(countBadge);
    
    header.add(titlePanel, BorderLayout.WEST);
    
    return header;
} 

private JTable createCompteTable() {
    
    String[] columns = {"N° Compte", "Type", "Solde", "Date ouverture", "Statut", 
                        "Dépôt initial", "Clé RIB", "Client", "Actions"};
    
    tableModel = new DefaultTableModel(columns, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 8;
        }
    };
    
    compteTable = new JTable(tableModel);
    compteTable.setFont(new Font("Century Gothic", Font.PLAIN, 14));
    compteTable.setRowHeight(60); 
    compteTable.setShowVerticalLines(false);
    compteTable.setShowHorizontalLines(false);
    compteTable.setGridColor(new Color(228, 230, 235));
    compteTable.setSelectionBackground(new Color(135, 206, 250));
    
    
    JTableHeader header = compteTable.getTableHeader();
    header.setFont(new Font("Century Gothic", Font.BOLD, 13));
    header.setBackground(new Color(248, 249, 250));
    header.setForeground(new Color(101, 103, 107));
    header.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(228, 230, 235)),
        BorderFactory.createEmptyBorder(12, 12, 12, 12)
    ));
    
    
    int[] widths = {150, 100, 100, 100, 80, 100, 80, 150, 60};
    for (int i = 0; i < widths.length; i++) {
        compteTable.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
    }
    
   
    compteTable.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            
            
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
            panel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            
            JLabel badge = new JLabel(value != null ? value.toString() : "");
            badge.setFont(new Font("Century Gothic", Font.BOLD, 12));
            badge.setOpaque(true);
            
            badge.setBorder(BorderFactory.createEmptyBorder(8, 12, 2, 12));
            
            String statut = value != null ? value.toString().toLowerCase() : "";
            
            if (statut.contains("actif")) {
                badge.setBackground(new Color(212, 237, 218));
                badge.setForeground(new Color(21, 87, 36));
            } else if (statut.contains("inactif")|| statut.contains("gele")) {
                badge.setBackground(new Color(248, 215, 218));
                badge.setForeground(new Color(114, 28, 36));
            } else if (statut.contains("bloque")){
                badge.setBackground(Color.red);
                badge.setForeground(new Color(21, 87, 36));
            }else{
                badge.setBackground(Color.WHITE);
                badge.setForeground(Color.BLACK);
            }
            
            panel.add(badge);
            return panel;
        }
    });
    
    return compteTable;
}

private void setupTableActions() {
    
    TableColumn actionColumn = compteTable.getColumnModel().getColumn(8);
    
    
    actionColumn.setCellRenderer(new DefaultTableCellRenderer() {
        private JButton createDetailButton() {
            JButton btn = new JButton();
            btn.setToolTipText("Voir les détails du compte");
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(false);
            btn.setFocusPainted(false);
            btn.setPreferredSize(new Dimension(30, 30));
            
           
            ImageIcon icon = loadIcon("/resources/icons/eye.png");
            if (icon != null) {
                btn.setIcon(icon);
            } else {
                btn.setIcon(createEyeIcon());
            }
            
            return btn;
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
            panel.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
            
            JButton btn = createDetailButton();
            panel.add(btn);
            
            return panel;
        }
    });
    
    
    actionColumn.setCellEditor(new CompteActionCellEditor(compteTable));
}


class CompteActionCellEditor extends AbstractCellEditor implements TableCellEditor {
    private JPanel panel;
    private JButton button;
    private int currentRow;
    private JTable table;
    
    public CompteActionCellEditor(JTable table) {
        this.table = table;
        
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
        panel.setBackground(Color.WHITE);
        
        button = new JButton();
        button.setToolTipText("Voir les détails du compte");
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
       
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(30, 30));
        
        ImageIcon icon = loadIcon("/resources/icons/eye.png");
        if (icon != null) {
            button.setIcon(icon);
        } else {
            button.setIcon(createEyeIcon());
        }
        
        button.addActionListener(e -> {
            String numCompte = (String) tableModel.getValueAt(currentRow, 0);
            showCompteDetails(numCompte);
            fireEditingStopped();
        });
        
        panel.add(button);
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        currentRow = row;
        panel.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
        return panel;
    }
    
    @Override
    public Object getCellEditorValue() {
        return null;
    }
}

private ImageIcon loadIcon(String path) {
    try {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        }
    } catch (Exception e) {
        System.out.println("Icône non trouvée: " + path);
    }
    return null;
}

private void showCompteDetails(String numCompte) {
    
    try{
        CompteDetailsDialog dialog = new CompteDetailsDialog(this, numCompte);
        dialog.setVisible(true);
        System.out.println("Dialogue ouvert avec succès");
    }catch(Exception e){
        System.out.println("Erreur lors de l'ouverture du dialogue: " + e.getMessage());
        e.printStackTrace();
        
        JOptionPane.showMessageDialog(this,
            "Erreur: " + e.getMessage(),
            "Erreur",
            JOptionPane.ERROR_MESSAGE);
    }

}

private ImageIcon createEyeIcon() {
    BufferedImage image = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = image.createGraphics();
    
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
   
    g2d.setColor(new Color(100, 100, 100));
    g2d.drawOval(4, 6, 16, 10);
    
    g2d.setColor(new Color(0, 102, 204));
    g2d.fillOval(9, 8, 6, 6);
    
    g2d.setColor(Color.BLACK);
    g2d.fillOval(11, 10, 2, 2);
    
    g2d.dispose();
    
    return new ImageIcon(image);
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
        java.awt.EventQueue.invokeLater(() -> new IComptes().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
