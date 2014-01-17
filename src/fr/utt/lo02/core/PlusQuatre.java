package fr.utt.lo02.core;
/**
 * Classe qui gère la carte spéciale PlusQuatre
 *
 */
public class PlusQuatre extends Carte {
	/**
	 * Constructeur qui permet de construire une carte PlusQuatre
	 * @param ptype Type de la carte (2 = carte PlusQuatre)
	 * @param pcouleur Couleur de la carte
	 * @param estSpecial True car c'est une carte spéciale
	 */
	public PlusQuatre(String ptype, int pcouleur, boolean estSpecial) {
		super(ptype, pcouleur, estSpecial);
	}

	/**
	 * Permet de faire piocher quatre cartes au joueur suivant et de changer la couleur
	 */
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
