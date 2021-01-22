package modele;

import infrastructure.jaxrs.HyperLien;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import javax.security.auth.callback.Callback;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.InvocationCallback;
import java.util.List;
import java.util.Optional;

public class RechercheAsynchroneStreamRx extends RechercheAsynchroneAbstraite{
    private NomAlgorithme nom;

    public RechercheAsynchroneStreamRx(String name){
        this.nom = new ImplemNomAlgorithme(name);
    }

    @Override
    public NomAlgorithme nom() {
        return this.nom;
    }

    @Override
    public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
        return Observable.fromIterable(bibliotheques)
                .flatMap((h -> Observable.fromFuture (super.rechercheAsync(h, l, client))))
                .subscribeOn(Schedulers.io())
                .filter(o -> !o.equals(Optional.empty()))
                .blockingFirst(Optional.empty());
    }
}
