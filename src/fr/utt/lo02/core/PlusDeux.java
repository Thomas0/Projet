package fr.utt.lo02.core;
/**
 * Classe qui gère la carte spéciale PlusDeux
 *
 */
public class PlusDeux extends Carte {
	/**
	 * Constructeur qui permet de construire une carte PlusDeux
	 * @param ptype Type de la carte (1 = carte PlusDeux)
	 * @param pcouleur Couleur de la carte
	 * @param estSpecial True car c'est une carte spéciale
	 */
	public PlusDeux(String ptype, int pcouleur, boolean estSpecial) {
		super(ptype, pcouleur, estSpecial);
	}

	/**
	 * Permet de faire piocher deux cartes au joueur suivant
	 */
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
