package net.hyperpowered.location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class Location {

    private long id;

    @Setter
    private String locationName;

    @Setter
    private String locationDescription;

    private String updated_at;
    private String created_at;

}
