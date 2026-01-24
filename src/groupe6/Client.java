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
public class Client {

    private int idCli;
    private String nom;
    private String prenom;
    private LocalDate dateNais;
    private int numTel;
    private String sexe;
    private String adresse;
    private String profession;
    private int npi;
    private String email;

    public Client(int idCli, String nom, String prenom, LocalDate dateNais,
                  int numTel, String sexe, String adresse,
                  String profession, int npi, String email) {
        this.idCli = idCli;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNais = dateNais;
        this.numTel = numTel;
        this.sexe = sexe;
        this.adresse = adresse;
        this.profession = profession;
        this.npi = npi;
        this.email = email;
    }

    // Getters & Setters
}
