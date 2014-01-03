package Core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;

public class Joueur extends Observable {

	private int id;
	private int nbCarteEnMain;
	private boolean estReel;
	private boolean piocher = false;
	private int score;

	private ArrayList<Carte> carteEnmain = new ArrayList<Carte>();
	private ArrayList<Carte> carteJouable = new ArrayList<Carte>();

	Talon talon = Talon.getInstance();
	Pioche pioche = Pioche.getInstance();

	IStrategie strategy;

	public Joueur() {

	}

	public Joueur(int id, boolean reel) {

		this.id = id;
		this.estReel = reel;
		this.score = 0;
	}

	/**
	 * Permet au joueur de dire UNO
	 */
	public void direUno() {

	}

	public ArrayList<Carte> carteJouable(Carte talon) {
		carteJouable.clear();
		Carte c;
		int size = carteEnmain.size();

		for (int i = 0; i < size; i++) {
			c = carteEnmain.get(i);

			if (c.peutEtreJouer() == true) {
				carteJouable.add(c);

			}

		}

		return carteJouable;

	}

	public void ajoutCarte(Carte c) {

		carteEnmain.add(c);
		setChanged();
		notifyObservers();
	}

	public int getSizeCarteEnMain() {

		return carteEnmain.size();
	}

	public ArrayList<Carte> getCarteEnmain() {

		return carteEnmain;

	}

	public boolean jouer(Carte c) {
		if (!c.peutEtreJouer())
			return false;

		Partie partie = Partie.getInstance();
		synchronized (partie) {
			talon.poserCarte(c);
			this.removeCarte(c);
			if (c.isEstSpecial() == true) {
				c.effet();
			}
			partie.setWait(false);
			partie.notify();
		}

		return true;
	}

	public void piocher() {
		if (piocher)
			return;

		ajoutCarte(Pioche.getInstance().piocher());
		piocher = true;

		ArrayList<Carte> cartejouable = carteJouable(talon.derniereCarte());
		if (cartejouable.size() == 0) {
			Partie partie = Partie.getInstance();
			synchronized (partie) {
				partie.setWait(false);
				partie.notify();
			}
		}
	}

	public Integer distribuer(ArrayList<Joueur> listejoueur) {

		ArrayList<Integer> dist = new ArrayList<Integer>();
		Carte c;
		Joueur j;

		for (int i = 0; i < listejoueur.size(); i++) {
			c = pioche.piocher();

			if (c.isEstSpecial() == true) {
				dist.add(0);
			} else {

				dist.add(c.getNumero());
			}

		}

		Integer i = dist.indexOf(Collections.max(dist));
		Integer itemoin = dist.indexOf(Collections.max(dist));

		if (i == listejoueur.size() - 1) {

			i = 0;

		} else {
			i++;
		}
		boolean z;

		// on reinitialise la pioche
		pioche.clearPioche();
		pioche.generationPioche();

		// on distribue
		for (int k = 0; k < 7; k++) {
			z = false;
			while (z == false) {

				if (i == listejoueur.size() - 1) {
					j = listejoueur.get(i);
					j.ajoutCarte(pioche.piocher());
					i = 0;

				} else {
					j = listejoueur.get(i);
					j.ajoutCarte(pioche.piocher());
					i++;
				}

				if (k == 0 && i == itemoin) {

					j = listejoueur.get(itemoin);
					j.ajoutCarte(pioche.piocher());

				}

				if (i == itemoin) {
					z = true;
				}

			}

		}

		// on return l'id du donneur
		return itemoin;

	}

	public boolean isReel() {
		return estReel;
	}

	public void strategie() {

		Carte c;
		carteJouable = carteJouable(talon.derniereCarte());

		if (carteJouable.size() == 0) {

			System.out.println("Le joueur " + this.id + " pioche une carte");
			c = pioche.piocher();

			// on test si la carte pioché est jouable
			if (c.peutEtreJouer() == true) {
				System.out.println("Le joueur " + this.id + " joue une carte");
				talon.poserCarte(c);

				if (c.isEstSpecial() == true) {
					c.effet();
				}

				carteEnmain.remove(c);

			} else {

				ajoutCarte(c);

			}

		} else {
			c = carteJouable.get(0);
			talon.poserCarte(c);

			if (c.isEstSpecial() == true) {
				c.effet();
			}

			carteEnmain.remove(c);
		}

		carteJouable.clear();
	}

	public int getValeurMain() {

		int sc = 0;
		Carte c;

		for (int i = 0; i < carteEnmain.size(); i++) {
			c = carteEnmain.get(i);

			if (c.isEstSpecial() == false) {
				sc = sc + c.getNumero();
			} else {
				String type;
				type = c.getType();

				switch (type) {

				case "Inversion":
					sc = sc + 20;
					break;
				case "PlusDeux":
					sc = sc + 20;
					break;
				case "PasserTour":
					sc = sc + 20;
					break;
				case "Joker":
					sc = sc + 50;
					break;
				case "PlusQuatre":
					sc = sc + 50;
					break;

				}
			}

		}

		return sc;
	}

	public int getId() {
		return id;
	}

	public ArrayList<Carte> getCarteJouable() {
		return carteJouable;
	}

	public void removeCarte(Carte c) {
		this.carteEnmain.remove(c);
		setChanged();
		notifyObservers();
	}

	public void clearMain() {

		carteEnmain.clear();
		setChanged();
		notifyObservers();
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Joueur other = (Joueur) obj;
		if (carteEnmain == null) {
			if (other.carteEnmain != null)
				return false;
		} else if (!carteEnmain.equals(other.carteEnmain))
			return false;
		if (carteJouable == null) {
			if (other.carteJouable != null)
				return false;
		} else if (!carteJouable.equals(other.carteJouable))
			return false;
		if (estReel != other.estReel)
			return false;
		if (id != other.id)
			return false;
		if (nbCarteEnMain != other.nbCarteEnMain)
			return false;
		if (pioche == null) {
			if (other.pioche != null)
				return false;
		} else if (!pioche.equals(other.pioche))
			return false;
		if (score != other.score)
			return false;
		if (talon == null) {
			if (other.talon != null)
				return false;
		} else if (!talon.equals(other.talon))
			return false;
		return true;
	}

	public void setPiocher(boolean piocher) {
		this.piocher = piocher;
	}

	public void setStrategy(IStrategie strategy) {
		this.strategy = strategy;
	}

}
