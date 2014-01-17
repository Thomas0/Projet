package fr.utt.lo02.gui;

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

import fr.utt.lo02.core.Joueur;
import fr.utt.lo02.core.Partie;

/**
 * Classe gérant l'affichage d'une partie
 * 
 * @see fr.utt.lo02.core.Partie
 */
public class VuePartie implements Observer {
	/**
	 * La partie géré par la vue
	 */
	private Partie partie = Partie.getInstance();

	/**
	 * La fenêtre contenant la partie
	 */
	private JFrame fenetre;

	/**
	 * JLabel représentant la pioche
	 */
	JLabel pioche;

	/**
	 * JLabel représentant le talon
	 * 
	 * @see VueTalon
	 */
	JLabel talon;

	/**
	 * Liste des joueurs
	 */
	ArrayList<Joueur> joueurs;

	/**
	 * Constructeur de la vue. Créé les éléments de base de la partie (joueurs,
	 * talon, pioche).
	 */
	public VuePartie() {
		partie.addObserver(this);
		joueurs = partie.getJoueur();

		// Création de la fenêtre
		fenetre = new JFrame("UNO");
		fenetre.setLayout(new BorderLayout());
		Container reservoir = fenetre.getContentPane();

		// Création vue joueur 0
		VueJoueur vj0 = new VueJoueur(joueurs.get(0), BoxLayout.X_AXIS);
		reservoir.add(vj0.getJPanel(), BorderLayout.SOUTH);

		// Création vue joueur 1
		VueJoueur vj1 = new VueJoueur(joueurs.get(1), BoxLayout.Y_AXIS);
		reservoir.add(vj1.getJPanel(), BorderLayout.WEST);

		if (joueurs.size() >= 3) { // Création vue joueur 3 s'il existe
			VueJoueur vj2 = new VueJoueur(joueurs.get(2), BoxLayout.X_AXIS);
			reservoir.add(vj2.getJPanel(), BorderLayout.NORTH);

			if (joueurs.size() == 4) { // Création vue joueur 4 s'il existe
				VueJoueur vj3 = new VueJoueur(joueurs.get(3), BoxLayout.Y_AXIS);
				reservoir.add(vj3.getJPanel(), BorderLayout.EAST);
			}
		}

		// Création conteneur central
		JPanel center = new JPanel();
		center.setLayout(new BoxLayout(center, BoxLayout.X_AXIS));
		center.add(Box.createHorizontalGlue());
		reservoir.add(center, BorderLayout.CENTER);

		// Création de la vue du talon
		VueTalon vt = new VueTalon();
		center.add(vt.getJlabel());

		try {
			// Création d'un label avec l'image du dos d'une carte pour
			// représenter la pioche
			BufferedImage imgPioche = ImageIO.read(new File("img/back.png"));
			JLabel pioche = new JLabel(new ImageIcon(imgPioche));

			// Evenement lors du clic permettant de piocher
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

		partie.demarrer(); // Démarre la partie
	}

	/**
	 * Fonction appelé lors de notification de la classe Partie. Affiche la fin
	 * de la partie / manche.
	 * 
	 * @param o
	 *            Objet ayant déclenché la notification
	 * @param arg
	 *            Argument passé durant la notification
	 * @see fr.utt.lo02.core.Partie
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (partie.isEstFiniPartie()) { // La partie est fini
			// Affichage des scores
			StringBuilder str = new StringBuilder(
					"La partie est fini.\n\nScore :\n");

			for (int i = 0; i < partie.getJoueur().size(); i++) {
				str.append("J" + i + " : "
						+ partie.getJoueur().get(i).getScore() + "\n");
			}

			str.append("\nRejouer ?");

			// On demande si l'utilisateur veux rejouer
			int reply = JOptionPane.showConfirmDialog(null, str.toString(),
					"Info", JOptionPane.YES_NO_OPTION);

			synchronized (partie) { // On redémarre la partie
				partie.setWait(false);
				partie.notify();
			}

			if (reply == JOptionPane.YES_OPTION) { // Redémarre la partie
				partie.clearPartie();
				partie.demarrer();
			} else { // Demande de nouveau paramètre
				fenetre.dispose();
				new VueParametrePartie();
			}
		}

		if (partie.isEstFiniManche() && !partie.isEstFiniPartie()) { // La
																		// manche
																		// est
																		// fini
			// Affichage des scores
			StringBuilder str = new StringBuilder(
					"La manche est fini.\n\nScore :\n");

			for (int i = 0; i < partie.getJoueur().size(); i++) {
				str.append("J" + i + " : "
						+ partie.getJoueur().get(i).getScore() + "\n");
			}

			JOptionPane.showMessageDialog(null, str.toString(), "Info",
					JOptionPane.INFORMATION_MESSAGE);

			synchronized (partie) { // Redémarrage de la partie
				partie.setWait(false);
				partie.notify();
			}
		}
	}
}
