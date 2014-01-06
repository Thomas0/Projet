package GUI;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import Core.Partie;
import Core.StrategieA;
import Core.StrategieB;

/**
 * Class affichant une fenêtre permettant le choix des options de la Partie
 * 
 * @see Core.Partie
 * 
 */
public class VueParametrePartie {
	/**
	 * Partie à paramétrer
	 */
	private Partie partie = Partie.getInstance();

	/**
	 * La fenêtre
	 */
	private JFrame fenetre;

	/**
	 * Liste déroulant permettant de choisir la méthode de comptage de points
	 */
	private JComboBox<String> points;

	/**
	 * Liste déroulant permettant de choisir la stratégie des joueurs virtuels
	 */
	private JComboBox<String> strategy;

	/**
	 * Bouton démarrant la partie
	 */
	private JButton valider;

	/**
	 * Champs permettant de définir le nombre de joueurs réels
	 */
	private JSpinner nbReel;

	/**
	 * Champs permettant de définir le nombre de joueurs virtuels
	 */
	private JSpinner nbVirtuel;

	/**
	 * Constructeur de la fenêtre. Créé le formulaire permettant de paramétrer
	 * la partie
	 */
	public VueParametrePartie() {
		// Création des labels
		JLabel txtPoints = new JLabel("Type de comptage des points");
		JLabel txtReel = new JLabel("Nombre de joueur réel");
		JLabel txtVirtuel = new JLabel("Nombre de joueur virtuel");

		// Combobox du choix de la méthode de comptage de points
		points = new JComboBox<String>();
		points.addItem("Le vainqueur prend tout");
		points.addItem("Tout le monde marque des points");

		// Champ numérique pour le choix du nombre de joueur réel
		nbReel = new JSpinner();
		nbReel.setModel(new SpinnerNumberModel(0, 0, 4, 1));
		nbReel.setEditor(new JSpinner.NumberEditor(nbReel, "0"));

		// Champs numérique pour le choix du nombre de joueur virtuel
		nbVirtuel = new JSpinner();
		nbVirtuel.setModel(new SpinnerNumberModel(0, 0, 4, 1));
		nbVirtuel.setEditor(new JSpinner.NumberEditor(nbVirtuel, "0"));

		// Combobox pour le choix de la stratégie des joueurs virtuel
		strategy = new JComboBox<String>();
		strategy.addItem("Normal");
		strategy.addItem("Offensif");

		// Bouton démarrant la partie
		valider = new JButton("Valider");

		// Création de la fenêtre
		fenetre = new JFrame("Paramètre de la partie");
		fenetre.setLayout(new GridLayout(4, 3));
		Container reservoir = fenetre.getContentPane();

		// Ajout des éléments à la fenêtre
		reservoir.add(txtPoints);
		reservoir.add(points);

		reservoir.add(txtReel);
		reservoir.add(nbReel);

		reservoir.add(txtVirtuel);
		reservoir.add(nbVirtuel);

		reservoir.add(valider);

		reservoir.add(strategy);

		// Evenement lors de la validation du formulaire
		valider.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				// Récupération des nombres de joueurs
				int reel = (int) nbReel.getValue();
				int virtuel = (int) nbVirtuel.getValue();

				// On vérifit le nombre de joueurs
				if (reel + virtuel > 4) {
					JOptionPane.showMessageDialog(null,
							"Le nombre de joueur maximum est de quatre.",
							"Erreur", JOptionPane.ERROR_MESSAGE);
				} else if (reel + virtuel < 2) {
					JOptionPane.showMessageDialog(null,
							"Il faut au moins deux joueurs.", "Erreur",
							JOptionPane.ERROR_MESSAGE);
				} else {
					// Définission de la méthode de comptage des points
					if (points.getSelectedIndex() == 0)
						partie.setTypeComptage(false);
					else
						partie.setTypeComptage(true);

					// Ajout des joueurs
					partie.ajouterJoueurReel(reel);
					partie.ajouterJoueurVirtuel(virtuel);

					// Définnissions de la stratégie des joueurs virtuels
					if (strategy.getSelectedIndex() == 0)
						for (int i = 0; i < partie.getJoueur().size(); i++) {
							if (!partie.getJoueur().get(i).isReel())
								partie.getJoueur().get(i)
										.setStrategy(new StrategieA());
						}
					else
						for (int i = 0; i < partie.getJoueur().size(); i++) {
							if (!partie.getJoueur().get(i).isReel())
								partie.getJoueur().get(i)
										.setStrategy(new StrategieB());
						}

					// Lancement de la partie
					fenetre.dispose();
					new VuePartie();
				}
			}
		});

		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.pack();
		fenetre.setVisible(true);
	}

	/**
	 * Fonction main affichant la fenêtre de réglages
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new VueParametrePartie();
	}
}
