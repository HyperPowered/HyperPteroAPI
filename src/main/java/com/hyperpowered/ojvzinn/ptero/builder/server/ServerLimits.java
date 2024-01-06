package com.hyperpowered.ojvzinn.ptero.builder.server;

import com.hyperpowered.ojvzinn.ptero.utils.CapacityEnum;
import lombok.Setter;
import org.json.simple.JSONObject;

public class ServerLimits {

    private Double memory;
    private Double swap;
    private Double disk;

    @Setter
    private Integer blockIO;

    @Setter
    private Integer cpu;

    public ServerLimits() {
        this.memory = CapacityEnum.MEGA.transformToMibs(128D);
        this.disk = CapacityEnum.MEGA.transformToMibs(512D);
        this.swap = 0D;
        this.blockIO = 500;
        this.cpu = 100;
    }

    public void setMemory(Double value, CapacityEnum type) {
        this.memory = type.transformToMibs(value);
    }

    public void setSwap(Double value, CapacityEnum type) {
        this.swap = type.transformToMibs(value);
    }

    public void setDisk(Double value, CapacityEnum type) {
        this.disk = type.transformToMibs(value);
    }

    @SuppressWarnings("all")
    public JSONObject makeJson() {
        JSONObject object = new JSONObject();
        object.put("memory", this.memory);
        object.put("swap", this.swap);
        object.put("disk", this.disk);
        object.put("io", this.blockIO);
        object.put("cpu", this.cpu);
        return object;
    }
}
