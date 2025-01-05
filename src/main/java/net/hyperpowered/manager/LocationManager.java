package net.hyperpowered.manager;

import net.hyperpowered.location.Location;
import net.hyperpowered.location.builder.LocationBuilder;
import net.hyperpowered.logger.PteroLogger;
import net.hyperpowered.requester.ApplicationEndpoint;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LocationManager extends Manager {

    private static final PteroLogger LOGGER = new PteroLogger("LOCATION");

    public CompletableFuture<List<Location>> listLocations() {
        CompletableFuture<List<Location>> response = new CompletableFuture<>();
        fetch(ApplicationEndpoint.LOCATIONS.getEndpoint()).thenAccept(responseJson -> {
            List<Location> locations = new ArrayList<>();
            try {
                JSONObject responseObject = (JSONObject) responseJson.get("response");
                JSONArray locationJson = (JSONArray) responseObject.get("data");
                for (Object locationDetails : locationJson) {
                    Location location = parseLocation(locationDetails.toString());
                    locations.add(location);
                }
            } catch (Exception e) {
                response.completeExceptionally(e);
            }

            response.complete(locations);
        }).exceptionally(throwable -> {
            LOGGER.severe("OCORREU UM ERRO AO CARREGAR AS MÁQUINAS: " + throwable.getMessage() + "\n");
            sendError(throwable, LOGGER);
            return null;
        });

        return response;
    }

    public CompletableFuture<Location> getLocation(long locationID) {
        CompletableFuture<Location> response = new CompletableFuture<>();
        fetch(ApplicationEndpoint.LOCATIONS.getEndpoint() + "/" + locationID).thenAccept(responseJson -> {
            try {
                JSONObject responseLocationJson = (JSONObject) responseJson.get("response");
                response.complete(parseLocation(responseLocationJson.toString()));
            } catch (Exception e) {
                response.completeExceptionally(e);
            }
        }).exceptionally(throwable -> {
            LOGGER.severe("OCORREU UM ERRO AO PEGAR OS DADOS DO SERVIDOR: " + throwable.getMessage() + "\n");
            sendError(throwable, LOGGER);
            return null;
        });

        return response;
    }

    public CompletableFuture<JSONObject> createLocation(LocationBuilder builder) {
        return create(builder, ApplicationEndpoint.LOCATIONS.getEndpoint()).exceptionally(throwable -> {
            LOGGER.severe("OCORREU UM ERRO AO CRIAR UMA MÁQUINA: " + throwable.getMessage() + "\n");
            sendError(throwable, LOGGER);
            return null;
        });
    }

    public CompletableFuture<JSONObject> updateLocation(Location location) {
        return update(ApplicationEndpoint.LOCATIONS.getEndpoint() + "/" + location.getId(), makeRequestJSON(location)).exceptionally(throwable -> {
            LOGGER.severe("OCORREU UM ERRO AO ATUALIZAR UMA MÁQUINA: " + throwable.getMessage() + "\n");
            sendError(throwable, LOGGER);
            return null;
        });
    }

    public CompletableFuture<JSONObject> deleteLocation(long id) {
        return delete(ApplicationEndpoint.LOCATIONS.getEndpoint() + "/" + id).exceptionally(throwable -> {
            LOGGER.severe("OCORREU UM ERRO AO DELETAR UMA MÁQUINA: " + throwable.getMessage() + "\n");
            sendError(throwable, LOGGER);
            return null;
        });
    }

    public Location parseLocation(String locationJson) throws ParseException {
        JSONObject locationDetailsGeneral = (JSONObject) new JSONParser().parse(locationJson);
        JSONObject locationDetails = (JSONObject) locationDetailsGeneral.get("attributes");
        return new Location(
                (Long) locationDetails.get("id"),
                (String) locationDetails.get("short"),
                (String) locationDetails.get("long"),
                (String) locationDetails.get("updated_at"),
                (String) locationDetails.get("created_at")
        );
    }

    private JSONObject makeRequestJSON(Location location) {
        JSONObject response = new JSONObject();
        response.put("short", location.getLocationName());
        response.put("long", location.getLocationDescription());
        return response;
    }
}
