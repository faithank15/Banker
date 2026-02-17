/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GestionClients;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import DBManager.DBManager;
import javax.swing.table.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;

/**
 *
 * @author CJ
 */
public class ClientDetailsDialog extends JDialog{
    
    private int clientId;
    private JPanel mainPanel;
    private JPanel infoPanel;
    private JPanel accountsPanel;
    private JTable accountsTable;
    private DefaultTableModel accountsTableModel;
    private IClient parentFrame;
    
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField dateField;
    private JTextField emailField;
    private JTextField telField;
    private JTextField adresseField;
    private JTextField professionField;
    private JTextField npiField;
    private JComboBox<String> sexeCombo;
    private JComboBox<String> statutCombo;
    
    public ClientDetailsDialog(IClient parent, int clientId){
        super(parent, "Détails du client", true);
        this.parentFrame = parent;
        this.clientId = clientId;
        
        setSize(1200, 700);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        initUI();
        loadClientData();
    }
    
    private void initUI(){
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 242, 245));
        
        infoPanel = createInfoPanel();
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        
        accountsPanel = createAccountsPanel();
        mainPanel.add(accountsPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        revalidate();
        repaint();
        System.out.println("initUI() terminé");
        
    }
    private JPanel createInfoPanel(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(228, 230, 235), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel titleLabel = new JLabel("Informations personnelles");
        titleLabel.setFont(new Font("Century Gothic", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 10, 10);
        
        addFormField(formPanel, gbc, 0, 0, "Nom: ", nomField = createStyledField(15), 15);
        addFormField(formPanel, gbc, 1, 0, "Prénom: ", prenomField = createStyledField(15), 15);
        addFormField(formPanel, gbc, 2, 0, "Date naissance: ", dateField = createStyledField(10), 10);
        
        addFormField(formPanel, gbc, 0, 1, "Email: ", emailField = createStyledField(20), 20);
        addFormField(formPanel, gbc, 1, 1, "Téléphone: ", telField = createStyledField(20), 20);
        
        gbc.gridx = 4;
        gbc.gridy = 1;
        JLabel sexeLabel = new JLabel("Sexe:");
        sexeLabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        formPanel.add(sexeLabel, gbc);
        
        gbc.gridx = 5;
        sexeCombo = createStyledCombo(new String[]{"Masculin", "Féminin"});
        sexeCombo.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        sexeCombo.setPreferredSize(new Dimension(120, 36));
        formPanel.add(sexeCombo, gbc);
        
        addFormField(formPanel, gbc, 0, 2, "Adresse: ", adresseField = createStyledField(25), 25);
        addFormField(formPanel, gbc, 1, 2, "Profession: ", professionField = createStyledField(25), 15);
        
        gbc.gridx = 4;
        gbc.gridy = 2;
        JLabel npiLabel = new JLabel("NPI:");
        npiLabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        formPanel.add(npiLabel, gbc);
        
        gbc.gridx = 5;
        npiField = createStyledField(15);
        npiField.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        npiField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(228, 230, 235), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        npiField.setColumns(15);
        formPanel.add(npiField, gbc);
        
        
        
        setFieldsEditable(false);
        
        panel.add(formPanel, BorderLayout.CENTER);
        
        return panel;
    }
    private void addFormField(JPanel panel, GridBagConstraints gbc, int x, int y, String label, JTextField field, int columns){
        gbc.gridx = x * 2;
        gbc.gridy = y;
        gbc.weightx =0;
        
        JLabel jlabel = new JLabel(label);
        jlabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        panel.add(jlabel, gbc);
        
        gbc.gridx = x * 2 + 1;
        gbc.weightx = 1.0;
        
        field.setColumns(columns);
        field.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(180, 36));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(228, 230, 235), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        panel.add(field, gbc);
    }
    
    private JTextField createStyledField(int columns){
        JTextField field = new JTextField(columns);
        field.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        field.setBackground(Color.WHITE);
        
        Border normalBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        );
        
        Border focusBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 102, 204), 2),
                BorderFactory.createEmptyBorder(7, 11, 7, 11)
        );
        
        field.setBorder(normalBorder);
        
        field.addFocusListener(new FocusAdapter(){
            @Override
            public void focusGained(FocusEvent e){
                field.setBorder(focusBorder);
            }
            @Override
            public void focusLost(FocusEvent e){
                field.setBorder(normalBorder);
            }
        });
        
        return field;
    }
    
    private JComboBox<String> createStyledCombo(String[] items){
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        combo.setBackground(Color.WHITE);
        combo.setFocusable(true);
        
        Border normalBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        );
        
        Border focusBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 102, 204), 2),
                BorderFactory.createEmptyBorder(5, 9, 5, 9)
        );
        
        combo.setBorder(normalBorder);
        
        combo.addFocusListener(new FocusAdapter (){
            @Override
            public void focusGained(FocusEvent e){
                combo.setBorder(focusBorder);
            }
            @Override
            public void focusLost(FocusEvent e){
                combo.setBorder(normalBorder);
            }
        });
        
        return combo;
    }
    
