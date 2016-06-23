package com.demo.rssapplication.common.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {

    // Common fields
    @PrimaryKey
    private long id;

    private String firstName;
    private String lastName;
    private String email;

    public User() {

    }
}