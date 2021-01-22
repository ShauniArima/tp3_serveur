package modele;

import java.net.URI;
import java.util.Optional;
import java.util.concurrent.Future;

import infrastructure.jaxrs.HyperLien;
import infrastructure.langage.Types;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;

import javax.ws.rs.core.UriBuilder;

import configuration.JAXRS;

public abstract class RechercheAsynchroneAbstraite implements AlgorithmeRecherche {

	protected Future<Optional<HyperLien<Livre>>> rechercheAsync(HyperLien<Bibliotheque> h, Livre l, Client client){
		URI uri = UriBuilder.fromUri(h.getUri()).path(JAXRS.SOUSCHEMIN_ASYNC).build();
		WebTarget target = client.target(uri);
		return target.request().async().put(Entity.entity(l, JAXRS.TYPE_MEDIA), Types.typeRetourChercherAsync());
	};

	protected Future<Optional<HyperLien<Livre>>> rechercheAsyncAvecRappel(
			HyperLien<Bibliotheque> h, Livre l, Client client,  
			InvocationCallback<Optional<HyperLien<Livre>>> retour){
		URI uri = UriBuilder.fromUri(h.getUri()).path(JAXRS.SOUSCHEMIN_ASYNC).build();
		WebTarget target = client.target(uri);
		return target.request().async().method("PUT", Entity.entity(l, JAXRS.TYPE_MEDIA), Types.typeRetourChercherAsync());
	}
}
