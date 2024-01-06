package com.hyperpowered.ojvzinn.ptero.builder;

import com.hyperpowered.ojvzinn.ptero.builder.server.FeatureLimits;
import com.hyperpowered.ojvzinn.ptero.builder.server.ServerLimits;
import org.json.simple.JSONObject;

import java.util.Set;

@SuppressWarnings("unchecked")
public class ServerCreatorBuilder {

    private final JSONObject jsonBuilder;

    public ServerCreatorBuilder() {
        this.jsonBuilder = new JSONObject();
    }

    public void appenServerName(String serverName) {
        this.jsonBuilder.put("name", serverName);
    }

    public void appendUserOwner(Integer userID) {
        this.jsonBuilder.put("user", userID);
    }

    public void appendEgg(Integer eggID) {
        this.jsonBuilder.put("egg", eggID);
    }

    public void appendDockerImage(String dockerImage) {
        this.jsonBuilder.put("docker_image", dockerImage);
    }

    public void appendStartupCode(String startupCode) {
        this.jsonBuilder.put("startup", startupCode);
    }

    public void appendEnvironment() {
        JSONObject environment = new JSONObject();
        environment.put("VANILLA_VERSION", "lasted");
        environment.put("SERVER_JARFILE", "server.jar");
        this.jsonBuilder.put("environment", environment);
    }

    public void appendLimits(ServerLimits limits) {
        this.jsonBuilder.put("limits", limits.makeJson());
    }

    public void appendFeatureLimits(FeatureLimits limits) {
        this.jsonBuilder.put("feature_limits", limits.makeJson());
    }

    public JSONObject completeConfiguration() {
        JSONObject allocation = new JSONObject();
        allocation.put("default", 17);
        this.jsonBuilder.put("allocation", allocation);
        checkIfForNull();
        return this.jsonBuilder;
    }

    private void checkIfForNull() {
        Set<String> keys = this.jsonBuilder.keySet();
        if (!keys.contains("name")) {
            this.jsonBuilder.put("name", "Building");
        }

        if (!keys.contains("user")) {
            this.jsonBuilder.put("user", 1);
        }

        if (!keys.contains("egg")) {
            this.jsonBuilder.put("egg", 1);
        }

        if (!keys.contains("docker_image")) {
            this.jsonBuilder.put("docker_image", "quay.io/pterodactyl/core:java");
        }

        if (!keys.contains("startup")) {
            this.jsonBuilder.put("startup", "java -Xms128M -Xmx128M -jar server.jar");
        }

        if (!keys.contains("environment")) {
            JSONObject environment = new JSONObject();
            environment.put("VANILLA_VERSION", "lasted");
            environment.put("SERVER_JARFILE", "server.jar");
            this.jsonBuilder.put("environment", environment);
        }

        if (!keys.contains("limits")) {
            this.jsonBuilder.put("limits", new ServerLimits().makeJson());
        }

        if (!keys.contains("feature_limits")) {
            this.jsonBuilder.put("feature_limits", new FeatureLimits().makeJson());
        }
    }

}
