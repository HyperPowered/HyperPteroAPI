package net.hyperpowered.node;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class Node {

    private long id;
    private UUID uuid;
    private boolean Public;

    @Setter
    private String name;

    @Setter
    private String description;

    @Setter
    private long location_id;

    @Setter
    private String fqdn;

    @Setter
    private String scheme;

    @Setter
    private boolean behind_proxy;

    @Setter
    private boolean maintenance_mode;

    @Setter
    private long memory;

    @Setter
    private long memory_overallocate;

    @Setter
    private long disk;

    @Setter
    private long disk_overallocate;

    @Setter
    private long upload_size;

    @Setter
    private long daemon_listen;

    @Setter
    private long daemon_sftp;

    private String daemon_base;
    private String created_at;
    private String updated_at;

}
