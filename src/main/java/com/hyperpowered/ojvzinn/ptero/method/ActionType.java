package com.hyperpowered.ojvzinn.ptero.method;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ActionType {

    CREATE_SERVER("/api/application/servers/");

    private final String linkPatch;
}
