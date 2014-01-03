package GUI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Core.Carte;

public class VueCarte {
	private Carte carte;
	private JLabel imgCarte;

	public VueCarte(Carte carte) {
		this.carte = carte;

		StringBuilder imgPath = new StringBuilder("img" + File.separator);

		if (carte.getType() == "PlusQuatre") {
			imgPath.append("+4.png");
		} else if (carte.getType() == "Joker") {
			imgPath.append("joker.png");
		} else {
			imgPath.append(carte.getCouleur() + File.separator);

			if (carte.isEstSpecial()) {
				if (carte.getType() == "Inversion") {
					imgPath.append("sens.png");
				} else if (carte.getType() == "PlusDeux") {
					imgPath.append("+2.png");
				} else if (carte.getType() == "PasserTour") {
					imgPath.append("passe.png");
				}
			} else {
				imgPath.append(carte.getNumero() + ".png");
			}
		}

		try {
			BufferedImage img = ImageIO.read(new File(imgPath.toString()));
			imgCarte = new JLabel(new ImageIcon(img));
		} catch (IOException e) {
			System.out.println(imgPath.toString());
			System.out.println(carte);
		}
	}

	public JLabel getJLabel() {
		return imgCarte;
	}

	public Carte getCarte() {
		return carte;
	}
}
