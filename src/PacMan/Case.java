package PacMan;

import PacMan.Position;

public class Case {
	
	//La position de ce case
	public Position position;
	
	//Le type de ce case (Pac-Gomme, Super Pac-Gomme, Couloir, Fantôme ou PacMan)
	private int type;
	
	//La construction de la classe
	public Case(Position pos, int type) {
		this.position = pos;
		this.type = type;
	}
	
	//Modifier le type de ce case
	public void setType(int type) {
		this.type = type;
	}
	
	//Retourner le type de ce case (type -> privée)
	public int getType() {
		return this.type;
	}
	
}
