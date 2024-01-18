package com.hyperpowered.ojvzinn.ptero.parser;

import com.hyperpowered.ojvzinn.ptero.model.ServerModel;
import com.hyperpowered.ojvzinn.ptero.model.server.ServerConfiguration;
import com.hyperpowered.ojvzinn.ptero.model.server.ServerContainer;
import com.hyperpowered.ojvzinn.ptero.model.server.ServerEnviroment;
import com.hyperpowered.ojvzinn.ptero.model.server.ServerFeatureLimits;
import lombok.Getter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ServerParser {

    private final String jsonString;

    @Getter
    private JSONObject object;

    public ServerParser(String jsonResponse) {
        this.jsonString = jsonResponse;
    }

    public void parser() {
        try {
            this.object = (JSONObject) new JSONParser().parse(this.jsonString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public ServerModel transform() {
        JSONObject jsonConfig = (JSONObject) this.object.get("limits");
        ServerConfiguration config = new ServerConfiguration(
                (Long) jsonConfig.get("memory"),
                (Long) jsonConfig.get("swap"),
                (Long) jsonConfig.get("disk"),
                (Long) jsonConfig.get("io"),
                (Long) jsonConfig.get("cpu")
        );

        JSONObject jsonFeature = (JSONObject) this.object.get("feature_limits");
        ServerFeatureLimits featureLimits = new ServerFeatureLimits(
                (Long) jsonFeature.get("databases"),
                (Long) jsonFeature.get("allocations"),
                (Long) jsonFeature.get("backups")
        );

        JSONObject jsonContainer = (JSONObject) this.object.get("container");
        JSONObject jsonEnvironment = (JSONObject) jsonContainer.get("environment");
        ServerContainer container = new ServerContainer(
                jsonEnvironment.get("STARTUP").toString(),
                jsonContainer.get("image").toString()
        );

        jsonEnvironment.remove("STARTUP");
        jsonEnvironment.remove("P_SERVER_LOCATION");
        jsonEnvironment.remove("P_SERVER_UUID");
        jsonEnvironment.remove("P_SERVER_ALLOCATION_LIMIT");
        ServerEnviroment environment = new ServerEnviroment();
        for (Object key : jsonEnvironment.keySet()) {
            environment.appendArg((String) key, jsonEnvironment.get(key).toString());
        }

        return new ServerModel(
                this.object.get("name").toString(),
                this.object.get("description").toString(),
                this.object.get("identifier").toString(),
                this.object.get("uuid").toString(),
                this.object.get("id").toString(),
                (boolean) this.object.get("suspended"),
                (Long) this.object.get("user"),
                (Long) this.object.get("node"),
                (Long) this.object.get("allocation"),
                (Long) this.object.get("nest"),
                (Long) this.object.get("egg"),
                config,
                featureLimits,
                container,
                environment,
                this.object
        );
    }

}
