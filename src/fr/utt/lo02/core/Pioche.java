package fr.utt.lo02.core;

import java.util.ArrayList;
import java.util.Collections;
/**
 * Classe qui pemret la gestion de la pioche
 *
 */
public class Pioche {

	/**
	 * Liste de cartes qui contient la pioche
	 */
	public ArrayList<Carte> pioche = new ArrayList<Carte>();

	/**
	 * Constructeur de la pioche
	 */
	private Pioche() {

	}

	private static Pioche INSTANCE = null;

	/**
	 * Singleton de la pioche
	 * @return Retourne une pioche
	 */
	public static Pioche getInstance() {

		if (INSTANCE == null) {

			INSTANCE = new Pioche();

		}
		return INSTANCE;

	}

	/**
	 * Permet de générer la pioche
	 */
	public void generationPioche() {

		// génération des 19 cartes bleues
		int i = 0;
		int v = 0;

		while (i < 19) {
			if (v <= 9) {
				pioche.add(new Carte(v, 0, false));
				v++;
				i++;
			} else {
				v = 0;
			}

		}

		pioche.add(new Inversion("0", 0, true));
		pioche.add(new Inversion("0", 0, true));

		pioche.add(new PlusDeux("1", 0, true));
		pioche.add(new PlusDeux("1", 0, true));

		pioche.add(new PasseTour("3", 0, true));
		pioche.add(new PasseTour("3", 0, true));

		// génération des 19 cartes vertes
		v = 0;
		i = 0;
		while (i < 19) {
			if (v <= 9) {
				pioche.add(new Carte(v, 1, false));
				v++;
				i++;
			} else {
				v = 0;
			}

		}

		pioche.add(new Inversion("0", 1, true));
		pioche.add(new Inversion("0", 1, true));

		pioche.add(new PlusDeux("1", 1, true));
		pioche.add(new PlusDeux("1", 1, true));

		pioche.add(new PasseTour("3", 1, true));
		pioche.add(new PasseTour("3", 1, true));

		// génération des 19 cartes rouges

		v = 0;
		i = 0;
		while (i < 19) {
			if (v <= 9) {
				pioche.add(new Carte(v, 2, false));
				v++;
				i++;
			} else {
				v = 0;
			}

		}

		pioche.add(new Inversion("0", 2, true));
		pioche.add(new Inversion("0", 2, true));

		pioche.add(new PlusDeux("1", 2, true));
		pioche.add(new PlusDeux("1", 2, true));

		pioche.add(new PasseTour("3", 2, true));
		pioche.add(new PasseTour("3", 2, true));

		// génération des 19 cartes jaunes

		v = 0;
		i = 0;
		while (i < 19) {
			if (v <= 9) {
				pioche.add(new Carte(v, 3, false));
				v++;
				i++;
			} else {
				v = 0;
			}

		}

		pioche.add(new Inversion("0", 3, true));
		pioche.add(new Inversion("0", 3, true));

		pioche.add(new PlusDeux("1", 3, true));
		pioche.add(new PlusDeux("1", 3, true));

		pioche.add(new PasseTour("3", 3, true));
		pioche.add(new PasseTour("3", 3, true));

		pioche.add(new PlusQuatre("2", 4, true));
		pioche.add(new PlusQuatre("2", 4, true));
		pioche.add(new PlusQuatre("2", 4, true));
		pioche.add(new PlusQuatre("2", 4, true));

		pioche.add(new Joker("4", 4, true));
		pioche.add(new Joker("4", 4, true));
		pioche.add(new Joker("4", 4, true));
		pioche.add(new Joker("4", 4, true));

		// on melange les cartes
		Collections.shuffle(pioche);

	}

	/**
	 * Permet de piocher une carte
	 * @return Retourne la carte piocher
	 */
	public Carte piocher() {

		if (pioche.size() == 0) {
			Talon talon = Talon.getInstance();
			Carte a;

			a = talon.derniereCarte();
			talon.removeCarte(talon.getSize() - 1);

			pioche = talon.getTalon();
			Collections.shuffle(pioche);
			talon.clearTalon();
			talon.poserCarte(a);
		}

		Carte c = pioche.get(0);
		pioche.remove(0);
		return c;
	}

	/**
	 * Permet d'effacer le contenu de la pioche
	 */
	public void clearPioche() {

		pioche.clear();

	}

	/**
	 * Permet de connaitre la taille de la pioche
	 * @return Retourne la taille de la pioche
	 */
	public int getSizeCarteEnMain() {

		return pioche.size();
	}

}
