package Core;

import java.util.ArrayList;
import java.util.Scanner;

public class Partie {

	private boolean estCommencer;
	private boolean estFini;
	private boolean sens;
	private int nbJoueur;
	
	private ArrayList<Joueur> joueur = new ArrayList<Joueur>();
	
	private Pioche pio;
	private Talon talon;
	private static Partie partie;
	private static Partie INSTANCE = null;
	
	private Partie() {
		
		this.estCommencer = false;
		this.estFini = false;
		this.sens = true;
		
	}
	
	public static Partie getInstance() {
		
		if(INSTANCE == null) {
			
			INSTANCE = new Partie();
			
		}
		return INSTANCE;
		
	}

	public static void main(String[] args) {
		partie = Partie.getInstance();
		partie.demarrer();

	}
	
	
	public void ajouterJoueurReel(int nombre) {
		
		for(int i = joueur.size(); i < nombre;i++) {
			
			joueur.add(new Joueur(i,true));
			
		}

	}
	
	public void ajouterJoueurVirtuel(int nombre) {
		
		for(int i = joueur.size(); i < nombre + joueur.size();i++) {
			
			joueur.add(new Joueur(i,false));
		}
		
	}
	
	public void joueurSuivant() {
		
	}
	
	public void demarrer() {
		
		Joueur j1 = new Joueur(1,true);
		Joueur j2 = new Joueur(2,true);	
		Carte c;
		
		this.estCommencer = true;
		
		pio = Pioche.getInstance();
		talon = Talon.getInstance();
		
		pio.generationPioche();
		
		// on distribue les cartes (juste pour tester sinon il faut créer une methode dans la classe Joueur)
		for (int i = 0; i<7;i++) {
			
			c = pio.piocher();
			j1.ajoutCarte(c);
			c = pio.piocher();
			j2.ajoutCarte(c);
		}
		
		
		
		System.out.println("j1 à " + j1.getSizeCarteEnMain() + " cartes");
		System.out.println("j2 à " + j2.getSizeCarteEnMain() + " cartes");
		System.out.println("Il reste " + pio.getSizeCarteEnMain() + " cartes dans la pioche");
		
		// on initialise le talon
		c = pio.piocher();
		talon.poserCarte(c);
		
	//debut tour	
	//		while(this.estFini == false) {
		
	    	j1.jouer();
			
			
	//	}
		
		
	}
	

}
