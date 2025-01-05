package net.hyperpowered.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ServerFutureLimit {

    private long databases;
    private long allocations;
    private long backups;

}
