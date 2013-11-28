package Core;

import java.util.Scanner;

public class Joker extends Carte {

	public Joker(String ptype, int pcouleur, boolean estSpecial) {
		super(ptype, pcouleur, estSpecial);
		
	}

	public void changeCouleur(){
		Partie partie = Partie.getInstance();
		Joueur jactuel = partie.getJoueurActuel();
		
		if (partie.isSens() == true){
		
			
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
