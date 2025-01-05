package net.hyperpowered.node.builder;

import lombok.Getter;
import net.hyperpowered.interfaces.Builder;
import org.json.simple.JSONObject;

@Getter
public class NodeBuilder implements Builder {

    private String name;
    private Long location_id;
    private String fqdn;
    private String scheme;
    private Long memory;
    private Long memory_overallocate;
    private Long disk;
    private Long disk_overallocate;
    private Long upload_size;
    private Long daemon_sftp;
    private Long daemon_listen;

    public NodeBuilder appendName(String name) {
        this.name = name;
        return this;
    }

    public NodeBuilder appendLocationID(long location_id) {
        this.location_id = location_id;
        return this;
    }

    public NodeBuilder appendFqdn(String fqdn) {
        this.fqdn = fqdn;
        return this;
    }

    public NodeBuilder appendScheme(String scheme) {
        this.scheme = scheme;
        return this;
    }

    public NodeBuilder appendMemory(long memory) {
        this.memory = memory;
        return this;
    }

    public NodeBuilder appendMemoryOverallocate(long memory_overallocate) {
        this.memory_overallocate = memory_overallocate;
        return this;
    }

    public NodeBuilder appendDisk(long disk) {
        this.disk = disk;
        return this;
    }

    public NodeBuilder appendDiskOverallocate(long disk_overallocate) {
        this.disk_overallocate = disk_overallocate;
        return this;
    }

    public NodeBuilder appendUploadSize(long upload_size) {
        this.upload_size = upload_size;
        return this;
    }

    public NodeBuilder appendDaemonSftp(long daemon_sftp) {
        this.daemon_sftp = daemon_sftp;
        return this;
    }

    public NodeBuilder appendDaemonListen(long daemon_listen) {
        this.daemon_listen = daemon_listen;
        return this;
    }

    @Override
    public JSONObject buildToJSON() throws IllegalArgumentException {
        if (this.name == null || this.location_id == null || this.fqdn == null || this.scheme == null || this.memory == null || this.memory_overallocate == null || this.disk == null || this.disk_overallocate == null || this.upload_size == null || this.daemon_sftp == null || this.daemon_listen == null) {
            throw new IllegalArgumentException("OS ARGUMENTOS NAO PODEM SER NULOS!");
        }

        JSONObject node = new JSONObject();
        node.put("name", this.name);
        node.put("location_id", this.location_id);
        node.put("fqdn", this.fqdn);
        node.put("scheme", this.scheme);
        node.put("memory", this.memory);
        node.put("memory_overallocate", this.memory_overallocate);
        node.put("disk", this.disk);
        node.put("disk_overallocate", this.disk_overallocate);
        node.put("upload_size", this.upload_size);
        node.put("daemon_sftp", this.daemon_sftp);
        node.put("daemon_listen", this.daemon_listen);
        return node;
    }
}
