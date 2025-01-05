package net.hyperpowered.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.hyperpowered.manager.*;

@AllArgsConstructor
@Getter
public enum ManagerPolicy {

    ALL(null),
    SERVER(ServerManager.class),
    USER(UserManager.class),
    NODE(NodeManager.class),
    LOCATION(LocationManager.class),
    NEST(NestManager.class);

    private final Class<? extends Manager> classManager;
}
