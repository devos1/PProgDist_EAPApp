import java.util.List;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.PersistenceException;
import entities.CategorieVehicule;
import session.*;

public class InitBD {
	public static void main(String[] args) {
		try {
			Properties prop = new Properties();
			prop.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
			Context ctx = new InitialContext(prop);
			IMobiOsLoRemote service = (IMobiOsLoRemote) ctx.lookup("ejb:PProgDist_EAP/PProgDist_EAPEJB/MobiOsLoService!session.IMobiOsLoRemote?stateful");

			// Création et insertion catégories dans la base
			service.addCatVehicule(new CategorieVehicule("A",2.0,12.0,0));
			service.addCatVehicule(new CategorieVehicule("B",3.0,14.0,0));

			// Lister les catégories
			List<CategorieVehicule> catVehicules = service.getCatVehicules();
			for (CategorieVehicule cv : catVehicules) {
				System.out.println(cv);
			}
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		} catch (NamingException ne){
			ne.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}