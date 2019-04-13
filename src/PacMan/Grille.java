package PacMan;

import PacMan.Case;

public class Grille {
	
	//Une grille est une matrice 2x2
	public static Case[][] grille;
	
	//La construction de la classe
	public Grille() {
		grille = new Case[Constants.CASE_LIGNE_MAX][Constants.CASE_COLONNE_MAX];
	}
	
	//Retourner le case de grille (ligne, colonne)
	public static Case getCase(int ligne, int colonne) {
		return grille[ligne][colonne];
	}
	
	//Ajouter une nouvelle case
	public void ajouterCase(Case c) {
		grille[c.position.ligne][c.position.colonne] = c;
	}
}
