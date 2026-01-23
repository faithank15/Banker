/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package groupe6;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author faithan15
 */
public class DBManager {
    public static final String DB = "jdbc:mysql://localhost:3306/FormTest";
    
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    private static Connection con = null;
    
    private static List<String> liste = new ArrayList<>();

    
    public static Connection link(){
        try{
        
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(DB, USER, PASSWORD);
    
        } catch(ClassNotFoundException | SQLException e) {e.printStackTrace(); }
    
        return con;
    }
    
    public boolean create_account(Connection con, String nom, String prenom, String dateNais, int numTel, String sexe, String addresse, String profession, int npi, String email){
        
    }
}
