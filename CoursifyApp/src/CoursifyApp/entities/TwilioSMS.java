/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoursifyApp.entities;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
/**
 *
 * @author hassa
 */
public class TwilioSMS {
    
    private static String ACCOUNT_SID;
    private static String AUTH_TOKEN;
    private static String SENDER_PHONE_NUMBER; // Your Twilio phone number

    public static void initialize(String accountSid, String authToken, String senderPhoneNumber) {
        ACCOUNT_SID = accountSid;
        AUTH_TOKEN = authToken;
        SENDER_PHONE_NUMBER = senderPhoneNumber;
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public static void sendCustomMessage(String recipientPhoneNumber, String messageText) {
        if (ACCOUNT_SID == null || AUTH_TOKEN == null || SENDER_PHONE_NUMBER == null) {
            System.err.println("Twilio not initialized. Call initialize() method first.");
            return;
        }

        Message message;
        message = Message.creator(
                new com.twilio.type.PhoneNumber("+" + recipientPhoneNumber),
                new com.twilio.type.PhoneNumber(SENDER_PHONE_NUMBER),
                messageText
        ).create();

        System.out.println("SMS sent: " + message.getSid());
    }
}