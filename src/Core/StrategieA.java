package Core;

import java.util.ArrayList;

public class StrategieA implements IStrategie {

	Talon talon = Talon.getInstance();
	Pioche pioche = Pioche.getInstance();

	public StrategieA() {
	}

	@Override
	public void strategie(Joueur j) {
		Carte c;

		ArrayList<Carte> carteJouable = j.carteJouable(talon.derniereCarte());

		if (carteJouable.size() == 0) {

			c = pioche.piocher();

			// on test si la carte pioché est jouable
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
			talon.poserCarte(c);

			if (c.isEstSpecial() == true) {
				c.effet();
			}

			j.removeCarte(c);
		}

		carteJouable.clear();

	}

}
