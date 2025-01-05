package net.hyperpowered.node;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SSL {

    private boolean enabled;
    private String cert;
    private String key;

}
