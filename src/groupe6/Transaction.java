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
public class Transaction {

    private int idTrans;
    private long numCompte;
    private double montTrans;
    private String statut;
    private String typeTrans;
    private String motif;
    private LocalDate dateTrans;

    public Transaction(int idTrans, long numCompte, double montTrans,
                       String statut, String typeTrans,
                       String motif, LocalDate dateTrans) {
        this.idTrans = idTrans;
        this.numCompte = numCompte;
        this.montTrans = montTrans;
        this.statut = statut;
        this.typeTrans = typeTrans;
        this.motif = motif;
        this.dateTrans = dateTrans;
    }

    // Getters & Setters
}
