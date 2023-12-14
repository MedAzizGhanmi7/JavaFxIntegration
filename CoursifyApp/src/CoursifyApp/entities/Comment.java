/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.entities;

import java.sql.Date;


/**
 *
 * @author PC
 */
public class Comment {
    private int id;
    private String ContenuCommentaire;
    private Date Date;
    private String EmailCommentateur;
    
    
    
   
/*Constructor*/

    public Comment(int id, String ContenuCommentaire, Date Date, String EmailCommentateur) {
        this.id = id;
        
        this.ContenuCommentaire = ContenuCommentaire;
        this.Date = Date;
        this.EmailCommentateur = EmailCommentateur;
    }

    public Comment(String ContenuCommentaire, String EmailCommentateur) {
        
        this.ContenuCommentaire = ContenuCommentaire;
        this.EmailCommentateur = EmailCommentateur;
    }

    public Comment(String ContenuCommentaire, Date Date, String EmailCommentateur) {
        
        this.ContenuCommentaire = ContenuCommentaire;
        this.Date = Date;
        this.EmailCommentateur = EmailCommentateur;
    }


    


    public Comment() {
    // Constructeur par defaut sans arguments
}


    
/*Getter*/ 

    public int getId() {
        return id;
    }



    public String getContenuCommentaire() {
        return ContenuCommentaire;
    }

    public Date getDate() {
        return Date;
    }

    public String getEmailCommentateur() {
        return EmailCommentateur;
    }
    

/*Setter*/    

    public void setId(int id) {
        this.id = id;
    }


    public void setContenuCommentaire(String ContenuCommentaire) {
        this.ContenuCommentaire = ContenuCommentaire;
    }

    public void setDate(Date Date) {
        this.Date = Date;
    }

    public void setEmailCommentateur(String EmailCommentateur) {
        this.EmailCommentateur = EmailCommentateur;
    }


    @Override
    public int hashCode() {
        return super.hashCode(); //To change body of generated methods, choose Tools | Templates.
    }

}
    



