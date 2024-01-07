package com.hyperpowered.ojvzinn.ptero.request;

import com.hyperpowered.ojvzinn.ptero.PterodactylAPI;
import com.hyperpowered.ojvzinn.ptero.method.ActionType;
import com.hyperpowered.ojvzinn.ptero.method.RequestMethod;
import com.hyperpowered.ojvzinn.ptero.utils.DebugMode;
import lombok.AllArgsConstructor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@AllArgsConstructor
public class Requester {

    private RequestMethod method;
    private String apiToken;
    private String panelLink;
    private ActionType actionType;

    public void request(String bodyRequest, String... additionalPatch) {
        String finalLink = this.panelLink + this.actionType.getLinkPatch() + (additionalPatch.length > 0 ? additionalPatch[0] : "");
        try {
            URL url = new URL(finalLink);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod(method.getMethod());
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + this.apiToken);
            connection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(bodyRequest);
            wr.flush();
            wr.close();

            if (PterodactylAPI.getDebugMode() == DebugMode.ON) {
                System.out.println("[HYPER-PTEROAPI] Responce Code: " + connection.getResponseCode());
                System.out.println("[HYPER-PTEROAPI] Request BodyCode: " + bodyRequest);
                System.out.println("[HYPER-PTEROAPI] Request Method: " + connection.getRequestMethod());
                System.out.println("[HYPER-PTEROAPI] Request Link: " + finalLink);
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (PterodactylAPI.getDebugMode() == DebugMode.ON) {
                System.out.println("[HYPER-PTEROAPI] Request BodyCode: " + bodyRequest);
                System.out.println("[HYPER-PTEROAPI] Request BodyCode: " + bodyRequest);
                System.out.println("[HYPER-PTEROAPI] Request Link: " + finalLink);
            }
        }
    }

}
