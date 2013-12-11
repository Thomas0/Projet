package Core;

import java.util.ArrayList;
import java.util.Scanner;


public class Partie {

	private boolean estFiniManche;
	private boolean estFiniPartie;
	private boolean sens;
	private int joueursuivant;
	
	private ArrayList<Joueur> joueur = new ArrayList<Joueur>();
	
	private Pioche pio;
	private Talon talon;
	private static Partie partie;
	private static Partie INSTANCE = null;
	
	private Partie() {
		
		this.estFiniManche = false;
		this.estFiniPartie = false;
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
	
	/**
	 * Methode permettant d'obtenir le numéro du joueur suivante
	 * @param r numéro du joueur actuel
	 * @return Retourne le numéro du joueur suivant
	 */
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
		
		pio = Pioche.getInstance();
		talon = Talon.getInstance();
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Nombre de joueur réel : ");
		int joueurr = Integer.parseInt(sc.nextLine());
		
		ajouterJoueurReel(joueurr);
	
		System.out.println("Nombre de joueur virtuel : ");
		int joueurv = Integer.parseInt(sc.nextLine());
		
		ajouterJoueurVirtuel(joueurv);
		
		Joueur j = new Joueur();		
		Integer u = 0;


		
	while (this.estFiniPartie == false)	{
		
		this.estFiniManche = false;
		
		pio.generationPioche();
		
		
		joueursuivant = j.distribuer(joueur);
		initTalon();
		
	    // debut tour	
		while(this.estFiniManche == false) {
		
	    	j = joueur.get(joueursuivant);
	    	
	    	if (j.isReel() == true) {
	    		j.jouer();
	    	} else {
	    		StrategieA s1 = new StrategieA();
				s1.strategie(j);
	    	}
		
			u = j.getSizeCarteEnMain();
			if(u==1) {
				j.direUno();
			}
			if (u == 0) {
				this.estFiniManche = true;
				
			} else{
				joueursuivant = joueurSuivant(joueursuivant);
			}
		}
		j.setScore(j.getScore() + compterPoint());
		System.out.println("la Manche est terminée. Le joueur " + joueursuivant + " a gagné. Il a " + j.getScore() +" points");
		
	
	if (j.getScore() >= 500) {
		System.out.println("la Partie est terminée. Le joueur " + joueursuivant + " a gagné.");
		this.estFiniPartie = true;
		
	}else {
		System.out.println("Aucun joueur ne possède 500 points. Lancement d'une nouvelle manche.");
		clearPartie();
	}
	
}
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
	
	public void clearPartie() {
		
		pio.clearPioche();
		talon.clearTalon();
		Joueur j;
		for(int i = 0; i < joueur.size();i++){
			j = joueur.get(i);
			j.clearMain();
			
		}
		
	}
	
	public int compterPoint(){
		int point = 0;
		int i = joueursuivant;
		Joueur j;
		boolean test = false;
		
		if (i == joueur.size() - 1) {
			i = 0;
		} else {
			i++;
			
		}
		
		while(i != joueursuivant) {
			
			j = joueur.get(i);
			point = point + j.getValeurMain();
			
			
			if (i == joueur.size() - 1) {
				i = 0;
			} else {
				i++;
				
			}
			
		}
		
		
		return point;
	}
	
	

}
