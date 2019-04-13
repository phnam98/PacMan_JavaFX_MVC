package PacMan;

import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import PacMan.Constants;
import PacMan.Grille;
import PacMan.Main;
import PacMan.Pacman;
import PacMan.Position;

public class Pacman extends Observable implements Runnable {
	
	//La position du Pac-Man
	static Position position;
	
	//Le point du joueur
	static int point;
	
	//La construction de cette classe
	public Pacman() {
		position= new Position(1,1);
		this.point=0;
	}
	
	//Pac-Man monte
	public boolean bougerAHaut() {
		if(Grille.getCase(position.colonne-1, position.ligne).getType() == Constants.Mur) 
			return false;
		else {
			Grille.getCase(position.colonne, position.ligne).setType(Constants.VIDE);
			this.position.colonne-=1;
			if (Grille.getCase(position.colonne, position.ligne).getType() == Constants.FanTome || Grille.getCase(position.colonne, position.ligne).getType() == Constants.FanTome2){
				Main.showAlert();
			}
			else {
				if (Grille.getCase(position.colonne, position.ligne).getType() == Constants.PacGomme) {
					this.point++;
					Main.playEatMusic();
				}
				else {
					if (Grille.getCase(position.colonne, position.ligne).getType() == Constants.Super_PacGomme) {
						this.point+=10;
						Main.playFruitMusic();
					}
				}
			}
			Grille.getCase(position.colonne, position.ligne).setType(Constants.PacMan);
			if(this.point == 163)
				Main.showWinAlert();
		}
		return true;
	}

	//Pac-Man descend
	public boolean bougerABas() {
		if(Grille.getCase(position.colonne+1, position.ligne).getType() == Constants.Mur) 
			return false;
		else {
			Grille.getCase(position.colonne, position.ligne).setType(Constants.VIDE);
			this.position.colonne+=1;
			if (Grille.getCase(position.colonne, position.ligne).getType() == Constants.FanTome || Grille.getCase(position.colonne, position.ligne).getType() == Constants.FanTome2){
				Main.showAlert();
			}
			else {
				if (Grille.getCase(position.colonne, position.ligne).getType() == Constants.PacGomme) {
					this.point++;
					Main.playEatMusic();
				}
				else {
					if (Grille.getCase(position.colonne, position.ligne).getType() == Constants.Super_PacGomme) {
						this.point+=10;
						Main.playFruitMusic();
					}
				}
			}
			Grille.getCase(position.colonne, position.ligne).setType(Constants.PacMan);
			if(this.point == 163)
				Main.showWinAlert();
		}
    	return true;
	}

	//Pac-Man va à gauche
	public boolean bougerAGauche() {
		if(Grille.getCase(position.colonne, position.ligne-1).getType() == Constants.Mur) 
			return false;
		else {
			Grille.getCase(position.colonne, position.ligne).setType(Constants.VIDE);
			this.position.ligne-=1;
			if (Grille.getCase(position.colonne, position.ligne).getType() == Constants.FanTome || Grille.getCase(position.colonne, position.ligne).getType() == Constants.FanTome2){
				Main.showAlert();
			}
			else {
				if (Grille.getCase(position.colonne, position.ligne).getType() == Constants.PacGomme) {
					this.point++;
					Main.playEatMusic();
				}
				else {
					if (Grille.getCase(position.colonne, position.ligne).getType() == Constants.Super_PacGomme) {
						this.point+=10;
						Main.playFruitMusic();
					}
				}
			}
			Grille.getCase(position.colonne, position.ligne).setType(Constants.PacMan);
			if(this.point == 163)
				Main.showWinAlert();
		}
		return true;
	}

	//Pac-Man va à droite
	public boolean bougerADroite() {
		if(Grille.getCase(position.colonne, position.ligne+1).getType() == Constants.Mur) 
			return false;
		else {
			Grille.getCase(position.colonne, position.ligne).setType(Constants.VIDE);
			this.position.ligne+=1;
			if (Grille.getCase(position.colonne, position.ligne).getType() == Constants.FanTome || Grille.getCase(position.colonne, position.ligne).getType() == Constants.FanTome2){
				Main.showAlert();
			}
			else {
				if (Grille.getCase(position.colonne, position.ligne).getType() == Constants.PacGomme) {
					this.point++;
					Main.playEatMusic();
				}
				else {
					if (Grille.getCase(position.colonne, position.ligne).getType() == Constants.Super_PacGomme) {
						this.point+=10;
						Main.playFruitMusic();
					}
				}
				Grille.getCase(position.colonne, position.ligne).setType(Constants.PacMan);
				if(this.point == 163)
					Main.showWinAlert();
			}
		}
		return false;
	}
	
	//Cette fonction est de lancer les threads
	public void start() {
		new Thread(this).start();
    }
	
	//La fonction d'exécution du Pac-Man
	@Override
	public void run() {
		while(true) {
			setChanged(); 
			notifyObservers();
			try {
				Thread.sleep(100);
			} catch (InterruptedException ex) {
				Logger.getLogger(Pacman.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}
