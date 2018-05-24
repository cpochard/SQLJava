package com.cpochard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOAnimal {

	String url=null;
	String utilisateur = null;
	String motDePasse = null;
	Connection connexion = null;

	public void initialisation () {
			//Chargement du driver JDBC pour MySQL
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (Exception e) {
				System.out.println("pb driver");
				e.printStackTrace();
			}
			//l'url contient : jdbc:mysql://host:port/baseDeDonn�es
			url = "jdbc:mysql://localhost:3306/test";
			//On pr�pare les identifiants utilisateurs
			utilisateur = "root";
			motDePasse = "root";
			try {
				//Cr�ation d'un objet connexion
				connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		public void cloture () {
			try {
				//Fermeture de la connexion
				connexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		public void afficherTousLesAnimaux () {
			initialisation();
			try {
				//Cr�ation de l'objet g�rant les requ�tes
				Statement statement = connexion.createStatement();
				//Ex�cution d'une requ�te de lecture, retourne les donn�es sous la forme d'un ResultSet
				ResultSet resultat = statement.executeQuery("SELECT * FROM Animal");
				//R�cup�ration des donn�es r�sultant de la requ�te lecture
				while (resultat.next()) {
					//Mise en variables
					String nom = resultat.getString("nom");
					String espece = resultat.getString("espece");
					int anneeNaissance = resultat.getInt("anneeNaissance");
					//Utilisation
					System.out.println(nom + " "+ espece + " n� en "+ anneeNaissance);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			cloture ();
		}

		public int addAnimal (Animal a) {
			initialisation();
			Statement statement;
			int statut = 0;
			try {
				//Cr�ation de l'objet g�rant les requ�tes
				statement = connexion.createStatement();
				//Pr�paration de la requ�te
				String requete = "INSERT INTO Animal (nom, espece, anneeNaissance)";
				requete += " VALUES ('"+a.getNom()+"', '"+a.getEspece()+"', "+a.getAnneeNaissance()+")";
				//Ex�cution de la requ�te
				statut = statement.executeUpdate(requete);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			cloture ();
			return statut;
		}

		public Animal getByNom (String nom) {
			initialisation();
			Statement statement;
			Animal a = null;
			try {
				statement = connexion.createStatement();
				ResultSet resultat = statement.executeQuery("SELECT * FROM Animal WHERE nom = '"+nom+"'");
				while (resultat.next()) {
				a = new Animal(resultat.getString("nom"), resultat.getString("espece"), resultat.getInt("anneeNaissance"));
			}
			}
				catch (SQLException e) {
				e.printStackTrace();
			}
			cloture ();
			return a;
		}


		public List<Animal> getByEspece (String espece){
			initialisation();
			Statement statement;
			List<Animal> list = new ArrayList <Animal> ();
			Animal a = null;
			try {
				statement = connexion.createStatement();
				ResultSet resultat = statement.executeQuery("SELECT * FROM Animal WHERE espece = '"+espece+"'");
				while (resultat.next()) {
					a = new Animal (resultat.getString("nom"), resultat.getString("espece"), resultat.getInt("anneeNaissance"));
					list.add(a);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			cloture();
		return list;
		}
		
		public void requeteurModif (String req) {
			initialisation ();
			Statement statement;
			String requete = null;
			try {
				statement = connexion.createStatement();
				requete = req;
				int statut = statement.executeUpdate(requete);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			cloture ();
		}
		
		public ResultSet requeteurLecture (String req) {
			initialisation();
			Statement statement;
			ResultSet resultat = null;
			String s = null;
			try {
				statement = connexion.createStatement();
				resultat = statement.executeQuery(req);
			}catch (SQLException e) {
				e.printStackTrace();
			}
			return resultat;
		}
		
		public int executeRequestsInTransaction (List<String>l) {
			initialisation();
			Statement statement=null;
			int statut = 0;
			int sommeStatut = 0;
			try {
				statement = connexion.createStatement();
				statut = statement.executeUpdate("START TRANSACTION");
				for (String s:l) {
					statut = statement.executeUpdate(s);
					sommeStatut ++;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				statut = statement.executeUpdate("COMMIT");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			cloture();
			return sommeStatut;
			
		}
	}

