package com.dsss.ankush.khidari;

import java.io.Serializable;

public class Player implements Serializable{
    public  Player()
    {

    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public Player(String name, String email, String phone, String dob, String gameplayed, String awards, String height, String weight, String chest, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.gameplayed = gameplayed;
        this.awards = awards;
        this.height = height;
        this.weight = weight;
        this.chest = chest;
        this.password = password;
        profile_pic="http://goo.gl/gEgYUd";
    }

    public String getName() {
        return name;

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGameplayed() {
        return gameplayed;
    }

    public void setGameplayed(String gameplayed) {
        this.gameplayed = gameplayed;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getChest() {
        return chest;
    }

    public void setChest(String chest) {
        this.chest = chest;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    String name,email,phone,dob,gameplayed,awards,height,weight,chest,password,profile_pic;

}
