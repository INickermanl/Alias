package com.nickrman.alias.data.db.repository;

import com.nickrman.alias.data.db.dao.VocabularyDAO;
import com.nickrman.alias.data.db.dao.WordDao;
import com.nickrman.alias.data.db.model.Vocabulary;
import com.nickrman.alias.data.db.model.Word;

import java.util.List;

import io.reactivex.Flowable;

public class RepositorySource implements IWordDataSourse {

    private static RepositorySource repositorySource;
    private VocabularyDAO vocabularyDAO;
    private WordDao wordDao;

    public RepositorySource(VocabularyDAO vocabularyDAO, WordDao wordDao) {
        this.vocabularyDAO = vocabularyDAO;
        this.wordDao = wordDao;
    }

    public static RepositorySource getINSTANCE(VocabularyDAO vocabularyDAO, WordDao wordDao) {
        if (repositorySource == null) {
            repositorySource = new RepositorySource(vocabularyDAO,wordDao);
        }
        return repositorySource;
    }

    @Override
    public Flowable<List<Vocabulary>> getListVocabulary() {
        return this.vocabularyDAO.getListVocabulary();
    }

    @Override
    public void insertVocabulary(Vocabulary... vocabularies) {
        this.vocabularyDAO.insertVocabulary(vocabularies);
    }

    @Override
    public void updateVocabulary(Vocabulary vocabulary) {
        this.vocabularyDAO.updateVocabulary(vocabulary);
    }

    @Override
    public void deleteVocabulary(Vocabulary vocabulary) {
        this.vocabularyDAO.deleteVocabulary(vocabulary);
    }

    @Override
    public Flowable<List<Word>> getWordsByIdVocabulary(int id_vocabulary) {
        return this.wordDao.getWordsByIdVocabulary(id_vocabulary);
    }

    @Override
    public void insertWord(Word... words) {
        this.wordDao.insertWord(words);
    }

    @Override
    public void deleteWord(Word word) {
        this.wordDao.deleteWord(word);
    }

    @Override
    public void deleteAllWords() {
        this.wordDao.deleteAllWords();
    }
}
