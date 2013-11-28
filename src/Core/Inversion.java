package Core;

public class Inversion extends Carte{

	public Inversion(String ptype, int pcouleur, boolean estSpecial) {
		super(ptype, pcouleur, estSpecial);
	}

	public void inverse(){
		boolean sens;
		Partie partie = Partie.getInstance();
		sens = partie.isSens();
		partie.setSens(!sens);
		System.out.println("Le sens est inversé");
		
	}

}
