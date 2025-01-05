package net.hyperpowered.server.builder;

import lombok.Getter;
import net.hyperpowered.interfaces.Builder;
import org.json.simple.JSONObject;

@Getter
public class ServerAllocationBuilder implements Builder {

    private Long Default;

    public ServerAllocationBuilder appendDefault(long Default) {
        this.Default = Default;
        return this;
    }

    @Override
    public JSONObject buildToJSON() throws IllegalArgumentException {
        if (this.Default == null) {
            throw new IllegalArgumentException("OS ARGUMENTOS NAO PODEM SER NULOS!");
        }

        JSONObject response = new JSONObject();
        response.put("default", this.Default);
        return response;
    }
}
