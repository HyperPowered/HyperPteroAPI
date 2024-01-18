package com.hyperpowered.ojvzinn.ptero.builder.server;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class Enviroment {

    @NonNull
    private EnviromentType type;
    private final Map<String, String> args = new HashMap<>();

    //Enviroment das nest padrões
    public void check() {
        switch (type) {
            case MINECRAFT: {
                this.args.put("SERVER_JARFILE", "server.jar");
                this.args.put("VANILLA_VERSION", "lasted");
                break;
            }

            case JAVA: {
                this.args.put("SERVER_JARFILE", "application.jar");
                break;
            }
        }
    }

    public void appendArgs(String argKey, String argValue) {
        this.args.put(argKey, argValue);
    }

}
