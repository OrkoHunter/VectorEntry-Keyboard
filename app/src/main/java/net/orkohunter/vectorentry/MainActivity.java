package net.orkohunter.vectorentry;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "Abracadabra";
    private float startX;
    private float startY;
    private float endX;
    private float endY;

    private boolean validSwipingState = true;

    private boolean GROUP_SELECTION = true; // true = selecting a group; false = select an element of the group
    private int SELECTED_GROUP = -1; // From 0 to 11

    private int timeToSelectGroup = 5000;  // in miliseconds

    private ArrayList<ArrayList<String>> listOfGroups = new ArrayList<ArrayList<String>>();

    MediaPlayer mp = new MediaPlayer();
    CountDownTimer waitTimer;

    GestureDetector gestureDetector;


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createListOfGroups();

        gestureDetector = new GestureDetector(getApplicationContext(), new GestureListener());
    }

    public void createListOfGroups() {
        // The first char of each group is selected with 5 second wait
        // and not by the second gesture
        ArrayList<String> group1 = new ArrayList<String>();
        group1.add("1");
        group1.add(".");
        group1.add("?");
        group1.add(",");

        listOfGroups.add(group1);

        ArrayList<String> group2 = new ArrayList<String>();
        group2.add("2");
        group2.add("a");
        group2.add("b");
        group2.add("c");

        listOfGroups.add(group2);


        ArrayList<String> group3 = new ArrayList<String>();
        group3.add("3");
        group3.add("d");
        group3.add("e");
        group3.add("f");

        listOfGroups.add(group3);

        ArrayList<String> group4 = new ArrayList<String>();
        group4.add("4");
        group4.add("g");
        group4.add("h");
        group4.add("i");

        listOfGroups.add(group4);


        ArrayList<String> group5 = new ArrayList<String>();
        group5.add("5");
        group5.add("j");
        group5.add("k");
        group5.add("l");

        listOfGroups.add(group5);

        ArrayList<String> group6 = new ArrayList<String>();
        group6.add("6");
        group6.add("m");
        group6.add("n");
        group6.add("o");

        listOfGroups.add(group6);


        ArrayList<String> group7 = new ArrayList<String>();
        group7.add("7");
        group7.add("p");
        group7.add("q");
        group7.add("r");
        group7.add("s");

        listOfGroups.add(group7);

        ArrayList<String> group8 = new ArrayList<String>();
        group8.add("8");
        group8.add("t");
        group8.add("u");
        group8.add("v");

        listOfGroups.add(group8);


        ArrayList<String> group9 = new ArrayList<String>();
        group9.add("9");
        group9.add("w");
        group9.add("x");
        group9.add("y");
        group9.add("z");

        listOfGroups.add(group9);

        ArrayList<String> group10 = new ArrayList<String>();
        group10.add("");
        group10.add(":");
        group10.add(";");
        group10.add("!");

        listOfGroups.add(group10);


        ArrayList<String> group11 = new ArrayList<String>();
        group11.add("0");
        group11.add("+");
        group11.add("-");
        group11.add("*");
        group11.add("/");

        listOfGroups.add(group11);

        ArrayList<String> group12 = new ArrayList<String>();
        group12.add("");
        group12.add("Quit");

        listOfGroups.add(group12);
    }

    public double calculateAngle() {
        return Math.toDegrees(Math.atan((endX - startX)/(endY - startY)));
    }

    public String calculateDirection() {
        String direction = "";
        if (endY - startY > 0) direction = "DOWN"; else direction = "UP";
        return direction;
    }

    public void selectGroup(int groupNo) {
        SELECTED_GROUP = groupNo;
        // Create timer
        waitTimer = new CountDownTimer(timeToSelectGroup, 1000) {

            public void onTick(long millisUntilFinished) {
                //called every second, which could be used to
                //send messages or some other action

                mp = MediaPlayer.create(getApplicationContext(), R.raw.tick);
                mp.start();
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayer.release();
                    }
                });

                Log.d(DEBUG_TAG, "validswipingstate = " + Boolean.toString(validSwipingState));
                Log.d(DEBUG_TAG, "GROUP_SELECTION = " + Boolean.toString(GROUP_SELECTION));
                Log.d(DEBUG_TAG, "SELECTED_GROUP = " + Integer.toString(SELECTED_GROUP));
            }

            public void onFinish() {
                //After finish milliseconds, finish current
                //if you would like to execute something when time finishes
                if (!GROUP_SELECTION) {
                    // No alphabet has been selected so far
                    // Choose the number of the selected group
                    selectChar(0);
                    GROUP_SELECTION = !GROUP_SELECTION;
                }
            }
        }.start();

    }

    public int getSoundFile(String letter) {
        switch (letter) {
            case "a": return R.raw.a;
            case "b": return R.raw.b;
            case "c": return R.raw.c;
            case "d": return R.raw.d;
            case "e": return R.raw.e;
            case "f": return R.raw.f;
            case "g": return R.raw.g;
            case "h": return R.raw.h;
            case "i": return R.raw.i;
            case "j": return R.raw.j;
            case "k": return R.raw.k;
            case "l": return R.raw.l;
            case "m": return R.raw.m;
            case "n": return R.raw.n;
            case "o": return R.raw.o;
            case "p": return R.raw.p;
            case "q": return R.raw.q;
            case "r": return R.raw.r;
            case "s": return R.raw.s;
            case "t": return R.raw.t;
            case "u": return R.raw.u;
            case "v": return R.raw.v;
            case "w": return R.raw.w;
            case "x": return R.raw.x;
            case "y": return R.raw.y;
            case "z": return R.raw.z;
            case "0": return R.raw.zero;
            case "1": return R.raw.one;
            case "2": return R.raw.two;
            case "3": return R.raw.three;
            case "4": return R.raw.four;
            case "5": return R.raw.five;
            case "6": return R.raw.six;
            case "7": return R.raw.seven;
            case "8": return R.raw.eight;
            case "9": return R.raw.nine;
        }

        return -1;
    }

    public void playSoundFile(int soundFile) {
        mp = MediaPlayer.create(getApplicationContext(), soundFile);
        validSwipingState = false;
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
                validSwipingState = true;
            }
        });
    }

    public void selectChar(int index) {
        String chars_to_append = listOfGroups.get(SELECTED_GROUP).get(index);
        appendText(chars_to_append);

        int soundFile = getSoundFile(chars_to_append);

        Log.d(DEBUG_TAG, "Sound file " + Integer.toString(soundFile) + " for letter " + chars_to_append);

        if (soundFile != -1) {
            playSoundFile(soundFile);
        }
    }

    public void action_top_left() {
        Log.d(DEBUG_TAG, "Top left");
        if (GROUP_SELECTION) {
            selectGroup(0);
            GROUP_SELECTION = !GROUP_SELECTION;
        }
    }

    public void action_top() {
        Log.d(DEBUG_TAG, "Top");
        if (GROUP_SELECTION) {
            selectGroup(1);
            GROUP_SELECTION = !GROUP_SELECTION;
        } else {
            try {
                selectChar(2);
                waitTimer.cancel();
                GROUP_SELECTION = !GROUP_SELECTION;
            } catch (IndexOutOfBoundsException e) {
                // pass
            }
        }
    }

    public void action_top_right() {
        Log.d(DEBUG_TAG, "Top right");
        if (GROUP_SELECTION) {
            selectGroup(2);
            GROUP_SELECTION = !GROUP_SELECTION;
        }
    }

    public void action_right() {
        Log.d(DEBUG_TAG, "Right");
        if (GROUP_SELECTION) {
            selectGroup(5);
            GROUP_SELECTION = !GROUP_SELECTION;
        } else {
            try {
                selectChar(4);
                waitTimer.cancel();
                GROUP_SELECTION = !GROUP_SELECTION;
            } catch (IndexOutOfBoundsException e) {
                // pass
            }
        }

    }

    public void action_bottom_right() {
        Log.d(DEBUG_TAG, "Bottom Right");
        if (GROUP_SELECTION) {
            selectGroup(8);
            GROUP_SELECTION = !GROUP_SELECTION;
        }
    }

    public void action_bottom() {
        Log.d(DEBUG_TAG, "Bottom");
        if (GROUP_SELECTION) {
            selectGroup(7);
            GROUP_SELECTION = !GROUP_SELECTION;
        } else {
            try {
                selectChar(3);
                waitTimer.cancel();
                GROUP_SELECTION = !GROUP_SELECTION;
            } catch (IndexOutOfBoundsException e) {
                // pass
            }
        }

    }

    public void action_bottom_left() {
        Log.d(DEBUG_TAG, "Bottom Left");
        if (GROUP_SELECTION) {
            selectGroup(6);
            GROUP_SELECTION = !GROUP_SELECTION;
        }
    }

    public void action_left() {
        Log.d(DEBUG_TAG, "Left");
        if (GROUP_SELECTION) {
            selectGroup(3);
            GROUP_SELECTION = !GROUP_SELECTION;
        } else {
            try {
                selectChar(1);
                waitTimer.cancel();
                GROUP_SELECTION = !GROUP_SELECTION;
            } catch (IndexOutOfBoundsException e) {
                // pass
            }
        }
    }

    public void action_longpress() {
        Log.d(DEBUG_TAG, "Long Press");

        if (GROUP_SELECTION) {
            selectGroup(4);
            GROUP_SELECTION = !GROUP_SELECTION;
        }
    }


    public void action_doubletap() {
        Log.d(DEBUG_TAG, "Double Tap");

        TextView main_textView = (TextView) findViewById(R.id.main_textView);
        String cur_text = (String) main_textView.getText();
        if (cur_text.length() > 0) main_textView.setText(cur_text.substring(0, cur_text.length() - 1));
    }


    public void appendText(String chars_to_append) {
        TextView main_textView = (TextView) findViewById(R.id.main_textView);

        String cur_text = (String) main_textView.getText();

        main_textView.setText(cur_text + chars_to_append);
    }

    public void checkMovement() {
        double angle = calculateAngle();
        Log.d(DEBUG_TAG, "angle = " + Double.toString(angle));
        String direction = calculateDirection();
        Log.d(DEBUG_TAG, "direction = " + direction);

        if (angle == Double.NaN) {  // No movement
            return;
        }

        if (direction == "UP" && angle >= 25 && angle <= 65) {
            action_top_left();
        } else if (direction == "UP" && angle >= -20 && angle <= 20) {
            action_top();
        } else if (direction == "UP" && angle >= -65 && angle <= -25) {
            action_top_right();
        } else if (direction == "UP" && angle >= -90 && angle <= -70) {
            action_right();
        } else if (direction == "DOWN" && angle >= 70 && angle <= 90) {
            action_right();
        } else if (direction == "DOWN" && angle >= 25 && angle <= 65) {
            action_bottom_right();
        } else if (direction == "DOWN" && angle >= -20 && angle <= 20) {
            action_bottom();
        } else if (direction == "DOWN" && angle >= -65 && angle <= -25) {
            action_bottom_left();
        } else if (direction == "UP" && angle >= 70 && angle <= 90) {
            action_left();
        } else if (direction == "DOWN" && angle >= -90 && angle <= -70) {
            action_left();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        gestureDetector.onTouchEvent(event);

        return true;

    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            startX = e.getRawX();
            startY = e.getRawY();
            Log.d(DEBUG_TAG,"Action was DOWN");

            Log.d(DEBUG_TAG, "pointer count" + Integer.toString(e.getPointerCount()));

            return true;
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float v1, float v2) {
            endX = e2.getRawX();
            endY = e2.getRawY();

//          if (validSwipingState) checkMovement();
            // There is some bug in using validSwipingState
            // The sound file does not play in some cases
            // Thus it's not possible to turn back validSwipingState to true
            checkMovement();

//            Log.d(DEBUG_TAG, "pointer count" + Integer.toString(e1.getPointerCount()));

            return true;
        }

        // event when double tap occurs
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            float x = e.getX();
            float y = e.getY();

//            if (validSwipingState) action_doubletap();

            action_doubletap();

            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            action_longpress();
        }

        // Read this for two finger tap https://stackoverflow.com/questions/12414680/how-to-implement-a-two-finger-double-click-in-android
    }
}
