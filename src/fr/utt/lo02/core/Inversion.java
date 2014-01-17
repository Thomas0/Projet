package fr.utt.lo02.core;

/**
 * Classe qui g�re la carte sp�ciale Inversion
 *
 */
public class Inversion extends Carte {

	/**
	 * Constructeur qui permet de construire une carte Inversion
	 * @param ptype Type de la carte (0 = carte inversion)
	 * @param pcouleur Couleur de la carte
	 * @param estSpecial True car c'est une carte sp�ciale
	 */
	public Inversion(String ptype, int pcouleur, boolean estSpecial) {
		super(ptype, pcouleur, estSpecial);
	}

	/**
	 * M�thode qui permet d'inverser le sens de la partie
	 */
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
