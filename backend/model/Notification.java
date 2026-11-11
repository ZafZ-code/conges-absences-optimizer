import java.util.Date;
import java.util.Properties;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.Message;
import jakarta.mail.Transport;

public class Notification {
    private int id;
	private String message;
	private Date dateEnvoi;
	private NotificationType type;
	private boolean lu=false;
	private Utilisateur destinataire;
	
	public Notification(int id,String message,Date dateEnvoi,NotificationType type,Utilisateur destinataire) {
		this.id=id;
		this.message=message;
		this.dateEnvoi=dateEnvoi;
		this.type=type;
		this.destinataire=destinataire;
	}
	
	public void envoyerEmail() throws EnvoiEmailException() {
		String adresseServeur =System.getenv("AdresseServeur");
		String adresseApplication =System.getenv("AdresseApp");
		String motDePasse=System.getenv("MotDePasse");
		String numPort=System.getenv("Port");
		//vérification de la complétidue de la configuration
		if(adresseServeur==null || adresseApplication==null || motDePasse==null || numPort==null){
			throw new EnvoiEmailException("La configuration du serveur est manquante!");
		}

		Properties parametres = new Properties();
		parametres.setProperty("mail.smtp.host", adresseServeur);
		parametres.setProperty("mail.smtp.port", numPort);
		parametres.setProperty("mail.smtp.auth","true");
		parametres.setProperty("mail.smtp.ssl.checkserveridentity","true");
		if(numPort.equals("465"))
			parametres.setProperty("mail.smtp.ssl.enable", "true");
		else{
			parametres.setProperty("mail.smtp.starttls.enable", "true");
			parametres.setProperty("mail.smtp.starttls.required", "true");
		} 
		parametres.setProperty("mail.smtp.connectiontimeout", "10000"); //10s
		parametres.setProperty("mail.smtp.timeout", "10000");
		parametres.setProperty("mail.smtp.writetimeout", "10000");
		Session session=Session.getInstance(parametres, new AppIdentification(adresseApplication,motDePasse));
		try {
			MimeMessage email = new MimeMessage(session);
            email.setFrom(new InternetAddress(adresseApplication));
			email.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire.getEmail()));
			email.setSubject("Notification :"+type , "UTF-8");
            email.setText(message, "UTF-8");
            email.setSentDate(new Date());
			Transport.send(email);
			this.dateEnvoi = new Date();

		} catch (Exception e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		
	}
	public void marquerCommeLue() {
        this.lu=true;
	}
	public int getId(){
		return id;
	}
	public String getMessage(){
		return message;
	}
	public Date getDateEnvoi(){
		return dateEnvoi;
	}
	public NotificationType getType(){
		return type;
	}
	public boolean isLu(){
		return lu;
	}
	public Utilisateur getDestinataire(){
		return destinataire;
	} 
}
