package Core;

public class PasseTour extends Carte{
	Partie partie = Partie.getInstance();
	public PasseTour(String ptype, int pcouleur, boolean estSpecial) {
		super(ptype, pcouleur, estSpecial);
		
	}
	
	public void passertour(){
		
	if(partie.isSens() == true) {
		
		partie.setJoueursuivant(partie.getNumJoueurSuivant());
	}else {
		partie.setJoueursuivant(partie.getNumJoueurPrecedent());
	  }
	}
	
}
