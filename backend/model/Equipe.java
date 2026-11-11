package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Equipe {
	private int id;
	private String nom;
	private int effectifMinimal;
	private ArrayList<Employe> listEmp = new ArrayList<>();

	public Equipe(int id, String nom, int effectifMinimal) {
		this.id = id;
		this.nom = nom;
		this.effectifMinimal = effectifMinimal;
	}

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public String getNom() { return nom; }
	public void setNom(String nom) { this.nom = nom; }

	public int getEffectifMinimal() { return effectifMinimal; }
	public void setEffectifMinimal(int effectifMinimal) { this.effectifMinimal = effectifMinimal; }

	public ArrayList<Employe> getListEmp() { return listEmp; }

	public void ajouterEmploye(Employe e) {
		listEmp.add(e);
	}

	public void supprimerEmploye(Employe e) {
		listEmp.remove(e);
	}

	public int calculerEffectifPresent(LocalDate date) {
		int count = 0;
		for (Employe e : listEmp) {
			boolean absent = false;
			for (DemandeAbsence d : e.consulterDemandes()) {
				if (d.getStatut() == StatutDemande.APPROUVEE
						&& !date.isBefore(d.getDateDebut())
						&& !date.isAfter(d.getDateFin())) {
					absent = true;
					break;
				}
			}
			if (!absent) {
				count++;
			}
		}
		return count;
	}

	public boolean verifierEffectifMinimal(LocalDate date) {
		return calculerEffectifPresent(date) > effectifMinimal;
	}
}