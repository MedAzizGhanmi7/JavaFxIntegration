package CoursifyApp.entities;

import java.util.Date;

public class Abonnement {
    private int idAbonnement;
    private String user;
    private String cours;
    private float price;
    private String typeAbonnement;
    private Date dateDebut;
    private Date dateExpiration;

    public Abonnement( String user, String cours, String typeAbonnement, float price, Date dateDebut, Date dateExpiration) {
    
    this.user = user;
    this.cours = cours;
    this.typeAbonnement = typeAbonnement;
    this.price = price;
    this.dateDebut = dateDebut;
    this.dateExpiration = dateExpiration;
}




//    public Abonnement(int idAbonnement, String nomUser, String selectedCourse, float price, String selectedAbonnementType, String dateDebut, String dateExpiration, String pending) {
//    }
//
//    public Abonnement(int idAbonnement, String user, String cours, float price, String typeAbonnement, java.sql.Date dateDebut, java.sql.Date dateExpiration) {
//    }
//
////    public Abonnement(int aInt, String string, String string0, float aFloat, String string1, java.sql.Date date, java.sql.Date date0) {
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
////    }
//
//    public Abonnement(String john_doe, String mathematics, float f, String monthly, java.sql.Date date, java.sql.Date date0) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    public Abonnement(String john_doe, String mathematics, String monthly, float f) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }


    public int getIdAbonnement() {
        return idAbonnement;
    }

    public String getUser() {
        return user;
    }

    public String getCours() {
        return cours;
    }

    public float getPrice() {
        return price;
    }

    public String getTypeAbonnement() {
        return typeAbonnement;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public Date getDateExpiration() {
        return dateExpiration;
    }



    public void setUser(String user) {
        this.user = user;
    }

    public void setCours(String cours) {
        this.cours = cours;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setTypeAbonnement(String typeAbonnement) {
        this.typeAbonnement = typeAbonnement;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateExpiration(Date dateExpiration) {
        this.dateExpiration = dateExpiration;
    }


}