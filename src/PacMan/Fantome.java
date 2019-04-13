package PacMan;

import java.util.Observable;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import PacMan.Constants;
import PacMan.Pacman;
import PacMan.Main;

public class Fantome extends Observable implements Runnable {
	//La position du Fant�me
	Position position;
	
	//Si cette variable est true, cela signifie que le dernier cas est un Pac-Gomme
	//Sinon il est vide
	private boolean precedent = true;
	
	//Si cette variable est false, le jeu continue � cr�er du Thread -> cela peut causer le probl�me quand on termine le jeu
	//Sinon, la cr�ation du Thread sera stopp�e
	private boolean gameEnd = false;
	
	//Cette variable est de g�rer les fant�mes, c'est le num�ro du fant�me
	//Dans ce cas, numero = 1 est le fant�me rouge (d�faut) et numero != 1 est le fant�me bleu (niv = 2 seulement)
	private int numero;
	
	//La construction de cette classe
	public Fantome(int ligne, int colonne, int numero) {
		position= new Position(colonne,ligne);
		this.numero = numero;
	}
	
	//Cette fonction aide le fant�me � se d�placer automatiquement
	public void FantomeMarche() {
		Random rand = new Random();
	    int randomNum = rand.nextInt((3 - 0) + 1) + 0;
	    if (Pacman.position.colonne > this.position.colonne){
	    	 if (Pacman.position.ligne > this.position.ligne) {
	    		 randomNum = rand.nextInt((1 - 0) + 1) + 0;		 
	    		 if (randomNum == 0)
	    			bougerABas();
	    		 else
	    			bougerADroite();	
	 	    }
	    	 if (Pacman.position.ligne <= this.position.ligne){
	    		 randomNum = rand.nextInt((1 - 0) + 1) + 0;
	    		 if (randomNum == 0)
	    			 bougerABas();
	    		 else
	    			 bougerAGauche();	
	 	    }
	    	else if (Pacman.position.colonne <= this.position.colonne) {
	    	 if (Pacman.position.ligne >= this.position.ligne){
	    		 randomNum = rand.nextInt((1 - 0) + 1) + 0;
	    		 if (randomNum == 0)
	    			 bougerAHaut();
	    		 else
	    			 bougerADroite();
	 	    }
	    	 if (Pacman.position.ligne < this.position.ligne){
	    		 randomNum = rand.nextInt((1 - 0) + 1) + 0;
	    		 if (randomNum == 0)
	    			 bougerAHaut();
	    		 else
	    			 bougerAGauche();
	 	    }
	    	}
	    }
	}
	
	//le fant�me monte
	public boolean bougerAHaut() {
		//System.out.println("Go Up");
		if(Grille.getCase(position.colonne-1, position.ligne).getType() == Constants.Mur) 
			return false;
		else {
			if(!precedent)
				Grille.getCase(position.colonne, position.ligne).setType(Constants.VIDE);
			else
				Grille.getCase(position.colonne, position.ligne).setType(Constants.PacGomme);
			if(Grille.getCase(position.colonne-1, position.ligne).getType() == Constants.VIDE)
				precedent = false;
			else
				precedent = true;
			this.position.colonne-=1;
			if (Grille.getCase(position.colonne, position.ligne).getType() == Constants.PacMan){
				gameEnd=true;
				Main.showAlert();
			}
			else
				if(numero == 1)
					Grille.getCase(position.colonne, position.ligne).setType(Constants.FanTome);
				else
					Grille.getCase(position.colonne, position.ligne).setType(Constants.FanTome2);
		}
		return true;
	}

	//le fant�me descend
	public boolean bougerABas() {
		if(Grille.getCase(position.colonne+1, position.ligne).getType() == Constants.Mur) 
			return false;
		else {
			if(!precedent)
				Grille.getCase(position.colonne, position.ligne).setType(Constants.VIDE);
			else
				Grille.getCase(position.colonne, position.ligne).setType(Constants.PacGomme);
			if(Grille.getCase(position.colonne+1, position.ligne).getType() == Constants.VIDE)
				precedent = false;
			else
				precedent = true;
			this.position.colonne+=1;
			if (Grille.getCase(position.colonne, position.ligne).getType() == Constants.PacMan){
				gameEnd=true;
				Main.showAlert();   
			}
			else
				if(numero == 1)
					Grille.getCase(position.colonne, position.ligne).setType(Constants.FanTome);
				else
					Grille.getCase(position.colonne, position.ligne).setType(Constants.FanTome2);
		}
    	return true;
	}

	//le fant�me va � gauche
	public boolean bougerAGauche() {
		//System.out.println("Turn Left");
		if(Grille.getCase(position.colonne, position.ligne-1).getType() == Constants.Mur) 
			return false;
		else {
			if(!precedent)
				Grille.getCase(position.colonne, position.ligne).setType(Constants.VIDE);
			else
				Grille.getCase(position.colonne, position.ligne).setType(Constants.PacGomme);
			if(Grille.getCase(position.colonne, position.ligne-1).getType() == Constants.VIDE)
				precedent = false;
			else
				precedent = true;
			this.position.ligne-=1;
			if (Grille.getCase(position.colonne, position.ligne).getType() == Constants.PacMan){
				gameEnd=true;
				Main.showAlert(); 
			}
			else 
				if(numero == 1)
					Grille.getCase(position.colonne, position.ligne).setType(Constants.FanTome);
				else
					Grille.getCase(position.colonne, position.ligne).setType(Constants.FanTome2);
		}
		return false;
	}

	//Le fant�me va � droite
	public boolean bougerADroite() {
		//System.out.println("Turn right");
		if(Grille.getCase(position.colonne, position.ligne+1).getType() == Constants.Mur) 
			return false;
		else {
			if(!precedent)
				Grille.getCase(position.colonne, position.ligne).setType(Constants.VIDE);
			else
				Grille.getCase(position.colonne, position.ligne).setType(Constants.PacGomme);
			if(Grille.getCase(position.colonne, position.ligne+1).getType() == Constants.VIDE)
				precedent = false;
			else
				precedent = true;
			this.position.ligne+=1;
			if (Grille.getCase(position.colonne, position.ligne).getType() == Constants.PacMan) {
				gameEnd=true;
				Main.showAlert(); 
			}
			else 
				if(numero == 1)
					Grille.getCase(position.colonne, position.ligne).setType(Constants.FanTome);
				else
					Grille.getCase(position.colonne, position.ligne).setType(Constants.FanTome2);
		}
		return false;
	}
	
	//Cette fonction est de lancer les threads
	public void start() {
        new Thread(this).start();
    }
	
	//La fonction d'ex�cution du fant�me
	@Override
	public void run() {
		while(!gameEnd) {
			if(Main.type == 1)
				FantomeMarche();
			setChanged(); 
			notifyObservers(); // notification de l'observer
			try {
				Thread.sleep(200); // pause
			} catch (InterruptedException ex) {
				Logger.getLogger(Pacman.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}
