package Core;

public class Carte {

	private int numero;
	private boolean estSpecial;	
	private String couleur;

	public final static String[] tcouleur = {"bleu","vert","rouge","jaune"};
	
	Talon talon = Talon.getInstance();
	
	public Carte(int numero,int pcouleur,boolean estSpecial) {
		
		this.numero = numero;
		this.couleur = tcouleur[pcouleur];
		this.estSpecial = estSpecial;
		
	}
	
	public boolean peutEtreJouer() {
		
		if (this.getCouleur() == talon.derniereCarte().getCouleur() || this.getNumero() == talon.derniereCarte().getNumero()) {
			
		return true;
		
		}
		
		else
		{
			return false;
		}
		
	}
	
	public String getCouleur() {
		return couleur;
	}
	
	public int getNumero() {
		return numero;
	}

}


