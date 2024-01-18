package com.hyperpowered.ojvzinn.ptero.manager;

import com.hyperpowered.ojvzinn.ptero.builder.ServerCreatorBuilder;
import com.hyperpowered.ojvzinn.ptero.builder.ServerUpdaterBuildBuilder;
import com.hyperpowered.ojvzinn.ptero.builder.ServerUpdaterDetailsBuilder;
import com.hyperpowered.ojvzinn.ptero.builder.ServerUpdaterStartupBuilder;
import com.hyperpowered.ojvzinn.ptero.method.ActionType;
import com.hyperpowered.ojvzinn.ptero.method.RequestMethod;
import com.hyperpowered.ojvzinn.ptero.model.ServerModel;
import com.hyperpowered.ojvzinn.ptero.parser.ServerParser;
import com.hyperpowered.ojvzinn.ptero.request.Requester;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@SuppressWarnings("unchecked")
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

    public List<ServerModel> listAllServers() {
        String response = new Requester(RequestMethod.GET, this.apiToken, this.painelLink, ActionType.SERVER).request("");
        List<ServerModel> servers = new ArrayList<>();
        try {
            JSONObject object = (JSONObject) new JSONParser().parse(response);
            JSONArray serverList = (JSONArray) object.get("data");
            for (Object serverObj : serverList) {
                ServerParser parser = new ServerParser(((JSONObject) serverObj).get("attributes").toString());
                parser.parser();
                servers.add(parser.transform());
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return servers;
    }

    public ServerModel getServerByID(String serverID) {
        ServerParser parser;
        try {
            String response = new Requester(RequestMethod.GET, this.apiToken, this.painelLink, ActionType.SERVER).request("", serverID);
            JSONObject serverJsonResponce = (JSONObject) new JSONParser().parse(response);
            System.out.println(serverJsonResponce.toString());
            parser = new ServerParser(serverJsonResponce.get("attributes").toString());
            parser.parser();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return parser.transform();
    }

    public void updateServerDetails(String serverID, ServerUpdaterDetailsBuilder builder) {
        new Requester(RequestMethod.PATCH, this.apiToken, this.painelLink, ActionType.SERVER).request(builder.completeConfiguration().toString(), serverID + "/details");
    }

    public void updateServerBuild(String serverID, ServerUpdaterBuildBuilder builder) {
        new Requester(RequestMethod.PATCH, this.apiToken, this.painelLink, ActionType.SERVER).request(builder.completeConfiguration().toString(), serverID + "/build");
    }

    public void updateServerStartup(String serverID, ServerUpdaterStartupBuilder builder) {
        new Requester(RequestMethod.PATCH, this.apiToken, this.painelLink, ActionType.SERVER).request(builder.completeConfiguration().toString(), serverID + "/startup");
    }
}
