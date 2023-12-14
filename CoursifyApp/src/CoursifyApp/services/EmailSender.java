public class EmailSender {
    private final String host;
    private final String username;
    private final String password;

    public EmailSender(String host, String username, String password) {
        this.host = host;
        this.username = username;
        this.password = password;
    }

    public void sendEmail(String recipient, String subject, String content) {
        // Mettez en œuvre l'envoi d'e-mails en utilisant les informations fournies
        // Vous pouvez utiliser JavaMail ou une autre bibliothèque pour envoyer des e-mails
        // Assurez-vous d'implémenter la logique d'envoi d'e-mails en fonction des détails spécifiés
        // Ici, j'utilise une implémentation factice pour simuler l'envoi d'e-mails
        System.out.println("Envoi d'un e-mail à : " + recipient);
        System.out.println("Sujet : " + subject);
        System.out.println("Contenu : " + content);
        System.out.println("E-mail envoyé avec succès.");
    }
}
