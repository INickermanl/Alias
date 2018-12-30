package com.nickrman.alias.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nickrman.alias.data.db.model.Vocabulary;

import java.util.List;

import io.reactivex.Flowable;


@Dao
public interface BookDao {

    @Query("SELECT * FROM vocabulary WHERE id = :userId")
    Flowable<Vocabulary> getBooksById(int userId);

    @Query("SELECT * FROM vocabulary")
    Flowable<List<Vocabulary>> getAllBooks();

    /*@Insert
    void insertBook(Vocabulary... vocabularies);

    @Update
    void updateBook(Vocabulary... vocabularies);

    @Delete
    void deleteBooks(Vocabulary Vocabulary);

    @Query("DELETE FROM vocabulary")
    void deleteAllBooks();*/
}
