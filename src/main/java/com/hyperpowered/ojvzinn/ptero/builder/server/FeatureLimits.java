package com.hyperpowered.ojvzinn.ptero.builder.server;

import com.hyperpowered.ojvzinn.ptero.utils.CapacityEnum;
import lombok.Setter;
import org.json.simple.JSONObject;

public class FeatureLimits {

    @Setter
    private Integer databases;

    @Setter
    private Integer backups;

    public FeatureLimits() {
        this.databases = 5;
        this.backups = 1;
    }

    @SuppressWarnings("all")
    public JSONObject makeJson() {
        JSONObject object = new JSONObject();
        object.put("databases", this.databases);
        object.put("backups", this.backups);
        return object;
    }
}
