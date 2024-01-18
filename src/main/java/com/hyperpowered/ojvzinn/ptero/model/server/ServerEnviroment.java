package com.hyperpowered.ojvzinn.ptero.model.server;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ServerEnviroment {

    private final Map<String, String> args = new HashMap<>();

    public void appendArg(String argKey, String argValue) {
        this.args.put(argKey, argValue);
    }

}
