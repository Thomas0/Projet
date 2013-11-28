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
		
	pioche.add(new Inversion("0",0,true));
	pioche.add(new Inversion("0",0,true));
	
	pioche.add(new PlusDeux("1",0,true));
	pioche.add(new PlusDeux("1",0,true));

	pioche.add(new PasseTour("3",0,true));
	pioche.add(new PasseTour("3",0,true));
	
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
		
		pioche.add(new Inversion("0",1,true));
		pioche.add(new Inversion("0",1,true));
		
		pioche.add(new PlusDeux("1",1,true));
		pioche.add(new PlusDeux("1",1,true));
		
		pioche.add(new PasseTour("3",1,true));
		pioche.add(new PasseTour("3",1,true));
		
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
		
		pioche.add(new Inversion("0",2,true));
		pioche.add(new Inversion("0",2,true));
		
		pioche.add(new PlusDeux("1",2,true));
		pioche.add(new PlusDeux("1",2,true));
		
		pioche.add(new PasseTour("3",2,true));
		pioche.add(new PasseTour("3",2,true));
		
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
		
		pioche.add(new Inversion("0",3,true));
		pioche.add(new Inversion("0",3,true));
		
		pioche.add(new PlusDeux("1",3,true));
		pioche.add(new PlusDeux("1",3,true));
		
		pioche.add(new PasseTour("3",3,true));
		pioche.add(new PasseTour("3",3,true));
		
		pioche.add(new PlusQuatre("2",4,true));
		pioche.add(new PlusQuatre("2",4,true));
		pioche.add(new PlusQuatre("2",4,true));
		pioche.add(new PlusQuatre("2",4,true));
		
		pioche.add(new Joker("4",4,true));
		pioche.add(new Joker("4",4,true));
		pioche.add(new Joker("4",4,true));
		pioche.add(new Joker("4",4,true));
		
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
