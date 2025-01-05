package net.hyperpowered.node;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Allocation {

    private long id;
    private String ip;
    private String alias;
    private long port;
    private String notes;
    private boolean assigned;

}
