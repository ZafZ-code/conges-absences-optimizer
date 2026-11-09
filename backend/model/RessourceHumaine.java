package model;

public class RessourceHumaine extends Utilisateur {

    // Constructeur avec id
    protected RessourceHumaine(int id, String nom, String prenom, String email, String motDePasseHash){
        super(id, nom, prenom, email, motDePasseHash);
    }
    // Constructeur sans id (id = 0)
    protected RessourceHumaine(String nom, String prenom, String email, String motDePasseHash){
        super(nom, prenom, email, motDePasseHash);
    }

    //Definition de la fonction abstraite
    public Role getRole(){
        return Role.RESSOUCE_HUMAINE;
    }
}
