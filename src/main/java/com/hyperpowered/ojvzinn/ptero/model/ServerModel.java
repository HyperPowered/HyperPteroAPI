package com.hyperpowered.ojvzinn.ptero.model;

import com.hyperpowered.ojvzinn.ptero.model.server.ServerConfiguration;
import com.hyperpowered.ojvzinn.ptero.model.server.ServerContainer;
import com.hyperpowered.ojvzinn.ptero.model.server.ServerEnviroment;
import com.hyperpowered.ojvzinn.ptero.model.server.ServerFeatureLimits;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.json.simple.JSONObject;

@AllArgsConstructor
@Getter
public class ServerModel {

    private String serverName;
    private String serverDescription;
    private String serverIdentifier;
    private String serverUUID;
    private String serverID;
    private boolean isSuspended;
    private Long serverOwnerID;
    private Long serverNodeID;
    private Long serverAllocationID;
    private Long serverNestID;
    private Long serverEggID;
    private ServerConfiguration configuration;
    private ServerFeatureLimits featureLimits;
    private ServerContainer container;
    private ServerEnviroment serverEnviroment;
    private JSONObject serverJson;
}
