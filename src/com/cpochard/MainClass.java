package com.cpochard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainClass {

	public static void main(String[] args) {
		//Vérification fonctionnalité classe Animal
		Animal kiki = new Animal("kiki", "lapin", 2009);
		System.out.println(kiki.toString());
		
		//Afficher tous les animaux de la BDD Animal
		DAOAnimal daoa = new DAOAnimal();
		daoa.afficherTousLesAnimaux ();
		
		//Ajouter un animal dans la BDD
		System.out.println(daoa.addAnimal(kiki));
		
		//Retourner un animal depuis son nom
		Animal a = daoa.getByNom("Moustache");
		System.out.println(a);
		
		//Retourner une liste d'animaux depuis leur espèce
		List<Animal> list = new ArrayList <Animal> ();
		list = daoa.getByEspece("lapin");
		System.out.println(list);
		
		//Utiliser un requeteur de modif
		daoa.requeteurModif("INSERT INTO Animal VALUES ('Coco', 'poulet', 2015)");
		
		//Utiliser un requeteur de lecture
		ResultSet r = daoa.requeteurLecture("SELECT nom FROM Animal");
		try {
			while (r.next()) {
			System.out.println(r.getString("nom"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Utiliser une fonction pour gérer les transactions
		List<String> l = new ArrayList<String>();
		l.add("INSERT INTO Animal VALUES ('Dede', 'herisson', 2015)");
		l.add("INSERT INTO Animal VALUES ('Froufrou', 'merle', 2014)");
		System.out.println(daoa.executeRequestsInTransaction(l));
	}

}
