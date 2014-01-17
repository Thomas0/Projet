package fr.utt.lo02.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;

/**
 * Classe qui permet de gérer un joueur
 * 
 *
 */
public class Joueur extends Observable {

	/**
	 * Identifiant unique à chaque joueur
	 */
	private int id;
	
	/**
	 * Nombre de carte en main du joueur
	 */
	private int nbCarteEnMain;
	
	/**
	 * Permet de savoir s'il s'agit d'une IA ou d'un joueur physique
	 */
	private boolean estReel;
	private boolean piocher = false;
	
	/**
	 * Permet de stocker le score du joueur
	 */
	private int score;

	/**
	 * Liste qui permet de stocker les carte en main
	 */
	private ArrayList<Carte> carteEnmain = new ArrayList<Carte>();
	
	/**
	 * Liste qui permet de stocker les carte jouable
	 */
	private ArrayList<Carte> carteJouable = new ArrayList<Carte>();

	Talon talon = Talon.getInstance();
	Pioche pioche = Pioche.getInstance();

	IStrategie strategy;

	/**
	 * Constructeur qui permet de créer un joueur
	 * @param id Il s'agit de l'id unique du joueur
	 * @param reel Boolean qui determine si le joueur est physique ou non
	 */
	public Joueur(int id, boolean reel) {

		this.id = id;
		this.estReel = reel;
		this.score = 0;
	}


/**
 * Méthode permettant de remplir la liste carteJouable avec les carte jouable du joueur en fonction de la carte présente sur le talon
 * @param talon Carte présente sur le talon
 * @return Retourne la liste des carte jouable
 */
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

	/**
	 * Permet d'ajouter une carte dans la main du joueur
	 * @param c Carte à ajouter dans la main du joueur
	 */
	public void ajoutCarte(Carte c) {

		carteEnmain.add(c);
		setChanged();
		notifyObservers();
	}

	/**
	 * Permet d'obtenir la taille de liste carteEnmain
	 * @return Retourne la taille de liste carteEnmain
	 */
	public int getSizeCarteEnMain() {

		return carteEnmain.size();
	}

	/**
	 * Permet d'obtenir la liste des cartes en main
	 * @return Retourne la liste des cartes en main
	 */
	public ArrayList<Carte> getCarteEnmain() {

		return carteEnmain;

	}

	/**
	 * Méthode qui pemret au joueur de jouer
	 * @param c Carte que l'on désire jouer
	 * @return Retourne False si la carte ne peut pas être jouer et True quand elle a été jouer
	 */
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

	/**
	 * Méthode permettant de piocher une carte
	 */
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

	/**
	 * Méthode qui permet la distribution des cartes
	 * @param listejoueur On envoie la liste des joueurs en paramètre
	 * @return Retourne l'id du donneur
	 */
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

	/**
	 * Méthode qui pemret d'obtenir la valeur de l'attribut estReel
	 * @return Retourne la valeur de l'attribut estReel
	 */
	public boolean isReel() {
		return estReel;
	}

	/**
	 * Permet d'obtenir le score de la main
	 * @return Retourne le score des cartes en main
	 */
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

	/**
	 * permet d'obtenir l'id du joueur
	 * @return Retourne l'id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Permet d'obtenir les cartes jouables
	 * @return Retourne les carte jouable sous la forme d'un arraylist
	 */
	public ArrayList<Carte> getCarteJouable() {
		return carteJouable;
	}

	/**
	 * Permet de supprimer la carte d'une main
	 * @param c Carte a supprimer
	 */
	public void removeCarte(Carte c) {
		this.carteEnmain.remove(c);
		setChanged();
		notifyObservers();
	}

	/**
	 * Permet de vider les mains des joueurs
	 */
	public void clearMain() {

		carteEnmain.clear();
		setChanged();
		notifyObservers();
	}

	/**
	 * Permet d'obtenir le score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Permet de changer la valeur du score
	 * @param score nouvelle valeur du score
	 */
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
