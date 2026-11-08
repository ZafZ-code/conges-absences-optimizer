package model;


public abstract class Utilisateur {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasseHash;

    protected  Utilisateur(int id, String nom, String prenom, String email, String motDePasseHash){
        setId(id);
        setNom(nom);
        setPrenom(prenom);
        setEmail(email);
        setMotDePasseHash(motDePasseHash);
    }

    protected  Utilisateur(String nom, String prenom, String email, String motDePasseHash){
        this(0, nom, prenom, email, motDePasseHash);
    }
    // The Getters and Setters 

    //GETTERS
    public int getId(){
        return id;
    }

    public String getPrenom(){
        return prenom;
    }
    public String getNom(){
        return nom;
    }
    public String getEmail(){
        return email;
    }
    public String getMotDePasseHash(){
        return motDePasseHash;
    }
    //SETTERS
    public final void setId(int id_modif){
        this.id = id_modif;
    }
    public final void setNom(String Nom_modif) throws IllegalArgumentException{
        if( Nom_modif == null || Nom_modif.isBlank() ){
            throw new IllegalArgumentException("Nom invalide");
        }
        this.nom = Nom_modif;
    }
    public final void setPrenom(String Prenom_modif){
        if(Prenom_modif == null || Prenom_modif.isBlank()){
            throw new IllegalArgumentException("Prenom invalide");
        }
        this.prenom = Prenom_modif;
    }
    public final void setEmail(String Email_modif){
        if(Email_modif == null || !Email_modif.contains("@") ){
            throw new IllegalArgumentException("Email invalide");
        }
        this.email = Email_modif;

    }
    public final void setMotDePasseHash(String MDP_modif){
        if(MDP_modif == null || MDP_modif.isBlank()){
            throw new IllegalArgumentException("Pas de mot de passe a initialiser ou changer");
        }
        this.motDePasseHash = MDP_modif;
    }

    // La Méthode abstraite
    public abstract Role getRole();

    // Méthode modiferProfil permet à l'utilisateur de changer son nom son prenom et son email
    public void modifierProfil(String nom, String prenom, String email) {
    
        String ancienNom = this.nom;
        String ancienPrenom = this.prenom;
        String ancienEmail = this.email;

        try {
            setNom(nom);
            setPrenom(prenom);
            setEmail(email);
        } 
        catch (IllegalArgumentException e) {
            this.nom = ancienNom;
            this.prenom = ancienPrenom;
            this.email = ancienEmail;
            throw e;
        }

    }    

}
