package com.hyperpowered.ojvzinn.ptero.manager;

import com.hyperpowered.ojvzinn.ptero.builder.ServerCreatorBuilder;
import com.hyperpowered.ojvzinn.ptero.method.ActionType;
import com.hyperpowered.ojvzinn.ptero.method.RequestMethod;
import com.hyperpowered.ojvzinn.ptero.request.Requester;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ServersManager {

    private final String painelLink;
    private final String apiToken;

    public void createServer(ServerCreatorBuilder builder) {
        new Requester(RequestMethod.POST, this.apiToken, this.painelLink, ActionType.SERVER).request(builder.completeConfiguration().toString());
    }

    public void deleteServer(String serverID) {
        new Requester(RequestMethod.DELETE, this.apiToken, this.painelLink, ActionType.SERVER).request("", serverID);
    }

    public void forceDeleteServer(String serverID) {
        new Requester(RequestMethod.DELETE, this.apiToken, this.painelLink, ActionType.SERVER).request("", serverID + "/force");
    }

    public void reinstallServer(String serverID) {
        new Requester(RequestMethod.POST, this.apiToken, this.painelLink, ActionType.SERVER).request("", serverID + "/reinstall");
    }

    public void unsuspendServer(String serverID) {
        new Requester(RequestMethod.POST, this.apiToken, this.painelLink, ActionType.SERVER).request("", serverID + "/unsuspend");
    }

    public void suspendServer(String serverID) {
        new Requester(RequestMethod.POST, this.apiToken, this.painelLink, ActionType.SERVER).request("", serverID + "/suspend");
    }
}
