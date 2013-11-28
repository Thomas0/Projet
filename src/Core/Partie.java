package Core;

import java.util.ArrayList;


public class Partie {

	private boolean estFini;
	private boolean sens;
	private int joueursuivant;
	
	private ArrayList<Joueur> joueur = new ArrayList<Joueur>();
	
	private Pioche pio;
	private Talon talon;
	private static Partie partie;
	private static Partie INSTANCE = null;
	
	private Partie() {
		
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
			
			joueursuivant = 0;
			return joueursuivant;
			
		} else {
			joueursuivant++;
			return joueursuivant;
		}

	}else {
		
	if ( r == 0) {
			
		    joueursuivant = joueur.size()-1;
			return joueursuivant;
			
		} else {
			joueursuivant--;
			return joueursuivant;
		}
		
	}
}
	
	public void demarrer() {
		
		ajouterJoueurReel(1);
		ajouterJoueurVirtuel(2);
		
		Joueur j = new Joueur();		
		Integer u = 0;
		
		pio = Pioche.getInstance();
		talon = Talon.getInstance();
		
		pio.generationPioche();
		
		
		joueursuivant = j.distribuer(joueur);
		
		// on initialise le talon
		initTalon();
		
		
	    // debut tour	
		while(this.estFini == false) {
		
	    	j = joueur.get(joueursuivant);
			j.jouer();
			u = j.getSizeCarteEnMain();
			if (u == 0) {
				this.estFini = true;
			} else{
				joueursuivant = joueurSuivant(joueursuivant);
			}
		}

		System.out.println("la Partie est terminée. Le joueur " + joueursuivant + " a gagné");
		
		
	}
	
	public void initTalon(){
		Carte c;
		c = pio.piocher();
		talon.poserCarte(c);
	
	if (c.isEstSpecial() == true){	
		boolean test = false;
		while(test == false){
			if (c.getType() == "PlusQuatre"){
				c = pio.piocher();
				talon.poserCarte(c);
					if(c.isEstSpecial() == false){
						if ( joueursuivant == joueur.size()-1) {
							
							joueursuivant = 0;
							
						} else {
							joueursuivant++;
							
						}
						test = true;
					}
			}else {
				c.effet();
			if(sens == true){
				if ( joueursuivant == joueur.size()-1) {
					
					joueursuivant = 0;
					
				} else {
					joueursuivant++;
					
				}
			}else {
				if(joueursuivant == 0) {
					joueursuivant = joueur.size()-1;
				}else{
					joueursuivant--;
				}
			}
				test = true;
			}
		}
	}else {
		if(sens == true){
			if ( joueursuivant == joueur.size()-1) {
				
				joueursuivant = 0;
				
			} else {
				joueursuivant++;
				
			}
		}else {
			if(joueursuivant == 0) {
				joueursuivant = joueur.size()-1;
			}else{
				joueursuivant--;
			}
		}
		}
	
			
			
		}

			
		
	

	public boolean isSens() {
		return sens;
	}

	public void setSens(boolean sens) {
		this.sens = sens;
	}

	public Joueur getJoueursuivant() {
		if(joueursuivant == joueur.size()-1){
			return joueur.get(0);
		}else {
			return joueur.get(joueursuivant+1);
		}
	
	}
	
	public Joueur getJoueurActuel() {
		
			return joueur.get(joueursuivant);
	
	}

	public void setJoueursuivant(int joueursuivant) {
		this.joueursuivant = joueursuivant;
	}
	
	public int getNumJoueurSuivant(){
		if(joueursuivant == joueur.size()-1) {
			return 0;
		}else {
			return joueursuivant+1;
		}
		
	}
	
	public int getNumJoueurPrecedent(){
		
		if(joueursuivant == 0) {
			return joueur.size()-1;
		}else {
			return joueursuivant-1;
		}
	
	}

	public Joueur getJoueurPrecedent() {
		if(joueursuivant == 0) {
			return joueur.get(joueur.size()-1);
		}else{
			return joueur.get(joueursuivant-1);
		}

}
	
	

}
