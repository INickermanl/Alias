package com.nickrman.alias.data.db.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

//
import android.support.annotation.NonNull;

@Entity(tableName = "vocabulary")
public class Vocabulary {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;


    @ColumnInfo(name = "nameBook")
    private String nameBook;

    @ColumnInfo(name = "id_vocabulary")
    private int vocabularyId;

    public Vocabulary(int vocabularyId, String nameBook) {
        this.nameBook = nameBook;
        this.vocabularyId = vocabularyId;
    }

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new StringBuffer(nameBook).toString();
    }

    public int getVocabularyId() {
        return vocabularyId;
    }

    public void setVocabularyId(int vocabularyId) {
        this.vocabularyId = vocabularyId;
    }
}
