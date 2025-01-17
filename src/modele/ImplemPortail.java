package modele;

import infrastructure.jaxrs.HyperLien;
import infrastructure.jaxrs.HyperLiens;
import infrastructure.jaxrs.LienVersRessource;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Singleton;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.container.AsyncResponse;
import configuration.Initialisation;
import configuration.JAXRS;
import configuration.Orchestrateur;

@Singleton
@Path(JAXRS.CHEMIN_PORTAIL)
public class ImplemPortail implements Portail {
	private final Client client;
	private final List<HyperLien<Bibliotheque>> bibliotheques;
	private final ConcurrentMap<NomAlgorithme, AlgorithmeRecherche> tableAlgos;
	private AlgorithmeRecherche algoRecherche;
	
	public ImplemPortail() {
		System.out.println("Déploiement de " + this + " : " + this.getClass());
		this.client = Orchestrateur.clientJAXRS();
		this.bibliotheques = Initialisation.bibliotheques();
		this.tableAlgos = new ConcurrentHashMap<>();
		this.algoRecherche = null;
		
		this.algoRecherche = new RechercheSynchroneSequentielle("recherche sync seq");
		AlgorithmeRecherche algo = this.algoRecherche;
		NomAlgorithme nom = this.algoRecherche.nom();
		tableAlgos.put(nom, algo);

		this.algoRecherche = new RechercheSynchroneMultiTaches("recherche sync multi");
		algo = this.algoRecherche;
		nom = algo.nom();
		tableAlgos.put(nom, algo);

		this.algoRecherche = new RechercheSynchroneStreamParallele("recherche sync stream 8");
		algo = this.algoRecherche;
		nom = algo.nom();
		tableAlgos.put(nom, algo);

		this.algoRecherche = new RechercheSynchroneStreamRx("recherche sync stream rx");
		algo = this.algoRecherche;
		nom = algo.nom();
		tableAlgos.put(nom, algo);

		this.algoRecherche = new RechercheAsynchroneSequentielle("recherche async seq");
		algo = algoRecherche;
		nom = algo.nom();
		tableAlgos.put(nom, algo);

		this.algoRecherche = new RechercheAsynchroneMultiTaches("recherche async multi");
		algo = algoRecherche;
		nom = algo.nom();
		tableAlgos.put(nom, algo);

		this.algoRecherche = new RechercheAsynchroneStreamParallele("recherche async stream 8");
		algo = this.algoRecherche;
		nom = algo.nom();
		tableAlgos.put(nom, algo);

		this.algoRecherche = new RechercheAsynchroneStreamRx("recherche async stream rx");
		algo = this.algoRecherche;
		nom = algo.nom();
		tableAlgos.put(nom, algo);
	}

	@Override
	public Optional<HyperLien<Livre>> chercher(Livre l) {
		long temps = System.nanoTime();
		Optional<HyperLien<Livre>> res = algoRecherche.chercher(l, bibliotheques, client);
		temps = System.nanoTime() - temps;
		System.out.println("Temps complet : " + (temps / 1000000) + " - Algorithme : " + this.algoRecherche.nom());
		return res;
	}

	@Override
	public Future<Optional<HyperLien<Livre>>> chercherAsynchrone(Livre l, AsyncResponse ar) {
		ImplementationAppelsAsynchrones.rechercheAsynchroneBibliotheque(this, l, ar);
		return null; 	// Le résultat n'importe pas mais permet de typer la fonction 
		// côté serveur de la même manière que côté client.
	}

	@Override
	public HyperLiens<Livre> repertorier() {
		Stream<HyperLien<Bibliotheque>> hBibs = Initialisation.bibliotheques().parallelStream();
		Stream<HyperLien<Livre>> catalogue = hBibs.flatMap(h -> {
			Bibliotheque proxy = LienVersRessource.proxy(client, h, Bibliotheque.class);
			return proxy.repertorier().getLiens().stream();
		});
		return new HyperLiens<>(catalogue.collect(Collectors.toList()));
	}

	@Override
	public void changerAlgorithmeRecherche(NomAlgorithme nom) {
		AlgorithmeRecherche a = tableAlgos.get(nom);
		if (a != null) {
			this.algoRecherche = a;
			System.out.println("Nouvel algorithme de recherche : " + a.nom());
		} else {
			System.out.println("Conservation de l'algorithme de recherche : " + algoRecherche.nom());
		}
	}

}
