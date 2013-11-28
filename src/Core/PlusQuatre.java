package Core;

import java.util.Scanner;

public class PlusQuatre extends Carte{

	public PlusQuatre(String ptype, int pcouleur, boolean estSpecial) {
		super(ptype, pcouleur, estSpecial);
	}

	public void ajoutquatre() {
		Partie partie = Partie.getInstance();
		Joueur jactuel = partie.getJoueurActuel();
		
		if (partie.isSens() == true){
		Joueur j = partie.getJoueursuivant();
		
		j.ajoutCarte(Pioche.getInstance().piocher());
		j.ajoutCarte(Pioche.getInstance().piocher());
		j.ajoutCarte(Pioche.getInstance().piocher());
		j.ajoutCarte(Pioche.getInstance().piocher());
		System.out.println("Le joueur " + j.getId() + " a piocher 4 cartes et passe son tour");
		partie.setJoueursuivant(partie.getNumJoueurSuivant());
		
		if (jactuel.isReel() == true){
		Scanner sc = new Scanner(System.in);
		System.out.println("Choisir une couleur :");
		System.out.println("0 = bleu | 1 = vert | 2 = rouge | 3 = jaune");
		String str = sc.nextLine();
		int couleur = Integer.parseInt(str);
		talon.setCouleur(couleur);
		}else {
			talon.setCouleur(0 + (int)(Math.random() * ((3 - 0) + 1)));
		}
		
		}
		else {
			
			Joueur j = partie.getJoueurPrecedent();
			j.ajoutCarte(Pioche.getInstance().piocher());
			j.ajoutCarte(Pioche.getInstance().piocher());
			j.ajoutCarte(Pioche.getInstance().piocher());
			j.ajoutCarte(Pioche.getInstance().piocher());
			System.out.println("Le joueur " + j.getId() + " a pioché 4 cartes et passe son tour");
			partie.setJoueursuivant(partie.getNumJoueurPrecedent());
			
			if (jactuel.isReel() ==true){
				Scanner sc = new Scanner(System.in);
				System.out.println("Choisir une couleur :");
				System.out.println("0 = bleu | 1 = vert | 2 = rouge | 3 = jaune");
				String str = sc.nextLine();
				int couleur = Integer.parseInt(str);
				talon.setCouleur(couleur);
				}else {
					talon.setCouleur(0 + (int)(Math.random() * ((3 - 0) + 1)));
				}
			
		}
	}

}
