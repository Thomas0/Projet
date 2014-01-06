package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Core.Carte;
import Core.Joueur;
import Core.Partie;
import Core.Talon;

/**
 * Classe gérant la vue d'un joueur
 * 
 * @see Core.Joueur
 * 
 */
public class VueJoueur implements Observer {
	/**
	 * Le joueur géré par la vue
	 */
	private Joueur j;

	/**
	 * JPanel contenant l'ensemble des cartes du joueur
	 */
	private JPanel jPanel;

	/**
	 * Conteneur global des infos du joueur
	 */
	private JPanel wrap;

	/**
	 * Axe dans lequel afficher le joueur (BoxLayout.X_AXIS ou BoxLayout.Y_AXIS)
	 * 
	 * @see BoxLayout
	 */
	private int axis;

	/**
	 * Constructeur de la vue du joueur. Créé les élements de base de la vue.
	 * 
	 * @param j
	 *            Le joueur à inclure dans la vue
	 * @param axis
	 *            Axe dans lequel afficher le joueur (BoxLayout.X_AXIS ou
	 *            BoxLayout.Y_AXIS)
	 */
	public VueJoueur(Joueur j, int axis) {
		// Initialisation variable
		this.j = j;
		this.axis = axis;

		// Ajout dans les observers
		j.addObserver(this);
		Partie.getInstance().addObserver(this);

		// Création du JPanel
		jPanel = new JPanel();
		jPanel.setLayout(new BoxLayout(jPanel, axis));

		// Création du wrap
		wrap = new JPanel();
		wrap.setLayout(new BoxLayout(wrap, axis));

		// Ajout Jpanel et scrollbar
		wrap.add(jPanel);
		JScrollPane jscrlpBox = new JScrollPane(jPanel);
		wrap.add(jscrlpBox);
	}

	/**
	 * Récupère le Jpanel contenant les infos du joueur
	 * 
	 * @return Jpanel contenant les infos du joueur
	 */
	public JPanel getJPanel() {
		return wrap;
	}

	/**
	 * Fonction appelé lors de notification des classes observées : mise à jour
	 * des cartes en main
	 * 
	 * @param o
	 *            Objet ayant déclenché la notification
	 * @param arg
	 *            Argument passé durant la notification
	 * @see Core.Partie Core.Joueur
	 */
	@Override
	public void update(Observable o, Object arg) {
		jPanel.removeAll(); // Suppression de toute les cartes

		// Ajout de "Glue" afin de centrer le contenu
		if (axis == BoxLayout.X_AXIS)
			jPanel.add(Box.createHorizontalGlue());
		else
			jPanel.add(Box.createVerticalGlue());

		ArrayList<Carte> cartes = j.getCarteEnmain(); // Récupération cartes en
														// main
		for (int i = 0; i < cartes.size(); i++) {
			if (Partie.getInstance().getJoueurActuel().equals(j) && j.isReel()) { // C'est
																					// au
																					// tour
																					// de
																					// ce
																					// joueur,
																					// et
																					// il
																					// est
																					// réel
																					// (non
																					// virtuel)
				// Récupération de la vue de la carte
				final VueCarte vc = new VueCarte(cartes.get(i));
				JLabel carte = vc.getJLabel();

				// Ajout d'un évènement lors du clic sur la carte
				carte.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent me) {
						if (vc.getCarte().getType() == "PlusQuatre"
								|| vc.getCarte().getType() == "Joker") { // La
																			// carte
																			// nécessite
																			// le
																			// choix
																			// d'une
																			// couleur
							// Création d'une fenêtre pour le choix
							final JFrame choixCouleur = new JFrame();
							JPanel panel = new JPanel();
							panel.setLayout(new BoxLayout(panel,
									BoxLayout.X_AXIS));

							// Bouton "rouge"
							JButton rouge = new JButton("Rouge");
							rouge.addActionListener(new ActionListener() { // Evenement
																			// déclenchant
																			// le
																			// changement
																			// de
																			// couleur
																			// du
																			// talon
								@Override
								public void actionPerformed(ActionEvent event) {
									Talon.getInstance().setCouleur(2);
									choixCouleur.dispose();
								}
							});

							// Bouton "vert"
							JButton vert = new JButton("Vert");
							vert.addActionListener(new ActionListener() { // Evenement
																			// déclenchant
																			// le
																			// changement
																			// de
																			// couleur
																			// du
																			// talon
								@Override
								public void actionPerformed(ActionEvent event) {
									Talon.getInstance().setCouleur(1);
									choixCouleur.dispose();
								}
							});

							// Bouton "bleu"
							JButton bleu = new JButton("Bleu");
							bleu.addActionListener(new ActionListener() { // Evenement
																			// déclenchant
																			// le
																			// changement
																			// de
																			// couleur
																			// du
																			// talon
								@Override
								public void actionPerformed(ActionEvent event) {
									Talon.getInstance().setCouleur(0);
									choixCouleur.dispose();
								}
							});

							// Bouton "jaune"
							JButton jaune = new JButton("Jaune");
							jaune.addActionListener(new ActionListener() { // Evenement
																			// déclenchant
																			// le
																			// changement
																			// de
																			// couleur
																			// du
																			// talon
								@Override
								public void actionPerformed(ActionEvent event) {
									Talon.getInstance().setCouleur(3);
									choixCouleur.dispose();
								}
							});

							// Ajout éléments à la fenêtre
							panel.add(jaune);
							panel.add(rouge);
							panel.add(vert);
							panel.add(bleu);
							choixCouleur.getContentPane().add(panel);
							choixCouleur
									.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							choixCouleur.pack();
							choixCouleur.setVisible(true);
						}
						if (!j.jouer(vc.getCarte())) { // On joue la carte
							// Si la carte n'est pas jouable, on affiche une
							// erreur
							JOptionPane.showMessageDialog(null,
									"Carte non jouable.", "Erreur",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				});

				jPanel.add(vc.getJLabel()); // Ajout de la carte au conteneur
			} else {
				// Joueur virtuel, ou joueur non actuellement en train de jouer
				// => cartes retournées
				try {
					BufferedImage img = ImageIO.read(new File("img/back.png"));
					JLabel carte = new JLabel(new ImageIcon(img));
					jPanel.add(carte);
				} catch (IOException e) {
				}
			}
		}

		// Ajout de "Glue" afin de centrer le contenu
		if (axis == BoxLayout.X_AXIS)
			jPanel.add(Box.createHorizontalGlue());
		else
			jPanel.add(Box.createVerticalGlue());

		jPanel.validate();
		jPanel.repaint();
	}

}
