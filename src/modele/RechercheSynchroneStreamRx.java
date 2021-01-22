package modele;

import infrastructure.jaxrs.HyperLien;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

import javax.ws.rs.client.Client;
import java.util.List;
import java.util.Optional;

public class RechercheSynchroneStreamRx extends RechercheSynchroneAbstraite {
    private NomAlgorithme nom;

    public RechercheSynchroneStreamRx(String name){
        this.nom = new ImplemNomAlgorithme(name);
    }

    @Override
    public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
        return Observable.fromIterable(bibliotheques)
                .flatMap((h -> Observable.fromCallable (() -> rechercheSync(h, l, client))))
                .subscribeOn(Schedulers.io())
                .filter(o -> !o.equals(Optional.empty()))
                .blockingFirst(Optional.empty());
    }

    @Override
    public NomAlgorithme nom() {
        return this.nom;
    }
}
