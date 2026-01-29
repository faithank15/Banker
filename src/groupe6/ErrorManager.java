/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package groupe6;

import javax.swing.JOptionPane;


/**
 *
 * @author faithan15
 */
public class ErrorManager {

    public static void showError(Exception e) {
        JOptionPane.showMessageDialog(
            null,
            "Erreur base de données :\n" + e.getMessage(),
            "Erreur",
            JOptionPane.ERROR_MESSAGE
        );
        e.printStackTrace();
    }
}
