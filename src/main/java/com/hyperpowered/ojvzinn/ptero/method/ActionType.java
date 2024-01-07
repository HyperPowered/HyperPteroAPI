package com.hyperpowered.ojvzinn.ptero.method;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ActionType {

    SERVER("/api/application/servers/");
    private final String linkPatch;
}
