package com.hyperpowered.ojvzinn.ptero.method;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RequestMethod {
    POST("POST"),
    DELETE("DELETE"),
    GET("GET"),
    PATCH("PATCH");

    private final String method;
}
