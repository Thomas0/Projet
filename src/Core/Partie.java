package Core;

import java.util.ArrayList;
import java.util.Collections;
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
		
		for(int i = 0; i < nombre;i++) {
			
			joueur.add(new Joueur(i,true));
			
		}

	}
	
	public void ajouterJoueurVirtuel(int nombre) {
		
		Integer size = joueur.size();
		
		for(int i = joueur.size(); i < nombre + size;i++) {
			
			joueur.add(new Joueur(i,false));
		}
		
	}
	
	public Integer joueurSuivant(Integer r) {
	
	if(this.sens == true){
		
		if ( r == joueur.size()-1) {
			
			r = 0;
			return r;
			
		} else {
			r++;
			return r;
		}

	}else {
		
	if ( r == 0) {
			
			r = joueur.size()-1;
			return r;
			
		} else {
			r--;
			return r;
		}
		
	}
}
	
	public void demarrer() {
		
		ajouterJoueurReel(1);
		ajouterJoueurVirtuel(1);
		
		Carte c;
		Joueur j = new Joueur();		
		this.estCommencer = true;
		Integer u = 0;
		
		pio = Pioche.getInstance();
		talon = Talon.getInstance();
		
		pio.generationPioche();
		
		
		Integer r = j.distribuer(joueur);
		
		// on initialise le talon
		c = pio.piocher();
		talon.poserCarte(c);
		
	    // debut tour	
		while(this.estFini == false) {
		
	    	j = joueur.get(r);
			j.jouer();
			u = j.getSizeCarteEnMain();
			if (u == 0) {
				this.estFini = true;
			} else{
			r = joueurSuivant(r);
			}
		}
		
		System.out.println("la Partie est terminée. Le joueur " + r + " a gagné");
		
		
	}
	

}
