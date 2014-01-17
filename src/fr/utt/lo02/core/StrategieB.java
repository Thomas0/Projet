package fr.utt.lo02.core;

import java.util.ArrayList;

/**
 * Classe qui permet de gerer une premiere strat�gie offensive.
 *
 */
public class StrategieB implements IStrategie {

	Talon talon = Talon.getInstance();
	Pioche pioche = Pioche.getInstance();

	public StrategieB() {
	}

	/**
	 * M�thode qui permet d'appliquer la strat�gie
	 * @param on envoie le joueur qui doit appliquer cette strat�gie
	 */
	@Override
	public void strategie(Joueur j) {
		Carte c;

		ArrayList<Carte> carteJouable = j.carteJouable(talon.derniereCarte());

		if (carteJouable.size() == 0) {

			c = pioche.piocher();

			// on test si la carte pioch� est jouable
			if (c.peutEtreJouer() == true) {
				talon.poserCarte(c);

				if (c.isEstSpecial() == true) {
					c.effet();
				}

				j.removeCarte(c);

			} else {

				j.ajoutCarte(c);

			}

		} else {
			c = carteJouable.get(0);

			for (int i = 0; i < carteJouable.size(); i++) {
				if (carteJouable.get(i).isEstSpecial()) {
					c = carteJouable.get(i);
				}
			}

			talon.poserCarte(c);

			if (c.isEstSpecial() == true) {
				c.effet();
			}

			j.removeCarte(c);
		}

		carteJouable.clear();

	}
}
