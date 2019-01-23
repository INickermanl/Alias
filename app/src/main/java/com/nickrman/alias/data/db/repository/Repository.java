package com.nickrman.alias.data.db.repository;

import com.nickrman.alias.data.db.model.Vocabulary;
import com.nickrman.alias.data.db.model.Word;

import java.util.List;

import io.reactivex.Flowable;

public class Repository implements IWordDataSourse {

    private static Repository INSTANCE;
    private IWordDataSourse iWordDataSourse;


    public Repository(IWordDataSourse iWordDataSourse) {
        this.iWordDataSourse = iWordDataSourse;

    }

    public static Repository getINSTANCE(IWordDataSourse iWordDataSourse) {
        if (INSTANCE == null) {
            INSTANCE = new Repository(iWordDataSourse);
        }
        return INSTANCE;
    }

    @Override
    public Flowable<List<Vocabulary>> getListVocabulary() {
        return this.iWordDataSourse.getListVocabulary();
    }

    @Override
    public void insertVocabulary(Vocabulary... vocabularies) {
        this.iWordDataSourse.insertVocabulary(vocabularies);
    }

    @Override
    public void updateVocabulary(Vocabulary vocabulary) {
        this.iWordDataSourse.updateVocabulary(vocabulary);
    }

    @Override
    public void deleteVocabulary(Vocabulary vocabulary) {
        this.iWordDataSourse.deleteVocabulary(vocabulary);
    }

    @Override
    public Flowable<List<Word>> getWordsByIdVocabulary(int id_vocabulary) {
        return this.iWordDataSourse.getWordsByIdVocabulary(id_vocabulary);
    }

    @Override
    public void insertWord(Word... words) {
        this.iWordDataSourse.insertWord(words);
    }

    @Override
    public void deleteWord(Word word) {
        this.iWordDataSourse.deleteWord(word);
    }

    @Override
    public void deleteAllWords() {
        this.iWordDataSourse.deleteAllWords();
    }
}
