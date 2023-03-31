package com.example.padil.Model;

public class ProfileModel {

    String avatar;

    public ProfileModel(){

    }

    public ProfileModel(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
