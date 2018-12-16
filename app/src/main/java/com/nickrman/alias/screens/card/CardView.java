package com.nickrman.alias.screens.card;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.nickrman.alias.R;
import com.nickrman.alias.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.Observable;

public class CardView implements CardContract.View {

    private static final long ANIMATION_DURATION = 150L;

    private View flipCard;
    private View currentCard;
    private TextView startText;
    private View mainContainer;
    private View firstCard;
    private View secondCard;
    private View thirdCard;
    private TextView explainWords_tv;
    private TextView timeToEndGame;
    private TextView rightAnswer;
    private TextView wrongAnswer;

    BaseActivity activity;
    private List<String> listWords = new ArrayList<>();

    private View root;

    public CardView(View root, BaseActivity activity) {
        this.root = root;
        this.activity = activity;
        initView();
    }

    private void initView() {
        flipCard = root.findViewById(R.id.flipped_card);
        currentCard = root.findViewById(R.id.current_card);
        mainContainer = root.findViewById(R.id.main_container);
        startText = root.findViewById(R.id.start_text);
        firstCard = root.findViewById(R.id.first_card);
        secondCard = root.findViewById(R.id.second_card);
        thirdCard = root.findViewById(R.id.third_card);
        explainWords_tv = root.findViewById(R.id.explain_word);
        timeToEndGame = root.findViewById(R.id.time_to_end);
        rightAnswer = root.findViewById(R.id.right_answer);
        wrongAnswer = root.findViewById(R.id.wrong_answer);
    }

    @Override
    public Observable<Object> flipCard() {
        return RxView.clicks(flipCard);
    }

    @Override
    public Observable<MotionEvent> currentCard() {
        return RxView.touches(currentCard);
    }


    @Override
    public void startAnimation() {
        ObjectAnimator rotationFlipCardAnim = ObjectAnimator.ofFloat(flipCard, "rotationY", 0.0f, 90.0f);
        rotationFlipCardAnim.setDuration(ANIMATION_DURATION);
        rotationFlipCardAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                currentCard.setVisibility(View.VISIBLE);
                flipCard.setVisibility(View.GONE);
                flipCard.setClickable(false);
            }
        });


        ObjectAnimator rotationCurrentCardAnim = ObjectAnimator.ofFloat(currentCard, "rotationY", -90.0f, 0.0f);
        rotationCurrentCardAnim.setDuration(ANIMATION_DURATION);
        AnimatorSet mainSet = new AnimatorSet();
        mainSet.playSequentially(rotationFlipCardAnim, rotationCurrentCardAnim);
        mainSet.start();
    }

    @Override
    public void dismissCard(String explainWords) {

        int distanceX = (mainContainer.getLeft() + currentCard.getWidth() + 200) * -1;
        swipeCard(distanceX,explainWords);

    }

    @Override
    public void acceptCard(String explainWords) {
        int distanceX = (mainContainer.getRight() + currentCard.getWidth() + 200);
        swipeCard(distanceX, explainWords);



    }
    private void swipeCard(int distanceX,String explainWords) {
        Random random = new Random();
        int distanceY = (random.nextInt(mainContainer.getTop()) * (random.nextInt(3) - 1));
        ObjectAnimator translateX = ObjectAnimator.ofFloat(currentCard, "translationX", 0.0f, (float) distanceX);
        ObjectAnimator translateY = ObjectAnimator.ofFloat(currentCard, "translationY", (float) distanceY);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(300);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(translateX,translateY);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startText.setVisibility(View.GONE);
                currentCard.setVisibility(View.GONE);
                currentCard.setTranslationX(0.0f);
                explainWords_tv.setText(explainWords);
                currentCard.setTranslationY(0.0f);
                flipCard.setVisibility(View.VISIBLE);
                rotateCards();
                startAnimation();
            }
        });
        set.start();
    }
    private void rotateCards(){
        ObjectAnimator rotationFirstCard;
        ObjectAnimator rotationSecondCard;
        ObjectAnimator rotationThirdCard;
        if(firstCard.getRotation() != 16.0f && secondCard.getRotation() != -16.0f && thirdCard.getRotation() != 10.0f) {
            rotationFirstCard = ObjectAnimator.ofFloat(firstCard, "rotation", firstCard.getRotation(), 16.0f);
            rotationSecondCard = ObjectAnimator.ofFloat(secondCard, "rotation", secondCard.getRotation(), -16.0f);
            rotationThirdCard = ObjectAnimator.ofFloat(thirdCard,"rotation", thirdCard.getRotation(), 10.0f);
        }else{
            rotationFirstCard = ObjectAnimator.ofFloat(firstCard, "rotation", firstCard.getRotation(), - 16.0f);
            rotationSecondCard = ObjectAnimator.ofFloat(secondCard, "rotation", secondCard.getRotation(), 16.0f);
            rotationThirdCard = ObjectAnimator.ofFloat(thirdCard,"rotation",thirdCard.getRotation(),-10.0f);
        }
        AnimatorSet set = new AnimatorSet();
        set.setDuration(400);
        set.playTogether(rotationFirstCard,rotationSecondCard, rotationThirdCard);
        set.start();


    }


    @Override
    public void setCardWords(String word) {
        explainWords_tv.setText(word);
    }

    @Override
    public void setTimeToEndGame(String time) {
        timeToEndGame.setText(time.toString().trim());
    }

    @Override
    public String getTimeToEndGame() {
        return timeToEndGame.getText().toString().trim();
    }

    @Override
    public void setCountRightAnswer(String rightAnswer) {
        this.rightAnswer.setText(rightAnswer.trim());
    }

    @Override
    public void setCountWrongAnswer(String wrongAnswer) {
        this.wrongAnswer.setText(wrongAnswer.trim());
    }
}
