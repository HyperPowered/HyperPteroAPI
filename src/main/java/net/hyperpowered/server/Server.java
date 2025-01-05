package net.hyperpowered.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class Server {

    private long id;

    @Setter
    private String externalID;

    private UUID uuid;
    private String identifier;

    @Setter
    private String name;

    @Setter
    private String description;

    private boolean suspended;
    private ServerLimit serverLimit;
    private ServerFutureLimit serverFutureLimit;

    @Setter
    private long user;

    private long node;

    @Setter
    private long allocation;

    private long nest;

    @Setter
    private long egg;

    private String pack;
    private ServerContainer container;
    private String updatedAt;
    private String createdAt;

}
