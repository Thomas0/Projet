package Core;

public class PlusQuatre extends Carte {

	public PlusQuatre(String ptype, int pcouleur, boolean estSpecial) {
		super(ptype, pcouleur, estSpecial);
	}

	public void ajoutquatre() {
		Partie partie = Partie.getInstance();
		Joueur jactuel = partie.getJoueurActuel();

		if (partie.isSens() == true) {
			Joueur j = partie.getJoueursuivant();

			j.ajoutCarte(Pioche.getInstance().piocher());
			j.ajoutCarte(Pioche.getInstance().piocher());
			j.ajoutCarte(Pioche.getInstance().piocher());
			j.ajoutCarte(Pioche.getInstance().piocher());
			partie.setJoueursuivant(partie.getNumJoueurSuivant());

			if (!jactuel.isReel()) {
				talon.setCouleur(0 + (int) (Math.random() * ((3 - 0) + 1)));
			}

		} else {

			Joueur j = partie.getJoueurPrecedent();
			j.ajoutCarte(Pioche.getInstance().piocher());
			j.ajoutCarte(Pioche.getInstance().piocher());
			j.ajoutCarte(Pioche.getInstance().piocher());
			j.ajoutCarte(Pioche.getInstance().piocher());
			partie.setJoueursuivant(partie.getNumJoueurPrecedent());

			if (!jactuel.isReel()) {
				talon.setCouleur(0 + (int) (Math.random() * ((3 - 0) + 1)));
			}

		}
	}

}
