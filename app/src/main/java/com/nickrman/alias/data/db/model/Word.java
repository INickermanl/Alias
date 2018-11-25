package com.nickrman.alias.data.db.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;


@Entity()
public class Word {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id ;

    @ColumnInfo
    private String word;

    @ColumnInfo
    private String bookName;


    public Word() {
    }

    @Ignore
    public Word(String word, String bookName) {
        this.word = word;
        this.bookName = bookName;
    }


    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }


    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
