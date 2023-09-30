package com.example.carte_e_mazzi.model;


import jakarta.persistence.*;


public class User {


    private long id;


    private String nickname;


    private String email;


    private String password;


    private boolean isOnline; //true = online, false = offline


    private int points;

    public User(){
    }

    public User(String nickname, String email, String password, boolean isOnline, int points){
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.isOnline = isOnline;
        this.points = points;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }


    public String getNickname() {
        return this.nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


    public boolean getisOnline() { return this.isOnline; }
    public void setisOnline(boolean isOnline) { this.isOnline = isOnline; }


    public int getPoints() { return this.points; }
    public void setPoints(int points){ this.points = points; }

}
