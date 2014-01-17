package fr.utt.lo02.core;

/**
 * Implementation du patron de conception Strat�gie via cette interface
 * @author Thomas
 *
 */
public interface IStrategie {

	/**
	 * Prototype de la m�thode strategie
	 * @param j On envoie un joueur en parametre
	 */
	public void strategie(Joueur j);

}
