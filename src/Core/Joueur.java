package Core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Joueur implements IStrategie{

	private int id;
	private int nbCarteEnMain;
	private boolean estReel;

	private ArrayList<Carte> carteEnmain = new ArrayList<Carte>();
	private ArrayList<Carte> carteJouable = new ArrayList<Carte>();
	
	Talon talon = Talon.getInstance();	
	Pioche pioche = Pioche.getInstance();
	
	public Joueur() {
		
	}
	
	public Joueur(int id, boolean reel) {
		
		this.id = id;
		this.estReel = reel;
	}

	public void direUno(){
		
	
	}
	
	public ArrayList<Carte> carteJouable(Carte talon) {
		
		Carte c;
		int size = carteEnmain.size();
		
		for (int i = 0;i < size;i++) {
			c = carteEnmain.get(i);
			
			if (c.getNumero() == talon.getNumero() || c.getCouleur() == talon.getCouleur()) {
				carteJouable.add(c);
				
			}
			
		}
		
		return carteJouable;
		
	}
	
	public void ajoutCarte(Carte c) {
		
		carteEnmain.add(c);
		
	}
	

	public int getSizeCarteEnMain() {
		
		return carteEnmain.size();
	}
	
	
	public ArrayList<Carte> getCarteEnmain() {
		
		return carteEnmain;
		
	}
	
	
	public void jouer() {
		
		Carte c;
		System.out.println("c'est au joueur " + this.id + " de jouer");	
		
		if (this.estReel == true) {
	
			// on affiche les cartes en main
			for(int i = 0; i < carteEnmain.size();i++) {
	    		c = carteEnmain.get(i);
	    		System.out.println("Carte n�"+ i + " : " + c.getNumero() + " " + c.getCouleur());
	    		
	    	}
			
			//on affiche les cartes jouables
			ArrayList<Carte> cartejouable = carteJouable(talon.derniereCarte());		
			int size = cartejouable.size();
			
			for (int i = 0; i < size;i++) {
				c = cartejouable.get(i);
				
				System.out.println("Cartes jouables : " + c.getNumero() + " " + c.getCouleur());
			
			}
			
			// on test s'il y a des cartes jouables
			if (cartejouable.size() == 0) {
				
				System.out.println("Vous ne pouvez jouer aucune carte, il faut piocher");
				c = pioche.piocher();
				
				System.out.println("Vous avez piocher un " + c.getNumero() + " " + c.getCouleur());
				
				//on test si la carte pioch� est jouable
				if (c.peutEtreJouer() == true) {
					System.out.println("Vous jouez cette carte");
					talon.poserCarte(c);
					
				} else {
					
					ajoutCarte(c);
				}
				
			} else {
				
				boolean jouer = false;
				
				while (jouer == false) {
					
				Scanner sc = new Scanner(System.in);
				System.out.println("Entrer le num�ro de la carte que vous souhaitez jouer : ");
				String str = sc.nextLine();
				
				c = carteEnmain.get(Integer.parseInt(str));
				
					//on test si la carte peut etre jouer
					if (c.peutEtreJouer() == true) {
					
						System.out.println("Vous avez jouer le " + c.getNumero() + " " + c.getCouleur());
						talon.poserCarte(c);
						carteEnmain.remove(Integer.parseInt(str));
						cartejouable.clear();
						jouer = true;
						
					}
					else {
						
						System.out.println("Vous ne pouvez pas jouer cette carte.");
					}
				
			}
				jouer = false;
		}
	}
		else {
			
			strategie();
		}
		
		
	}

	
	public Integer distribuer(ArrayList<Joueur> listejoueur) {
		
		ArrayList<Integer> dist = new ArrayList<Integer>();
		Carte c;
		Joueur j;
		
		for(int i = 0;i < listejoueur.size();i++) {
			c = pioche.piocher();
			
			if(c.isEstSpecial() == true) {
				dist.add(0);
				} else {
					
					dist.add(c.getNumero());
				}
			
		}
		
		Integer i = dist.indexOf(Collections.max(dist));
		Integer itemoin = dist.indexOf(Collections.max(dist));
		System.out.println("Le joueur " + Integer.toString(i) + " distribue");
		
		if ( i == listejoueur.size()-1) {
			
			i = 0;
			
		} else {
			i++;
		}
		boolean z;
		
		// on reinitialise la pioche
		pioche.clearPioche();
		pioche.generationPioche();
		
		// on distribue
		for (int k = 0; k < 7;k++) {
			z = false;
			while (z == false) {
				
				if (i == listejoueur.size()-1) {
					j = listejoueur.get(i);
					j.ajoutCarte(pioche.piocher());
					i = 0;
					
					
				} else {
					j = listejoueur.get(i);
					j.ajoutCarte(pioche.piocher());
					i++;
						}
				
				if(k==0 && i == itemoin) {
				
					j = listejoueur.get(itemoin);
					j.ajoutCarte(pioche.piocher());
					
				}
				
				if (i == itemoin) {
					z = true;
				}
			
													}
		
								}
		
		//on return l'index du joueur qui doit jouer le premier tour
		if ( itemoin == listejoueur.size()-1) {
			
			i = 0;
			return i;
			
		} else {
			i++;
			return i;
		}
		
	
	}
	
	
	
	
	
	public boolean isReel() {
		return estReel;
	}

	@Override
	public void strategie() {
		
		Carte c;
	    carteJouable = carteJouable(talon.derniereCarte());		
		
	    
		if (carteJouable.size() == 0) {
			
			System.out.println("Le joueur " + this.id + " pioche une carte");
			c = pioche.piocher();
			
			//on test si la carte pioch� est jouable
			if (c.peutEtreJouer() == true) {
				System.out.println("Le joueur " + this.id + " joue une carte");
				talon.poserCarte(c);
				carteEnmain.remove(c);
			} else {
				
				ajoutCarte(c);
				
			}
			
		} else {
			talon.poserCarte(carteJouable.get(0));
			carteEnmain.remove(0);
		}
		
		carteJouable.clear();
	}
	
	
}
