# TP3

Auteurs :
- Quentin GROSMANGIN
- Ander FRIOU
- Maxime GRATENS

## Bibliotheque

```java
- Repertoire {

	@PUT
	@Produces(TYPE_MEDIA)
	@Consumes(TYPE_MEDIA)
	@ReponsesPUTOption
	// Requête (méthode http + url) : PUT /portail/
	// Corps : 
	// <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
	// <livre>
	// 	<titre>Services9.7</titre>
	// </livre>
	// Réponses (à spécifier par code) :
	// - code : 200 
	//   <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
	//   <hyperlien uri="http://localhost:8099/bib9/bibliotheque/7"/>
	// - code : 404 vide
	Optional<HyperLien<Livre>> chercher(Livre l);


	@PUT
	@ReponsesPUTOption
	@Path(JAXRS.SOUSCHEMIN_ASYNC)
	@Consumes(JAXRS.TYPE_MEDIA)
	@Produces(JAXRS.TYPE_MEDIA)
	// Requête (méthode http + url) : PUT /portail/async/
	// Corps : 
	// <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
	// <livre>
	// 	<titre>Services9.7</titre>
	// </livre>
	// Réponses (à spécifier par code) :
	// - code : 200 
	//   <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
	//   <hyperlien uri="http://localhost:8099/bib9/bibliotheque/7"/>
	// - code : 404 vide
	Future<Optional<HyperLien<Livre>>> chercherAsynchrone(Livre l, @Suspended final AsyncResponse ar);

	@GET
	@Path(SOUSCHEMIN_CATALOGUE)
	@Produces(TYPE_MEDIA)
	// Requête (méthode http + url) : GET /portail/catalogue/
	// Corps : vide
	// Réponses (à spécifier par code) :
	// - code : 200
	//  <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
	//  <liste>
	// 	 <hyperlien uri="http://localhost:8090/bib0/bibliotheque/0"/>
	// 	 <hyperlien uri="http://localhost:8090/bib0/bibliotheque/1"/>
	// 	 <hyperlien uri="http://localhost:8090/bib0/bibliotheque/2"/>
	// 	 <hyperlien uri="http://localhost:8090/bib0/bibliotheque/3"/>
	// 	 <hyperlien uri="http://localhost:8090/bib0/bibliotheque/4"/>
	// 	 ...
	//  </liste> 
	HyperLiens<Livre> repertorier();

- Archive 
	@Path("{id}")
	@ReponsesGETNullEn404
	// Adresse de la sous-ressource : 
	// Requête sur la sous-ressource (méthode http + url) : GET /bibliotheque/{id}
	// Corps : 
	// Réponses (à spécifier par code) :
	// - code : 
	Livre sousRessource(@PathParam("id") IdentifiantLivre id) ;

	@Path("{id}")
	@GET 
	@Produces(JAXRS.TYPE_MEDIA)
	@ReponsesGETNullEn404
	// Requête (méthode http + url) : GET /bibliotheque/{id}
	// Corps : 
	// Réponses (à spécifier par code) :
	// - code : 
	Livre getRepresentation(@PathParam("id") IdentifiantLivre id);

	@POST
	@ReponsesPOSTEnCreated
	@Consumes(JAXRS.TYPE_MEDIA)
	@Produces(JAXRS.TYPE_MEDIA)
	// Requête (méthode http + url) : POST /bibliotheque/
	// Corps : 
	// Réponses (à spécifier par code) :
	// - code : 
	HyperLien<Livre> ajouter(Livre l);
}
```

## Portail

