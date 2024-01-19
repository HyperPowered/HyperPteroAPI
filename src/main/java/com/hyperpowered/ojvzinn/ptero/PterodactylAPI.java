package com.hyperpowered.ojvzinn.ptero;

import com.hyperpowered.ojvzinn.ptero.manager.ServersManager;
import com.hyperpowered.ojvzinn.ptero.utils.DebugMode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PterodactylAPI {

    private DebugMode debugMode;

    public ServersManager managerServer(String apiToken, String painelLink) {
        return new ServersManager(painelLink, apiToken);
    }

    public void changeDebugMode() {
        if (debugMode == DebugMode.ON) {
            debugMode = DebugMode.OFF;
        } else {
            debugMode = DebugMode.ON;
        }
    }

    public void setDebugMode(DebugMode mode) {
        debugMode = mode;
    }

}
