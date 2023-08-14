package com.microservice.authentication.model;

import jakarta.persistence.*;

@Entity //specifico che la classe di sotto è una Entity e quindi avrà una propria tabella nel DB sottostante.
@Table(name = "user")
public class User {

    @Id //Con questa annotation specifico che ogni customer avrà un id univoco
    @GeneratedValue(strategy = GenerationType.AUTO) //con questa annotation specifico che ogni volta che aggiungo una nuova riga ovvero un nuovo customer, l'id si autoincrementerà da solo.
    private long id;

    @Column(name = "nickname")//specifico che name sarà una colonna della tabella customer.
    private String nickname;

    @Column(name = "email")//specifico che name sarà una colonna della tabella customer.
    private String email;

    @Column(name = "password")//specifico che name sarà una colonna della tabella customer.
    private String password;

    public User(){
    }

    public User(String nickname, String email, String password){
        this.nickname = nickname;
        this.email = email;
        this.password = password;
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
}
