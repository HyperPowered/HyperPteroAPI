package net.hyperpowered.nest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.json.simple.JSONObject;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class Egg {

    private long id;
    private UUID uuid;
    private long nest;
    private String author;
    private String description;
    private String docker_image;
    private JSONObject config;
    private String startup;
    private EggScript eggScript;
    private String created_at;
    private String updated_at;

}
