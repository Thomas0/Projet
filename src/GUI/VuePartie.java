package GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Core.Joueur;
import Core.Partie;

public class VuePartie implements Observer {
	private Partie partie = Partie.getInstance();
	private JFrame fenetre;
	JLabel pioche;
	JLabel talon;
	ArrayList<Joueur> joueurs;

	public VuePartie() {
		partie.addObserver(this);
		joueurs = partie.getJoueur();

		fenetre = new JFrame("UNO");
		fenetre.setLayout(new BorderLayout());
		Container reservoir = fenetre.getContentPane();

		VueJoueur vj0 = new VueJoueur(joueurs.get(0), BoxLayout.X_AXIS);
		reservoir.add(vj0.getJPanel(), BorderLayout.SOUTH);

		VueJoueur vj1 = new VueJoueur(joueurs.get(1), BoxLayout.Y_AXIS);
		reservoir.add(vj1.getJPanel(), BorderLayout.WEST);

		if (joueurs.size() >= 3) {
			VueJoueur vj2 = new VueJoueur(joueurs.get(2), BoxLayout.X_AXIS);
			reservoir.add(vj2.getJPanel(), BorderLayout.NORTH);

			if (joueurs.size() == 4) {
				VueJoueur vj3 = new VueJoueur(joueurs.get(3), BoxLayout.Y_AXIS);
				reservoir.add(vj3.getJPanel(), BorderLayout.EAST);
			}
		}

		JPanel center = new JPanel();
		center.setLayout(new BoxLayout(center, BoxLayout.X_AXIS));
		center.add(Box.createHorizontalGlue());
		reservoir.add(center, BorderLayout.CENTER);

		VueTalon vt = new VueTalon();
		center.add(vt.getJlabel());

		try {
			BufferedImage imgPioche = ImageIO.read(new File("img/back.png"));
			JLabel pioche = new JLabel(new ImageIcon(imgPioche));

			pioche.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent me) {
					partie.getJoueurActuel().piocher();
				}
			});

			center.add(pioche);
		} catch (IOException e) {
		}

		center.add(Box.createHorizontalGlue());

		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.pack();
		fenetre.setVisible(true);

		partie.demarrer();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (partie.isEstFiniPartie()) {
			StringBuilder str = new StringBuilder(
					"La partie est fini.\n\nScore :\n");

			for (int i = 0; i < partie.getJoueur().size(); i++) {
				str.append("J" + i + " : "
						+ partie.getJoueur().get(i).getScore() + "\n");
			}

			str.append("\nRejouer ?");

			int reply = JOptionPane.showConfirmDialog(null, str.toString(),
					"Info", JOptionPane.YES_NO_OPTION);

			synchronized (partie) {
				partie.setWait(false);
				partie.notify();
			}

			if (reply == JOptionPane.YES_OPTION) {
				partie.clearPartie();
				partie.demarrer();
			} else {
				fenetre.dispose();
				new VueParametrePartie();
			}
		}

		if (partie.isEstFiniManche() && !partie.isEstFiniPartie()) {
			StringBuilder str = new StringBuilder(
					"La manche est fini.\n\nScore :\n");

			for (int i = 0; i < partie.getJoueur().size(); i++) {
				str.append("J" + i + " : "
						+ partie.getJoueur().get(i).getScore() + "\n");
			}

			JOptionPane.showMessageDialog(null, str.toString(), "Info",
					JOptionPane.INFORMATION_MESSAGE);

			synchronized (partie) {
				partie.setWait(false);
				partie.notify();
			}
		}
	}
}
