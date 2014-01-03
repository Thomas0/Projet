package Core;

import java.util.ArrayList;
import java.util.Observable;

public class Partie extends Observable implements Runnable {

	private boolean estFiniManche;
	private boolean estFiniPartie;
	private boolean sens;
	private int joueursuivant;
	private boolean wait = false;
	private boolean typeComptage = false;

	private ArrayList<Joueur> joueur = new ArrayList<Joueur>();

	private Pioche pio;
	private Talon talon;
	private static Partie INSTANCE = null;

	private Partie() {

		this.estFiniManche = false;
		this.estFiniPartie = false;
		this.sens = true;
		this.pio = Pioche.getInstance();
		this.talon = Talon.getInstance();
	}

	public static Partie getInstance() {

		if (INSTANCE == null) {

			INSTANCE = new Partie();

		}
		return INSTANCE;

	}

	/**
	 * Méthode permettant d'ajouter des joueurs reels
	 * 
	 * @param nombre
	 *            Nombre de joueurs à ajouter
	 */
	public void ajouterJoueurReel(int nombre) {

		for (int i = 0; i < nombre; i++) {

			joueur.add(new Joueur(i, true));

		}

	}

	/**
	 * Méthode permettant d'ajouter des joueurs virtuels
	 * 
	 * @param nombre
	 *            Nombre de joueurs à ajouter
	 */
	public void ajouterJoueurVirtuel(int nombre) {

		Integer size = joueur.size();

		for (int i = joueur.size(); i < nombre + size; i++) {

			joueur.add(new Joueur(i, false));
		}

	}

	/**
	 * Methode permettant d'obtenir le numéro du joueur suivante
	 * 
	 * @param r
	 *            numéro du joueur actuel
	 * @return Retourne le numéro du joueur suivant
	 */
	public Integer joueurSuivant(Integer r) {

		if (this.sens == true) {

			if (r == joueur.size() - 1) {

				joueursuivant = 0;
				return joueursuivant;

			} else {
				joueursuivant++;
				return joueursuivant;
			}

		} else {

			if (r == 0) {

				joueursuivant = joueur.size() - 1;
				return joueursuivant;

			} else {
				joueursuivant--;
				return joueursuivant;
			}

		}
	}

	public synchronized void demarrer() {

		Thread t = new Thread(this);
		t.start();

	}

	public void initTalon() {
		Carte c;
		c = pio.piocher();
		talon.poserCarte(c);

		if (c.isEstSpecial() == true) {
			boolean test = false;
			while (test == false) {
				if (c.getType() == "PlusQuatre") {
					c = pio.piocher();
					talon.poserCarte(c);
					if (c.isEstSpecial() == false) {
						if (joueursuivant == joueur.size() - 1) {

							joueursuivant = 0;

						} else {
							joueursuivant++;

						}
						test = true;
					}
				} else {
					c.effet();
					if (sens == true) {
						if (joueursuivant == joueur.size() - 1) {

							joueursuivant = 0;

						} else {
							joueursuivant++;

						}
					} else {
						if (joueursuivant == 0) {
							joueursuivant = joueur.size() - 1;
						} else {
							joueursuivant--;
						}
					}
					test = true;
				}
			}
		} else {
			if (sens == true) {
				if (joueursuivant == joueur.size() - 1) {

					joueursuivant = 0;

				} else {
					joueursuivant++;

				}
			} else {
				if (joueursuivant == 0) {
					joueursuivant = joueur.size() - 1;
				} else {
					joueursuivant--;
				}
			}
		}

	}

	public boolean isSens() {
		return sens;
	}

	public void setSens(boolean sens) {
		this.sens = sens;
	}

	public Joueur getJoueursuivant() {
		if (joueursuivant == joueur.size() - 1) {
			return joueur.get(0);
		} else {
			return joueur.get(joueursuivant + 1);
		}

	}

	public Joueur getJoueurActuel() {

		return joueur.get(joueursuivant);

	}

