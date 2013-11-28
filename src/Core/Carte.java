package Core;

public class Carte {

	private int numero;
	private boolean estSpecial;	
	private String couleur;
	private String type;

	public final static String[] tcouleur = {"bleu","vert","rouge","jaune"," "};
	public final static String[] tspecial = {"Inversion","PlusDeux","PlusQuatre","PasserTour","Joker"};
	
	Talon talon = Talon.getInstance();
	
	public Carte(int numero,int pcouleur,boolean estSpecial) {
		
		this.numero = numero;
		this.couleur = tcouleur[pcouleur];
		this.estSpecial = estSpecial;
		
	}
	
	public Carte(String ptype, int pcouleur,boolean estSpecial){
		
		this.type = tspecial[Integer.parseInt(ptype)];
		this.couleur = tcouleur[pcouleur];
		this.estSpecial = estSpecial;
	}
	


	public boolean peutEtreJouer() {
	Carte c;
	c = talon.derniereCarte();
if (c.isEstSpecial() == false)	{
	if(this.estSpecial == false) {
		
		if (this.getCouleur() == talon.derniereCarte().getCouleur() || this.getNumero() == talon.derniereCarte().getNumero()) {
			
		return true;
		
		}
		
		else
		{
			return false;
		}
		
		}
	else{
		switch(type){
		
		case "Inversion":
		case "PlusDeux":
		case "PasserTour":
			if (this.getCouleur() == c.getCouleur()) {
			
				return true;
				
				}
			else
				{ 
				return false;
				}			
		default:
			return true;			
		}
		
		}
}
	else {
		
		if(this.estSpecial == false) {
			
			if (this.getCouleur() == talon.derniereCarte().getCouleur()) {
				
			return true;
			
			}
			
			else
			{
				return false;
			}
			
			}
		else {
			switch(type){
			
			case "Inversion":
			case "PlusDeux":
			case "PasserTour":
				if (this.getCouleur() == c.getCouleur() || this.getType() == c.getType()) {
				
					return true;
					
					}
				else
					{ 
					return false;
					}			
			default:
				return true;			
			}
		
		}
		
	}
	}
	
	public void effet(){
		
		switch(type) {
		case "Inversion":
			Inversion inversion = new Inversion("0",0,true);
			inversion.inverse();
			break;
		case "PlusDeux":
			PlusDeux plusdeux = new PlusDeux("1",0,true);
			plusdeux.ajoutdeux();
			break;
		case "PlusQuatre":
			PlusQuatre plusquatre = new PlusQuatre("2",4,true);
			plusquatre.ajoutquatre();
			break;
		case "PasserTour":
			PasseTour pass = new PasseTour("3",0,true);
			pass.passertour();
			break;
		case "Joker":
			Joker joker = new Joker("4",0,true);
			joker.changeCouleur();
			break;
		}
		
	}
	
	public boolean isEstSpecial() {
		return estSpecial;
	}

	public String getCouleur() {
		return couleur;
	}
	
	public int getNumero() {
		return numero;
	}
	
	public String getType() {
		return type;
	}

	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}
	
	

}


