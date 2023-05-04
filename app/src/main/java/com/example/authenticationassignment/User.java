package com.example.authenticationassignment;

public class User {
    private String id;
    private String name;
    private String phoneNumber;
    private String image;
    private String email;

    public User() {
    }

    public User(String id, String name, String phoneNumber, String image) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.image = image;
    }

    public User( String name, String phoneNumber) {
//        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getImage() {
        return image;
    }
}
