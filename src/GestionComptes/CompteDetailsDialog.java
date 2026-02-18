/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GestionComptes;
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
public class CompteDetailsDialog extends JDialog{
    
     private String numCompte;
     private JPanel mainPanel;
     private JButton editButton;
     private JButton saveButton;
     private boolean isEditing = false;
     private IComptes parentFrame;
     
     private JTextField numCompteField;
     private JComboBox<String> typeCombo; 
     private JTextField soldeField;
     private JTextField dateField;
     private JComboBox<String> statutCombo;
     private JTextField depotInitField;
     private JTextField cleRIBField;
     private JTextField deviseField;
     private JTextField clientField;
     
     public CompteDetailsDialog(IComptes parent, String numCompte){
         super(parent, "Détails du compte", true);
         
         this.parentFrame = parent;
         this.numCompte = numCompte;
         
         setSize(1200, 700);
         setLocationRelativeTo(parent);
         setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
         initUI();
         loadCompteData();
     }
    
     private void initUI(){
         mainPanel = new JPanel(new BorderLayout());
         mainPanel.setBackground(new Color(240, 242, 245));
         mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
         
         JPanel card = new JPanel(new BorderLayout());
         card.setBackground(Color.WHITE);
         card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(228, 230, 235), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
         ));
         
         JLabel titleLabel = new JLabel("Détails du compte " + numCompte);
         titleLabel.setFont(new Font("Century Gothic", Font.BOLD, 18));
         titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
         card.add(titleLabel, BorderLayout.NORTH);
         
         JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 10, 10);
        
        initializeFields();
        
        addFormRow(formPanel, gbc, 0, "Numéro de compte:", numCompteField);
        addFormRow(formPanel, gbc, 1, "Type:", typeCombo);
        addFormRow(formPanel, gbc, 2, "Solde:", soldeField);
        addFormRow(formPanel, gbc, 3, "Date d'ouverture:", dateField);
        addFormRow(formPanel, gbc, 4, "Statut:", statutCombo);
        addFormRow(formPanel, gbc, 5, "Dépôt initial:", depotInitField);
        addFormRow(formPanel, gbc, 6, "Clé RIB:", cleRIBField);
        addFormRow(formPanel, gbc, 7, "Devise:", deviseField);
        addFormRow(formPanel, gbc, 8, "Client:", clientField);
        
        card.add(formPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = createButtonPanel();
        card.add(buttonPanel, BorderLayout.SOUTH);
        
        mainPanel.add(card, BorderLayout.CENTER);
        add(mainPanel);
        
        setFieldsEditable(false);
    }
     
    private void initializeFields() {
        numCompteField = createTextField();
        typeCombo = new JComboBox<>(new String[]{"Courant", "Épargne", "Titre", "Joint"});
        soldeField = createTextField();
        dateField = createTextField();
        statutCombo = new JComboBox<>(new String[]{"Actif", "Inactif", "Bloqué", "Gelé"});
        depotInitField = createTextField();
        cleRIBField = createTextField();
        deviseField = createTextField();
        clientField = createTextField();
        clientField.setEditable(false); 
       }
    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(228, 230, 235), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
    return field;
   }
    
   private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent comp) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        JLabel jlabel = new JLabel(label);
        jlabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        panel.add(jlabel, gbc);
    
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(comp, gbc);
    }
   
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panel.setBackground(Color.WHITE);
    
    
        editButton = new JButton("Modifier");
        editButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        editButton.setBackground(new Color(255, 153, 0));
        editButton.setForeground(Color.WHITE);
        editButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        editButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        editButton.addActionListener(e -> toggleEditMode());
    
    
        saveButton = new JButton("Sauvegarder");
        saveButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        saveButton.setBackground(new Color(40, 167, 69));
        saveButton.setForeground(Color.WHITE);
        saveButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        saveButton.setEnabled(false);
        saveButton.addActionListener(e -> saveChanges());
    
    
        JButton closeButton = new JButton("Fermer");
        closeButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        closeButton.setBackground(new Color(108, 117, 125));
        closeButton.setForeground(Color.WHITE);
        closeButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(e -> dispose());
    
        panel.add(editButton);
        panel.add(saveButton);
        panel.add(closeButton);
    
        return panel;
    }

    private void setFieldsEditable(boolean editable) {
        numCompteField.setEditable(editable);
        typeCombo.setEditable(editable);
        soldeField.setEditable(editable);
        dateField.setEditable(editable);
        statutCombo.setEditable(editable);
        depotInitField.setEditable(editable);
        cleRIBField.setEditable(editable);
        deviseField.setEditable(editable);
    
    }

    private void toggleEditMode() {
        isEditing = !isEditing;
        setFieldsEditable(isEditing);
        editButton.setEnabled(!isEditing);
        saveButton.setEnabled(isEditing);
    }
    
    private void saveChanges() {
        System.out.println("Sauvegarde des modifications pour le compte: " + numCompte);
    
    
        String type = (String) typeCombo.getSelectedItem();
        String soldeText = soldeField.getText().trim();
        String dateText = dateField.getText().trim();
        String statut = (String) statutCombo.getSelectedItem();
        String depotInitText = depotInitField.getText().trim();
        String cleRIB = cleRIBField.getText().trim();
        String devise = deviseField.getText().trim();
    
    
        if (type.isEmpty() || soldeText.isEmpty() || dateText.isEmpty() || 
            statut.isEmpty() || devise.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Veuillez remplir tous les champs obligatoires",
                "Validation",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
    
    
        double solde, depotInit = 0;
        try {
            solde = Double.parseDouble(soldeText.replace(",", ".").replace(" ", ""));
            if (!depotInitText.isEmpty() && !depotInitText.equals("-")) {
                depotInit = Double.parseDouble(depotInitText.replace(",", ".").replace(" ", ""));
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Les montants doivent être des nombres valides",
                "Erreur de format",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        Connection conn = null;
        PreparedStatement pstmt = null;
    
        try {
            conn = DBManager.link();
            if (conn == null) return;
        
            String sql = "UPDATE compte SET type = ?, solde = ?, dateCrea = ?, " +
                        "statutCompte = ?, depotInit = ?, cleRIB = ?, devise = ? " +
                        "WHERE numCompte = ?";
        
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, type);
            pstmt.setDouble(2, solde);
            pstmt.setString(3, dateText);
            pstmt.setString(4, statut);
            pstmt.setDouble(5, depotInit);
            pstmt.setString(6, cleRIB);
            pstmt.setString(7, devise);
            pstmt.setString(8, numCompte);
        
            int rowsAffected = pstmt.executeUpdate();
        
            if (rowsAffected > 0) {
                System.out.println(" Compte mis à jour avec succès");
            
                JOptionPane.showMessageDialog(this,
                    "Compte mis à jour avec succès!",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE);
            
            
                isEditing = false;
                setFieldsEditable(false);
                editButton.setEnabled(true);
                saveButton.setEnabled(false);
                
                if (parentFrame != null) {
                    parentFrame.loadComptes();
                }
            
            } else {
                JOptionPane.showMessageDialog(this,
                    "Aucune modification effectuée",
                    "Information",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        
        } catch (SQLException e) {
            System.out.println("Erreur SQL: " + e.getMessage());
            e.printStackTrace();
        
            JOptionPane.showMessageDialog(this,
                "Erreur lors de la sauvegarde: " + e.getMessage(),
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private void loadCompteData() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
    
        try {
            conn = DBManager.link();
            if (conn == null) return;
        
            String sql = "SELECT c.*, cl.nomCli, cl.preCli " +
                        "FROM compte c " +
                        "LEFT JOIN client cl ON c.idCli = cl.idCli " +
                        "WHERE c.numCompte = ?";
        
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, numCompte);
            rs = pstmt.executeQuery();
        
            if (rs.next()) {
                numCompteField.setText(rs.getString("numCompte"));
                String type = rs.getString("type");
                typeCombo.setSelectedItem(type);
            
                double solde = rs.getDouble("solde");
                String devise = rs.getString("devise");
                soldeField.setText(String.format("%.2f", solde));
            
                dateField.setText(rs.getString("dateCrea"));
                String statut = rs.getString("statutCompte");
                statutCombo.setSelectedItem(statut);
            
                double depotInit = rs.getDouble("depotInit");
                depotInitField.setText(depotInit > 0 ? String.format("%.2f", depotInit) : "");
            
                cleRIBField.setText(rs.getString("cleRIB"));
                deviseField.setText(devise);
            
                String nomCli = rs.getString("nomCli");
                String preCli = rs.getString("preCli");
                if (nomCli != null && preCli != null) {
                    clientField.setText(preCli + " " + nomCli);
                } else {
                    clientField.setText("Non assigné");
                }
            }
        
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Erreur lors du chargement des données: " + e.getMessage(),
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
}

