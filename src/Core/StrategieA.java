package Core;

import java.util.ArrayList;

public class StrategieA implements IStrategie {
	
	Talon talon = Talon.getInstance();	
	Pioche pioche = Pioche.getInstance();
	
	public StrategieA() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void strategie(Joueur j) {
		Carte c;
		
		ArrayList<Carte> carteJouable = j.carteJouable(talon.derniereCarte());		
		
	    
		if (carteJouable.size() == 0) {
			
			System.out.println("Le joueur " + j.getId() + " pioche une carte");
			c = pioche.piocher();
			
			//on test si la carte pioché est jouable
			if (c.peutEtreJouer() == true) {
				System.out.println("Le joueur " + j.getId() + " joue une carte");
				talon.poserCarte(c);
				
				if (c.isEstSpecial() == true){
					c.effet();
				}
				
				j.removeCarte(c);
				
			} else {
				
				j.ajoutCarte(c);
				
			}
			
		} else {
			c = carteJouable.get(0);
			talon.poserCarte(c);
			
			if (c.isEstSpecial() == true){
				c.effet();
			}
			
			j.removeCarte(c);
		}
		
		carteJouable.clear();
		
	}

}
