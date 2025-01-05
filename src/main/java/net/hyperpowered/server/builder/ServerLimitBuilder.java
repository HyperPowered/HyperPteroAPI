package net.hyperpowered.server.builder;

import lombok.Getter;
import net.hyperpowered.interfaces.Builder;
import org.json.simple.JSONObject;

@Getter
public class ServerLimitBuilder implements Builder {

    private Long memory;
    private Long swap;
    private Long disk;
    private Long io;
    private Long cpu;

    public ServerLimitBuilder appendMemory(long memory) {
        this.memory = memory;
        return this;
    }

    public ServerLimitBuilder appendSwap(long swap) {
        this.swap = swap;
        return this;
    }

    public ServerLimitBuilder appendDisk(long disk) {
        this.disk = disk;
        return this;
    }

    public ServerLimitBuilder appendIO(long io) {
        this.io = io;
        return this;
    }

    public ServerLimitBuilder appendCPU(long cpu) {
        this.cpu = cpu;
        return this;
    }

    @Override
    public JSONObject buildToJSON() throws IllegalArgumentException {
        if (this.memory == null || this.swap == null || this.disk == null || this.io == null || this.cpu == null) {
            throw new IllegalArgumentException("OS ARGUMENTOS NAO PODEM SER NULOS!");
        }

        JSONObject response = new JSONObject();
        response.put("memory", memory);
        response.put("swap", swap);
        response.put("disk", disk);
        response.put("io", io);
        response.put("cpu", cpu);
        return response;
    }
}
