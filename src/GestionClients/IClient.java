/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GestionClients;
import groupe6.*;
import DBManager.DBManager;
import java.awt.*;
import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Graphics;
import javax.swing.border.Border;
import javax.swing.JComboBox;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.io.File;



/**
 *
 * @author CJ
 */
public class IClient extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(IClient.class.getName());
    
    private JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel contentPanel;
    private JScrollPane tablescrollpane;
    private JTable clientTable;
    private DefaultTableModel tableModel;
    private DBManager dbManager;

    /**
     * Creates new form IClient
     */
    public IClient() {
        CustomInit();
        setTitle("Gestion Clients - Banker");
        setSize(1200,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        
        
        
        
    }
    
    private void CustomInit(){
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 242, 245));
        
        createHeader();
        createAddClientForm();
        createClientListCard();
        
        dbManager = new DBManager();
        
        
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        pack();
        setSize(1200, 900);
       
    }
    
    private void createHeader(){
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
        
        JLabel titleLabel = new JLabel("Gestion des clients");
        titleLabel.setFont(new Font("Century Gothic", Font.BOLD, 32));
        titleLabel.setForeground(new Color(28,30,33));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
       
        
        JLabel subtitleLabel = new JLabel("Liste complète des clients de la banque");
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
        
        JTextField searchField = new JTextField(){
            @Override
            protected void paintComponent(Graphics g){
                if (!isOpaque() && getBorder() instanceof RoundedBorder){
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
        
        searchField.addFocusListener(new FocusAdapter(){
            @Override
            public void focusGained(FocusEvent evt){
                if (searchField.getText().equals("Rechercher un client...")){
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }
            
            @Override
            public void focusLost(FocusEvent evt){
                if (searchField.getText().isEmpty()){
                    searchField.setText("Rechercher un client...");
                    searchField.setForeground(new Color(153, 153, 153));
                }
            }
        });
        
        searchPanel.add(searchField, BorderLayout.EAST);
        
        
        headerPanel.add(searchPanel, gbc);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
         
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
    
    private void createAddClientForm(){
        JPanel formCard = new JPanel();
        formCard.setLayout(new BorderLayout());
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(228, 230, 235), 1),
                 BorderFactory.createEmptyBorder(0, 0, 24, 0)));
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(228, 230, 235)),
                    BorderFactory.createEmptyBorder(20, 24, 20, 24)));
        
        JLabel titleLabel = new JLabel("Ajout rapide de client");
        titleLabel.setFont(new Font("Century Gothic", Font.BOLD, 18));
        titleLabel.setForeground(new Color(28, 30, 33));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        formCard.add(headerPanel, BorderLayout.NORTH);
        
        JPanel bodyPanel = new JPanel();
        bodyPanel.setBackground(Color.WHITE);
        bodyPanel.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 2));
        bodyPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 15, 5);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        
        JLabel nomLabel = new JLabel("Nom *");
        nomLabel.setForeground(new Color(28, 30, 33));
        nomLabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        bodyPanel.add(nomLabel, gbc);
        
        gbc.gridx = 1;
        nomField = new JTextField();
        configureTextField(nomField);
        bodyPanel.add(nomField, gbc);
        
        gbc.gridx = 2;
        JLabel prenomLabel = new JLabel("Prénom *");
        prenomLabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        prenomLabel.setBackground(new Color(28, 30, 33));
        bodyPanel.add(prenomLabel, gbc);
        
        
        gbc.gridx = 3;
        prenomField = new JTextField();
        configureTextField(prenomField);
        bodyPanel.add(prenomField, gbc);
        
        gbc.gridx = 4;
        JLabel dateLabel = new JLabel ("Date de naissance *");
        dateLabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        dateLabel.setBackground(new Color(28, 30, 33));
        bodyPanel.add(dateLabel, gbc);
        
        gbc.gridx = 5;
        dateField = new JTextField();
        configureTextField(dateField);
        bodyPanel.add(dateField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel telLabel = new JLabel("Numéro de téléphone *");
        telLabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        telLabel.setBackground(new Color(28, 30, 33));
        bodyPanel.add(telLabel, gbc);
        
        gbc.gridx = 1;
        telField = new JTextField();
        configureTextField(telField);
        bodyPanel.add(telField, gbc);
        
        gbc.gridx = 2;
        JLabel sexeLabel = new JLabel("Sexe *");
        sexeLabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        sexeLabel.setBackground(new Color(28, 30, 33));
        bodyPanel.add(sexeLabel, gbc);
        
        gbc.gridx = 3;
        sexeComboBox = new JComboBox<>(new String[]{"", "Masculin", "Féminin"});
        sexeComboBox.setFont(new Font("Century Gothic", Font.PLAIN, 12));
        sexeComboBox.setBackground(Color.WHITE);
        sexeComboBox.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        sexeComboBox.setPreferredSize(new Dimension(150, 36));
        bodyPanel.add(sexeComboBox, gbc);
        
        gbc.gridx = 4;
        JLabel emailLabel = new JLabel("Email *");
        emailLabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        emailLabel.setBackground(new Color(28, 30, 33));
        bodyPanel.add(emailLabel, gbc);
        
        gbc.gridx = 5;
        emailField = new JTextField();
        configureTextField(emailField);
        bodyPanel.add(emailField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel adresseLabel = new JLabel("Adresse");
        adresseLabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        adresseLabel.setBackground(new Color(28, 30, 33));
        bodyPanel.add(adresseLabel, gbc);
        
        gbc.gridx = 1;
        adresseField = new JTextField();
        configureTextField(adresseField);
        bodyPanel.add(adresseField, gbc);
        
        gbc.gridx = 2;
        JLabel professionLabel = new JLabel("Profession");
        professionLabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        professionLabel.setBackground(new Color(28, 30, 33));
        bodyPanel.add(professionLabel, gbc);
        
        gbc.gridx = 3;
        professionField = new JTextField();
        configureTextField(professionField);
        bodyPanel.add(professionField, gbc);
        
        gbc.gridx = 4;
        JLabel npiLabel = new JLabel("Numéro Pièce d'Identité (NPI) *");
        npiLabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        npiLabel.setBackground(new Color(28, 30, 33));
        bodyPanel.add(npiLabel, gbc);
        
        gbc.gridx = 5;
        npiField = new JTextField();
        configureTextField(npiField);
        bodyPanel.add(npiField, gbc);
        
        formCard.add(bodyPanel, BorderLayout.CENTER);
        
        //Then the fucking buttons !!!
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 24, 24, 24));
        
        JButton saveButton = new JButton("Enregistrer le client");
        saveButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        saveButton.setBackground(new Color(0, 102, 204));
        saveButton.setForeground(Color.WHITE);
        saveButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveButton.addActionListener(e -> saveClient());
        
        JButton resetButton = new JButton("Réinitialiser");
        resetButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        resetButton.setBackground(Color.WHITE);
        resetButton.setForeground(new Color(0, 102, 204));
        resetButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        resetButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        resetButton.addActionListener(e -> resetForm());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(resetButton);
        
        formCard.add(buttonPanel, BorderLayout.SOUTH);
        
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(new Color(240, 242, 245));
        
        GridBagConstraints gdc = new GridBagConstraints();
        gdc.gridx = 0;
        gdc.gridy = 0;
        gdc.weightx = 1.0;
        gdc.insets = new Insets(20, 20, 20, 20);
        gdc.fill = GridBagConstraints.HORIZONTAL;
        
        container.add(formCard, gdc);
        mainPanel.add(container, BorderLayout.CENTER);
        
               
    }
    
    private void configureTextField(JTextField field){
        field.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(228, 230, 235), 1), 
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.setPreferredSize(new Dimension(150, 36));
    }
    
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField dateField;
    private JTextField telField;
    private JComboBox<String> sexeComboBox;
    private JTextField emailField;
    private JTextField adresseField;
    private JTextField professionField;
    private JTextField npiField;
    
    private void saveClient() {
        
        if(nomField.getText().trim().isEmpty() ||
           prenomField.getText().trim().isEmpty() ||
           dateField.getText().trim().isEmpty() ||
           telField.getText().trim().isEmpty() ||
           sexeComboBox.getSelectedIndex() == 0 ||
           emailField.getText().trim().isEmpty() ||
           npiField.getText().trim().isEmpty()){
            
            JOptionPane.showMessageDialog(this, "Veuillez remplir tout les champs", "Important", JOptionPane.WARNING_MESSAGE);
            return;
        }

        
        String nom = nomField.getText().trim();
        String prenom = prenomField.getText().trim();
        String dateText = dateField.getText().trim();
        String telText = telField.getText().trim();
        String sexe = (String) sexeComboBox.getSelectedItem();
        String email = emailField.getText().trim();
        String adresse = adresseField.getText().trim();
        String profession = professionField.getText().trim();
        String npiText = npiField.getText().trim();

        String dateNais = convertDateForMySQL(dateText);
        
        
        if (dateNais == null){
            JOptionPane.showMessageDialog(this,
                    "Date invalide!\n",
                            "Format accepté JJ/MM/AAAA",
                            JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        long numTel;
        try {
            if (!telText.matches("\\d{9,15}")){
                throw new NumberFormatException("Le tel doit contenir 8 à 10 chiffres");
                
            }
            numTel = Long.parseLong(telText);
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this, 
                    "Numéro invalide",
                     "Doit contenir des chiffres only",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        long npi;
        
        try {
            if (!npiText.matches("\\d{5,15}")){
                throw new NumberFormatException("Le NPI doit contenir 5 à 15 chiffres");
                
            }
            npi = Long.parseLong(npiText);
        }catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                    "NPI invalide" ,
                    "Doit contenir uni chiffres",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (sexeComboBox.getSelectedIndex() == 0){
            JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner un sexe",
                    "Sexe manquant",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        String sexeCode = sexe.equals("Masculin") ? "M" : "F";
        
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")){
            JOptionPane.showMessageDialog(this, 
                    "Email invalide\n",
                    "Exemple: nom@domaine.com",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        
       
 
            
            Connection conn = DBManager.link();
            if (conn == null){
                JOptionPane.showMessageDialog(this, "Connexion impossible",
                        "Verification nécéssaire",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            
            
            
            try {
                if(checkIfClientExists(conn, "email", email)){
                    JOptionPane.showMessageDialog(this,
                            "Cet email existe déjà",
                            "Email",
                            JOptionPane.WARNING_MESSAGE);
                    conn.close();
                    return;
                }
                
                if(checkIfClientExists(conn, "npi", String.valueOf(npi))){
                    JOptionPane.showMessageDialog(this,
                            "Cet NPI existe déjà",
                            "Veuillez entrer un NPI valide",
                            JOptionPane.WARNING_MESSAGE);
                    conn.close();
                    return;
                }
                            
                dbManager = new DBManager();
                boolean success = dbManager.create_client(conn, nom, prenom, dateNais, numTel, sexeCode, adresse, profession, npi, email);
                conn.close();
            
                if (success){
                
                JOptionPane.showMessageDialog(this,
                        "Client ajouté avec succès !\n" +
                                nom + " " + prenom + "\n" + "NPI:" + npi,
                        "Succèes",
                        JOptionPane.INFORMATION_MESSAGE);
                resetForm();
                }else {
                    
                    JOptionPane.showMessageDialog(this, 
                        "Erreur lors de l'insertion",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                }
            }catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Erreur SQL: " + e.getMessage(),
                    "Erreur base de données", 
                    JOptionPane.ERROR_MESSAGE);
                
                try {
                    if (conn != null) conn.close();
                }catch (SQLException ex){}
            }
            
            
            

        System.out.println("\nThe end..");
    }
    
    private boolean checkIfClientExists(Connection conn, String champ, String valeur){
        if (conn == null || champ == null || champ.trim().isEmpty() || valeur == null){
            return false;
        }
        champ = champ.trim();
        
        if (!champ.matches("[a-zA-Z0-9_]+")){
            return false;
        }
        
        String sql = "SELECT COUNT(*) FROM client WHERE '" + champ + "' = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, valeur);
            
            try (ResultSet rs = pstmt.executeQuery()){
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    private boolean checkIfExistsInt(String field, int value, Connection conn){
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            String sql = "SELECT COUNT(*) FROM client WHERE" + field + " = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, value);
            rs = pstmt.executeQuery();
            
            if(rs.next()){
                return rs.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
    
    
    private String convertDateForMySQL(String dateStr){
       if (dateStr == null || dateStr.trim().isEmpty()){
           return null;
       }
       
       dateStr = dateStr.trim();
       
       try{
           if(dateStr.matches("\\d{1,2}/\\d{1,2}/\\d{4}")){
               String[] parts = dateStr.split("/");
               
               int jour = Integer.parseInt(parts[0]);
               int mois = Integer.parseInt(parts[1]);
               int annee = Integer.parseInt(parts[2]);
               
               if(jour < 1 || jour > 31){
                   return null;
               }
               
               if (mois < 1 || mois > 12){
                   return null;
               }
               
               if (annee < 1900 || annee > 2100){
                   return null;
               }
               
               int[] joursParMois = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
               
               if (mois == 2 && ((annee %4 == 0 && annee % 100 != 0) || (annee % 400 == 0))){
                   joursParMois[1] = 29;
               }
               
               if (jour > joursParMois[mois -1]){
                   return null;
               }
               
               String jourStr = String.format("%02d", jour);
               String moisStr = String.format("%02d", mois);
               
               
               String resultat = annee + "-" + moisStr + "-" + jourStr;
               return resultat;
           }
           
           if (dateStr.matches("\\d{4}-\\d{1,2}-\\d{1,2}")) {
               String[] parts = dateStr.split("-");
               int annee = Integer.parseInt(parts[0]);
               int mois = Integer.parseInt(parts[1]);
               int jour = Integer.parseInt(parts[2]);
               
               if(jour < 1 || jour > 31){
                   return null;
               }
               if(mois < 1 || mois > 12){
                   return null;
               }
               if(annee < 1990 || annee > 2100){
                   return null;
               }
               
               return dateStr;
           }
           
           return null;
          
       }catch (Exception e){
           return null;
       }
    }
    private void resetForm() {
        if (nomField != null) nomField.setText("");
        if (prenomField != null) prenomField.setText("");
        if (dateField != null) dateField.setText("");
        if (telField != null) telField.setText("");
        if (sexeComboBox != null) sexeComboBox.setSelectedIndex(0);
        if (emailField != null) emailField.setText("");
        if (adresseField != null) adresseField.setText("");
        if (professionField != null) professionField.setText("");
        if (npiField != null) npiField.setText("");
        
    }
    
    private void createClientListCard(){
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(228, 230, 235), 1),
                 BorderFactory.createEmptyBorder(0, 0, 24, 0)));
        
        JPanel header = createListHeader();
        card.add(header, BorderLayout.NORTH);
        
        JTable table = createClientTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        card.add(scrollPane, BorderLayout.CENTER);
        
        JPanel pagination = createPagination();
        card.add(pagination, BorderLayout.SOUTH);
        
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(card, BorderLayout.SOUTH);
        
        
        
    }
    
    private JPanel createListHeader(){
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(228, 230, 235)),
                BorderFactory.createEmptyBorder(20, 24, 20, 24)
        ));
        
        JLabel title = new JLabel("Liste des clients");
        title.setFont(new Font("Century Gothic", Font.BOLD, 18));
        title.setForeground(new Color(28, 30, 33));
        
        header.add(title, BorderLayout.WEST);
        
        return header;
        
        
    }
    
    private JTable createClientTable(){
        String[] columns = {"ID", "Client", "Contact", "Informations", "Comptes", "Statut", "Actions"};
        
        tableModel = new DefaultTableModel(columns, 0){
            
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
            
            @Override
            public Class<?> getColumnClass(int column){
                return String.class;
            }
        };
        
        clientTable = new JTable(tableModel);
        
        configureTable();
        
        loadClients();
        
        return clientTable;
    }
    
    private void configureTable (){
        clientTable.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        clientTable.setRowHeight(60);
        clientTable.setShowVerticalLines(false);
        clientTable.setShowHorizontalLines(false);
        clientTable.setGridColor(new Color(228, 230, 235));
        
        JTableHeader header = clientTable.getTableHeader();
        header.setFont(new Font ("Century Gothic", Font.BOLD, 13));
        header.setBackground(new Color(248, 249, 250));
        header.setForeground(new Color(101, 103, 107));
        
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(228, 230, 235)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        
        int[] widths = {60, 200, 180, 200, 120, 100, 80};
        for (int i = 0; i < widths.length; i++){
            clientTable.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
            
            clientTable.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer(){
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
                    JLabel label = (JLabel) super.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column
                    );
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    label.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));
                    
                    String statuts = value != null ? value.toString().toLowerCase() : "";
                    
                    if (statuts.contains("actif")){
                        label.setBackground(new Color(212, 237, 218));
                        label.setForeground(new Color(21, 87, 36));
                        
                        
                    } else if (statuts.contains("inactif")){
                        label.setBackground(new Color(248, 215, 218));
                        label.setForeground(new Color(114, 28, 36));
                        
                    }else if (statuts.contains("attente")){
                        label.setBackground(new Color(255, 243, 205));
                        label.setForeground(new Color(133, 100, 4));
                    }else {
                        label.setBackground(Color.LIGHT_GRAY);
                        label.setForeground(Color.DARK_GRAY);
                    }
                    
                    label.setOpaque(true);
                    return label;
                }
              
            });
            
            clientTable.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer(){
                private JButton createDetailButton(){
                    JButton btn = new JButton();
                    btn.setToolTipText("Voir détails");
                    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    btn.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
                    btn.setBackground(new Color(240, 242, 245));
                    btn.setFocusPainted(false);
                    
                    try{
                        ImageIcon icon = loadIcon("/icons/eye.png");
                        if(icon != null){
                            btn.setIcon(icon);
                        }else {
                            btn.setText("D");
                        }
                    }catch(Exception e){
                        btn.setText("Détail");
                    }
                    
                    return btn;
                }
                
                
                public Component getTableCellRenderComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
                    JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
                    panel.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
                    panel.add(createDetailButton());
                    return panel;
                }
            });
        }
    }
        
        private ImageIcon loadIcon(String path){
            try{
                java.net.URL imgURL = getClass().getResource(path);
                if(imgURL != null){
                    ImageIcon icon = new ImageIcon(imgURL);
                    Image img = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
                    return new ImageIcon(img);
                }
                File file = new File("resources" + path);
                if(file.exists()){
                    ImageIcon icon = new ImageIcon(file.getAbsolutePath());
                    Image img = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
                    return new ImageIcon(img);
                }
            }catch(Exception e){
                System.out.println("Impossible de charger l'icône");
            }
            
            return null;
        }
        
        private void loadClients(){
            tableModel.setRowCount(0);
            
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            
            try{
                conn = DBManager.link();
                if(conn == null){
                    
                    return;
                }
                String sql = "SELECT idCli, nomCli, preCli, email, numTel, dateNais, profession, sexe, npi FROM client ORDER BY DESC LIMIT 10";
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                
                while(rs.next()){
                    addClientRow(rs);
                }
                
            }catch(SQLException e){
                
            }finally{
                
            }
        }
        
        private void addClientRow(ResultSet rs) throws SQLException {
            int id = rs.getInt("idCli");
            String nom = rs.getString("nomCli");
            String prenom = rs.getString("preCli");
            String email = rs.getString("email");
            String tel = rs.getString("numTel");
            String date = rs.getString("dateNais");
            String profession = rs.getString("profession");
            String sexe = rs.getString("sexe");
            String npi = rs.getString("npi");
            
            String clientCell = "<html><b>" + nom + " " + prenom + "</b><br><small>NPI: " + npi + "</small></html>";
            String contactCell = "<html>" + email + "<br><small>" + formatPhone(tel) + "</small></html>";
            String infoCell = "<html>Né(e): " + (date != null ? date : "N/A") + "<br><small>" + (profession != null ? profession : "Non renseigné") + "</small></html>";
            
            tableModel.addRow(new Object[]{
                "#" + id,
                clientCell,
                contactCell,
                infoCell,
                "0 compte<br><small>Solde: 0f</small>",
                "Actif",
                ""
            });
        }
        
        private JPanel createPagination(){
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBackground(new Color(248, 249, 250));
            panel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(228, 230, 235)),
                    BorderFactory.createEmptyBorder(16, 24, 16, 24)
            ));
            
            JLabel info = new JLabel("Affichage de 1 à" + tableModel.getRowCount() + "sur" + tableModel.getRowCount() + "clients");
            info.setFont(new Font("Century Gothic", Font.PLAIN, 14));
            info.setForeground(new Color(101, 103, 107));
            
            JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 0));
            
            JButton prev = createPageButton("Précédent", false);
            JButton next = createPageButton("Suivant", false);
            
            for (int i=1; i<=3; i++){
                JButton btn = createPageButton(String.valueOf(i), i == 1);
                buttons.add(btn);
            }
            
            panel.add(info, BorderLayout.WEST);
            panel.add(buttons, BorderLayout.EAST);
            
            return panel;
        }
        
        private JButton createPageButton(String text, boolean active){
            JButton btn = new JButton(text);
            btn.setFont(new Font("Century Gothic", Font.PLAIN, 14));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            if(active){
                btn.setBackground(new Color(0, 102, 204));
                btn.setForeground(Color.WHITE);
                btn.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
                  
            }else {
                btn.setBackground(Color.WHITE);
                btn.setForeground(new Color(10, 103, 107));
                btn.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(228, 230, 235), 1),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
            
            return btn;
        }
        
        private String formatPhone(String phone){
            if(phone == null || phone.length() < 9){
                return phone;
            }
            
            try{
                phone = phone.replaceAll("\\s+", "");
                
                if (phone.length() >= 10){
                    return phone.substring(0, 2) + " " +
                           phone.substring(2, 4) + " " +
                           phone.substring(4, 6) + " " +
                           phone.substring(6, 8) + " " +
                           phone.substring(8, Math.min(10, phone.length()));
                           
                }else {
                    return  phone.substring(0, 2) + " " +
                            phone.substring(2, 4) + " " +
                            phone.substring(4, 6) + " " +
                            phone.substring(6);
                }
            }catch(Exception e){
                return phone;
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
        java.awt.EventQueue.invokeLater(() -> new IClient().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
