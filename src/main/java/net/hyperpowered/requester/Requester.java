package net.hyperpowered.requester;

import lombok.AllArgsConstructor;
import net.hyperpowered.logger.PteroLogger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
public class Requester {

    private static final PteroLogger LOGGER = new PteroLogger("REQUESTER");

    private String URL;
    private String userAgent;
    private String apiToken;

    public CompletableFuture<JSONObject> sendRequest(RequestMethod requestMethod, JSONObject body) {
        if (URL == null || URL.isEmpty()) {
            throw new IllegalArgumentException("URL cannot be null or empty.");
        }

        CompletableFuture<JSONObject> response = new CompletableFuture<>();
        JSONObject jsonResponse = new JSONObject();
        Runnable task = () -> {
            HttpURLConnection connection = null;
            try {
                URL url = new URL(URL);
                URLConnection urlConnect = url.openConnection();
                connection = (HttpURLConnection) urlConnect;
                appendProperty(requestMethod, connection);
                writeBody(body, connection);

                int responseCode = connection.getResponseCode();
                jsonResponse.put("httpCode", responseCode);
                jsonResponse.put("httpHeader", connection.getHeaderFields());
                jsonResponse.put("response", getJsonResponse(connection));
                response.complete(jsonResponse);
            } catch (IOException | ParseException e) {
                response.completeExceptionally(e);
            } finally {
                if (connection != null) connection.disconnect();
            }
        };

        CompletableFuture.runAsync(task).exceptionally(throwable -> {
            LOGGER.severe("OCORREU UM ERRO AO PEGAR A RESPOSTA DA REQUEST");
            throw new RuntimeException(throwable);
        });

        return response;
    }

    private void writeBody(JSONObject body, HttpURLConnection connection) throws IOException {
        if (body != null && !body.isEmpty()) {
            try (DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream())) {
                String finalJson = body.toString();
                dataOutputStream.writeBytes(finalJson);
                dataOutputStream.flush();
            }
        }
    }

    private void appendProperty(RequestMethod requestMethod, HttpURLConnection connection) throws ProtocolException {
        connection.setRequestMethod(requestMethod == RequestMethod.PATCH ? RequestMethod.POST.getValue() : requestMethod.getValue());
        connection.setRequestProperty("User-Agent", this.userAgent);
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + apiToken);
        if (requestMethod == RequestMethod.PATCH) connection.setRequestProperty("X-HTTP-Method-Override", "PATCH");
        if (requestMethod != RequestMethod.GET) connection.setDoOutput(true);
    }

    private JSONObject getJsonResponse(HttpURLConnection connection) throws IOException, ParseException {
        try (InputStream is = connection.getInputStream()) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                return sb.toString().isEmpty() ? new JSONObject() : (JSONObject) new JSONParser().parse(sb.toString());
            }
        }
    }

}
