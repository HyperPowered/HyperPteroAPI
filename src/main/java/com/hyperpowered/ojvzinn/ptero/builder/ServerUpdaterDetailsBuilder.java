package com.hyperpowered.ojvzinn.ptero.builder;

import com.hyperpowered.ojvzinn.ptero.model.ServerModel;
import org.json.simple.JSONObject;

@SuppressWarnings("unchecked")
public class ServerUpdaterDetailsBuilder {

    private final JSONObject jsonBuilder;

    public ServerUpdaterDetailsBuilder(ServerModel serverBackup) {
        this.jsonBuilder = new JSONObject();
        this.jsonBuilder.put("name", serverBackup.getServerName());
        this.jsonBuilder.put("user", serverBackup.getServerOwnerID());
        this.jsonBuilder.put("external_id", null);
        this.jsonBuilder.put("description", serverBackup.getServerDescription());
    }

    public void appendName(String name) {
        this.jsonBuilder.replace("name", name);
    }

    public void appendDescription(String description) {
        this.jsonBuilder.replace("description", description);
    }

    public void appendUserOwnerID(Integer id) {
        this.jsonBuilder.replace("user", id);
    }

    public JSONObject completeConfiguration() {
        return this.jsonBuilder;
    }

}