private JPanel createAccountsPanel() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(Color.WHITE);
    panel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(228, 230, 235), 1),
        BorderFactory.createEmptyBorder(20, 20, 20, 20)
    ));
    
    
    JPanel headerPanel = new JPanel(new BorderLayout());
    headerPanel.setBackground(Color.WHITE);
    headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
    
    JLabel titleLabel = new JLabel("Comptes bancaires");
    titleLabel.setFont(new Font("Century Gothic", Font.BOLD, 18));
    titleLabel.setForeground(new Color(28, 30, 33));
    
    JLabel countLabel = new JLabel("0 compte(s)");
    countLabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
    countLabel.setForeground(new Color(101, 103, 107));
    
    headerPanel.add(titleLabel, BorderLayout.WEST);
    headerPanel.add(countLabel, BorderLayout.EAST);
    
    panel.add(headerPanel, BorderLayout.NORTH);
    
   
    String[] columns = {"N° Compte", "Type", "Solde", "Date ouverture", "Statut", "Dépôt initial", "Clé RIB"};
    accountsTableModel = new DefaultTableModel(columns, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    
    accountsTable = new JTable(accountsTableModel);
    accountsTable.setFont(new Font("Century Gothic", Font.PLAIN, 14));
    accountsTable.setRowHeight(40);
    accountsTable.setShowVerticalLines(false);
    accountsTable.setShowHorizontalLines(false);
    accountsTable.setGridColor(new Color(228, 230, 235));
    
    
    JTableHeader header = accountsTable.getTableHeader();
    header.setFont(new Font("Century Gothic", Font.BOLD, 13));
    header.setBackground(new Color(248, 249, 250));
    header.setForeground(new Color(101, 103, 107));
    header.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(228, 230, 235)),
        BorderFactory.createEmptyBorder(12, 12, 12, 12)
    ));
    
    
    accountsTable.getColumnModel().getColumn(0).setPreferredWidth(150); // N° Compte
    accountsTable.getColumnModel().getColumn(1).setPreferredWidth(100); // Type
    accountsTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Solde
    accountsTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Date
    accountsTable.getColumnModel().getColumn(4).setPreferredWidth(80);  // Statut
    accountsTable.getColumnModel().getColumn(5).setPreferredWidth(100); // Dépôt initial
    accountsTable.getColumnModel().getColumn(6).setPreferredWidth(80);  // Clé RIB
    
   
    accountsTable.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            JLabel label = (JLabel) super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
            
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
            
            String statut = value != null ? value.toString().toLowerCase() : "";
            
            if (statut.contains("actif")) {
                label.setBackground(new Color(212, 237, 218));
                label.setForeground(new Color(21, 87, 36));
            } else if (statut.contains("gelé")) {
                label.setBackground(new Color(248, 215, 218));
                label.setForeground(new Color(114, 28, 36));
            } else {
                label.setBackground(Color.WHITE);
                label.setForeground(Color.BLACK);
            }
            
            label.setOpaque(true);
            return label;
        }
    });
    
   
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
    accountsTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); 
    accountsTable.getColumnModel().getColumn(5).setCellRenderer(centerRenderer); 
    accountsTable.getColumnModel().getColumn(6).setCellRenderer(centerRenderer); 
    
    JScrollPane scrollPane = new JScrollPane(accountsTable);
    scrollPane.setBorder(BorderFactory.createLineBorder(new Color(228, 230, 235), 1));
    scrollPane.getViewport().setBackground(Color.WHITE);
    
    panel.add(scrollPane, BorderLayout.CENTER);
    
    return panel;
    
   
}
    private JButton editButton;
    private JButton saveButton;
    
    private JPanel createButtonPanel(){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panel.setBackground(new Color(240, 242, 245));
        
        
       
        editButton = new JButton("Modifier");
        editButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        editButton.setBackground(new Color(0, 102, 204));
        editButton.setForeground(Color.WHITE);
        editButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        editButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        
        saveButton = new JButton("Sauvegarder");
        saveButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        saveButton.setBackground(new Color(40, 167, 69));
        saveButton.setForeground(Color.WHITE);
        saveButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveButton.setEnabled(false);
        
        
        JButton closeButton = new JButton("Fermer");
        closeButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        closeButton.setBackground(Color.WHITE);
        closeButton.setForeground(new Color(108, 117, 125));
        closeButton.setBorder(BorderFactory.createLineBorder(new Color(108, 117, 125), 1));
        closeButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        editButton.addActionListener(e -> {
            toggleEditMode();
            editButton.setEnabled(false);
            saveButton.setEnabled(true);
        });
        
        saveButton.addActionListener(e -> {
            saveChanges();
            editButton.setEnabled(true);
            saveButton.setEnabled(false);
            
        });
        
        closeButton.addActionListener(e -> dispose());
        
        panel.add(editButton);
        panel.add(saveButton);
        panel.add(closeButton);
        
        return panel;
    }
    
    private void loadClientData(){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try{
            conn = DBManager.link();
             String clientSql = "SELECT nomCli, preCli, dateNais, email, numTel, adresse, profession, npi, sexe FROM client WHERE idCli = ?";
            pstmt = conn.prepareStatement(clientSql);
            pstmt.setInt(1, clientId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                nomField.setText(rs.getString("nomCli"));
                prenomField.setText(rs.getString("preCli"));
                dateField.setText(rs.getString("dateNais"));
                emailField.setText(rs.getString("email"));
                telField.setText(rs.getString("numTel"));
                adresseField.setText(rs.getString("adresse"));
                professionField.setText(rs.getString("profession"));
                npiField.setText(rs.getString("npi"));
                
                String sexe = rs.getString("sexe");
                sexeCombo.setSelectedItem("M".equals(sexe) ? "Masculin" : "Féminin");
            }
            
            loadClientAccounts(conn);
        }catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Erreur lors du chargement des données: " + e.getMessage(),
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
        }finally{
            try{
               if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close(); 
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
    
    private void setFieldsEditable(boolean editable) {
    nomField.setEditable(editable);
    prenomField.setEditable(editable);
    dateField.setEditable(editable);
    emailField.setEditable(editable);
    telField.setEditable(editable);
    adresseField.setEditable(editable);
    professionField.setEditable(editable);
    npiField.setEditable(editable);
    sexeCombo.setEnabled(editable);
    
}

private void toggleEditMode() {
    setFieldsEditable(true);
}

private void saveChanges() {
    String nom = nomField.getText().trim();
    String prenom = prenomField.getText().trim();
    String dateNais = dateField.getText().trim();
    String email = emailField.getText().trim();
    String telephone = telField.getText().trim();
    String adresse = adresseField.getText().trim();
    String profession = professionField.getText().trim();
    String npi = npiField.getText().trim();
    String sexe = (String) sexeCombo.getSelectedItem();
    
    
    if(nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || telephone.isEmpty()){
        JOptionPane.showMessageDialog(this, 
                "Veuillez remplir les champs obligatoires",
                "Validation",
                JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    String sexeCode = sexe.equals("Masculin") ? "M" : "F";
    
    Connection conn = null;
    PreparedStatement pstmt = null;
    
    try{
        conn = DBManager.link();
        if(conn == null){
            return;
        }
        
        String sql = "UPDATE client SET nomCli = ?, preCli = ?, dateNais = ?, email = ?, " +
                     "numTel = ?, adresse = ?, profession = ?, npi = ?, sexe = ? " +
                     "WHERE idCli = ?";
        
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, nom);
        pstmt.setString(2, prenom);
        pstmt.setString(3, dateNais);
        pstmt.setString(4, email);
        pstmt.setString(5, telephone);
        pstmt.setString(6, adresse);
        pstmt.setString(7, profession);
        pstmt.setString(8, npi);
        pstmt.setString(9, sexeCode);
        pstmt.setInt(10, clientId);
        
        int rowsAffected = pstmt.executeUpdate();
        
        if(rowsAffected > 0){
            JOptionPane.showMessageDialog(this, 
                    "Client mis à jour avec succès !",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE);
        
            
            setFieldsEditable(false);
            editButton.setEnabled(true);
            saveButton.setEnabled(false);
            if(parentFrame != null){
                parentFrame.loadClients();
            }
        }else {
            JOptionPane.showMessageDialog(this,
                    "Aucune modification n'a été effectuée",
                    "Information",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        
    }catch(SQLException e){
        e.printStackTrace();
        
        JOptionPane.showMessageDialog(this, 
                "Erreur lors de la sauvegarde",
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
    }finally{
        try{
            if(pstmt != null) pstmt.close();
            if(conn != null) conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
}
private void updateButtonState(boolean isEditing) {
    
    Component[] components = ((Container) mainPanel.getComponent(2)).getComponents();
    for (Component comp : components) {
        if (comp instanceof JButton) {
            JButton btn = (JButton) comp;
            if (btn.getText().equals("Modifier")) {
                btn.setEnabled(!isEditing);
            } else if (btn.getText().equals("Sauvegarder")) {
                btn.setEnabled(isEditing);
            }
        }
    }
}

    private void loadClientAccounts(Connection conn) throws SQLException {
        accountsTableModel.setRowCount(0);
        
        String accountsSql = "SELECT numCompte, type, solde, dateCrea, statutCompte, depotInit, cleRIB, devise " +
                         "FROM compte WHERE idCli = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(accountsSql)) {
        pstmt.setInt(1, clientId);
        
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String numCompte = rs.getString("numCompte");
                String type = rs.getString("type");
                double solde = rs.getDouble("solde");
                String dateCrea = rs.getString("dateCrea");
                String statut = rs.getString("statutCompte");
                double depotInit = rs.getDouble("depotInit");
                String cleRIB = rs.getString("cleRIB");
                String devise = rs.getString("devise");
                String soldeFormate = String.format("%,.2f %s", solde, devise);
                
                
                accountsTableModel.addRow(new Object[]{
                    numCompte,
                    type,
                    soldeFormate,
                    dateCrea,
                    statut,
                    depotInit,
                    cleRIB
                });
            }
        }
    }

    }
}
