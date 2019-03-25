package com.nickrman.alias.data.db.repository;


import com.nickrman.alias.data.db.model.Vocabulary;
import com.nickrman.alias.data.db.model.Word;

import java.util.List;

import io.reactivex.Flowable;

public interface IWordDataSourse {


    Flowable<List<Vocabulary>> getListVocabulary();

    void insertVocabulary(Vocabulary... vocabularies);


    void updateVocabulary(Vocabulary vocabulary);


    void deleteVocabulary(Vocabulary vocabulary);

    Flowable<List<Word>> getWordsByIdVocabulary(int id_vocabulary);

    void insertWord(Word... words);

    void deleteWord(Word word);

    void deleteAllWords();

    void deleteAllVocabulary();

}
