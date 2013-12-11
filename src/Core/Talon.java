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
		System.out.println(" ");
		if(c.isEstSpecial() == false){
			System.out.println("Carte sur le Talon : " + c.getNumero() + " " + c.getCouleur());
			}else{
    			System.out.println("Carte sur le Talon : " + c.getType() + " " + c.getCouleur());
    		}
		
		System.out.println(" ");
		
	}
	
	public void setCouleur(int nombre) {
		Carte c;
		c = talon.get(talon.size()-1);
		switch(nombre){
	
		case 0 :
			c.setCouleur("bleu");
			System.out.println("La couleur du talon est bleu");
			break;
		case 1 :
			c.setCouleur("vert");
			System.out.println("La couleur du talon est vert");
			break;
		case 2 :
			c.setCouleur("rouge");
			System.out.println("La couleur du talon est rouge");
			break;
		case 3 :
			c.setCouleur("jaune");
			System.out.println("La couleur du talon est jaune");
			break;
		}
		
	}
	
public void clearTalon(){
		
		talon.clear();
		
	}
	
	

}
