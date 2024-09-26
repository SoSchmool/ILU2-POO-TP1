package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtals);
	}
	
	private static class Marche{
		private Etal[] etals;
		private int nbEtals;
		
		private Marche(int nbEtals) {
			this.nbEtals = nbEtals;
			etals = new Etal[nbEtals];
			for (int i=0; i <nbEtals; i++) {
				etals[i] = new Etal();
			}
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			for(int i=0; i<this.nbEtals; i++) {
				if(!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			int nombreEtal=0;
			int compteur=0;
			for (int i=0; i<this.nbEtals; i++) {
				if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) 
					nombreEtal++;
			}
			Etal[] etalOccupe = new Etal[nombreEtal];
			for(int j=0; j<this.nbEtals; j++) {
				if(etals[j].contientProduit(produit)) {
					etalOccupe[compteur]= etals[j];
					compteur++;
				}
			}
			return etalOccupe;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for(int i=0; i<this.nbEtals; i++) {
				if (etals[i].getVendeur().getNom().equals(gaulois.getNom()))
					return etals[i];
			}
			return null;
		}
		
		private String afficherMarche() {
			StringBuilder chaineMarche = new StringBuilder();
			int nbEtalVide = this.nbEtals;
			for (int i=0; i<this.nbEtals; i++) {
				if (etals[i].isEtalOccupe()) {
					chaineMarche.append(etals[i].afficherEtal());
					nbEtalVide--;
				}
			}
			chaineMarche.append("Il reste " + nbEtalVide + " etals non utilisés dans le marché. \n");
			return chaineMarche.toString();
		}
		
		
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException{
		StringBuilder chaine = new StringBuilder();
		if (chef == null)
			throw new VillageSansChefException("Il n'y a pas de chef");
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les lÃ©gendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		int numEtal;
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		numEtal = marche.trouverEtalLibre();
		marche.utiliserEtal(numEtal, vendeur, produit, nbProduit);
		chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°" + (numEtal+1) + ".\n");
		
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etalsProduit = marche.trouverEtals(produit);
		if (etalsProduit.length == 0) {
			chaine.append("Il n'y a pas de vendeur qui propose des " + produit + " au marché");
		}else if(etalsProduit.length == 1){
			chaine.append("Seul le vendeur " + etalsProduit[0].getVendeur().getNom() + " propose des " + produit + " au marché");
		}else {
			chaine.append("Les vendeurs qui proposent des " + produit + " sont :\n");
			for (int i=0; i<etalsProduit.length; i++) {
				chaine.append("- " + etalsProduit[i].getVendeur().getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		StringBuilder chaine = new StringBuilder();
		Etal etalVendeur = rechercherEtal(vendeur);
		chaine.append(etalVendeur.libererEtal());
		return chaine.toString();
	}

	
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Le marché du village " + this.nom + "possède plusieurs étals :\n" + marche.afficherMarche());
		return chaine.toString(); 
	}
	
	
}