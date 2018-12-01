package com.nickrman.alias.data.models;

public class VocabularyItem {
    private String nameVocabulary;

    public VocabularyItem(String nameVocabulary) {
        this.nameVocabulary = nameVocabulary;
    }

    public VocabularyItem() {
    }

    public String getNameVocabulary() {
        return nameVocabulary;
    }

    public void setNameVocabulary(String nameVocabulary) {
        this.nameVocabulary = nameVocabulary;
    }
}
