package net.hyperpowered.manager;

import net.hyperpowered.logger.PteroLogger;
import net.hyperpowered.requester.ApplicationEndpoint;
import net.hyperpowered.user.User;
import net.hyperpowered.user.builder.UserBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class UserManager extends Manager {

    private static final PteroLogger LOGGER = new PteroLogger("USER");

    public CompletableFuture<List<User>> listUsers() {
        CompletableFuture<List<User>> response = new CompletableFuture<>();
        fetch(ApplicationEndpoint.USERS.getEndpoint()).thenAccept(responseJson -> {
            List<User> users = new ArrayList<>();
            try {
                JSONObject responseObject = (JSONObject) responseJson.get("response");
                JSONArray usersJson = (JSONArray) responseObject.get("data");
                for (Object userDetails : usersJson) {
                    User user = parseUser(userDetails.toString());
                    users.add(user);
                }
            } catch (Exception e) {
                response.completeExceptionally(e);
            }
            response.complete(users);
        }).exceptionally(throwable -> {
            LOGGER.severe("OCORREU UM ERRO AO CARREGAR OS USUÁRIOS: " + throwable.getMessage() + "\n");
            sendError(throwable, LOGGER);
            return null;
        });

        return response;
    }

    public CompletableFuture<User> getUser(long userID) {
        return fetchUser("/", String.valueOf(userID)).exceptionally(throwable -> {
            LOGGER.severe("OCORREU UM ERRO AO PEGAR OS DADOS DO USUÁRIO: " + throwable.getMessage() + "\n");
            sendError(throwable, LOGGER);
            return null;
        });
    }

    public CompletableFuture<User> getUser(String externalID) {
        return fetchUser("/external/", externalID).exceptionally(throwable -> {
            LOGGER.severe("OCORREU UM ERRO AO PEGAR OS DADOS DO USUÁRIO: " + throwable.getMessage() + "\n");
            sendError(throwable, LOGGER);
            return null;
        });
    }

    public CompletableFuture<JSONObject> createUser(UserBuilder builder) {
        return create(builder, ApplicationEndpoint.USERS.getEndpoint()).exceptionally(throwable -> {
            LOGGER.severe("OCORREU UM ERRO CRIAR O USUÁRIO: " + throwable.getMessage() + "\n");
            sendError(throwable, LOGGER);
            return null;
        });
    }

    public CompletableFuture<JSONObject> updateUser(User user) {
        return update(ApplicationEndpoint.USERS.getEndpoint() + "/" + user.getId(), makeRequestJSON(user)).exceptionally(throwable -> {
            LOGGER.severe("OCORREU UM ERRO AO ATUALIZAR UM USUÁRIO: " + throwable.getMessage() + "\n");
            sendError(throwable, LOGGER);
            return null;
        });
    }

    public CompletableFuture<JSONObject> deleteUser(long userID) {
        return delete(ApplicationEndpoint.USERS.getEndpoint() + "/" + userID).exceptionally(throwable -> {
            LOGGER.severe("OCORREU UM ERRO AO DELETAR UM USUÁRIO: " + throwable.getMessage() + "\n");
            sendError(throwable, LOGGER);
            return null;
        });
    }

    public User parseUser(String userJson) throws ParseException {
        JSONObject userDetailsGeneral = (JSONObject) new JSONParser().parse(userJson);
        JSONObject userDetails = (JSONObject) userDetailsGeneral.get("attributes");
        return new User((long)
                userDetails.get("id"),
                (String) userDetails.get("external_id"),
                UUID.fromString((String) userDetails.get("uuid")),
                (String) userDetails.get("username"),
                (String) userDetails.get("email"),
                (String) userDetails.get("first_name"),
                (String) userDetails.get("last_name"),
                (String) userDetails.get("language"),
                (Boolean) userDetails.get("root_admin"),
                (Boolean) userDetails.get("2fa"),
                (String) userDetails.get("created_at"),
                (String) userDetails.get("updated_at"));
    }

    private JSONObject makeRequestJSON(User user) {
        JSONObject response = new JSONObject();
        response.put("email", user.getEmail());
        response.put("username", user.getUsername());
        response.put("first_name", user.getFirstName());
        response.put("last_name", user.getLastName());
        response.put("language", user.getLanguage());
        response.put("root_admin", user.isAdmin());
        response.put("2fa", user.isTwoFactors());
        if(user.getPassword() != null && !user.getPassword().isEmpty()){
            response.put("password", user.getPassword());
        }
        return response;
    }

    private CompletableFuture<User> fetchUser(String midPoint, String id) {
        CompletableFuture<User> response = new CompletableFuture<>();
        fetch(ApplicationEndpoint.USERS.getEndpoint() + midPoint + id).thenAccept(responseJson -> {
            try {
                JSONObject responseUserJson = (JSONObject) responseJson.get("response");
                response.complete(parseUser(responseUserJson.toString()));
            } catch (Exception e) {
                response.completeExceptionally(e);
            }
        }).exceptionally(throwable -> {
            LOGGER.severe("OCORREU UM ERRO AO CARREGAR O USUÁRIO: " + throwable.getMessage() + "\n");
            sendError(throwable, LOGGER);
            return null;
        });

        return response;
    }
}