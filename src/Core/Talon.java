package Core;

import java.util.ArrayList;

public class Talon {

	private ArrayList<Carte> talon = new ArrayList<Carte>();
	
	private Talon() {
	
	}
	
	private static Talon INSTANCE = null;
	
	public static Talon getInstance() {
		
		if(INSTANCE == null) {
			
			INSTANCE = new Talon();
			
		}
		return INSTANCE;
		
	}
	
	public Carte derniereCarte() {

		return talon.get(talon.size() - 1);
	}
	
	
	public void poserCarte(Carte c) {
		talon.add(c);
		System.out.println("Carte sur le Talon : " + c.getNumero() + " " + c.getCouleur());
		
	}
	

}
