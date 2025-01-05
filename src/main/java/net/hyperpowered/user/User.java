package net.hyperpowered.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class User {

    private long id;
    private String externalID;
    private UUID uuid;

    @Setter
    private String username;

    @Setter
    private String email;

    @Setter
    private String firstName;

    @Setter
    private String lastName;

    @Setter
    private String language;

    @Setter
    private boolean admin;

    @Setter
    private boolean twoFactors;

    private String createDate;
    private String updateDate;

}
