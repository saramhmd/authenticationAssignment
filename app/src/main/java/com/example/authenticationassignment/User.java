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
//        this.image = image;
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
/*
@IgnoreExtraProperties
public class User {

    public String username;
    public String email;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

}
 */