import java.util.ArrayList;
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
import entities.Station;
import entities.Vehicule;
import session.*;

public class InitBD {
	public static void main(String[] args) {
		
		ArrayList<Place> places = new ArrayList<Place>();
		ArrayList<Vehicule> vehicules = new ArrayList<Vehicule>();
		
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
			
			// ======================================
			// FIN CATEGORIE DE VEHICULES            
			// ======================================
		
			// ======================================
			// VEHICULES            
			// ======================================
			// Ajout véhicules dans une liste
			vehicules.add(new Vehicule(new GregorianCalendar(2014, 0, 1), "Bleu", EnumCatPrix.Budget, EnumMarque.Audi, "A1"));			
			vehicules.add(new Vehicule(new GregorianCalendar(2010, 4, 12), "Gris", EnumCatPrix.Cabrio, EnumMarque.BMW, "Z3"));
			vehicules.add(new Vehicule(new GregorianCalendar(2001, 2, 14), "Noir", EnumCatPrix.Economy, EnumMarque.Dacia, "Entry"));
			vehicules.add(new Vehicule(new GregorianCalendar(2012, 1, 7), "Blanc", EnumCatPrix.Electro, EnumMarque.Renault, "Zoe"));
			vehicules.add(new Vehicule(new GregorianCalendar(2015, 8, 8), "Rouge", EnumCatPrix.Emotion, EnumMarque.BMW, "M3"));	
			
			// ======================================
			// FIN VEHICULES            
			// ======================================
			
			// ======================================
			// PLACES            
			// ======================================
			for (int i = 0; i < 20; i++) {
				Place p = new Place(EnumTypePlace.Voiture);
				places.add(p);
			}
			for (int i = 0; i < 10; i++) {
				Place p = new Place(EnumTypePlace.Vélo);
				places.add(p);
			}
			
			// Ajout des véhicules dans les places
			for (int i = 0; i < vehicules.size(); i++) {				
				places.get(i).setVehicule(vehicules.get(i));			
				places.get(i).getVehicule().setPlace(places.get(i));
			}
			// ======================================
			// FIN PLACES            
			// ======================================
			
			// ======================================
			// STATIONS      
			// ======================================
			Station station = new Station(0, 0, "STATION A", "Suisse", "Yverdon");
			for (Place place : places) {
				station.addPlace(place);
			}
			service.addStation(station);
			//service.addStation(new Station(0, 0, "STATION A", "Suisse", "Yverdon"));
			
			// ======================================
			// FIN STATIONS            
			// ======================================
			
			// ======================================
			// AFFICHAGE CONSOLE            
			// ======================================
			// Lister les catégories
			List<CategorieVehicule> catVehicules = service.getCatVehicules();
			for (CategorieVehicule cv : catVehicules) {
				System.out.println(cv);
			}
			
			// Lister les véhicules
/*			List<Vehicule> vehiculesList = service.getVehicules();
			for (Vehicule v : vehiculesList) {
				System.out.println(v);
			}*/
			
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		} catch (NamingException ne){
			ne.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}