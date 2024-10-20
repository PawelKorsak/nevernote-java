package org.nevernote.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String username;
    private String passwordHash;

    public void setId(Long id) {this.id=id;}
    public Long getId() {return id;}
    public void setUsername(String username) {this.username=username;}
    public String getUsername() {return this.username;}
    public void setPasswordHash(String passwordHash) {this.passwordHash=passwordHash;}
    public String getPasswordHash() {return this.passwordHash;}

}
