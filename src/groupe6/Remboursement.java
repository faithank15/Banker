/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package groupe6;

import java.time.LocalDate;
/**
 *
 * @author faithan15
 */
public class Remboursement {

    private int idRemb;
    private int idPret;
    private double montRemb;
    private LocalDate dateRemb;

    public Remboursement(int idRemb, int idPret,
                         double montRemb, LocalDate dateRemb) {
        this.idRemb = idRemb;
        this.idPret = idPret;
        this.montRemb = montRemb;
        this.dateRemb = dateRemb;
    }

    // Getters & Setters
}
