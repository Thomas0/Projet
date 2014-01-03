package Core;

public class Inversion extends Carte {

	public Inversion(String ptype, int pcouleur, boolean estSpecial) {
		super(ptype, pcouleur, estSpecial);
	}

	public void inverse() {
		boolean sens;
		int i = 0;

		Partie partie = Partie.getInstance();
		sens = partie.isSens();
		partie.setSens(!sens);
		i = partie.getSizeJoueur();
		if (i == 2) {
			i = partie.getNumJoueurSuivant();
			partie.setJoueursuivant(i);
		}

	}

}
