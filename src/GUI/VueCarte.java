package GUI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Core.Carte;

/**
 * Classe g�rant la vue d'une carte
 * 
 * @see Core.Carte
 * 
 */
public class VueCarte {
	/**
	 * La carte que la vue va g�rer
	 */
	private Carte carte;

	/**
	 * Le label, repr�sentant la carte, qui est g�n�r� par la classe
	 */
	private JLabel imgCarte;

	/**
	 * Constructeur de la classe. G�n�re le JLabel rer�sentant la carte.
	 * 
	 * @param carte
	 *            La carte � repr�senter.
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

			if (carte.isEstSpecial()) { // Carte sp�cial
				if (carte.getType() == "Inversion") {
					imgPath.append("sens.png");
				} else if (carte.getType() == "PlusDeux") {
					imgPath.append("+2.png");
				} else if (carte.getType() == "PasserTour") {
					imgPath.append("passe.png");
				}
			} else { // Carte num�ro
				imgPath.append(carte.getNumero() + ".png");
			}
		}

		try {
			// Cr�ation JLabel avec l'image de la carte
			BufferedImage img = ImageIO.read(new File(imgPath.toString()));
			imgCarte = new JLabel(new ImageIcon(img));
		} catch (IOException e) {
		}
	}

	/**
	 * R�cup�re le JLabel g�n�r� par le constructeur
	 * 
	 * @return JLabel repr�sentant la carte g�r� par la vue
	 */
	public JLabel getJLabel() {
		return imgCarte;
	}

	/**
	 * R�cup�re la carte g�r� par la vue
	 * 
	 * @return La carte g�r� par la vue
	 * @see Core.Carte
	 */
	public Carte getCarte() {
		return carte;
	}
}
