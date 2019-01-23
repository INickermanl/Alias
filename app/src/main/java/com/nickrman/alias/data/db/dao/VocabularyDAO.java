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
public interface VocabularyDAO {

   @Query("SELECT * FROM vocabulary")
    Flowable<List<Vocabulary>> getListVocabulary();

   @Insert
    void insertVocabulary(Vocabulary... vocabularies);

   @Update
    void updateVocabulary(Vocabulary vocabulary);

   @Delete
    void deleteVocabulary(Vocabulary vocabulary);

}
