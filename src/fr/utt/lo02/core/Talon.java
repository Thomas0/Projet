package fr.utt.lo02.core;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Classe permettant de gérer le talon
 *
 */
public class Talon extends Observable {

	/**
	 * Liste de carte qui contient le talon
	 */
	private ArrayList<Carte> talon = new ArrayList<Carte>();

	private Talon() {

	}

	private static Talon INSTANCE = null;

	/**
	 * Singleton pour eviter d'voir de multiple talon
	 * @return Retourne le talon
	 */
	public static Talon getInstance() {

		if (INSTANCE == null) {

			INSTANCE = new Talon();

		}
		return INSTANCE;

	}

	/**
	 * Méthode permettant d'obtenir la carte au dessus du talon
	 * @return Retourne la carte qui est au dessus du talon
	 */
	public Carte derniereCarte() {

		return talon.get(talon.size() - 1);
	}

	/**
	 * Permet de poser une carte sur le talon
	 * @param c Carte à déposer sur le talon
	 */
	public void poserCarte(Carte c) {
		talon.add(c);
		setChanged();
		notifyObservers();
	}

	/**
	 * Méthode qui permet de changer la couleur de la carte du talon
	 * @param nombre Nombre qui determine la couleur (0=bleu 1=vert 2=rouge 3=jaune)
	 */
	public void setCouleur(int nombre) {
		Carte c;
		c = talon.get(talon.size() - 1);
		switch (nombre) {

		case 0:
			c.setCouleur("bleu");
			break;
		case 1:
			c.setCouleur("vert");
			break;
		case 2:
			c.setCouleur("rouge");
			break;
		case 3:
			c.setCouleur("jaune");
			break;
		}

		setChanged();
		notifyObservers();

		Partie partie = Partie.getInstance();
		synchronized (partie) {
			partie.setWait(false);
			partie.notify();
		}

	}

	/**
	 * Méthode qui permet d'effacer le talon
	 */
	public void clearTalon() {

		talon.clear();

	}

	/**
	 * Méthode qui permet de retirer une carte du talon
	 * @param index index de la carte a retirer
	 */
	public void removeCarte(int index) {
		talon.remove(index);
	}

	/**
	 * Permet d'obtenir la taille du talon
	 * @return Retourne la taille du talon
	 */
	public int getSize() {
		return talon.size();
	}

	/**
	 * Permet de recuperer le talon
	 * @return Retourne le talon
	 */
	public ArrayList<Carte> getTalon() {
		return talon;
	}
}
