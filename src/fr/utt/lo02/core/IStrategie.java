package fr.utt.lo02.core;

/**
 * Implementation du patron de conception Stratégie via cette interface
 * @author Thomas
 *
 */
public interface IStrategie {

	/**
	 * Prototype de la méthode strategie
	 * @param j On envoie un joueur en parametre
	 */
	public void strategie(Joueur j);

}
