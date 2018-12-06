package com.nickrman.alias.screens.card;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.nickrman.alias.R;
import com.nickrman.alias.base.App;
import com.nickrman.alias.base.BaseActivity;

import io.reactivex.Observable;

public class CardView implements CardContract.View {

    private static final long ANIMATION_DURATION = 150L;

    private View flipCard;
    private View currentCard;
    private TextView startText;
    private View mainContainer;
    BaseActivity activity;
    GestureDetector gd;

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
            }
        });


        ObjectAnimator rotationCurrentCardAnim = ObjectAnimator.ofFloat(currentCard, "rotationY", -90.0f, 0.0f);
        rotationCurrentCardAnim.setDuration(ANIMATION_DURATION);
        AnimatorSet mainSet = new AnimatorSet();
        mainSet.playSequentially(rotationFlipCardAnim, rotationCurrentCardAnim);
        mainSet.start();
    }

    @Override
    public void dismissCard() {
        float distanceX = (mainContainer.getRight() + currentCard.getWidth() + 50) * -1;
        float distanceY = (mainContainer.getTop());
        ObjectAnimator translateX = ObjectAnimator.ofFloat(currentCard, "translationX", 0.0f, distanceX);
        ObjectAnimator translateY = ObjectAnimator.ofFloat(currentCard, "translationY", distanceY);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(200);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(translateX, translateY);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startText.setVisibility(View.GONE);
                currentCard.setVisibility(View.GONE);
                currentCard.setTranslationX(0.0f);
                currentCard.setTranslationY(0.0f);
                flipCard.setVisibility(View.VISIBLE);
                startAnimation();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);

            }
        });
        set.start();
    }

    @Override
    public void acceptCard() {
        float distanceX = (mainContainer.getBottom());
        float distanceY = (mainContainer.getRight() + currentCard.getHeight() + 50) * -1;

        ObjectAnimator translateX = ObjectAnimator.ofFloat(currentCard, "translationX", 0.0f, distanceX);
        ObjectAnimator translateY = ObjectAnimator.ofFloat(currentCard, "translationY", distanceY);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(200);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(translateX, translateY);
        set.addListener(new AnimatorListenerAdapter() {


            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startText.setVisibility(View.GONE);
                currentCard.setVisibility(View.GONE);
                currentCard.setTranslationX(0.0f);
                currentCard.setTranslationY(0.0f);
                flipCard.setVisibility(View.VISIBLE);
                startAnimation();

            }


        });

        set.start();


    }

    @Override
    public void startTextVisibility(boolean visibility) {
        startText.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }
}
