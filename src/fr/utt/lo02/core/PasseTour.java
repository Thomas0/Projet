package fr.utt.lo02.core;

/**
 * Classe qui permet de gérer la carte spéciale passer un tour
 */
public class PasseTour extends Carte {
	Partie partie = Partie.getInstance();

	/**
	 * Constructeur qui permet de construire une carte PasseTour
	 * @param ptype Type de la carte (3 = carte PasseTour)
	 * @param pcouleur Couleur de la carte
	 * @param estSpecial True car c'est une carte spéciale
	 */
	public PasseTour(String ptype, int pcouleur, boolean estSpecial) {
		super(ptype, pcouleur, estSpecial);

	}

	/**
	 * Méthode qui permet de passer le tour du joueur suivant le sens de la partie
	 */
	public void passertour() {

		if (partie.isSens() == true) {

			partie.setJoueursuivant(partie.getNumJoueurSuivant());
		} else {
			partie.setJoueursuivant(partie.getNumJoueurPrecedent());
		}
	}

}
