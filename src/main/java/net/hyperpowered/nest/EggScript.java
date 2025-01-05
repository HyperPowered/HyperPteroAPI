package net.hyperpowered.nest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EggScript {

    private boolean privileged;
    private String install;
    private String entry;
    private String container;

}
