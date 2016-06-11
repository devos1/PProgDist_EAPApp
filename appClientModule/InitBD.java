import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.PersistenceException;
import entities.CategorieVehicule;
import entities.EnumCatPrix;
import entities.EnumMarque;
import entities.EnumTypePlace;
import entities.EnumUnite;
import entities.Place;
import entities.Vehicule;
import session.*;

public class InitBD {
	public static void main(String[] args) {
		try {
			Properties prop = new Properties();
			prop.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
			Context ctx = new InitialContext(prop);
			IMobiOsLoRemote service = (IMobiOsLoRemote) ctx.lookup("ejb:PProgDist_EAP/PProgDist_EAPEJB/MobiOsLoService!session.IMobiOsLoRemote?stateful");

			// ======================================
			// CATEGORIE DE VEHICULES            
			// ======================================
			// Création et insertion catégories dans la base
			service.addCatVehicule(new CategorieVehicule("A",2.0,3.0,EnumUnite.Heure));
			service.addCatVehicule(new CategorieVehicule("B",3.0,14.1,EnumUnite.Minute));

			// Lister les catégories
			List<CategorieVehicule> catVehicules = service.getCatVehicules();
			for (CategorieVehicule cv : catVehicules) {
				System.out.println(cv);
			}
			// ======================================
			// FIN CATEGORIE DE VEHICULES            
			// ======================================
		
			// ======================================
			// VEHICULES            
			// ======================================
			service.addVehicule(new Vehicule(new GregorianCalendar(2014, 0, 1), "Bleu", EnumCatPrix.Budget, EnumMarque.Audi, "A1"));
			service.addVehicule(new Vehicule(new GregorianCalendar(2010, 4, 12), "Gris", EnumCatPrix.Cabrio, EnumMarque.BMW, "Z3"));
			service.addVehicule(new Vehicule(new GregorianCalendar(2001, 2, 14), "Noir", EnumCatPrix.Economy, EnumMarque.Dacia, "Entry"));
			service.addVehicule(new Vehicule(new GregorianCalendar(2012, 1, 7), "Blanc", EnumCatPrix.Electro, EnumMarque.Renault, "Zoe"));
			service.addVehicule(new Vehicule(new GregorianCalendar(2015, 8, 8), "Rouge", EnumCatPrix.Emotion, EnumMarque.BMW, "M3"));
			// ======================================
			// FIN VEHICULES            
			// ======================================
			
			// ======================================
			// PLACES            
			// ======================================
			for (int i = 0; i < 20; i++) {
				service.addPlace(new Place(EnumTypePlace.Voiture));
			}
			for (int i = 0; i < 10; i++) {
				service.addPlace(new Place(EnumTypePlace.Vélo));
			}
			// ======================================
			// FIN PLACES            
			// ======================================
			
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		} catch (NamingException ne){
			ne.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}