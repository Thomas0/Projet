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

public class VueTalon implements Observer {
	private Talon talon;
	private JLabel labelTalon;

	public VueTalon() {
		talon = Talon.getInstance();
		talon.addObserver(this);

		try {
			BufferedImage img = ImageIO.read(new File("img/back.png"));
			labelTalon = new JLabel(new ImageIcon(img));
			labelTalon.setBorder(BorderFactory.createLineBorder(Color.black));
		} catch (IOException e) {
		}
	}

	@Override
	public void update(Observable talon, Object arg) {
		VueCarte vc = new VueCarte(this.talon.derniereCarte());
		labelTalon.setIcon(vc.getJLabel().getIcon());

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

	public JLabel getJlabel() {
		return labelTalon;
	}
}
