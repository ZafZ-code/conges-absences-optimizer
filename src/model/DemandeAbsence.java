import java.time.LocalDate;
import java.time.DayOfWeek; 
import java.util.Set;


public class DemandeAbsence {
    private int id ;
    private LocalDate dateDebut;
    private LocalDate dateFin ;
    private StatutDemande statut;
    private TypeAbsence type ;
    private int nombreJours;
    private double scorePriorite  ;
    private LocalDate dateCreation ;

    
    
    public DemandeAbsence() {
    }
    
    public DemandeAbsence(int id, LocalDate dateDebut, LocalDate dateFin) {
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }
    public int getId() {
        return id;
    }
    public LocalDate getDateDebut() {
        return dateDebut;
    }
    public LocalDate getDateFin() {
        return dateFin;
    }
    public int getNombreJours() {
        return nombreJours;
    }
    public double getScorePriorite() {
        return scorePriorite;
    }
    public LocalDate getDateCreation() {
        return dateCreation;
    }
    

    public StatutDemande getStatut() {
        return statut;
    }

    public TypeAbsence getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }
    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }
    public void setNombreJours(int nombreJours) {
        this.nombreJours = nombreJours;
    }
    public void setScorePriorite(double scorePriorite) {
        this.scorePriorite = scorePriorite;
    }
    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public int calculerDuree (Set<LocalDate> joursFeries)
    {
        int compteur =0 ;
        LocalDate jour= dateDebut ;

        if (dateFin.isBefore(dateDebut))
        {
            throw new IllegalArgumentException("La date de fin est avant la date de début");
        }

        while (!jour.isAfter(dateFin)) // Tant que jour n'est pas strictement après dateFin (dateFin incluse)
        {
            DayOfWeek j =jour.getDayOfWeek();
            boolean weekend = (j ==DayOfWeek.SUNDAY || j ==DayOfWeek.SATURDAY );

            if (!weekend && !joursFeries.contains(jour))
            {
                compteur++;
            }
            jour=jour.plusDays(1);// Passage au jour suivant

        }

        this.nombreJours=compteur ;
        return compteur;

    }

  

}

