package com.nickrman.alias.screens.result;

import android.os.Bundle;

import com.nickrman.alias.data.models.ItemAnswer;
import com.nickrman.alias.services.Navigator;
import com.nickrman.alias.services.navigation.BackNavigator;
import com.nickrman.alias.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class ResultPresenter implements ResultContract.Presenter {
    private ResultContract.View view;
    private List<ItemAnswer> listItemAnswer = new ArrayList<>();
    private CompositeDisposable subscription;
    private Bundle data;


    public ResultPresenter(ResultContract.View view, Bundle data) {
        this.view = view;
        this.data = data;
        makeListAnswer();
    }

    private void makeListAnswer() {
        String words = data.getString(Constants.USER_WORDS);
        String answers = data.getString(Constants.USER_ANSWER);


        List<Boolean> listAnswer = new ArrayList<>();
        List<String> listWords = new ArrayList<>();

        for (String answer : answers.split(",")) {
            listAnswer.add(Boolean.valueOf(answer));
        }
        for (String word : words.split(",")) {
            listWords.add(word);
        }

        for (int i = 0; i < listWords.size(); i++) {

            listItemAnswer.add(new ItemAnswer(listWords.get(i), listAnswer.get(i)));

        }


    }

    @Override
    public void start() {
        subscription = new CompositeDisposable();
        setupAction();

    }

    private void setupAction() {
        view.setTeamList(listItemAnswer);
    }

    @Override
    public void stop() {
        subscription.dispose();
        subscription = null;
    }

    @Override
    public void setNavigator(Navigator navigator) {

    }

    @Override
    public void setBackNavigator(BackNavigator backNavigator) {

    }
}
