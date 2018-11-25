package com.nickrman.alias.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.nickrman.alias.data.db.model.Word;

import java.util.List;


@Dao
public interface WordDao {

    @Query("SELECT * FROM Word WHERE bookName IS :nameBook")
    List<Word> getWordsInBook(String nameBook);

    @Query("SELECT * FROM Word WHERE word IS :word")
    Word getWord(String word);

    @Insert
    void insetAllWord(Word...wordList);

    @Delete
    void deleteWord(Word word);
}
