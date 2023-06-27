package org.example.pojo;

public class CreateUserJson {

    private final String email;

    private final String password;

    private final String name;

    public CreateUserJson(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

}
