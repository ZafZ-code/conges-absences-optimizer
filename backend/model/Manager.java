package model;

import java.util.ArrayList;
import java.util.Comparator;

public class Manager {
	private Equipe equipe;

	public Manager(Equipe equipe) {
		this.equipe = equipe;
	}

	public Equipe getEquipe() {
		return equipe;
	}

	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}

	public ArrayList<DemandeAbsence> consulterDemandeEquipe() {
		ArrayList<DemandeAbsence> L = new ArrayList<>();
		for (Employe e : equipe.getListEmp()) {
			L.addAll(e.consulterDemandes());
		}
		return L;
	}

	public void approuverDemande(DemandeAbsence d) {
		if (d.getStatut() == StatutDemande.EN_ATTENTE
				&& equipe.verifierEffectifMinimal(d.getDateDebut())) {
			d.setStatut(StatutDemande.APPROUVEE);
		}
	}

	public void refuserDemande(DemandeAbsence d) {
		if (d.getStatut() == StatutDemande.EN_ATTENTE) {
			d.setStatut(StatutDemande.REFUSEE);
		}
	}

	// Traite les demandes en attente par ordre de priorité décroissante
	public void lancerOptimisation() {
		ArrayList<DemandeAbsence> enAttente = new ArrayList<>();
		for (DemandeAbsence d : consulterDemandeEquipe()) {
			if (d.getStatut() == StatutDemande.EN_ATTENTE) {
				enAttente.add(d);
			}
		}
		enAttente.sort(Comparator.comparingDouble(DemandeAbsence::getScorePriorite).reversed());

		for (DemandeAbsence d : enAttente) {
			if (equipe.verifierEffectifMinimal(d.getDateDebut())) {
				d.setStatut(StatutDemande.APPROUVEE);
			} else {
				d.setStatut(StatutDemande.REFUSEE);
			}
		}
	}

	// Approuve malgré le non-respect de l'effectif minimal (décision explicite du manager)
	public void confirmerDepassementEff(DemandeAbsence d) {
		if (d.getStatut() == StatutDemande.EN_ATTENTE
				&& !equipe.verifierEffectifMinimal(d.getDateDebut())) {
			d.setStatut(StatutDemande.APPROUVEE);
		}
	}
}