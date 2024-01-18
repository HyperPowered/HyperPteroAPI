package com.hyperpowered.ojvzinn.ptero.request;

import com.hyperpowered.ojvzinn.ptero.PterodactylAPI;
import com.hyperpowered.ojvzinn.ptero.method.ActionType;
import com.hyperpowered.ojvzinn.ptero.method.RequestMethod;
import com.hyperpowered.ojvzinn.ptero.utils.DebugMode;
import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@AllArgsConstructor
public class Requester {

    private RequestMethod method;
    private String apiToken;
    private String panelLink;
    private ActionType actionType;

    public String request(String bodyRequest, String... additionalPatch) {
        String finalLink = this.panelLink + this.actionType.getLinkPatch() + (additionalPatch.length > 0 ? additionalPatch[0] : "");
        try {
            URL url = new URL(finalLink);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod(method == RequestMethod.PATCH ? "POST" : method.getMethod());
            if (this.method == RequestMethod.PATCH) {
                connection.setRequestProperty("X-HTTP-Method-Override", "PATCH");
            }

            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + this.apiToken);
            connection.setDoOutput(true);

            if (!bodyRequest.isEmpty()) {
                DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(bodyRequest);
                wr.flush();
                wr.close();
            }

            BufferedReader inputStream = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = inputStream.readLine()) != null) {
                sb.append(line);
            }

            if (PterodactylAPI.getDebugMode() == DebugMode.ON) {
                System.out.println("[HYPER-PTEROAPI] Responce Code: " + connection.getResponseCode());
                System.out.println("[HYPER-PTEROAPI] Request BodyCode: " + bodyRequest);
                System.out.println("[HYPER-PTEROAPI] Request Method: " + connection.getRequestMethod());
                System.out.println("[HYPER-PTEROAPI] Request Link: " + finalLink);
                System.out.println("[HYPER-PTEROAPI] Request ResponseJson " + sb);
            }

            return sb.toString();
        } catch (IOException e) {
            if (PterodactylAPI.getDebugMode() == DebugMode.ON) {
                System.out.println("[HYPER-PTEROAPI] Request BodyCode: " + bodyRequest);
                System.out.println("[HYPER-PTEROAPI] Request Method: " + this.method.getMethod());
                System.out.println("[HYPER-PTEROAPI] Request Link: " + finalLink);
            }

            e.printStackTrace();
        }

        return "";
    }

}
