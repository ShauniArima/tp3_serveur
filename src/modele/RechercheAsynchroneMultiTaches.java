package modele;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.InvocationCallback;

import infrastructure.jaxrs.HyperLien;

public class RechercheAsynchroneMultiTaches extends RechercheAsynchroneAbstraite {

    NomAlgorithme nom;

    public RechercheAsynchroneMultiTaches(String nom) {
        super();
        this.nom = new ImplemNomAlgorithme(nom);
    }

    @Override
    public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
        CountDownLatch synLatch = new CountDownLatch(bibliotheques.size());
        AtomicReference<Optional<HyperLien<Livre>>> resultat = new AtomicReference<>(Optional.empty());
       
        for (HyperLien<Bibliotheque> bibliotheque : bibliotheques) {
            InvocationCallback<Optional<HyperLien<Livre>>> callback = new InvocationCallback<Optional<HyperLien<Livre>>>(){
                @Override
                public void completed(Optional<HyperLien<Livre>> response) {
                    resultat.set(response);
                    if (!resultat.get().isEmpty()) {
                        liberer(synLatch);
                    }

                    synLatch.countDown();
                }

                @Override
                public void failed(Throwable throwable) {}
            };

            this.rechercheAsyncAvecRappel(bibliotheque, l, client, callback);
        }

        try {
            synLatch.await();

            return resultat.get();
        } catch(InterruptedException e) {
            System.out.println(e);
        }

        return Optional.empty();
    }

    private static void liberer(CountDownLatch latch) {
        while (latch.getCount() > 0) {
            latch.countDown();
        }
    }

    @Override
    public NomAlgorithme nom() {
        return this.nom;
    }

}