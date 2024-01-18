package com.hyperpowered.ojvzinn.ptero.builder.server;

import lombok.Setter;
import org.json.simple.JSONObject;

public class FeatureLimits {

    @Setter
    private Integer databases;

    @Setter
    private Integer backups;

    @Setter
    private Integer allocations;

    public FeatureLimits() {
        this.databases = 5;
        this.backups = 1;
        this.allocations = 5;
    }

    @SuppressWarnings("all")
    public JSONObject makeJson() {
        JSONObject object = new JSONObject();
        object.put("databases", this.databases);
        object.put("backups", this.backups);
        object.put("allocations", this.allocations);
        return object;
    }
}
