package com.hyperpowered.ojvzinn.ptero.model.server;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ServerConfiguration {

    private Long serverMemory;
    private Long serverSwap;
    private Long serverDisk;
    private Long serverBlockIO;
    private Long serverCPU;

}
