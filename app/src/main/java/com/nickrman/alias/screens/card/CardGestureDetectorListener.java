package com.nickrman.alias.screens.card;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import timber.log.Timber;

public class CardGestureDetectorListener extends GestureDetector.SimpleOnGestureListener {
    private static final int SWIPE_MIN_DISTANCE = 50;
    private static final int SWIPE_THRESHOLD_VELOCITY = 100;
    private SwipeCallback callback;

    public CardGestureDetectorListener(SwipeCallback callback) {
        this.callback = callback;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float deltaX = (e1.getX() - e2.getX());
        if (Math.abs(deltaX) >= SWIPE_MIN_DISTANCE) {
            if (Math.abs(velocityY) >= SWIPE_THRESHOLD_VELOCITY) {

                if (deltaX > 0) {
                    callback.swipeLeft();
                    Log.d("LOG", "swipe left");
                } else {
                    callback.swipeRight();
                    Log.d("LOG", "swipe right");
                }
            }
        }
        return false;
    }
}
