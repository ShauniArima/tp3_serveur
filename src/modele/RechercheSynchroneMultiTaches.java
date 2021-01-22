package modele;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import javax.ws.rs.client.Client;

import infrastructure.jaxrs.HyperLien;

public class RechercheSynchroneMultiTaches extends RechercheSynchroneAbstraite {

    NomAlgorithme nom;

    public RechercheSynchroneMultiTaches(String nom) {
        super();
        this.nom = new ImplemNomAlgorithme(nom);
    }

    @Override
    public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client)  {
        ExecutorService services = Executors.newCachedThreadPool();
        CountDownLatch synLatch = new CountDownLatch(bibliotheques.size());
        AtomicReference<Optional<HyperLien<Livre>>> resultat = new AtomicReference<>(Optional.empty());
       
        for (HyperLien<Bibliotheque> bibliotheque : bibliotheques) {
            services.submit(() -> {
                resultat.set(this.rechercheSync(bibliotheque, l, client));
                
                if (!resultat.get().isEmpty()) {
                    liberer(synLatch);
                }

                synLatch.countDown();
            });
        }

        try {
            synLatch.await();

            return resultat.get();
        } catch(InterruptedException e) {
            System.out.println(e);
        }

        return Optional.empty();
    }

    private void liberer(CountDownLatch latch) {
        while (latch.getCount() > 0) {
            latch.countDown();
        }
    }

    @Override
    public NomAlgorithme nom() {
        return this.nom;
    }

}