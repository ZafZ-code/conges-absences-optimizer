package model;

public class Employe extends Utilisateur {
    private String matricule;
    private int anciennete;
    private int soldeConge;
    // Constructeur pour l'employe avec id 
    protected Employe(int id, String nom, String prenom, String email, String motDePasseHash,
         String matricule, int anciennete, int soldeConge ){
        super(id, nom, prenom, email, motDePasseHash);
        this.matricule = matricule;
        this.anciennete = anciennete;
        this.soldeConge = soldeConge;
    }

    protected Employe(String nom, String prenom, String email, String motDePasseHash,
         String matricule, int anciennete, int soldeConge ){
        super(nom, prenom, email, motDePasseHash);
        setMatricule(matricule);
        setAnciennete(anciennete);
        setSoldeConge(soldeConge);
    }
    // Définir la méthode abstraite dans Untilisateur
    public Role getRole(){
        return Role.EMPLOYE;
    }

    //Les Getters et Setters
    
    // Les Getters 
    public String getMatricule(){
        return matricule;
    }
    public int getAnciennte(){
        return anciennete;
    }
    public int getSoldeConge(){
        return soldeConge;
    }

    // Les Setters
    public final void setMatricule(String matricule){
        if(matricule==null || matricule.isBlank()){
            throw new IllegalArgumentException("Matricule invalide");
        }
        this.matricule = matricule;
    }

    public final void setAnciennete(int anciennete){
        if(anciennete<0){
            throw new IllegalArgumentException("Anciennete invalide valeur entrée est negative");
        }
        this.anciennete = anciennete;
    }

    public final void setSoldeConge(int soldeConge){
        if(soldeConge<0){
            throw new IllegalArgumentException("Solde de Congé invalide valuer entrée est negative");
        }
        this.soldeConge = soldeConge;
    }
}
