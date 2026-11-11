package model;
import java.time.*;
import java.util.*;

public class Employe extends Utilisateur {
    private String matricule;
    private int anciennete;
    private int soldeConge;
    private ArrayList<DemandeAbsence> demandes = new ArrayList<>();
    // Constructeur pour l'employe avec id 
    public Employe(int id, String nom, String prenom, String email, String motDePasseHash,
         String matricule, int anciennete, int soldeConge ){
        super(id, nom, prenom, email, motDePasseHash);
        this.matricule = matricule;
        this.anciennete = anciennete;
        this.soldeConge = soldeConge;
    }

    public Employe(String nom, String prenom, String email, String motDePasseHash,
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
    public ArrayList<DemandeAbsence> consulterDemandes(){
        return demandes;
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

    public void deposerDemande(DemandeAbsence demande, Justificatif justif) {
        // 1. dates valides
        if (demande.getDateDebut() == null || demande.getDateFin() == null || demande.getDateFin().isBefore(demande.getDateDebut())) {
            throw new IllegalArgumentException("Dates invalides");
        }
        // 2. justificatif obligatoire (maladie, naissance, décès)
        TypeAbsence type = demande.getType();
        boolean exigeJustificatif = (type == TypeAbsence.MALADIE || type == TypeAbsence.CONGE_PAYE 
            || type == TypeAbsence.CONGE_SANS_SOLDE || type == TypeAbsence.NAISSANCE || type == TypeAbsence.DECES);
        if (exigeJustificatif && ( justif.getReference() == null || justif.getReference().isBlank())) {
            throw new IllegalArgumentException("Justificatif manquant");
        }
        // 3. délai de prévenance de 7 jours (congé payé uniquement)
        if (! verifierDelai(demande)) {
            throw new IllegalArgumentException("Délai de prévenance de 7 jours non respecté");
        }
        // 4. chevauchement avec une autre demande active de cet employé
        // Verifier l'algorithme de chevauchement
        /* 
        for (DemandeAbsence d : demandes) {
            boolean active = d.getStatut() == StatutDemande.EN_ATTENTE
                          || d.getStatut() == StatutDemande.APPROUVEE;
            boolean chevauche = !(fin.isBefore(d.getDateDebut()) || debut.isAfter(d.getDateFin()));
            if (active && chevauche) {
                throw new IllegalArgumentException("Chevauchement avec une autre demande");
            }
        }
            */


        // 5. solde suffisant (congé payé uniquement). Le solde n'est PAS déduit ici.
        // Verifier les attributs dans verifierSolde()
        if (!verifierSolde()) {
            throw new IllegalArgumentException(
                "Solde insuffisant : " + soldeConge + " disponible(s), " + demande.getNombreJours() + " demandé(s)");
        }
        demandes.add(demande);
    }

    public void annulerDemande(int idDemande){
        for(DemandeAbsence d : demandes){
            if(d.getId()==idDemande){
                //Fonction annuler dans la classe DemandeAbsence
                d.annuler();
            }
        }
    }

}
