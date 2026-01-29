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
public class Pret {

    private int idPret;
    private long numCompte;
    private double montPret;
    private String statutPret;
    private LocalDate datePret;

    public Pret(int idPret, long numCompte, double montPret,
                String statutPret, LocalDate datePret) {
        this.idPret = idPret;
        this.numCompte = numCompte;
        this.montPret = montPret;
        this.statutPret = statutPret;
        this.datePret = datePret;
    }

    // Getters & Setters
}
