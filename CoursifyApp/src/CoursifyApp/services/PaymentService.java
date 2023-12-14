/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.services;

/**
 *
 * @author hassa
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import CoursifyApp.entities.Abonnement;
import CoursifyApp.entities.Payment;
import CoursifyApp.utilities.Myconnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author hassa
 */
public class PaymentService implements CrudInterface<Payment> {
    private Connection cnx;

    public PaymentService() {
        cnx = Myconnection.getInstance().getCnx();
    }

    
    
    
    private java.sql.Date convertUtilDateToSqlDate(Date utilDate) {
    return new java.sql.Date(utilDate.getTime());
}

@Override
public void create(Payment payment) {
    if (
            !isValidCardNumber(payment.getCardNumber()) ||
            !isValidEightCard(payment.getEightCard()) ||
            !isValidDate(payment.getExpirationDate())) {
        System.err.println("Invalid input data.");
        return;
    }

    String query = "INSERT INTO payment (cardNumber, expirationDate, eightCard) VALUES (?, ?, ?)";
    try (PreparedStatement pst = cnx.prepareStatement(query)) {
        pst.setString(1, payment.getCardNumber());
        pst.setDate(2, payment.getExpirationDate());
        pst.setString(3, payment.getEightCard());

        pst.executeUpdate();
        System.out.println("Payment added successfully");
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
}

@Override
public void update(Payment payment) {
    String query = "UPDATE payment SET cardNumber=?, expirationDate=?, eightCard=? WHERE idPayment=?";
    try (PreparedStatement pst = cnx.prepareStatement(query)) {
        pst.setString(2, payment.getCardNumber());
        pst.setDate(3, payment.getExpirationDate());
        pst.setString(4, payment.getEightCard());
        pst.setInt(1, payment.getIdPayment());

        int rowsUpdated = pst.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Payment updated successfully");
        } else {
            System.out.println("No payment was updated. Please check the payment ID.");
        }
    } catch (SQLException ex) {
        System.err.println("Error updating payment: " + ex.getMessage());
    }
}





   
    @Override
    public void delete(int paymentId) {
        String query = "DELETE FROM payment WHERE idPayment=?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, paymentId);
            pst.executeUpdate();
            System.out.println("Payment deleted successfully");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public Payment getById(int paymentId) {
        String query = "SELECT * FROM payment WHERE idPayment=?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, paymentId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Payment payment;
                    payment = new Payment(


                            rs.getString("cardNumber"),
                            rs.getDate("expirationDate"),
                            rs.getString("eightCard")
                    );
                    return payment;
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
    
    
    

    
    
  @Override
public List<Payment> getAll() {
    List<Payment> payments = new ArrayList<>();
    String query = "SELECT * FROM payment";
    try (PreparedStatement pst = cnx.prepareStatement(query);
         ResultSet rs = pst.executeQuery()) {
        while (rs.next()) {
            Payment payment = new Payment(

                rs.getString("cardNumber"),
                rs.getDate("expirationDate"),
                rs.getString("eightCard")
            );
            payments.add(payment);
        }
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
    return payments;
}





    
   

private boolean isValidCardNumber(String cardNumber) {
    return Pattern.matches("^\\d{16}$", cardNumber);
}

private boolean isValidEightCard(String eightCard) {
    return Pattern.matches("^\\d{8}$", eightCard);
}

private boolean isValidDate(Date date) {
    // Vérification de la date
    return date != null; // Insérez votre logique de validation de date ici
}

private boolean isValidString(String text) {
    return text != null && !text.trim().isEmpty();
}


}