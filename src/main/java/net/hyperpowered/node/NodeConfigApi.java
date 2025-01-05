package net.hyperpowered.node;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NodeConfigApi {

    private String host;
    private long port;
    private SSL ssl;
    private long upload_limit;

}
