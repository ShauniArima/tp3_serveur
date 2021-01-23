package client;

import modele.ImplemLivre;
import modele.ImplemNomAlgorithme;
import modele.Livre;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

/*
Classe de test automatique du serveur et de mesure du temps d'exécution.
 */
public class ClientTest {

    public static final String BASE_URL = "http://localhost:8081/PortailServeur2/portail/";

    public static final List<String> ALGOS = Arrays.asList(
            "recherche sync seq",
            "recherche sync multi",
            "recherche sync stream",
            "recherche sync stream rx",
            "recherche async seq",
            "recherche async multi",
            "recherche async stream 8",
            "recherche async stream rx"
    );

    public static Client clientJAXRS() {
        ClientConfig config = new ClientConfig();
        return ClientBuilder.newClient(config);
    }

    public static void search(String nomAlgo, Invocation.Builder adminInvocBuilder, Invocation.Builder searchInvocBuilder){
        Response responseAdmin = adminInvocBuilder.put(Entity.xml(new ImplemNomAlgorithme(nomAlgo)));
        if(responseAdmin.getStatus() != 204){
            System.out.printf("%s failed\n", nomAlgo);
            return;
        }

        // On utilise les millisecondes au lieu des nano car c'est plus lisible et la différence est suffisement importante
        Entity<Livre> entity = Entity.xml(new ImplemLivre("Services5.6"));
        Long startTime = System.currentTimeMillis();
        Response responseSearch = searchInvocBuilder.put(entity);
        Long endTime = System.currentTimeMillis();
        if(responseSearch.getStatus() == 200) {
            System.out.printf("%s took %f seconds to execute \n", nomAlgo, (endTime * 1.0 - startTime)/1000);
        }
        else {
            System.out.printf("%s failed\n", nomAlgo);
        }
    }

    public static void main(String args[]) {

        WebTarget adminTarget = clientJAXRS().target(BASE_URL + "admin/recherche");
        Invocation.Builder adminInvocBuilder = adminTarget.request(MediaType.APPLICATION_XML);

        WebTarget searchTarget = clientJAXRS().target(BASE_URL);
        Invocation.Builder searchInvocBuilder = searchTarget.request(MediaType.APPLICATION_XML);

        ALGOS.forEach(algo -> search(algo, adminInvocBuilder, searchInvocBuilder));
    }
}
