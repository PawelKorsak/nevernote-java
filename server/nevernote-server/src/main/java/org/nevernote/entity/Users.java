package org.nevernote.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String passwordHash;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Note> notes;

    public void setId(Long id) {this.id=id;}
    public Long getId() {return id;}
    public void setUsername(String username) {this.username=username;}
    public String getUsername() {return this.username;}
    public void setPasswordHash(String passwordHash) {this.passwordHash=passwordHash;}
    public String getPasswordHash() {return this.passwordHash;}

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
