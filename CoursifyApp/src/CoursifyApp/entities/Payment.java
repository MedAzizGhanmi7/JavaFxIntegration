/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.entities;

import java.sql.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Date;

/**
 *
 * @author hassa
 */public class Payment {
    private int idPayment;
    private String cardNumber;
    private Date expirationDate;
    private String eightCard;

    public Payment(String cardNumber, Date expirationDate, String eightCard) {


        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.eightCard = eightCard;
    }


    



    public int getIdPayment() {
        return idPayment;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public java.sql.Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getEightCard() {
        return eightCard;
    }

    public void setEightCard(String eightCard) {
        this.eightCard = eightCard;
    }



}