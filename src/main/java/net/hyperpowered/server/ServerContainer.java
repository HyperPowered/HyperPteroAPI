package net.hyperpowered.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;

@AllArgsConstructor
@Getter
public class ServerContainer {

    @Setter
    private String startup;

    @Setter
    private String image;

    private boolean installed;

    @Setter
    private JSONObject environment;

}
