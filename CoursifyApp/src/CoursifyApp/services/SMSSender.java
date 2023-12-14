public class SMSSender {
    private final String apiUrl;
    private final String apiKey;

    public SMSSender(String apiUrl, String apiKey) {
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
    }

    public void sendSMS(String phoneNumber, String content) {
        // Mettez en œuvre l'envoi de SMS en utilisant les informations fournies
        // Vous pouvez utiliser une API SMS tierce ou un fournisseur de services pour envoyer des SMS
        // Assurez-vous d'implémenter la logique d'envoi de SMS en fonction des détails spécifiés
        // Ici, j'utilise une implémentation factice pour simuler l'envoi de SMS
        System.out.println("Envoi d'un SMS au numéro : " + phoneNumber);
        System.out.println("Contenu : " + content);
        System.out.println("SMS envoyé avec succès.");
    }
}
