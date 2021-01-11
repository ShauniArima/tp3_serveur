package modele;

import java.util.Optional;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static configuration.JAXRS.*;

import infrastructure.jaxrs.HyperLien;
import infrastructure.jaxrs.HyperLiens;

public interface Bibliotheque {
		
	@Path("")
	@PUT
	@Consumes(TYPE_MEDIA)
	@Produces(TYPE_MEDIA)
	Optional<HyperLien<Livre>> chercher(Livre l);

	@Path(SOUSCHEMIN_CATALOGUE)
	@Produces(TYPE_MEDIA)
	HyperLiens<Livre> repertorier();
	
}
