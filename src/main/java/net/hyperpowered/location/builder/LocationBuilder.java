package net.hyperpowered.location.builder;

import lombok.Getter;
import net.hyperpowered.interfaces.Builder;
import org.json.simple.JSONObject;

@Getter
public class LocationBuilder implements Builder {

    private String locationName;
    private String locationDescription;

    public LocationBuilder appendLocationName(String locationName) {
        this.locationName = locationName;
        return this;
    }

    public LocationBuilder appendLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
        return this;
    }

    @Override
    public JSONObject buildToJSON() throws IllegalArgumentException {
        if (this.locationName == null || this.locationDescription == null) {
            throw new IllegalArgumentException("OS ARGUMENTOS NAO PODEM SER NULOS!");
        }

        JSONObject location = new JSONObject();
        location.put("short", this.locationName);
        location.put("long", this.locationDescription);
        return location;
    }
}
