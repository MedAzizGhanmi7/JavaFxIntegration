/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.entities;

import java.sql.Date;


import java.sql.Connection;
import java.util.List;
import javafx.scene.control.TextField;

/**
 *
 * @author PC
 */
public class Message {
    private int id;
    private String ObjetMessage;
    private String ContenuMessage;
    private Date DateEnvoi;
    private String EmailDestinataire;
    private String EmailEmetteur;


/*Constructor*/
    
        public Message(String ObjetMessage, String ContenuMessage, String EmailDestinataire, String EmailEmetteur) {
        this.ObjetMessage = ObjetMessage;
        this.ContenuMessage = ContenuMessage;
        this.EmailDestinataire = EmailDestinataire;
        this.EmailEmetteur = EmailEmetteur;
    }


    public Message(int id, String ObjetMessage, String ContenuMessage, Date DateEnvoi, String EmailDestinataire, String EmailEmetteur) {
        this.id = id;
        this.ObjetMessage = ObjetMessage;
        this.ContenuMessage = ContenuMessage;
        this.DateEnvoi = DateEnvoi;
        this.EmailDestinataire = EmailDestinataire;
        this.EmailEmetteur = EmailEmetteur;
        
    // Constructeur par défaut sans arguments
}

    public Message(String ObjetMessage, String ContenuMessage) {
        this.ObjetMessage = ObjetMessage;
        this.ContenuMessage = ContenuMessage;
    }
    

    public Message() {
    }
    
/*Getter*/ 
    
    public int getId() {
        return id;
    }

    public String getObjetMessage() {
        return ObjetMessage;
    }
    
   
    public String getContenuMessage() {
        return ContenuMessage;
    }

    public Date getDateEnvoi() {
        return DateEnvoi;
    }

    public String getEmailDestinataire() {
        return EmailDestinataire;
    }

    public String getEmailEmetteur() {
        return EmailEmetteur;
    }
    
    



/*Setter*/    
    
    public void setId(int id) {
        this.id = id;
    }

    public void setObjetMessage(String ObjetMessage) {
        this.ObjetMessage = ObjetMessage;
    }
   

    public void setContenuMessage(String ContenuMessage) {
        this.ContenuMessage = ContenuMessage;
    }

    public void setDateEnvoi(Date DateEnvoi) {
        this.DateEnvoi = DateEnvoi;
    }

    public void setEmailDestinataire(String EmailDestinataire) {
        this.EmailDestinataire = EmailDestinataire;
    }

    public void setEmailEmetteur(String EmailEmetteur) {
        this.EmailEmetteur = EmailEmetteur;
    }






    

/*hashCode*/    
    @Override
    public int hashCode() {
        return super.hashCode(); //To change body of generated methods, choose Tools | Templates.
    }

    public void setidMessage(TextField idMessage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setId(TextField Id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
    


