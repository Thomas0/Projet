package GUI;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Core.Talon;

/**
 * Classe gérant la vue du talon
 * 
 * @see Core.Talon
 */
public class VueTalon implements Observer {
	/**
	 * Talon géré par la vue
	 */
	private Talon talon;

	/**
	 * JLabel représentant la vue
	 */
	private JLabel labelTalon;

	/**
	 * Constructeur de la vue : création du JLabel
	 */
	public VueTalon() {
		// Récupération du talon et ajout aux observers
		talon = Talon.getInstance();
		talon.addObserver(this);

		try {
			// Création d'un label avec image dos de carte
			BufferedImage img = ImageIO.read(new File("img/back.png"));
			labelTalon = new JLabel(new ImageIcon(img));
			labelTalon.setBorder(BorderFactory.createLineBorder(Color.black));
		} catch (IOException e) {
		}
	}

	/**
	 * Fonction appelé lors de notification de la classe Talon. Met à jour
	 * l'image de la carte sur le dessus du talon.
	 * 
	 * @param o
	 *            Objet ayant déclenché la notification
	 * @param arg
	 *            Argument passé durant la notification
	 * @see Core.Talon
	 */
	@Override
	public void update(Observable o, Object arg) {
		// Récupération et création de la vue de la carte sur le ton
		VueCarte vc = new VueCarte(this.talon.derniereCarte());
		labelTalon.setIcon(vc.getJLabel().getIcon());

		// Modification de la couleur de la bordure du JLabel en fonction de la
		// couleur du talon (Utile après Joker ou +4)
		if (this.talon.derniereCarte().getCouleur() == "bleu") {
			labelTalon.setBorder(BorderFactory.createLineBorder(Color.blue));
		} else if (this.talon.derniereCarte().getCouleur() == "rouge") {
			labelTalon.setBorder(BorderFactory.createLineBorder(Color.red));
		} else if (this.talon.derniereCarte().getCouleur() == "vert") {
			labelTalon.setBorder(BorderFactory.createLineBorder(Color.green));
		} else if (this.talon.derniereCarte().getCouleur() == "jaune") {
			labelTalon.setBorder(BorderFactory.createLineBorder(Color.yellow));
		}
	}

	/**
	 * Récupère le JLabel représentant le Talon
	 * 
	 * @return JLabel représentant le talon
	 */
	public JLabel getJlabel() {
		return labelTalon;
	}
}
