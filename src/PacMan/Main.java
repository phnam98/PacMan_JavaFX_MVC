package PacMan;

import javafx.scene.image.Image;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.Scanner;

import javax.swing.JOptionPane;

import java.lang.String;

import PacMan.Constants;
import PacMan.Pacman;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.scene.layout.BorderPane;

public class Main extends Application {
	
	//Les variables
	public static Grille grille;
	private static ArrayList<Position> mur = new ArrayList<>();
	private static ArrayList<Position> superPacGomme = new ArrayList<>();
	public int direction = 0;
	public static boolean gameEnd = false;
	public static int type;
	public static int niv;
	public static boolean win = false;
	private String meilleurScore="";
	
	//lire le fichier "meilleurScore.dat"
	public static String getMeilleurScore() {
		FileReader readFile = null;
		BufferedReader reader = null;
		try {
			readFile = new FileReader("meilleurscore.dat");
			reader = new BufferedReader(readFile);
			return reader.readLine();
		}
		catch (Exception e) {
			return "Nul:0";
		}
		finally {
			try {
				if(reader != null)
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//Comparer le point de joueur et le meilleur score de "meilleurScore.dat"
	public void checkScore(int point) {
		System.out.println(meilleurScore);
		if (point > Integer.parseInt((meilleurScore.split(": ")[1]))) {
			String name = JOptionPane.showInputDialog("Vous avez fait un nouveau meilleur score, tapez votre nom: ");
			meilleurScore = name + ": " + point;
			
			File scoreFile = new File("meilleurscore.dat");
			if(!scoreFile.exists()) {
				try {
					scoreFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			FileWriter writeFile = null;
			BufferedWriter writer = null;
			try {
				writeFile = new FileWriter(scoreFile);
				writer = new BufferedWriter(writeFile);
				writer.write(meilleurScore);
			}
			catch (Exception e) {}
			finally {
				try {
					if(writer != null)
					writer.close();
				} catch (IOException e) {}
			}
		}
	}
	
	//initialisation de mur
	private static void initMur(){
		mur.add(new Position(2, 2));
		mur.add(new Position(1, 4));
		mur.add(new Position(2, 4));
		mur.add(new Position(3, 4));
		mur.add(new Position(4, 4));

		mur.add(new Position(4, 2));
		mur.add(new Position(5, 2));
		mur.add(new Position(6, 2));

		mur.add(new Position(6, 3));

		mur.add(new Position(13, 4));
		mur.add(new Position(12, 4));
		mur.add(new Position(11, 4));
		mur.add(new Position(10, 4));
		
		mur.add(new Position(12, 2));
		mur.add(new Position(8, 2));
		mur.add(new Position(9, 2));
		mur.add(new Position(11, 2));

		mur.add(new Position(3, 6));
		
		int loopSize = mur.size();
		for (int i =0;i< loopSize;i++){
			
			Position tmpPosition = mur.get(i);
			Position newPosition = new Position(tmpPosition.ligne, Constants.CASE_COLONNE_MAX-1-tmpPosition.colonne);
			mur.add(newPosition);
		}
		
		mur.add(new Position(6, 6));
		mur.add(new Position(7, 6));
		mur.add(new Position(8, 6));
		mur.add(new Position(8, 7));
		mur.add(new Position(8, 8));

		mur.add(new Position(7, 8));
		mur.add(new Position(6, 8));

		mur.add(new Position(10, 7));
		mur.add(new Position(11, 7));
		mur.add(new Position(12, 7));

		mur.add(new Position(2, 7));
		mur.add(new Position(3, 7));
		mur.add(new Position(4, 7));
	}
	
	//initialisation de Super Pac-Gomme
	private static void initSuperPacGomme(){
		superPacGomme.add(new Position(7,7));
		
		superPacGomme.add(new Position(3,3));
		superPacGomme.add(new Position(11,3));
		superPacGomme.add(new Position(3,11));
		superPacGomme.add(new Position(11,11));
		
	}
	
	//vérifier si c'est un mur ou pas
	private static boolean estMur(Position position){
		
		for (int i =0;i< mur.size();i++){
			Position tmpPosition = mur.get(i);			
			if (position.ligne == tmpPosition.ligne && position.colonne == tmpPosition.colonne)
				return true;
		}
		return false;
	}
	
	//vérifier si c'est un super pac-gomme ou pas
	private static boolean isSuperPacGomme(Position position) {
		for (int i =0;i< superPacGomme.size();i++){
			Position tmpPosition = superPacGomme.get(i);			
			if (position.ligne == tmpPosition.ligne && position.colonne == tmpPosition.colonne)
				return true;
		}
		return false;
	}
	
	//Lire le fichier de musique "eatball.wav" et le jouer quand on l'appelle
	public static void playEatMusic()
	{
		MediaPlayer mediaplayermove;
	    Media soundMove = new Media("file:///D:/Eclipse-espace/PacManJavaFXProjet/src/eatball.wav");
	    mediaplayermove = new MediaPlayer(soundMove);
	    mediaplayermove.play();
	}
	
	//Lire le fichier de musique "eatfruit.wav" et le jouer quand on l'appelle
	public static void playFruitMusic()
	{
		MediaPlayer mediaplayerfruit;
	    Media soundFruit = new Media("file:///D:/Eclipse-espace/PacManJavaFXProjet/src/eatfruit.wav");
	    mediaplayerfruit = new MediaPlayer(soundFruit);
	    mediaplayerfruit.play();
	}
	
	//Lire le fichier de musique "pacdies.mp3" et le jouer quand on l'appelle
	public static void playDeadMusic()
	{
		MediaPlayer mediaplayerdie;
	    Media soundDie = new Media("file:///D:/Eclipse-espace/PacManJavaFXProjet/src/pacdies.mp3");
	    mediaplayerdie = new MediaPlayer(soundDie);
	    mediaplayerdie.play();
	}
	
	//Lire le fichier de musique "win.wav" et le jouer quand on l'appelle
	public static void playWinMusic()
	{
		MediaPlayer mediaplayerwin;
	    Media soundWin = new Media("file:///D:/Eclipse-espace/PacManJavaFXProjet/src/win.wav");
	    mediaplayerwin = new MediaPlayer(soundWin);
	    mediaplayerwin.play();
	}

	//Alerter le joueur quand le jeu se termine - il a perdu
	public static void showAlert(){
		win = false;
		playDeadMusic();
		for (int i =0;i< Constants.CASE_COLONNE_MAX;i++)
            for (int j =0;j< Constants.CASE_LIGNE_MAX;j++) {
            	Grille.getCase(i, j).setType(Constants.FanTome);
            }
		gameEnd = true;
	}
	
	//Alerter le joueur quand le jeu se termine - il a gagné
	public static void showWinAlert(){
		win = true;
		playWinMusic();
		for (int i =0;i< Constants.CASE_COLONNE_MAX;i++)
            for (int j =0;j< Constants.CASE_LIGNE_MAX;j++) {
            	Grille.getCase(i, j).setType(Constants.PacMan);
            }
		gameEnd = true;
	}
	
	//Fonction de démarrage
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//Création des variables
		BorderPane root = new BorderPane();
		Pacman pacman = new Pacman();
		Fantome fantome = new Fantome(4,3,1);
		Fantome fantome2 = new Fantome(8,3,2);
		Scene scene = new Scene(root, Constants.SIZE_X_MAX, Constants.SIZE_Y_MAX, Color.BLACK);
		grille = new Grille();
		//Création Mur + Super Pac-Gomme
		initMur();
		initSuperPacGomme();
		
		do {
			System.out.println("1: 1 Joueur, 2: 2 Joueurs");
			Scanner input1 = new Scanner(System.in);
			type = input1.nextInt();
		}while(type != 1 && type != 2); 
		if(type == 1) {
			do {
				System.out.println("1: niveau 1, 2: niveau 2");
				Scanner input2 = new Scanner(System.in);
				niv = input2.nextInt();
				input2.close();
			}while(niv != 1 && niv != 2);
			
		}
		if(type == 2)
			System.out.println("Début de partie avec 2 joueurs !!!");
		else
			System.out.println("Début de partie avec 1 joueur - niveau " + niv);
		System.out.println("Bonne Chance");
		
		
		// Création de la grille
		GridPane grid = new GridPane(); 

		// Préparation des images
        Image imPM = new Image("pacman.png"); 
        Image imVide = new Image("vide.jpg");
        Image imFantome = new Image("fantome.png");
        Image imFantome2 = new Image("fantome2.png");
        Image imSuperPacGomme = new Image("super-pac-gomme.png");
        Image imMur = new Image("mur.jpg");
        Image imPacGomme = new Image("pac-gomme.png");
        Image imPMR = new Image("pacman-rotate.png");
        Image imPMR1 = new Image("pacman-rotate-1.png");
        Image imPMR2 = new Image("pacman-rotate-2.png");
        
        //Initialisation de la grille (sans image)
		ImageView[][] tab = new ImageView[Constants.CASE_COLONNE_MAX][Constants.CASE_LIGNE_MAX];
        for (int i = 0; i < Constants.CASE_COLONNE_MAX; i++) { 
            for (int j = 0; j < Constants.CASE_LIGNE_MAX; j++) {
                ImageView img = new ImageView();
                tab[i][j] = img;
                grid.add(img, i, j);
            }   
        }
        
        //Initialisation de la grille - type de chaque case
        for (int i =0;i< Constants.CASE_COLONNE_MAX;i++)
            for (int j =0;j< Constants.CASE_LIGNE_MAX;j++){
            	Position position = new Position(i,j);
            	int type = Constants.Mur;
            	if ( i != Constants.CASE_COLONNE_MAX-1 && j != Constants.CASE_LIGNE_MAX-1 && i != 0 && j!= 0){
            		if (i == pacman.position.colonne & j == pacman.position.ligne)
            			type = Constants.PacMan;
            		else if (i == fantome.position.colonne && j == fantome.position.ligne)
            			type = Constants.FanTome;
            		else if (i == fantome2.position.colonne && j == fantome2.position.ligne && niv == 2)
            			type = Constants.FanTome2;
            		else if (estMur(position))
            			type = Constants.Mur;
            		else if (isSuperPacGomme(position))
            			type = Constants.Super_PacGomme;
            		else 
            			type = Constants.PacGomme;
            	}
            	Case c= new Case(position,type);
            	grille.ajouterCase(c);
            }
        
        //Lire le fichier de musique "Pacman.mp3" et le jouer au démarrage du jeu 
        MediaPlayer mediaplayer;
        Media sound = new Media("file:///D:/Eclipse-espace/PacManJavaFXProjet/src/Pacman.mp3");
        mediaplayer = new MediaPlayer(sound);
        mediaplayer.setAutoPlay(true);
        MediaView mediaView = new MediaView(mediaplayer);
        root.getChildren().add(mediaView);
        
        //Démarrer l'observateur, la fonction est appelée à chaque notification
		Observer o =  new Observer() {
			@Override
            public void update(Observable o, Object arg) {
				for(int i = 0; i < Constants.CASE_COLONNE_MAX; i++)
					for(int j =0; j < Constants.CASE_LIGNE_MAX; j++) {
						//Dessiner les murs
						if (Grille.getCase(j, i).getType() == Constants.Mur) {
							tab[i][j].setImage(imMur);
						}
						//Dessiner Pac-Man et faire attention à la direction du mouvement
						else if (Grille.getCase(j, i).getType() == Constants.PacMan) {
								if(direction == 0)
									tab[i][j].setImage(imPM);
								else if(direction == 1)
									tab[i][j].setImage(imPMR);
								else if(direction == 2)
									tab[i][j].setImage(imPMR1);
								else 
									tab[i][j].setImage(imPMR2);	
						}
						//Dessiner fantôme
						else if (Grille.getCase(j, i).getType() == Constants.FanTome)
							tab[i][j].setImage(imFantome);
						//Dessiner fantôme2 (niveau 2)
						else if (Grille.getCase(j, i).getType() == Constants.FanTome2)
							tab[i][j].setImage(imFantome2);
						//Dessiner Pac-Gomme
						else if (Grille.getCase(j, i).getType() == Constants.PacGomme)
							tab[i][j].setImage(imPacGomme);
						//Dessiner Super Pac-Gomme
						else if (Grille.getCase(j, i).getType() == Constants.Super_PacGomme)
							tab[i][j].setImage(imSuperPacGomme);
						else
							//Si rien, c'est vide
							tab[i][j].setImage(imVide);
					}
				}
		};
		
		//Démarrer Pacman et les fantômes
		pacman.addObserver(o);
		fantome.addObserver(o);
		pacman.start();
		fantome.start();
		if(niv == 2) {
			fantome2.addObserver(o);
			fantome2.start();
		}
		
		//Définir des composants supplémentaires
		primaryStage.setTitle("Le Jeu de PacMan - Projet JavaFX+MVC strict - PHAN Hoàng Nam");
		primaryStage.setScene(scene);
		primaryStage.show();
		root.getChildren().add(grid);
		
		//On écoute le clavier
		//Partie contrôleur
		root.setOnKeyPressed(new EventHandler<javafx.scene.input.KeyEvent>() { 
			@Override
            public void handle(javafx.scene.input.KeyEvent event) {
				if(!gameEnd) {
					if (event.getCode()==KeyCode.UP) {
						direction = 3;
						pacman.bougerAHaut();
					}
					if(event.getCode()==KeyCode.DOWN) {
						direction = 1;
						pacman.bougerABas();
					}
					if (event.getCode()==KeyCode.LEFT) {
						direction = 2;
						pacman.bougerAGauche();
					}
					if(event.getCode()==KeyCode.RIGHT) {
						direction = 0;
						pacman.bougerADroite();
					}
					if(type == 2) {
						if (event.getCode()==KeyCode.W) {
							fantome.bougerAHaut();
						}
						if(event.getCode()==KeyCode.S) {
							fantome.bougerABas();
						}
						if (event.getCode()==KeyCode.A) {
							fantome.bougerAGauche();
						}
						if(event.getCode()==KeyCode.D) {
							fantome.bougerADroite();
						}
					}
				}
				else
					if(event.getCode()==KeyCode.SPACE) {
						//Vérifier le meilleur score et terminez la partie
						if(win == false) {
							if(meilleurScore.equals("")) {
								meilleurScore = Main.getMeilleurScore();
							}
							checkScore(pacman.point);
							Alert alert = new Alert(Alert.AlertType.INFORMATION);
							alert.setTitle("Vous avez perdu");
							alert.setHeaderText("Votre Pacman a été mangé");
							alert.setContentText("Votre point est " + Pacman.point + "\nMeilleur score: " + meilleurScore);
					    
							Optional<ButtonType> result = alert.showAndWait();
							ButtonType button = result.orElse(ButtonType.CANCEL);
							if (button == ButtonType.OK) {
								System.out.println("Ok pressed");
								System.exit(0);
							} else {
					        System.out.println("canceled");
							}
						}
						else {
							if(meilleurScore.equals("")) {
								meilleurScore = Main.getMeilleurScore();
							}
							checkScore(pacman.point);
							Alert alert = new Alert(Alert.AlertType.INFORMATION);
							alert.setTitle("Vous êtes le vainqueur");
							alert.setHeaderText("Vous avez mangé tous les Pac-gommes");
							alert.setContentText("Votre point est " + Pacman.point + "\nMeilleur score: " + meilleurScore);
							
							Optional<ButtonType> result = alert.showAndWait();
							ButtonType button = result.orElse(ButtonType.CANCEL);
							if (button == ButtonType.OK) {	
								System.out.println("Ok pressed"); 
								System.exit(0);
							} else {
					        System.out.println("canceled");
							}
						}
					}
            }
        });
		//Permettre au contrôleur de fonctionner
		grid.requestFocus();
	}
	public static void main(String[] args) {
		launch(args);
	}
	
}
