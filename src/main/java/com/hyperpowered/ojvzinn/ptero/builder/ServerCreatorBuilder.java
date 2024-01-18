package com.hyperpowered.ojvzinn.ptero.builder;

import com.hyperpowered.ojvzinn.ptero.builder.server.Enviroment;
import com.hyperpowered.ojvzinn.ptero.builder.server.EnviromentType;
import com.hyperpowered.ojvzinn.ptero.builder.server.FeatureLimits;
import com.hyperpowered.ojvzinn.ptero.builder.server.ServerLimits;
import org.json.simple.JSONObject;

@SuppressWarnings("unchecked")
public class ServerCreatorBuilder {

    private final JSONObject jsonBuilder;

    public ServerCreatorBuilder() {
        this.jsonBuilder = new JSONObject();
        JSONObject allocation = new JSONObject();
        allocation.put("default", 1);
        this.jsonBuilder.put("allocation", allocation);
        this.jsonBuilder.put("name", "Building");
        this.jsonBuilder.put("user", 1);
        this.jsonBuilder.put("egg", 1);
        this.jsonBuilder.put("docker_image", "quay.io/pterodactyl/core:java");
        this.jsonBuilder.put("startup", "java -Xms128M -Xmx128M -jar server.jar");

        Enviroment enviroment = new Enviroment(EnviromentType.MINECRAFT);
        enviroment.check();
        this.jsonBuilder.put("environment", new JSONObject(enviroment.getArgs()));
        this.jsonBuilder.put("limits", new ServerLimits().makeJson());

        JSONObject object = new FeatureLimits().makeJson();
        this.jsonBuilder.put("feature_limits", object);
    }

    public void appendServerName(String serverName) {
        this.jsonBuilder.replace("name", serverName);
    }

    public void appendUserOwner(Integer userID) {
        this.jsonBuilder.replace("user", userID);
    }

    public void appendEgg(Integer eggID) {
        this.jsonBuilder.replace("egg", eggID);
    }

    public void appendDockerImage(String dockerImage) {
        this.jsonBuilder.replace("docker_image", dockerImage);
    }

    public void appendStartupCode(String startupCode) {
        this.jsonBuilder.replace("startup", startupCode);
    }

    public void appendEnvironment(Enviroment enviroment) {
        enviroment.check();
        this.jsonBuilder.replace("environment", new JSONObject(enviroment.getArgs()));
    }

    public void appendLimits(ServerLimits limits) {
        this.jsonBuilder.replace("limits", limits.makeJson());
    }

    public void appendFeatureLimits(FeatureLimits limits) {
        JSONObject object = limits.makeJson();
        this.jsonBuilder.replace("feature_limits", object);
    }

    public void appendServerPortIndex(Long portIndex) {
        ((JSONObject) this.jsonBuilder.get("allocation")).replace("default", portIndex);
    }

    public JSONObject completeConfiguration() {
        return this.jsonBuilder;
    }

}
