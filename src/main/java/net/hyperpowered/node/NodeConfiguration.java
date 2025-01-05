package net.hyperpowered.node;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class NodeConfiguration {

    private boolean debug;
    private UUID uuid;
    private String token_id;
    private String token;
    private NodeConfigApi api;
    private NodeConfigSystem system;
    private String remote;

}
