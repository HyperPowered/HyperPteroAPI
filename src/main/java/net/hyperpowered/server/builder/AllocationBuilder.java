package net.hyperpowered.server.builder;

import lombok.Getter;
import net.hyperpowered.interfaces.Builder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Arrays;

@Getter
public class AllocationBuilder implements Builder {

    private String ip;
    private JSONArray ports;

    public AllocationBuilder appendIP(String ip) {
        this.ip = ip;
        return this;
    }

    public AllocationBuilder appendPorts(long... ports) {
        JSONArray portsValue = new JSONArray();
        Arrays.stream(ports).forEach(port -> portsValue.add(String.valueOf(port)));
        this.ports = portsValue;
        return this;
    }

    @Override
    public JSONObject buildToJSON() throws IllegalArgumentException {
        if (this.ip == null || this.ports == null) {
            throw new IllegalArgumentException("OS ARGUMENTOS NAO PODEM SER NULOS!");
        }

        JSONObject response = new JSONObject();
        response.put("ip", ip);
        response.put("ports", ports);
        return response;
    }
}
