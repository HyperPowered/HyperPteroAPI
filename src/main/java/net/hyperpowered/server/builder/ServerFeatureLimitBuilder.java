package net.hyperpowered.server.builder;

import lombok.Getter;
import net.hyperpowered.interfaces.Builder;
import org.json.simple.JSONObject;

@Getter
public class ServerFeatureLimitBuilder implements Builder {

    private Long databases;
    private Long backups;

    public ServerFeatureLimitBuilder appendDatabase(long databases) {
        this.databases = databases;
        return this;
    }

    public ServerFeatureLimitBuilder appendBackups(long backups) {
        this.backups = backups;
        return this;
    }

    @Override
    public JSONObject buildToJSON() throws IllegalArgumentException {
        if (this.databases == null || this.backups == null) {
            throw new IllegalArgumentException("OS ARGUMENTOS NAO PODEM SER NULOS!");
        }

        JSONObject response = new JSONObject();
        response.put("databases", databases);
        response.put("backups", backups);
        return response;
    }
}
