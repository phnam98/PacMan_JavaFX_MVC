package PacMan;

public abstract class Entite {
	Position position;
	public Entite(int ligne, int colonne) {
		position= new Position(ligne,colonne);
	}
	public abstract boolean bougerAHaut();
	public abstract boolean bougerABas();
	public abstract boolean bougerAGauche();
	public abstract boolean bougerADroite();
}
