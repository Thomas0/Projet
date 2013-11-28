package Core;

import java.util.ArrayList;
import java.util.Collections;

public class Pioche {
	
	public ArrayList<Carte> pioche = new ArrayList<Carte>();
	
	private Pioche() {
		
	}
	
	private static Pioche INSTANCE = null;
	
	public static Pioche getInstance() {
		
		if(INSTANCE == null) {
			
			INSTANCE = new Pioche();
			
		}
		return INSTANCE;
		
	}
	
	public void generationPioche() {
		
	//génération des 19 cartes bleues
		int i = 0;
		int v = 0;
		
		while (i < 19)
		{
			if(v <= 9){
				pioche.add(new Carte(v,0,false));
				v++;
				i++;
			}	else {
				v = 0;
			}
			
		}
		
	//génération des 19 cartes vertes
		v = 0;
		i = 0;
		while (i<19)
		{
			if(v <= 9){
				pioche.add(new Carte(v,1,false));
				v++;
				i++;
			}	else {
				v = 0;
			}
			
		}
		
	//génération des 19 cartes rouges
		
		v = 0;
		i = 0;
		while (i<19)
		{
			if(v <= 9){
				pioche.add(new Carte(v,2,false));
				v++;
				i++;
			}	else {
				v = 0;
			}
			
		}
		
	//génération des 19 cartes jaunes
		
		v = 0;
		i = 0;
		while (i<19)
		{
			if(v <= 9){
				pioche.add(new Carte(v,3,false));
				v++;
				i++;
			}	else {
				v = 0;
			}
			
		}
		
	
		// on melange les cartes
		Collections.shuffle(pioche);
		
	}
	
	public Carte piocher() {
		Carte c = pioche.get(0);
		pioche.remove(0);
		return c;
	}
	
	public void clearPioche(){
		
		pioche.clear();
		
	}
	
	public int getSizeCarteEnMain() {
		
		return pioche.size();
	}

}
