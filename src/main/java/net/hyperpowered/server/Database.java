package net.hyperpowered.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.json.simple.JSONObject;

@AllArgsConstructor
@Getter
public class Database {

    private long id;
    private long server;
    private long host;
    private String database;
    private String password;
    private String remote;
    private long max_connections;
    private String created_at;
    private String updated_at;
    private JSONObject relationships;

}
