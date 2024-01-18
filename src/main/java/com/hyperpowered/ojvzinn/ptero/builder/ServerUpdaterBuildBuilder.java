package com.hyperpowered.ojvzinn.ptero.builder;

import com.hyperpowered.ojvzinn.ptero.builder.server.FeatureLimits;
import com.hyperpowered.ojvzinn.ptero.model.ServerModel;
import com.hyperpowered.ojvzinn.ptero.utils.CapacityEnum;
import org.json.simple.JSONObject;

@SuppressWarnings("unchecked")
public class ServerUpdaterBuildBuilder {

    private final JSONObject jsonBuilder;

    public ServerUpdaterBuildBuilder(ServerModel serverBackup) {
        this.jsonBuilder = new JSONObject();
        this.jsonBuilder.put("allocation", serverBackup.getServerAllocationID());
        this.jsonBuilder.put("memory", serverBackup.getConfiguration().getServerMemory());
        this.jsonBuilder.put("swap", serverBackup.getConfiguration().getServerSwap());
        this.jsonBuilder.put("disk", serverBackup.getConfiguration().getServerDisk());
        this.jsonBuilder.put("io", serverBackup.getConfiguration().getServerBlockIO());
        this.jsonBuilder.put("cpu", serverBackup.getConfiguration().getServerCPU());
        this.jsonBuilder.put("threads", null);

        JSONObject feature_limits = new JSONObject();
        feature_limits.put("databases", serverBackup.getFeatureLimits().getServerDatabaseLimit());
        feature_limits.put("allocations", serverBackup.getFeatureLimits().getServerAllocationsLimit());
        feature_limits.put("backups", serverBackup.getFeatureLimits().getServerBackupLimit());
        this.jsonBuilder.put("feature_limits", feature_limits);
    }

    public void appendAllocation(Long allocationIndex) {
        this.jsonBuilder.replace("allocation", allocationIndex);
    }

    public void appendMemoryLimit(Double memoryLimit, CapacityEnum capacityType) {
        this.jsonBuilder.replace("memory", capacityType.transformToMibs(memoryLimit));
    }

    public void appendSwapLimit(Double swap, CapacityEnum capacityType) {
        this.jsonBuilder.replace("swap", capacityType.transformToMibs(swap));
    }

    public void appendDeskLimit(Double disk, CapacityEnum capacityType) {
        this.jsonBuilder.replace("disk", capacityType.transformToMibs(disk));
    }

    public void appendBlockIO(Long blockIO) {
        this.jsonBuilder.replace("io", blockIO);
    }

    public void appendCPULimit(Long cpu) {
        this.jsonBuilder.replace("cpu", cpu);
    }

    public void appendFeatureLimits(FeatureLimits featureLimits) {
        this.jsonBuilder.replace("feature_limits", featureLimits.makeJson());
    }

    public JSONObject completeConfiguration() {
        return this.jsonBuilder;
    }

}
