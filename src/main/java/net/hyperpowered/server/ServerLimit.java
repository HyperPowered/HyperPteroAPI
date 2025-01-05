package net.hyperpowered.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ServerLimit {

    private long memory;
    private long swap;
    private long disk;
    private long io;
    private long cpu;
    private Long threads;

}
