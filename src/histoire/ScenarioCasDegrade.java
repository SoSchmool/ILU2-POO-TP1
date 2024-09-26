package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;
import villagegaulois.VillageSansChefException;

public class ScenarioCasDegrade {

	public static void main(String[] args) {
		Village village = new Village("le village des irréductibles", 10, 5);
		Gaulois asterix = new Gaulois("Astérix", 8);
		Gaulois assurancetourix = new Gaulois("Assurancetourix", 2);
		Gaulois brix = new Gaulois("Brix", 12);
		Etal etal = new Etal();
		etal.libererEtal();
		//etal.acheterProduit(0, brix);
		
		village.ajouterHabitant(assurancetourix);
		village.ajouterHabitant(asterix);
		
		try {
			village.afficherVillageois();
		} catch (VillageSansChefException e) {
			e.printStackTrace();
		}
		
		System.out.println("Fin du test");

	}

}
