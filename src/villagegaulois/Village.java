package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;
	
	private static class Marche {
		private Etal[] etals;

		private Marche(int nbEtals) {
			etals = new Etal[nbEtals];
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			int cpt = -1;
			for (int i=0;i<etals.length;i++) {
				if (! (etals[i].isEtalOccupe())) {
					cpt = i;
					break;
				}
			}
			return cpt;
		}
		
		private Etal[] trouverEtals(String produit) {
			int nb=0;
			for (int i=0;i<etals.length;i++) {
				if (etals[i].contientProduit(produit))
					nb++;
			}
			Etal[] tabEtal = new Etal[nb];
			int e=0;
			for (int i=0;i<etals.length;i++) {
				if (etals[i].contientProduit(produit)) {
					tabEtal[e] = etals[i];
					e++;
					}
				}
			return tabEtal;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i<etals.length;i++) {
				if (etals[i].getVendeur().equals(gaulois))
					return etals[i];
					break;
			}
			return null;
		}
		
		private String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			int cpt = 0;
			for (int i=0;i<etals.length;i++) {
				if (etals[i].isEtalOccupe()) {
					chaine.append(etals[i].afficherEtal());
				} else cpt++;
			}
			if (cpt!=0) chaine.append("Il reste " + cpt + " étals non utilisés dans le marché.\n");
			return chaine.toString();
		}
	}

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		Marche marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " 
		+ nbProduit + " " + produit +". \n");
		int etal = marche.trouverEtalLibre();
		marche.utiliserEtal(etal, vendeur, produit, nbProduit);
		chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " a l'etal n° " + etal + ". \n");
		return chaine.toString();
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

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
}