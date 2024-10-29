package org.nevernote.dto;

import java.util.List;

public class UserDTO {

    private Long id;
    private String username;
    private String password;
    private List<NoteDTO> notes;

    public void setId(Long id) {this.id=id;}
    public Long getId() {return id;}
    public void setUsername(String username) {this.username=username;}
    public String getUsername() {return this.username;}
    public void setPassword(String password) {this.password = password;}
    public String getPassword() {return this.password;}

    public List<NoteDTO> getNotes() {
        return notes;
    }

    public void setNotes(List<NoteDTO> notes) {
        this.notes = notes;
    }
}
