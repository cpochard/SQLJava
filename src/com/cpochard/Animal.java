package com.cpochard;

public class Animal {
	
	private String nom;
	private String espece;
	private int anneeNaissance;
	
	public Animal(String nom, String espece, int anneeNaissance) {
		this.nom = nom;
		this.espece = espece;
		this.anneeNaissance = anneeNaissance;
	}

	public String toString() {
		return "Animal [nom=" + nom + ", espece=" + espece + ", anneeNaissance=" + anneeNaissance + "]";
	}

	public String getNom() {
		return nom;
	}

	public String getEspece() {
		return espece;
	}

	public int getAnneeNaissance() {
		return anneeNaissance;
	}
	
	
	
}
