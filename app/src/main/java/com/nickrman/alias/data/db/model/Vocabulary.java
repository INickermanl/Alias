package com.nickrman.alias.data.db.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

//
import android.support.annotation.NonNull;

@Entity(tableName = "vocabulary")
public class Vocabulary {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;


    @ColumnInfo(name = "nameBook")
    private String nameBook;

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new StringBuffer(nameBook).toString();
    }
}
