package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Planification {

    private int id;
    private Date dateDebut;
    private Date dateFin;
    private double valeurTotale;
    private Date dateGeneration;


    private List<DemandeAbsence> demandes;


    public Planification() {
        this.demandes = new ArrayList<>();
    }

    public Planification(int id, Date dateDebut, Date dateFin, Date dateGeneration) {
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.dateGeneration = dateGeneration;
        this.demandes = new ArrayList<>();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public double getValeurTotale() {
        return valeurTotale;
    }

    public void setValeurTotale(double valeurTotale) {
        this.valeurTotale = valeurTotale;
    }

    public Date getDateGeneration() {
        return dateGeneration;
    }

    public void setDateGeneration(Date dateGeneration) {
        this.dateGeneration = dateGeneration;
    }

    public List<DemandeAbsence> getDemandes() {
        return demandes;
    }

    public void setDemandes(List<DemandeAbsence> demandes) {
        this.demandes = demandes;
    }

  

    public double calculerValeurTotale() {
        double total = 0;
        for (DemandeAbsence demande : obtenirDemandesAcceptees()) {
            total += demande.getScorePriorite() * demande.getNombreJours();
        }
        this.valeurTotale = total;
        return total;
    }


    public List<DemandeAbsence> obtenirDemandesAcceptees() {
        List<DemandeAbsence> acceptees = new ArrayList<>();
        for (DemandeAbsence demande : demandes) {
            if (demande.getStatut() == StatutDemande.APPROUVEE) {
                acceptees.add(demande);
            }
        }
        return acceptees;
    }


    public List<DemandeAbsence> obtenirDemandesRefusees() {
        List<DemandeAbsence> refusees = new ArrayList<>();
        for (DemandeAbsence demande : demandes) {
            if (demande.getStatut() == StatutDemande.REFUSEE) {
                refusees.add(demande);
            }
        }
        return refusees;
    }


    public void validerPlanification() {
        if (demandes == null || demandes.isEmpty()) {
            throw new IllegalStateException("Impossible de valider une planification sans demandes.");
        }
        if (dateDebut == null || dateFin == null) {
            throw new IllegalStateException("Les dates de la planification sont obligatoires.");
        }
        if (dateDebut.after(dateFin)) {
            throw new IllegalArgumentException("La date de début doit être avant la date de fin.");
        }
        this.dateGeneration = new Date(); 
        this.valeurTotale = calculerValeurTotale();
    }


    public void ajouterDemande(DemandeAbsence demande) {
        if (demande != null && !demandes.contains(demande)) {
            demandes.add(demande);
        }
    }

    @Override
    public String toString() {
        return "Planification{id=" + id
                + ", dateDebut=" + dateDebut
                + ", dateFin=" + dateFin
                + ", valeurTotale=" + valeurTotale
                + ", nbDemandes=" + (demandes != null ? demandes.size() : 0)
                + "}";
    }
}
