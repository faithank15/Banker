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
public class Compte {
    
    private long numCompte;
    private LocalDate dateCrea;
    private double depotInit;
    private String type;
    private int cleRIB;
    private String devise;
    private double solde;
    private String statutCompte;
    private int idCli;

    public Compte(long numCompte, LocalDate dateCrea, double depotInit, String type,
                  int cleRIB, String devise, double solde, String statutCompte, int idCli) {
        this.numCompte = numCompte;
        this.dateCrea = dateCrea;
        this.depotInit = depotInit;
        this.type = type;
        this.cleRIB = cleRIB;
        this.devise = devise;
        this.solde = solde;
        this.statutCompte = statutCompte;
        this.idCli = idCli;
    }

    // getters & setters
}
