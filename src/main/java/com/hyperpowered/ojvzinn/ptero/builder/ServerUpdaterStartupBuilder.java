package com.hyperpowered.ojvzinn.ptero.builder;

import com.hyperpowered.ojvzinn.ptero.builder.server.Enviroment;
import com.hyperpowered.ojvzinn.ptero.model.ServerModel;
import org.json.simple.JSONObject;

@SuppressWarnings("unchecked")
public class ServerUpdaterStartupBuilder {

    private final JSONObject jsonBuilder;

    public ServerUpdaterStartupBuilder(ServerModel serverBackup) {
        this.jsonBuilder = new JSONObject();
        this.jsonBuilder.put("startup", serverBackup.getContainer().getServerStartupCommand());

        JSONObject environment = new JSONObject(serverBackup.getServerEnviroment().getArgs());
        this.jsonBuilder.put("environment", environment);

        this.jsonBuilder.put("egg", serverBackup.getServerEggID());
        this.jsonBuilder.put("image", serverBackup.getContainer().getServerImage());
        this.jsonBuilder.put("skip_scripts", false);
    }

    public void appendStartupArguments(String startup) {
        this.jsonBuilder.replace("startup", startup);
    }

    public void appendEnvironment(Enviroment enviroment) {
        enviroment.check();
        this.jsonBuilder.replace("environment", enviroment.getArgs());
    }

    public void appendEggID(Long egg) {
        this.jsonBuilder.put("egg", egg);
    }

    public void appendServerImage(String image) {
        this.jsonBuilder.put("image", image);
    }

    public JSONObject completeConfiguration() {
        return this.jsonBuilder;
    }

}
