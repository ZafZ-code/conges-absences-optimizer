package model;
import java.time.*;
import java.util.*;
public class RessourceHumaine extends Utilisateur {

    private ArrayList<Employe> employes = new ArrayList<>();
    private ArrayList<Equipe> equipes = new ArrayList<>();
    private ArrayList<JourFerie> joursferies = new ArrayList<>();
    // Constructeur avec id
    public RessourceHumaine(int id, String nom, String prenom, String email, String motDePasseHash){
        super(id, nom, prenom, email, motDePasseHash);
    }
    // Constructeur sans id (id = 0)
    public RessourceHumaine(String nom, String prenom, String email, String motDePasseHash){
        super(nom, prenom, email, motDePasseHash);
    }

    //Definition de la fonction abstraite
    public Role getRole(){
        return Role.RESSOUCE_HUMAINE;
    }

    public void ajouterEquipe(Equipe equipe) {
        equipes.add(equipe);
    }
    // Une methode de RH ou d'autre classe
    public void modifierEquipe(Equipe equipe, String nom, int effectifMinimal) {
        equipe.setNom(nom);
        equipe.setEffectifMinimal(effectifMinimal);
    }

    public void supprimerEquipe(int equipeId) {
        for(Equipe q : equipes)
            if(!q.getId() == equipeId){
                
                throw new IllegalArgumentException("Équipe introuvable");
            }
            else{
                equipes.remove(q);
            }
            

        
    }

    // ---- gererJoursFeries ----
    public void ajouterJourFerie(JourFerie jour) {
        for (JourFerie j : joursferies) {
            if (j.getDate().equals(jour.getDate())) {
                throw new IllegalArgumentException("Ce jour férié existe déjà");
            }
        }
        joursferies.add(jour);
    }

    public void modifierJourFerie(JourFerie jour, LocalDate date, String libelle) {
        jour.setDate(date);
        jour.setLibelle(libelle);
    }

    public void supprimerJourFerie( int jourId) {
        if (!joursferies.removeIf(j -> j.getId() == jourId)) {
            throw new IllegalArgumentException("Jour férié introuvable");
        }
    }

    // ---- validerDemandeManager (un manager ne valide pas sa propre demande) ----
    public void validerDemandeManager(DemandeAbsence d) {
        
        d.setStatut(StatutDemande.APPROUVEE);
    }

    public void refuserDemandeManager(DemandeAbsence d) {
        d.setStatut(StatutDemande.REFUSEE);
    }


    // ---- consulterStatistiques (version de départ) ----
    
    
}
