package modele;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.client.Client;

import infrastructure.jaxrs.HyperLien;

public class RechercheSynchroneSequentielle extends RechercheSynchroneAbstraite {

    NomAlgorithme nom;

    public RechercheSynchroneSequentielle(String nom) {
        super();
        this.nom = new ImplemNomAlgorithme(nom);
    }

    @Override
    public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
        for (HyperLien<Bibliotheque> bibliotheque : bibliotheques) {
            Optional<HyperLien<Livre>> resultat = this.rechercheSync(bibliotheque, l, client);

            if (!resultat.isEmpty()) {
                return resultat;
            }
        }

        return Optional.empty();
    }

    @Override
    public NomAlgorithme nom() {
        return this.nom;
    }

}