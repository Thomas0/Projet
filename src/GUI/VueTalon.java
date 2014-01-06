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
 * Classe g�rant la vue du talon
 * 
 * @see Core.Talon
 */
public class VueTalon implements Observer {
	/**
	 * Talon g�r� par la vue
	 */
	private Talon talon;

	/**
	 * JLabel repr�sentant la vue
	 */
	private JLabel labelTalon;

	/**
	 * Constructeur de la vue : cr�ation du JLabel
	 */
	public VueTalon() {
		// R�cup�ration du talon et ajout aux observers
		talon = Talon.getInstance();
		talon.addObserver(this);

		try {
			// Cr�ation d'un label avec image dos de carte
			BufferedImage img = ImageIO.read(new File("img/back.png"));
			labelTalon = new JLabel(new ImageIcon(img));
			labelTalon.setBorder(BorderFactory.createLineBorder(Color.black));
		} catch (IOException e) {
		}
	}

	/**
	 * Fonction appel� lors de notification de la classe Talon. Met � jour
	 * l'image de la carte sur le dessus du talon.
	 * 
	 * @param o
	 *            Objet ayant d�clench� la notification
	 * @param arg
	 *            Argument pass� durant la notification
	 * @see Core.Talon
	 */
	@Override
	public void update(Observable o, Object arg) {
		// R�cup�ration et cr�ation de la vue de la carte sur le ton
		VueCarte vc = new VueCarte(this.talon.derniereCarte());
		labelTalon.setIcon(vc.getJLabel().getIcon());

		// Modification de la couleur de la bordure du JLabel en fonction de la
		// couleur du talon (Utile apr�s Joker ou +4)
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
	 * R�cup�re le JLabel repr�sentant le Talon
	 * 
	 * @return JLabel repr�sentant le talon
	 */
	public JLabel getJlabel() {
		return labelTalon;
	}
}
