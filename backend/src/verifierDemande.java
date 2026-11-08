import java.time.LocalDate;

public class verifierDemande {
    public boolean verifierSolde(DemandeAbsence d) {
        int solde = d.getEmplyer().soldeConge;
        if (solde - d.nbJours >= 0)
            return true;
        return false;
    }

    public boolean verifierDelai(DemandeAbscence d) {
        return d.nbJours >= 7;
    }

    public boolean verifierChevauchement(DemandeAbscence d) {
        LocalDate dateDebut = d.dateDebut;
        LocalDate dateFin = d.dateFin;
        LocalDate planDebut = d.getPlannification().dateDebut;
        LocalDate planFin = d.getPlannification().dateFin;
        return !dateDebut.isAfter(planDebut) && !dateFin.isBefore(planFin);
    }
}