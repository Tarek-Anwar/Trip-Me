package com.example.tripmei;

import java.io.Serializable;

public class NotesDetils implements Serializable {

    private  String note;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public NotesDetils(String note) {
        this.note = note;
    }
    public NotesDetils() {

    }
}