	public void setJoueursuivant(int joueursuivant) {
		this.joueursuivant = joueursuivant;
	}

	public int getNumJoueurSuivant() {
		if (joueursuivant == joueur.size() - 1) {
			return 0;
		} else {
			return joueursuivant + 1;
		}

	}

	public int getNumJoueurPrecedent() {

		if (joueursuivant == 0) {
			return joueur.size() - 1;
		} else {
			return joueursuivant - 1;
		}

	}

	public Joueur getJoueurPrecedent() {
		if (joueursuivant == 0) {
			return joueur.get(joueur.size() - 1);
		} else {
			return joueur.get(joueursuivant - 1);
		}

	}

	public void clearPartie() {

		pio.clearPioche();
		talon.clearTalon();
		Joueur j;
		for (int i = 0; i < joueur.size(); i++) {
			j = joueur.get(i);
			j.clearMain();
			j.setScore(0);
		}

	}

	public void compterPoint() {
		if (typeComptage) {
			for (int i = 0; i < joueur.size(); i++) {
				joueur.get(i).setScore(
						joueur.get(i).getScore()
								+ joueur.get(i).getValeurMain());
			}
		} else {
			int point = 0;

			for (int i = 0; i < joueur.size(); i++) {
				if (i != joueursuivant) {
					point += joueur.get(i).getValeurMain();
				}
			}

			joueur.get(joueursuivant).setScore(
					joueur.get(joueursuivant).getScore() + point);
		}
	}

	public int getSizeJoueur() {
		return joueur.size();
	}

	public ArrayList<Joueur> getJoueur() {
		return joueur;
	}

	public boolean somebodyWin() {
		for (int i = 0; i < joueur.size(); i++) {
			if (joueur.get(i).getScore() >= 500)
				return true;
		}

		return false;
	}

	@Override
	public synchronized void run() {
		Joueur j = joueur.get(0);
		int u = 0;
		estFiniPartie = false;

		while (this.estFiniPartie == false) {

			this.estFiniManche = false;

			for (int i = 0; i < joueur.size(); i++) {
				joueur.get(i).clearMain();
			}

			pio.generationPioche();

			joueursuivant = j.distribuer(joueur);
			initTalon();

			setChanged();
			notifyObservers();

			while (this.estFiniManche == false) {
				j = joueur.get(joueursuivant);
				j.setPiocher(false);

				setChanged();
				notifyObservers();

				if (j.isReel() == true) {
					wait = true;

					while (wait == true) {
						try {
							this.wait();
						} catch (InterruptedException e) {
						}
					}
					setChanged();
					notifyObservers();

					if ((talon.derniereCarte().getType() == "PlusQuatre" || talon
							.derniereCarte().getType() == "Joker")
							&& talon.derniereCarte().getCouleur() == " ") {
						wait = true;

						while (wait == true) {
							try {
								this.wait();
							} catch (InterruptedException e) {
							}
						}
					}
				} else {
					j.strategy.strategie(j);
				}

				u = j.getSizeCarteEnMain();
				if (u == 1) {
					j.direUno();
				}
				if (u == 0) {
					this.estFiniManche = true;
				} else {
					joueursuivant = joueurSuivant(joueursuivant);
				}
			}
			compterPoint();

			wait = true;

			setChanged();
			notifyObservers();

			while (wait == true) {
				try {
					this.wait();
				} catch (InterruptedException e) {
				}
			}

			if (somebodyWin()) {
				this.estFiniPartie = true;

				wait = true;
				setChanged();
				notifyObservers();

				while (wait == true) {
					try {
						this.wait();
					} catch (InterruptedException e) {
					}
				}
			}
		}
	}

	public void setWait(boolean wait) {
		this.wait = wait;
	}

	public boolean isEstFiniManche() {
		return estFiniManche;
	}

	public boolean isEstFiniPartie() {
		return estFiniPartie;
	}

	public void setTypeComptage(boolean typeComptage) {
		this.typeComptage = typeComptage;
	}

}
