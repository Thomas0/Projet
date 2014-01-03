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

public class VueParametrePartie {
	private Partie partie = Partie.getInstance();
	private JFrame fenetre;
	private JComboBox<String> points;
	private JComboBox<String> strategy;
	private JButton valider;
	private JSpinner nbReel;
	private JSpinner nbVirtuel;

	public VueParametrePartie() {
		JLabel txtPoints = new JLabel("Type de comptage des points");
		JLabel txtReel = new JLabel("Nombre de joueur réel");
		JLabel txtVirtuel = new JLabel("Nombre de joueur virtuel");

		points = new JComboBox<String>();
		points.addItem("Le vainqueur prend tout");
		points.addItem("Tout le monde marque des points");

		nbReel = new JSpinner();
		nbReel.setModel(new SpinnerNumberModel(0, 0, 4, 1));
		nbReel.setEditor(new JSpinner.NumberEditor(nbReel, "0"));

		nbVirtuel = new JSpinner();
		nbVirtuel.setModel(new SpinnerNumberModel(0, 0, 4, 1));
		nbVirtuel.setEditor(new JSpinner.NumberEditor(nbVirtuel, "0"));

		strategy = new JComboBox<String>();
		strategy.addItem("Normal");
		strategy.addItem("Offensif");

		valider = new JButton("Valider");

		fenetre = new JFrame("Paramètre de la partie");
		fenetre.setLayout(new GridLayout(4, 3));
		Container reservoir = fenetre.getContentPane();

		reservoir.add(txtPoints);
		reservoir.add(points);

		reservoir.add(txtReel);
		reservoir.add(nbReel);

		reservoir.add(txtVirtuel);
		reservoir.add(nbVirtuel);

		reservoir.add(valider);

		reservoir.add(strategy);

		valider.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				int reel = (int) nbReel.getValue();
				int virtuel = (int) nbVirtuel.getValue();

				if (reel + virtuel > 4) {
					JOptionPane.showMessageDialog(null,
							"Le nombre de joueur maximum est de quatre.",
							"Erreur", JOptionPane.ERROR_MESSAGE);
				} else if (reel + virtuel < 2) {
					JOptionPane.showMessageDialog(null,
							"Il faut au moins deux joueurs.", "Erreur",
							JOptionPane.ERROR_MESSAGE);
				} else {
					if (points.getSelectedIndex() == 0)
						partie.setTypeComptage(false);
					else
						partie.setTypeComptage(true);

					partie.ajouterJoueurReel(reel);
					partie.ajouterJoueurVirtuel(virtuel);

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

					fenetre.dispose();
					new VuePartie();
				}
			}
		});

		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.pack();
		fenetre.setVisible(true);
	}

	public static void main(String[] args) {
		new VueParametrePartie();
	}
}
