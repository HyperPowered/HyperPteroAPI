package com.hyperpowered.ojvzinn.ptero.model.server;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ServerFeatureLimits {

    private Long serverDatabaseLimit;
    private Long serverAllocationsLimit;
    private Long serverBackupLimit;
}
