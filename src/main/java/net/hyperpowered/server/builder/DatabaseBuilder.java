package net.hyperpowered.server.builder;

import lombok.Getter;
import net.hyperpowered.interfaces.Builder;
import org.json.simple.JSONObject;

@Getter
public class DatabaseBuilder implements Builder {

    private String database;
    private String remote;
    private Long host;

    public DatabaseBuilder appendDatabase(String database) {
        this.database = database;
        return this;
    }

    public DatabaseBuilder appendRemote(String remote) {
        this.remote = remote;
        return this;
    }

    public DatabaseBuilder appendHost(long host) {
        this.host = host;
        return this;
    }

    @Override
    public JSONObject buildToJSON() throws IllegalArgumentException {
        if (this.database == null || this.remote == null || this.host == null) {
            throw new IllegalArgumentException("OS ARGUMENTOS NAO PODEM SER NULOS!");
        }

        JSONObject response = new JSONObject();
        response.put("database", this.database);
        response.put("remote", this.remote);
        response.put("host", this.host);
        return response;
    }
}
