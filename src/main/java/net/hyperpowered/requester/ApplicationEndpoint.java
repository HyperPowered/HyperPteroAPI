package net.hyperpowered.requester;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApplicationEndpoint {

    USERS("/api/application/users"),
    SERVERS("/api/application/servers"),
    LOCATIONS("/api/application/locations"),
    NESTS("/api/application/nests"),
    NODES("/api/application/nodes");

    private final String endpoint;

}
