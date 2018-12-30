package com.nickrman.alias.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.nickrman.alias.data.db.model.Vocabulary;

import java.util.List;

import io.reactivex.Flowable;


@Dao
public interface VocabularyDAO {

    @Query("SELECT * FROM vocabulary WHERE id = :userId")
    Flowable<Vocabulary> getBooksById(int userId);

    @Query("SELECT * FROM vocabulary")
    Flowable<List<Vocabulary>> getAllBooks();

    @Insert
    void insertBook(Vocabulary vocabulary);

/*
    @Update
    void updateBook(Vocabulary... vocabularies);

    @Delete
    void deleteBooks(Vocabulary Vocabulary);

    @Query("DELETE FROM vocabulary")
    void deleteAllBooks();*/
}
