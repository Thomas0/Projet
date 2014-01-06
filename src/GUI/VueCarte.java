package GUI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Core.Carte;

/**
 * Classe gérant la vue d'une carte
 * 
 * @see Core.Carte
 * 
 */
public class VueCarte {
	/**
	 * La carte que la vue va gérer
	 */
	private Carte carte;

	/**
	 * Le label, représentant la carte, qui est généré par la classe
	 */
	private JLabel imgCarte;

	/**
	 * Constructeur de la classe. Génère le JLabel rerésentant la carte.
	 * 
	 * @param carte
	 *            La carte à représenter.
	 */
	public VueCarte(Carte carte) {
		this.carte = carte;

		StringBuilder imgPath = new StringBuilder("img" + File.separator); // Base
																			// de
																			// chemin
																			// de
																			// l'image
																			// de
																			// la
																			// carte

		// Carte sans couleur
		if (carte.getType() == "PlusQuatre") {
			imgPath.append("+4.png");
		} else if (carte.getType() == "Joker") {
			imgPath.append("joker.png");
		} else { // Carte avec couleur
			imgPath.append(carte.getCouleur() + File.separator);

			if (carte.isEstSpecial()) { // Carte spécial
				if (carte.getType() == "Inversion") {
					imgPath.append("sens.png");
				} else if (carte.getType() == "PlusDeux") {
					imgPath.append("+2.png");
				} else if (carte.getType() == "PasserTour") {
					imgPath.append("passe.png");
				}
			} else { // Carte numéro
				imgPath.append(carte.getNumero() + ".png");
			}
		}

		try {
			// Création JLabel avec l'image de la carte
			BufferedImage img = ImageIO.read(new File(imgPath.toString()));
			imgCarte = new JLabel(new ImageIcon(img));
		} catch (IOException e) {
		}
	}

	/**
	 * Récupère le JLabel généré par le constructeur
	 * 
	 * @return JLabel représentant la carte géré par la vue
	 */
	public JLabel getJLabel() {
		return imgCarte;
	}

	/**
	 * Récupère la carte géré par la vue
	 * 
	 * @return La carte géré par la vue
	 * @see Core.Carte
	 */
	public Carte getCarte() {
		return carte;
	}
}
