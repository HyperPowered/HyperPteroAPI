package com.hyperpowered.ojvzinn.ptero;

import com.hyperpowered.ojvzinn.ptero.manager.ServersManager;
import com.hyperpowered.ojvzinn.ptero.utils.DebugMode;
import lombok.Getter;

public class PterodactylAPI {

    @Getter
    private static DebugMode debugMode = DebugMode.OFF;

    public static ServersManager managerServer(String apiToken, String painelLink) {
        return new ServersManager(painelLink, apiToken);
    }

    public static void changeDebugMode() {
        if (debugMode == DebugMode.ON) {
            debugMode = DebugMode.OFF;
        } else {
            debugMode = DebugMode.ON;
        }
    }

    public static void setDebugMode(DebugMode mode) {
        debugMode = mode;
    }

}
