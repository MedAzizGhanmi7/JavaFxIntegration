/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author ZARAMI
 */
import java.sql.Date;

public class DON {
    private int id;
    private String montant;
    private String numero_mobile;
    private String address;
    private String code_postal;
    private String nom;
    private String prenom;
    private String email;

    public DON() {
    }
     public DON( String montant, String numero_mobile, String address, String code_postal, String nom, String prenom, String email) {
        
        this.montant = montant;
        this.numero_mobile = numero_mobile;
        this.address = address;
        this.code_postal = code_postal;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }

    public DON(int id, String montant, String numero_mobile, String address, String code_postal, String nom, String prenom, String email) {
        this.id = id;
        this.montant = montant;
        this.numero_mobile = numero_mobile;
        this.address = address;
        this.code_postal = code_postal;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getMontant() {
        return montant;
    }

    public String getNumero_mobile() {
        return numero_mobile;
    }

    public String getAddress() {
        return address;
    }

    public String getCode_postal() {
        return code_postal;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public void setNumero_mobile(String numero_mobile) {
        this.numero_mobile = numero_mobile;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCode_postal(String code_postal) {
        this.code_postal = code_postal;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Donation{" +
                "id=" + id +
                ", montant=" + montant +
                ", numero_mobile='" + numero_mobile + '\'' +
                ", address='" + address + '\'' +
                ", code_postal='" + code_postal + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

