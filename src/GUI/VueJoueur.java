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

public class VueJoueur implements Observer {
	private Joueur j;
	private JPanel jPanel;
	private JPanel wrap;
	private int axis;

	public VueJoueur(Joueur j, int axis) {
		this.j = j;
		this.axis = axis;
		j.addObserver(this);
		Partie.getInstance().addObserver(this);

		jPanel = new JPanel();
		jPanel.setLayout(new BoxLayout(jPanel, axis));

		wrap = new JPanel();
		wrap.setLayout(new BoxLayout(wrap, axis));
		wrap.add(jPanel);
		JScrollPane jscrlpBox = new JScrollPane(jPanel);
		wrap.add(jscrlpBox);
	}

	public JPanel getJPanel() {
		return wrap;
	}

	@Override
	public void update(Observable joueur, Object arg) {
		jPanel.removeAll();

		if (axis == BoxLayout.X_AXIS)
			jPanel.add(Box.createHorizontalGlue());
		else
			jPanel.add(Box.createVerticalGlue());

		ArrayList<Carte> cartes = j.getCarteEnmain();
		for (int i = 0; i < cartes.size(); i++) {
			if (Partie.getInstance().getJoueurActuel().equals(j) && j.isReel()) {
				final VueCarte vc = new VueCarte(cartes.get(i));
				JLabel carte = vc.getJLabel();

				carte.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent me) {
						if (vc.getCarte().getType() == "PlusQuatre"
								|| vc.getCarte().getType() == "Joker") {
							final JFrame choixCouleur = new JFrame();
							JPanel panel = new JPanel();
							panel.setLayout(new BoxLayout(panel,
									BoxLayout.X_AXIS));

							JButton rouge = new JButton("Rouge");
							rouge.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent event) {
									Talon.getInstance().setCouleur(2);
									choixCouleur.dispose();
								}
							});

							JButton vert = new JButton("Vert");
							vert.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent event) {
									Talon.getInstance().setCouleur(1);
									choixCouleur.dispose();
								}
							});

							JButton bleu = new JButton("Bleu");
							bleu.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent event) {
									Talon.getInstance().setCouleur(0);
									choixCouleur.dispose();
								}
							});

							JButton jaune = new JButton("Jaune");
							jaune.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent event) {
									Talon.getInstance().setCouleur(3);
									choixCouleur.dispose();
								}
							});

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
						if (!j.jouer(vc.getCarte())) {
							JOptionPane.showMessageDialog(null,
									"Carte non jouable.", "Erreur",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				});

				jPanel.add(vc.getJLabel());
			} else {
				try {
					BufferedImage img = ImageIO.read(new File("img/back.png"));
					JLabel carte = new JLabel(new ImageIcon(img));
					jPanel.add(carte);
				} catch (IOException e) {
				}
			}
		}

		if (axis == BoxLayout.X_AXIS)
			jPanel.add(Box.createHorizontalGlue());
		else
			jPanel.add(Box.createVerticalGlue());

		jPanel.validate();
		jPanel.repaint();
	}

}
