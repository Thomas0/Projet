package Core;

public class Joker extends Carte {

	public Joker(String ptype, int pcouleur, boolean estSpecial) {
		super(ptype, pcouleur, estSpecial);

	}

	public void changeCouleur() {
		Partie partie = Partie.getInstance();
		Joueur jactuel = partie.getJoueurActuel();

		if (partie.isSens() == true) {

			if (!jactuel.isReel()) {
				talon.setCouleur(0 + (int) (Math.random() * ((3 - 0) + 1)));
			}

		} else {

			if (!jactuel.isReel()) {
				talon.setCouleur(0 + (int) (Math.random() * ((3 - 0) + 1)));
			}

		}

	}

}
