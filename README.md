# Bibliotheque

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

# Portail

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