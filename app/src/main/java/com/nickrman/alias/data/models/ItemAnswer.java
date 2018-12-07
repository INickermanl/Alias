package com.nickrman.alias.data.models;

public class ItemAnswer {
    private String word;
    private boolean answer;

    public ItemAnswer(String word, boolean answer) {
        this.word = word;
        this.answer = answer;
    }

    public ItemAnswer(String word) {
        this.word = word;
    }

    public ItemAnswer(boolean answer) {
        this.answer = answer;
    }

    public String getWord() {
        return word;
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }
}
