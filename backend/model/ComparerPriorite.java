package model;

import java.util.Comparator;

public class ComparerPriorite implements Comparator<DemandeAbscence> {

    @Override
    public int compare(DemandeAbscence d1, DemandeAbscence d2) {
        int resultat = Double.compare(d2.getScorePriorite(), d1.getScorePriorite());
        // Si deux demandes possèdes la même valeur de priorité, TreeMap va ajouté seuelement
        // une seule demande dans la méthode trierDemandes
        // donc on compare les identifiants des demandes pour éviter ce problème
        if (resultat == 0) {
            return Integer.compare(d1.getId(), d2.getId());
        }
        return resultat;
    }
}
