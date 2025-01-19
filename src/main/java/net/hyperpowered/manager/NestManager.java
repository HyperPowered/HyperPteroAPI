package net.hyperpowered.manager;

import net.hyperpowered.logger.PteroLogger;
import net.hyperpowered.nest.Egg;
import net.hyperpowered.nest.EggScript;
import net.hyperpowered.nest.Nest;
import net.hyperpowered.requester.ApplicationEndpoint;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class NestManager extends Manager {

    private static final PteroLogger LOGGER = new PteroLogger("NEST");

    public CompletableFuture<List<Nest>> listNest() {
        CompletableFuture<List<Nest>> response = new CompletableFuture<>();
        fetch(ApplicationEndpoint.NESTS.getEndpoint()).thenAccept(responseJson -> {
            List<Nest> nests = new ArrayList<>();
            try {
                JSONObject responseObject = (JSONObject) responseJson.get("response");
                JSONArray nestJson = (JSONArray) responseObject.get("data");
                for (Object nestDetails : nestJson) {
                    Nest nest = parseNest(nestDetails.toString());
                    nests.add(nest);
                }
            } catch (Exception e) {
                response.completeExceptionally(e);
            }

            response.complete(nests);
        }).exceptionally(throwable -> {
            LOGGER.severe("OCORREU UM ERRO AO CARREGAR OS NESTS: " + throwable.getMessage() + "\n");
            sendError(throwable, LOGGER);
            return null;
        });

        return response;
    }

    public CompletableFuture<List<Egg>> listEggs(long nestID) {
        CompletableFuture<List<Egg>> response = new CompletableFuture<>();
        fetch(ApplicationEndpoint.NESTS.getEndpoint() + "/" + nestID + "/include=nest,servers").thenAccept(responseJson -> {
            List<Egg> eggs = new ArrayList<>();
            try {
                JSONObject responseObject = (JSONObject) responseJson.get("response");
                JSONArray eggJson = (JSONArray) responseObject.get("data");
                for (Object eggDetails : eggJson) {
                    Egg egg = parseEgg(eggDetails.toString());
                    eggs.add(egg);
                }
            } catch (Exception e) {
                response.completeExceptionally(e);
            }

            response.complete(eggs);
        }).exceptionally(throwable -> {
            LOGGER.severe("OCORREU UM ERRO AO CARREGAR OS EGGS: " + throwable.getMessage() + "\n");
            sendError(throwable, LOGGER);
            return null;
        });

        return response;
    }

    public CompletableFuture<Nest> getNest(long nestID) {
        CompletableFuture<Nest> response = new CompletableFuture<>();
        fetch(ApplicationEndpoint.NESTS.getEndpoint() + "/" + nestID).thenAccept(responseJson -> {
            try {
                JSONObject responseNestJson = (JSONObject) responseJson.get("response");
                response.complete(parseNest(responseNestJson.toString()));
            } catch (Exception e) {
                response.completeExceptionally(e);
            }
        }).exceptionally(throwable -> {
            LOGGER.severe("OCORREU UM ERRO AO PEGAR O NEST DO SERVIDOR: " + throwable.getMessage() + "\n");
            sendError(throwable, LOGGER);
            return null;
        });

        return response;
    }

    public CompletableFuture<Egg> getEgg(long nestID, long eggID) {
        CompletableFuture<Egg> response = new CompletableFuture<>();
        fetch(ApplicationEndpoint.NESTS.getEndpoint() + "/" + nestID + "/eggs/" + eggID).thenAccept(responseJson -> {
            try {
                JSONObject responseEggJson = (JSONObject) responseJson.get("response");
                response.complete(parseEgg(responseEggJson.toString()));
            } catch (Exception e) {
                response.completeExceptionally(e);
            }
        }).exceptionally(throwable -> {
            LOGGER.severe("OCORREU UM ERRO AO PEGAR O EGG DO NEST: " + throwable.getMessage() + "\n");
            sendError(throwable, LOGGER);
            return null;
        });

        return response;
    }

    public Nest parseNest(String nestJson) throws ParseException {
        JSONObject nestDetailsGeneral = (JSONObject) new JSONParser().parse(nestJson);
        JSONObject nestDetails = (JSONObject) nestDetailsGeneral.get("attributes");
        return new Nest(
                (Long) nestDetails.get("id"),
                UUID.fromString((String) nestDetails.get("uuid")),
                (String) nestDetails.get("author"),
                (String) nestDetails.get("name"),
                (String) nestDetails.get("description"),
                (String) nestDetails.get("created_at"),
                (String) nestDetails.get("updated_at")
        );
    }

    public Egg parseEgg(String eggJson) throws ParseException {
        JSONObject eggDetailsGeneral = (JSONObject) new JSONParser().parse(eggJson);
        JSONObject eggDetails = (JSONObject) eggDetailsGeneral.get("attributes");
        JSONObject eggScriptDetails = (JSONObject) eggDetails.get("script");
        EggScript eggScript = new EggScript(
                (Boolean) eggScriptDetails.get("privileged"),
                (String) eggScriptDetails.get("install"),
                (String) eggScriptDetails.get("entry"),
                (String) eggScriptDetails.get("container")
        );

        return new Egg(
                (Long) eggDetails.get("id"),
                UUID.fromString((String) eggDetails.get("uuid")),
                (long) eggDetails.get("nest"),
                (String) eggDetails.get("author"),
                (String) eggDetails.get("description"),
                (String) eggDetails.get("docker_image"),
                (JSONObject) eggDetails.get("config"),
                (String) eggDetails.get("startup"),
                eggScript,
                (String) eggDetails.get("created_at"),
                (String) eggDetails.get("updated_at")
        );
    }
}
