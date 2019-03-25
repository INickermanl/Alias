package com.nickrman.alias.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.nickrman.alias.data.db.model.Word;

import java.util.List;

import io.reactivex.Flowable;


@Dao
public interface WordDao {

    @Query("SELECT * FROM words WHERE id_name_vocabulary = :id_vocabulary")
    Flowable<List<Word>> getWordsByIdVocabulary(int id_vocabulary);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWord(Word... words);

    @Delete
    void deleteWord(Word word);

    @Query("DELETE FROM words")
    void deleteAllWords();
}
