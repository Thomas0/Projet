package fr.utt.lo02.core;

/**
 * Classe qui permet de g�rer la carte sp�ciale Joker
 *
 */
public class Joker extends Carte {

	/**
	 * Constructeur qui permet de construire une carte Joker
	 * @param ptype Type de la carte (4 = carte joker)
	 * @param pcouleur Couleur de la carte
	 * @param estSpecial True car c'est une carte sp�ciale
	 */
	public Joker(String ptype, int pcouleur, boolean estSpecial) {
		super(ptype, pcouleur, estSpecial);

	}

	/**
	 * M�thode permetant de changer la couleur du talon pour l'IA
	 */
	public void changeCouleur() {
		Partie partie = Partie.getInstance();
		Joueur jactuel = partie.getJoueurActuel();

			if (!jactuel.isReel()) {
				talon.setCouleur(0 + (int) (Math.random() * ((3 - 0) + 1)));
			}

		

	}

}
