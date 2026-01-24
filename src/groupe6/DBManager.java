/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package groupe6;

import java.sql.Connection;
import java.sql.*;

/**
 *
 * @author faithan15
 */
public class DBManager {
    public static final String DB = "jdbc:mysql://localhost:3306/FormTest";
    
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    private static Connection con = null;
    
    public static Connection link(){
        try{
        
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(DB, USER, PASSWORD);
    
        } catch(ClassNotFoundException | SQLException e) {e.printStackTrace(); }
    
        return con;
    }
    
    //LES METHODES DE CREATION/INSERTION DANS LA DB
    
    public boolean create_compte(Connection con, long numCompte, String dateCrea,
                             double depotInit, String type, int cleRIB,
                             String devise, double solde, String statutCompte,
                             int idCli) {

        String sql = "INSERT INTO Compte(numCompte, dateCrea, depotInit, type, cleRIB, devise, solde, statutCompte, idCli) " +
                     "VALUES (?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setLong(1, numCompte);
            pst.setDate(2, Date.valueOf(dateCrea));
            pst.setDouble(3, depotInit);
            pst.setString(4, type);
            pst.setInt(5, cleRIB);
            pst.setString(6, devise);
            pst.setDouble(7, solde);
            pst.setString(8, statutCompte);
            pst.setInt(9, idCli);

            pst.executeUpdate();
            return true;

        } catch (SQLException e) {
            ErrorManager.showError(e);
            return false;
        }
    }
    
    public boolean create_client(Connection con, String nom, String prenom, String dateNais,
                             int numTel, String sexe, String addresse,
                             String profession, int npi, String email) {

        String sql = "INSERT INTO Client(nomCli, preCli, dateNais, numTel, sexe, adresse, profession, npi, email) VALUES (?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, nom);
            pst.setString(2, prenom);
            pst.setString(3, dateNais);
            pst.setInt(4, numTel);
            pst.setString(5, sexe);
            pst.setString(6, addresse);
            pst.setString(7, profession);
            pst.setInt(8, npi);
            pst.setString(9, email);

            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean create_trans(Connection con, int numCompte, int montTrans,
                            String statut, String typeTrans, String motif) {

        String sql = "INSERT INTO Transaction(numCompte, montTrans, statut, typeTrans, motif) VALUES (?,?,?,?,?)";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, numCompte);
            pst.setInt(2, montTrans);
            pst.setString(3, statut);
            pst.setString(4, typeTrans);
            pst.setString(5, motif);

            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public boolean create_pret(Connection con, int numCompte, int montPret, String statutPret) {

        String sql = "INSERT INTO Pret(numCompte, montPret, statutPret) VALUES (?,?,?)";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, numCompte);
            pst.setInt(2, montPret);
            pst.setString(3, statutPret);

            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean create_remb(Connection con, int idPret, int montRemb) {

        String sql = "INSERT INTO Remboursement(idPret, montRemb) VALUES (?,?)";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, idPret);
            pst.setInt(2, montRemb);

            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    
    //LES METHODES DE LECTURE
    
    
    public Compte get_compte(Connection con, long numCompte) {

        String sql = "SELECT * FROM Compte WHERE numCompte = ?";
        Compte compte = null;

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setLong(1, numCompte);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                compte = new Compte(
                    rs.getLong("numCompte"),
                    rs.getDate("dateCrea").toLocalDate(),
                    rs.getDouble("depotInit"),
                    rs.getString("type"),
                    rs.getInt("cleRIB"),
                    rs.getString("devise"),
                    rs.getDouble("solde"),
                    rs.getString("statutCompte"),
                    rs.getInt("idCli")
                );
            }

        } catch (SQLException e) {
            ErrorManager.showError(e);
        }
        return compte;
    }
    
    public Client get_client(Connection con, int idCli) {

        String sql = "SELECT * FROM Client WHERE idCli = ?";
        Client client = null;

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, idCli);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                client = new Client(
                    rs.getInt("idCli"),
                    rs.getString("nomCli"),
                    rs.getString("preCli"),
                    rs.getDate("dateNais").toLocalDate(),
                    rs.getInt("numTel"),
                    rs.getString("sexe"),
                    rs.getString("adresse"),
                    rs.getString("profession"),
                    rs.getInt("npi"),
                    rs.getString("email")
                );
            }

        } catch (SQLException e) {
            ErrorManager.showError(e);
        }
        return client;
    }

    
    public Transaction get_trans(Connection con, int idTrans) {

        String sql = "SELECT * FROM Transaction WHERE idTrans = ?";
        Transaction t = null;

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, idTrans);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                t = new Transaction(
                    rs.getInt("idTrans"),
                    rs.getLong("numCompte"),
                    rs.getDouble("montTrans"),
                    rs.getString("statut"),
                    rs.getString("typeTrans"),
                    rs.getString("motif"),
                    rs.getDate("dateTrans").toLocalDate()
                );
            }

        } catch (SQLException e) {
            ErrorManager.showError(e);
        }
        return t;
    }
    
    public Pret get_pret(Connection con, int idPret) {

        String sql = "SELECT * FROM Pret WHERE idPret = ?";
        Pret p = null;

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, idPret);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                p = new Pret(
                    rs.getInt("idPret"),
                    rs.getLong("numCompte"),
                    rs.getDouble("montPret"),
                    rs.getString("statutPret"),
                    rs.getDate("datePret").toLocalDate()
                );
            }

        } catch (SQLException e) {
            ErrorManager.showError(e);
        }
        return p;
    }
    
    public Remboursement get_remb(Connection con, int idRemb) {

        String sql = "SELECT * FROM Remboursement WHERE idRemb = ?";
        Remboursement r = null;

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, idRemb);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                r = new Remboursement(
                    rs.getInt("idRemb"),
                    rs.getInt("idPret"),
                    rs.getDouble("montRemb"),
                    rs.getDate("dateRemb").toLocalDate()
                );
            }

        } catch (SQLException e) {
            ErrorManager.showError(e);
        }
        return r;
    }
    
    
    
    //LES METHODES DE MODIFICATION/UPDATE
    
    
    public boolean set_compte(Connection con, Compte c) {

        String sql = "UPDATE Compte SET dateCrea=?, depotInit=?, type=?, cleRIB=?, devise=?, solde=?, statutCompte=?, idCli=? " +
                     "WHERE numCompte=?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setDate(1, Date.valueOf(c.getDateCrea()));
            pst.setDouble(2, c.getDepotInit());
            pst.setString(3, c.getType());
            pst.setInt(4, c.getCleRIB());
            pst.setString(5, c.getDevise());
            pst.setDouble(6, c.getSolde());
            pst.setString(7, c.getStatutCompte());
            pst.setInt(8, c.getIdCli());
            pst.setLong(9, c.getNumCompte());

            pst.executeUpdate();
            return true;

        } catch (SQLException e) {
            ErrorManager.showError(e);
            return false;
        }
    }
    
    public boolean set_client(Connection con, Client c) {

        String sql = "UPDATE Client SET nomCli=?, preCli=?, dateNais=?, numTel=?, sexe=?, " +
                     "adresse=?, profession=?, npi=?, email=? WHERE idCli=?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, c.getNom());
            pst.setString(2, c.getPrenom());
            pst.setDate(3, Date.valueOf(c.getDateNais()));
            pst.setInt(4, c.getNumTel());
            pst.setString(5, c.getSexe());
            pst.setString(6, c.getAdresse());
            pst.setString(7, c.getProfession());
            pst.setInt(8, c.getNpi());
            pst.setString(9, c.getEmail());
            pst.setInt(10, c.getIdCli());

            pst.executeUpdate();
            return true;

        } catch (SQLException e) {
            ErrorManager.showError(e);
            return false;
        }
    }
    
    public boolean set_trans(Connection con, Transaction t) {

        String sql = "UPDATE Transaction SET numCompte=?, montTrans=?, statut=?, typeTrans=?, motif=? " +
                     "WHERE idTrans=?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setLong(1, t.getNumCompte());
            pst.setDouble(2, t.getMontTrans());
            pst.setString(3, t.getStatut());
            pst.setString(4, t.getTypeTrans());
            pst.setString(5, t.getMotif());
            pst.setInt(6, t.getIdTrans());

            pst.executeUpdate();
            return true;

        } catch (SQLException e) {
            ErrorManager.showError(e);
            return false;
        }
    }
    
    public boolean set_pret(Connection con, Pret p) {

        String sql = "UPDATE Pret SET numCompte=?, montPret=?, statutPret=? WHERE idPret=?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setLong(1, p.getNumCompte());
            pst.setDouble(2, p.getMontPret());
            pst.setString(3, p.getStatutPret());
            pst.setInt(4, p.getIdPret());

            pst.executeUpdate();
            return true;

        } catch (SQLException e) {
            ErrorManager.showError(e);
            return false;
        }
    }
    
    public boolean set_remb(Connection con, Remboursement r) {

        String sql = "UPDATE Remboursement SET idPret=?, montRemb=? WHERE idRemb=?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, r.getIdPret());
            pst.setDouble(2, r.getMontRemb());
            pst.setInt(3, r.getIdRemb());

            pst.executeUpdate();
            return true;

        } catch (SQLException e) {
            ErrorManager.showError(e);
            return false;
        }
    }

 
}
