package modele;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

import javax.ws.rs.client.Client;

import infrastructure.jaxrs.HyperLien;

public class RechercheAsynchroneSequentielle extends RechercheAsynchroneAbstraite {

    NomAlgorithme nom;

    public RechercheAsynchroneSequentielle(String nom) {
        super();
        this.nom = new ImplemNomAlgorithme(nom);
    }

    @Override
    public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
        List<Future<Optional<HyperLien<Livre>>>> promises = new ArrayList<>();

        bibliotheques.forEach(b -> {
            try {
                promises.add(this.rechercheAsync(b, l, client));
            } catch (Exception e) {
                System.out.println(e);
            }
        });

        for (Future<Optional<HyperLien<Livre>>> p : promises) {
            try {
                Optional<HyperLien<Livre>> resultat = p.get();
                
                if (!resultat.isEmpty()) {
                    return resultat;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        };

        return Optional.empty();
    }

    @Override
    public NomAlgorithme nom() {
        return this.nom;
    }

}