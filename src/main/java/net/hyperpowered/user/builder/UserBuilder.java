package net.hyperpowered.user.builder;

import lombok.Getter;
import net.hyperpowered.interfaces.Builder;
import org.json.simple.JSONObject;

@Getter
public class UserBuilder implements Builder {

    private String email;
    private String username;
    private String firstName;
    private String lastName;

    public UserBuilder appendEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder appendUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder appendFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder appendLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public JSONObject buildToJSON() throws IllegalArgumentException {
        if (this.email == null || this.username == null || this.firstName == null || this.lastName == null) {
            throw new IllegalArgumentException("OS ARGUMENTOS NAO PODEM SER NULOS!");
        }

        JSONObject user = new JSONObject();
        user.put("email", this.getEmail());
        user.put("username", this.getUsername());
        user.put("first_name", this.getFirstName());
        user.put("last_name", this.getLastName());
        return user;
    }
}
