package net.hyperpowered.server.builder;

import lombok.Getter;
import net.hyperpowered.interfaces.Builder;
import org.json.simple.JSONObject;

@Getter
public class ServerBuilder implements Builder {

    private String name;
    private Long user;
    private Long egg;
    private String dockerImage;
    private String startup;
    private String externalId;
    private int node = 1;
    private JSONObject environment;
    private Builder serverLimitBuilder;
    private Builder serverFeatureLimitBuilder;
    private Builder serverAllocationBuilder;

    public ServerBuilder appendName(String name) {
        this.name = name;
        return this;
    }

    public ServerBuilder appendUser(long user) {
        this.user = user;
        return this;
    }

    public ServerBuilder appendEgg(long egg) {
        this.egg = egg;
        return this;
    }

    public ServerBuilder appendDockerImage(String dockerImage) {
        this.dockerImage = dockerImage.replace("\\", "/");
        return this;
    }

    public ServerBuilder appendStartup(String startup) {
        this.startup = startup;
        return this;
    }

    public ServerBuilder appendEnvironment(JSONObject environment) {
        this.environment = environment;
        return this;
    }

    public ServerBuilder appendServerLimit(ServerLimitBuilder builder) {
        this.serverLimitBuilder = builder;
        return this;
    }

    public ServerBuilder appendServerFutureLimit(ServerFutureLimitBuilder builder) {
        this.serverFeatureLimitBuilder = builder;
        return this;
    }

    public ServerBuilder appendServerAllocationLimit(ServerAllocationBuilder builder) {
        this.serverAllocationBuilder = builder;
        return this;
    }

    public ServerBuilder appendExternalId(String externalId) {
        this.externalId = externalId;
        return this;
    }

    public ServerBuilder appendNode(int node) {
        this.node = node;
        return this;
    }

    @Override
    public JSONObject buildToJSON() throws IllegalArgumentException {
        if (this.name == null || this.user == null || this.egg == null || this.dockerImage == null || this.startup == null || this.environment == null || this.serverLimitBuilder == null || this.serverFeatureLimitBuilder == null || this.serverAllocationBuilder == null) {
            throw new IllegalArgumentException("OS ARGUMENTOS NAO PODEM SER NULOS!");
        }

        JSONObject response = new JSONObject();
        response.put("name", name);
        response.put("user", user);
        response.put("egg", egg);
        response.put("docker_image", dockerImage);
        response.put("startup", startup);
        response.put("environment", environment);
        response.put("limits", serverLimitBuilder.buildToJSON());
        response.put("feature_limits", serverFeatureLimitBuilder.buildToJSON());
        response.put("allocation", serverAllocationBuilder.buildToJSON());
        response.put("node", node);
        if (externalId != null && !externalId.isEmpty()) {
            response.put("external_id", externalId);
        }
        return response;
    }
}
