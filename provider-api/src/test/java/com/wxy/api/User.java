package com.wxy.api;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private String username;
    private String password;
    private int age;

    public User(String johnDoe, String password123, int i) {
        username = johnDoe;
        password = password123;
        age = i;
    }

}