```java
- Repertoire {

	@PUT
	@Produces(TYPE_MEDIA)
	@Consumes(TYPE_MEDIA)
	@ReponsesPUTOption
	// Requête (méthode http + url) : PUT /portail/
	// Corps : 
	// <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
	// <livre>
	// 	<titre>Services9.7</titre>
	// </livre>
	// Réponses (à spécifier par code) :
	// - code : 200 
	//   <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
	//   <hyperlien uri="http://localhost:8099/bib9/bibliotheque/7"/>
	// - code : 404 vide
	Optional<HyperLien<Livre>> chercher(Livre l);


	@PUT
	@ReponsesPUTOption
	@Path(JAXRS.SOUSCHEMIN_ASYNC)
	@Consumes(JAXRS.TYPE_MEDIA)
	@Produces(JAXRS.TYPE_MEDIA)
	// Requête (méthode http + url) : PUT /portail/async/
	// Corps : 
	// <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
	// <livre>
	// 	<titre>Services9.7</titre>
	// </livre>
	// Réponses (à spécifier par code) :
	// - code : 200 
	//   <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
	//   <hyperlien uri="http://localhost:8099/bib9/bibliotheque/7"/>
	// - code : 404 vide
	Future<Optional<HyperLien<Livre>>> chercherAsynchrone(Livre l, @Suspended final AsyncResponse ar);

	@GET
	@Path(SOUSCHEMIN_CATALOGUE)
	@Produces(TYPE_MEDIA)
	// Requête (méthode http + url) : GET /portail/catalogue/
	// Corps : vide
	// Réponses (à spécifier par code) :
	// - code : 200
	//  <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
	//  <liste>
	// 	 <hyperlien uri="http://localhost:8090/bib0/bibliotheque/0"/>
	// 	 <hyperlien uri="http://localhost:8090/bib0/bibliotheque/1"/>
	// 	 <hyperlien uri="http://localhost:8090/bib0/bibliotheque/2"/>
	// 	 <hyperlien uri="http://localhost:8090/bib0/bibliotheque/3"/>
	// 	 <hyperlien uri="http://localhost:8090/bib0/bibliotheque/4"/>
	// 	 ...
	//  </liste> 
	HyperLiens<Livre> repertorier();

- AdminAlgo
	@PUT
	@Path(JAXRS.SOUSCHEMIN_ALGO_RECHERCHE)
	@Consumes(JAXRS.TYPE_MEDIA)
	// Requête (méthode http + url) : PUT /portail/admin/recherche
	// Corps : 
	// <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
	// <algo nom="recherche async multi"/>  
	// Réponses (à spécifier par code) :
	// - code : 500 vide
	// - code : 204 vide
	void changerAlgorithmeRecherche(NomAlgorithme algo);
```

## Exemples de schemas

### Nom algorithme

```xml
<algo nom="mon algo magnifique"/>
```

### Livre

```xml
<livre>
	<titre>Services9.0</titre>
</livre>
```

## Client et tests
Pour des raisons de simplicité, nous avons créé le client dans un package 'client' plutôt que de créer un nouveau projet.

Pour effectuer un test, procéder comme suit :
- Exécuter serveur.LancementDixArchives
- Exécuter serveur.LancementPortail
- Exécuter client.ClientTest

Voici un résultat type (les temps d'exécutions varient d'un test à l'autre à l'autre, mais l'ordre de grandeur et le classement reste le même)
- recherche sync seq took 7,545000 seconds to execute 
- recherche sync multi took 0,106000 seconds to execute 
- recherche sync stream took 1,489000 seconds to execute 
- recherche sync stream rx took 7,578000 seconds to execute 
- recherche async seq took 1,549000 seconds to execute 
- recherche async multi took 0,278000 seconds to execute 
- recherche async stream 8 took 1,985000 seconds to execute 
- recherche async stream rx took 7,535000 seconds to execute

On constate d'une part que les versions synchrones prennent en générale moins de temps que les excutions asychrones.
Cela s'explique par le faible volume de données à traiter.

On constate que quelque soit le mode d'exécution, la recherche multi-threadée est la plus rapide.

La recherche séquentielle semble être la seule à profiter de l'exécution asynchrone.

Ces résultats varient en fonction de la configuration matérielle. A titre d'information,
les résultats présentés ci-dessus ont été obtenus en exécutant le client et le serveur sur une seule machine équipée d'un CPU
Intel i5 7300K avec 4 coeurs cadensés à 2,50 GHz.