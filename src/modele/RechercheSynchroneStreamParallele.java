package modele;

import infrastructure.jaxrs.HyperLien;
import infrastructure.jaxrs.HyperLiens;

import javax.ws.rs.client.Client;
import java.util.List;
import java.util.Optional;

public class RechercheSynchroneStreamParallele extends RechercheSynchroneAbstraite {

    private NomAlgorithme nom;

    public RechercheSynchroneStreamParallele(String nom){
        this.nom = new ImplemNomAlgorithme(nom);
    }

    @Override
    public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
        return bibliotheques.parallelStream()
                .map(h -> super.rechercheSync(h, l, client))
                .filter(res -> !res.equals(Optional.empty()))
                .findAny().orElse(Optional.empty());
    }

    @Override
    public NomAlgorithme nom() {
        return this.nom;
    }
}
