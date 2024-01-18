package com.hyperpowered.ojvzinn.ptero.builder.server;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnviromentType {

    MINECRAFT("minecraft"),
    JAVA("java"),
    PERSONALITY("personality");

    private final String jsonKeyName;

}
