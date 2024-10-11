package org.nevernote.dto;

public class NoteDTO {
    private Long id;
    private String title;
    private String description;
    public void  setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    public void  setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
