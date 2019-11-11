package com.dsss.ankush.khidari;

import java.io.Serializable;

public class Sponser implements Serializable{
    String name,email,mobile,dob,comapany,pan,password,profile_pic;

    public String getName() {
        return name;
    }

    public Sponser() {
        profile_pic="http://goo.gl/gEgYUd";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getComapany() {
        return comapany;
    }

    public void setComapany(String comapany) {
        this.comapany = comapany;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
