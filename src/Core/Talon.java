package Core;

import java.util.ArrayList;
import java.util.Observable;

public class Talon extends Observable {

	private ArrayList<Carte> talon = new ArrayList<Carte>();

	private Talon() {

	}

	private static Talon INSTANCE = null;

	public static Talon getInstance() {

		if (INSTANCE == null) {

			INSTANCE = new Talon();

		}
		return INSTANCE;

	}

	public Carte derniereCarte() {

		return talon.get(talon.size() - 1);
	}

	public void poserCarte(Carte c) {
		talon.add(c);
		setChanged();
		notifyObservers();
	}

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

	public void clearTalon() {

		talon.clear();

	}

	public void removeCarte(int index) {
		talon.remove(index);
	}

	public int getSize() {
		return talon.size();
	}

	public ArrayList<Carte> getTalon() {
		return talon;
	}
}
