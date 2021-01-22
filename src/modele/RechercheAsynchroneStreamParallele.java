package modele;

import infrastructure.jaxrs.HyperLien;
import infrastructure.jaxrs.Outils;

import javax.ws.rs.client.Client;
import java.util.List;
import java.util.Optional;

public class RechercheAsynchroneStreamParallele extends RechercheAsynchroneAbstraite{
    private NomAlgorithme nom;

    public RechercheAsynchroneStreamParallele(String name) {
        this.nom = new ImplemNomAlgorithme(name);
    }

    @Override
    public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
        return bibliotheques.parallelStream()
                .map(h -> super.rechercheAsync(h, l, client))
                .map(Outils::remplirPromesse)
                .filter(o -> !o.isEmpty())
                .findAny().orElse(Optional.empty());
    }

    @Override
    public NomAlgorithme nom() {
        return this.nom;
    }
}
