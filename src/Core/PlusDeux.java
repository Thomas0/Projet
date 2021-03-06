package Core;

public class PlusDeux extends Carte {

	public PlusDeux(String ptype, int pcouleur, boolean estSpecial) {
		super(ptype, pcouleur, estSpecial);
	}

	public void ajoutdeux() {
		Partie partie = Partie.getInstance();

		if (partie.isSens() == true) {
			Joueur j = partie.getJoueursuivant();

			j.ajoutCarte(Pioche.getInstance().piocher());
			j.ajoutCarte(Pioche.getInstance().piocher());
			partie.setJoueursuivant(partie.getNumJoueurSuivant());

		} else {

			Joueur j = partie.getJoueurPrecedent();
			j.ajoutCarte(Pioche.getInstance().piocher());
			j.ajoutCarte(Pioche.getInstance().piocher());
			partie.setJoueursuivant(partie.getNumJoueurPrecedent());

		}

	}

}
