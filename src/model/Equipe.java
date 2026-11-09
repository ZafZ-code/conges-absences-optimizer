package model;
import java.util.ArrayList;
import java.time.LocalDate;
public class Equipe{
	private int id;
	private String nom;
	private int effectifMinimal;
	private ArrayList<Employe> listEmp=new ArrayList<>();
	
	public Equipe(int id, String nom, int effectifMinimal) {
		super();
		this.id = id;
		this.nom = nom;
		this.effectifMinimal = effectifMinimal;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getEffectifMinimal() {
		return effectifMinimal;
	}

	public void setEffectifMinimal(int effectifMinimal) {
		this.effectifMinimal = effectifMinimal;
	}

	public ArrayList<Employe> getListEmp() {
		return listEmp;
	}
	
	public void ajouterEmp(Employe e) {
		listEmp.add(e);
	}
	
	public void supprimerEmp(Employe e) {
		listEmp.remove(e);
	}
	
	public int calculerEffectifPresent(LocalDate date) {
		int count =0;
		for(Employe e:listEmp) {
			ArrayList<DemandeAbsence> demandes = e.consulterDemandes();
			for (DemandeAbsence d : demandes) {
				if(!(date.isAfter(d.getDateDebut() && date.isBefore(d.getDateFin() && d.getStatut()=="APPROUVEE")))) {
					count++;
				}
			}
			
		}
		return count ;	
	}
	
	public boolean verifierEffectifMinimal(LocalDate date) {
		return calculerEffectifPresent(date)>=effectifMinimal;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}