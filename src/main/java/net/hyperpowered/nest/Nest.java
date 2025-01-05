package net.hyperpowered.nest;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class Nest {

    private long id;
    private UUID uuid;
    private String author;
    private String name;
    private String description;
    private String created_at;
    private String updated_at;

}
