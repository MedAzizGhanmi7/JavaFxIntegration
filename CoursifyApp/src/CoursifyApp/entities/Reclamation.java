/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.entities;

import java.sql.Date;
import java.sql.Time;


/**
 *
 * @author ZARAMI
 */
public class Reclamation {
     int id;
     String numero_mobile;
    Date date_traitement;
    String description,objet,status,nom,prenom,email;
    private String titre;

    public Reclamation() {
    }

    public Reclamation(String numero_mobile,  String description, String objet, String nom, String prenom, String email) {
        this.numero_mobile = numero_mobile;
    
        this.description = description;
        this.objet = objet;
   
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }
    
    public Reclamation(int id, String numero_mobile,  String description, String objet,  String nom, String prenom, String email) {
        this.id = id;
        this.numero_mobile = numero_mobile;
       
        this.description = description;
        this.objet = objet;
     
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getNumero_mobile() {
        return numero_mobile;
    }

  

    public String getDescription() {
        return description;
    }

    public String getObjet() {
        return objet;
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

    public void setNumero_mobile(String numero_mobile) {
        this.numero_mobile = numero_mobile;
    }

   

    public void setDescription(String description) {
        this.description = description;
    }

    public void setObjet(String objet) {
        this.objet = objet;
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
 public String getTitre() {
        return titre;
    }
    @Override
    public String toString() {
        return "reclamation{" + "id=" + id + ", numero_mobile=" + numero_mobile + ", description=" + description + ", objet=" + objet + ",  nom=" + nom + ", prenom=" + prenom + ", email=" + email + '}';
    }
    
}
