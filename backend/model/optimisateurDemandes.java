import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class optimisateurDemandes {
    // calculerPriorite, 3la ana ases puisque score bech yit7sib fil demande
    // abscense
    public Map<DemandeAbscence, Equipe> trierDemandes(Map<DemandeAbscence, Equipe> ld) {
        // Trier les demandes concurrentes par priorité décroissante
        ld.sort(Comparator.comparingDouble(DemandeAbscense::getScorePriorite).reversed());
        return ld;
    }

    public Planification genererPlanification(List<Demande> demandesEquipe) {
        // Ici comment faire pour les attributs d'une planification
        Planification planning = new Planification();

        // trierDemandes(demandes);

        // for (Demande d : demandes) {

        // if (verifierContraintes(d)) {
        // planning.ajouterDemandeAcceptee(d);
        // mettreAJourEffectif(d);
        // } else {
        // planning.ajouterDemandeRefusee(d);
        // }
        // }

        // return planning;
        // }
        // Effectif disponible temporaire pour chaque équipe
        Map<Equipe, Integer> effectifsDisponibles = new HashMap<>();

        // Initialiser les effectifs
        for (Equipe equipe : demandesEquipe.values()) {
            effectifsDisponibles.putIfAbsent(
                    equipe,
                    equipe.getEffectifPresent());
        }

        // Trier les demandes par priorité
        List<Map.Entry<DemandeAbsence, Equipe>> demandesTriees = trierDemandes(demandesEquipe);

        // Traiter les demandes dans l'ordre de priorité
        for (Map.Entry<DemandeAbsence, Equipe> entry : demandesTriees) {

            DemandeAbsence demande = entry.getKey();
            Equipe equipe = entry.getValue();

            int effectifDisponible = effectifsDisponibles.get(equipe);

            if (effectifDisponible - 1 >= equipe.getEffectifMinimal()) {

                // Accepter la demande
                effectifsDisponibles.put(equipe, effectifDisponible - 1);

                demande.setStatusDemande("Accepté");
                planning.ajouterDemande(demande);

            } else {

                // Refuser la demande
                demande.setStatusDemande("Refusé");
                planning.ajouterDemande(demande);
            }
        }

        // 2. Recherche locale
        ameliorerParRechercheLocale(planning);

        // 3. Retourner la planification améliorée
        return planning;
    }

    // public void ameliorerParRechercheLocale(Planification planning) {
    // /*
    // * Amélioration par recherche locale: échanger une demande accordée contre
    // * une ou plusieurs demandes refusées si la valeur totale augmente.
    // */
    // List<DemandeAbscence> d = planning.getDemandes();
    // }
    public void ameliorerParRechercheLocale(Planification planning, Map<DemandeAbsence, Equipe> demandesEquipe) {
        List<DemandeAbsence> acceptees = planning.obtenirDemandesAcceptees();
        List<DemandeAbsence> refusees = planning.obtenirDemandesRefusees();
        boolean amelioration;
        do {
            amelioration = false;
            // Examiner chaque demande acceptée
            for (DemandeAbsence acceptee : new ArrayList<>(acceptees)) {
                Equipe equipeAcceptee = demandesEquipe.get(acceptee);
                double scoreActuel = acceptee.getScorePriorite();
                // Examiner les demandes refusées
                for (DemandeAbsence refusee : new ArrayList<>(refusees)) {
                    Equipe equipeRefusee = demandesEquipe.get(refusee);
                    // Pour commencer, on échange seulement dans la même équipe
                    if (!equipeAcceptee.equals(equipeRefusee)) {
                        continue;
                    }
                    double nouveauScore = refusee.getScorePriorite();
                    // Si le score augmente
                    if (nouveauScore > scoreActuel) {
                        // Remplacer la demande acceptée
                        acceptees.remove(acceptee);
                        refusees.remove(refusee);
                        acceptees.add(refusee);
                        refusees.add(acceptee);
                        refusee.setStatusDemande("Accepté");
                        acceptee.setStatusDemande("Refusé");
                        amelioration = true;
                        break;
                    }
                }
                if (amelioration) {
                    break;
                }
            }
        } while (amelioration);
    }

    public Planification comparerAvecOrdreArrivee(Planification initiale, Map<DemandeAbsence, Equipe> demandesEquipe) {
        // Recuit simulé ou recherche tabou pour explorer d’autres combinaisons de
        // demandes accordées.
        // 2. La recherche tabou (Tabu Search)
        // La recherche tabou fonctionne différemment.
        // Paramètres de la recherche tabou
        int maxIterations = 100;
        int tailleTabou = 10;
        List<String> listeTabou = new ArrayList<>();
        Planification courante = initiale;
        Planification meilleure = initiale;
        double meilleurScore = meilleure.getValeurTotale();
        // Recherche tabou
        for (int i = 0; i < maxIterations; i++) {
            Planification meilleurVoisin = null;
            String meilleurMouvement = null;
            double meilleurScoreVoisin = Double.NEGATIVE_INFINITY;
            // Générer les voisins de la solution courante
            List<Planification> voisins = genererVoisins(courante, demandesEquipe);
            for (Planification voisin : voisins) {
                double score = voisin.calculerValeurTotale();
                String mouvement = identifierMouvement(courante, voisin);
                boolean tabou = listeTabou.contains(mouvement);
                // Critère d'aspiration
                if (tabou && score <= meilleurScore) {
                    continue;
                }
                // Choisir le meilleur voisin
                if (score > meilleurScoreVoisin) {
                    meilleurVoisin = voisin;
                    meilleurMouvement = mouvement;
                    meilleurScoreVoisin = score;
                }
            }
            // Aucun voisin admissible
            if (meilleurVoisin == null) {
                break;
            }
            // Effectuer le mouvement
            courante = meilleurVoisin;
            // Ajouter le mouvement dans la liste tabou
            listeTabou.addLast(meilleurMouvement);
            // Limiter la taille de la liste tabou
            if (listeTabou.size() > tailleTabou) {
                listeTabou.removeFirst();
            }
            // Actualiser la meilleure solution
            if (meilleurScoreVoisin > meilleurScore) {
                meilleure = courante;
                meilleurScore = meilleurScoreVoisin;
            }
        }
        return meilleure;
        }
        // Son principe est de conserver une liste tabou (Tabu List) qui mémorise
        // temporairement les mouvements déjà effectués pour éviter de revenir
        // constamment aux mêmes solutions.
        // Fonctionnement
        // 1. Commencer avec une planification initiale.
        // 2. Générer plusieurs solutions voisines.
        // 3. Choisir le meilleur mouvement possible.
        // 4. Ajouter le mouvement effectué dans la liste tabou.
        // 5. Interdire temporairement les mouvements inverses ou répétitifs.
        // 6. Continuer jusqu'à atteindre un nombre maximal d'itérations ou une limite
        // de stagnation.
    }
}
