package net.orkohunter.vectorentry;

import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

public abstract class SimpleTwoFingerDoubleTapDetector {
    // Credits https://stackoverflow.com/a/12556832
    private static final int TIMEOUT = ViewConfiguration.getDoubleTapTimeout() + 100;
    private long mFirstDownTime = 0;
    private boolean mSeparateTouches = false;
    private byte mTwoFingerTapCount = 0;

    private void reset(long time) {
        mFirstDownTime = time;
        mSeparateTouches = false;
        mTwoFingerTapCount = 0;
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if(mFirstDownTime == 0 || event.getEventTime() - mFirstDownTime > TIMEOUT)
                    reset(event.getDownTime());
                break;
            case MotionEvent.ACTION_POINTER_UP:
                if(event.getPointerCount() == 2) {
                    mTwoFingerTapCount++;
                } else {
                    mFirstDownTime = 0;
                }
                break;
            case MotionEvent.ACTION_UP:
                if(!mSeparateTouches) {
                    mSeparateTouches = true;
                    if(mTwoFingerTapCount == 1) {
                        onTwoFingerSingleTap();
                        return true;
                    }
                }
                else if(mTwoFingerTapCount == 2 && event.getEventTime() - mFirstDownTime < TIMEOUT) {
                    onTwoFingerDoubleTap();
                    mFirstDownTime = 0;
                    return true;
                }
        }

        return false;
    }

    public abstract void onTwoFingerSingleTap();
    public abstract void onTwoFingerDoubleTap();
}