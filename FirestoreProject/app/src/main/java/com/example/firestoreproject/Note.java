package com.example.firestoreproject;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.List;

public class Note {

    private String documentId;
    private String title;
    private String description;
    private int priority;
    List<String> tags;

    public Note() {}

    public Note(String title, String description, int priority, List<String> tags) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public List<String> getTags() {
        return tags;
    }
}
