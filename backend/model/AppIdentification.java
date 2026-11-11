
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
public class AppIdentification extends Authenticator {
    private String adresse;
    private String motDePasse;
    public AppIdentification(String adresse,String motDePasse){
        this.adresse=adresse;
        this.motDePasse=motDePasse;   
    }
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(adresse,motDePasse);
    }
    
}
