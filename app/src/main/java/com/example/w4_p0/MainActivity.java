package com.example.w4_p0;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.gesture.Gesture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnGestureListener {

    private CameraManager cameraManager;
    private String cameraID;
    private Switch flashlight;
    private EditText textAction;
    GestureDetector gestureDetector;
    public static final int SWIPE_THRESHOLD = 100;
    public static final int SWIPE_VELOCITY_THRESHOLD = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textAction = (EditText) findViewById(R.id.textAction);
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraID = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        textAction.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

//                if(!textAction.getText().toString().equalsIgnoreCase("on")){
//                    if (!textAction.getText().toString().equalsIgnoreCase("off")){
//                        Toast.makeText(getApplicationContext(), "Please enter ON or OFF", Toast.LENGTH_LONG).show();
//                    }
//                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void afterTextChanged(Editable s) {

                if (textAction.getText().toString().equalsIgnoreCase("on")){
                    try {
                        cameraManager.setTorchMode(cameraID, true);
                        flashlight.setChecked(true);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }
                else if(textAction.getText().toString().equalsIgnoreCase("off")){
                    try {
                        cameraManager.setTorchMode(cameraID, false);
                        flashlight.setChecked(false);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        flashlight = (Switch) findViewById(R.id.flishlight);

        flashlight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    try {
                        cameraManager.setTorchMode(cameraID, true);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }
                else if(!isChecked){
                    try {
                        cameraManager.setTorchMode(cameraID, false);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        });



    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onFling(MotionEvent downEvent, MotionEvent moveEvent, float velocityX, float velocityY) {
        boolean result = false;
        float diffY = moveEvent.getY() - downEvent.getY();
        float diffX = moveEvent.getX() - downEvent.getX();
        if (!(Math.abs(diffX) > Math.abs(diffY))){
            if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD){
                if (diffY > 0){
                    onSwipeBottom();
                }
                else{
                    onSwipeTop();
                }
            }
        }

        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void onSwipeTop() {
        try {
            cameraManager.setTorchMode(cameraID, true);
            flashlight.setChecked(true);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void onSwipeBottom() {
        try {
            cameraManager.setTorchMode(cameraID, false);
            flashlight.setChecked(false);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

}