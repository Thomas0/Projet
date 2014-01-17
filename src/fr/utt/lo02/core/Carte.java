package fr.utt.lo02.core;

/**
 * Classe permettant la gestion d'une carte
 *
 */

public class Carte {

	/**
	 * Numéro de la carte
	 */
	private int numero;
	
	/**
	 * Determine si la carte est special ou non
	 */
	private boolean estSpecial;
	
	/**
	 * Couleur de la carte
	 */
	private String couleur;
	
	/**
	 * Type de la carte (s'il s'agit d'une carte special passetour,inversion ou autre...)
	 */
	private String type;

	/**
	 * Tableau définissant les couleurs des cartes
	 */
	public final static String[] tcouleur = { "bleu", "vert", "rouge", "jaune",
			" " };
	
	/**
	 * Tableau définissant les carte spécial
	 */
	public final static String[] tspecial = { "Inversion", "PlusDeux",
			"PlusQuatre", "PasserTour", "Joker" };

	Talon talon = Talon.getInstance();

	
	/**
	 * Constructeur d'une carte par numéro
	 * 
	 * @param numero Numéro de la carte
	 * @param pcouleur Couleur de la carte(1,2,3,4)
	 * @param estSpecial Boolean pour specifier si la carte est spécial ou non
	 */
	public Carte(int numero, int pcouleur, boolean estSpecial) {

		this.numero = numero;
		this.couleur = tcouleur[pcouleur];
		this.estSpecial = estSpecial;

	}

	/**
	 * Constructeur d'une carte spécial par type
	 * @param ptype Type de la carte (inversion,+2 etc...)
	 * @param pcouleur Couleur de la carte
	 * @param estSpecial Boolean pour specifier si la carte est spécial ou non
	 */
	public Carte(String ptype, int pcouleur, boolean estSpecial) {

		this.type = tspecial[Integer.parseInt(ptype)];
		this.couleur = tcouleur[pcouleur];
		this.estSpecial = estSpecial;
	}

	/**
	 * Méthode qui permet de savoir si la carte est jouable
	 * @return True si elle est jouable et false si elle n'est pas jouable
	 */
	public boolean peutEtreJouer() {
		Carte c;
		c = talon.derniereCarte();
		if (c.isEstSpecial() == false) {
			if (this.estSpecial == false) {

				if (this.getCouleur() == talon.derniereCarte().getCouleur()
						|| this.getNumero() == talon.derniereCarte()
								.getNumero()) {

					return true;

				}

				else {
					return false;
				}

			} else {
				switch (type) {

				case "Inversion":
				case "PlusDeux":
				case "PasserTour":
					if (this.getCouleur() == c.getCouleur()) {

						return true;

					} else {
						return false;
					}
				default:
					return true;
				}

			}
		} else {

			if (this.estSpecial == false) {

				if (this.getCouleur() == talon.derniereCarte().getCouleur()) {

					return true;

				}

				else {
					return false;
				}

			} else {
				switch (type) {

				case "Inversion":
				case "PlusDeux":
				case "PasserTour":
					if (this.getCouleur() == c.getCouleur()
							|| this.getType() == c.getType()) {

						return true;

					} else {
						return false;
					}
				default:
					return true;
				}

			}

		}
	}

	
	/**
	 * Méthode qui permet d'appliquer les effet d'une carte spéciale
	 */
	public void effet() {

		switch (type) {
		case "Inversion":
			Inversion inversion = new Inversion("0", 0, true);
			inversion.inverse();
			break;
		case "PlusDeux":
			PlusDeux plusdeux = new PlusDeux("1", 0, true);
			plusdeux.ajoutdeux();
			break;
		case "PlusQuatre":
			PlusQuatre plusquatre = new PlusQuatre("2", 4, true);
			plusquatre.ajoutquatre();
			break;
		case "PasserTour":
			PasseTour pass = new PasseTour("3", 0, true);
			pass.passertour();
			break;
		case "Joker":
			Joker joker = new Joker("4", 0, true);
			joker.changeCouleur();
			break;
		}

	}

	/**
	 * Retourne la valeur de l'attribut estSpécial
	 * @return Retourne True si la carte est spéciale ou False si elle ce n'est pas le cas
	 */
	
	public boolean isEstSpecial() {
		return estSpecial;
	}

	/**
	 * Permet d'obtenir la couleur de la carte
	 * @return Retourne la couleur de la carte
	 */
	public String getCouleur() {
		return couleur;
	}

	/**
	 * Permet d'obtenir le numéro de la carte
	 * @return Retourne le numéro de la carte
	 */
	public int getNumero() {
		return numero;
	}

	/**
	 * Permet d'obtenir le type de la carte
	 * @return Retourne le type de la carte
	 */
	public String getType() {
		return type;
	}

	/**
	 * Permet de changer la couleur d'une carte
	 * @param couleur Couleur que l'on désire appliquer
	 */
	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}

	@Override
	public String toString() {
		return "Carte [numero=" + numero + ", estSpecial=" + estSpecial
				+ ", couleur=" + couleur + ", type=" + type + "]";
	}

}
