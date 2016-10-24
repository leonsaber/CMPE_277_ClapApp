package com.example.leonzhang.clapapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by Fuyao Zhang on 10/4/16.
 */
public class ActionListener implements SensorEventListener {

    private static final int SPEED_SHRESHOLD = 30;
    private static final int UPTATE_INTERVAL_TIME = 70;
    private SensorManager sensorManager;
    private Sensor sensor;
    private OnShakeListener onShakeListener;
    private Context mContext;
    private float lastX;
    private float lastY;
    private float lastZ;
    private long lastUpdateTime;

    public ActionListener(Context c) {
        mContext = c;
        start();
    }

    public void start() {
        sensorManager = (SensorManager) mContext
                .getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        }
        if (sensor != null) {
            sensorManager.registerListener(this, sensor,
                    SensorManager.SENSOR_DELAY_GAME);
        }

    }

    public void stop() {
        sensorManager.unregisterListener(this);
    }

    public void setOnShakeListener(OnShakeListener listener) {
        onShakeListener = listener;
    }

    public void onSensorChanged(SensorEvent event) {
        long currentUpdateTime = System.currentTimeMillis();
        long timeInterval = currentUpdateTime - lastUpdateTime;
        if (timeInterval < UPTATE_INTERVAL_TIME)
            return;
        lastUpdateTime = currentUpdateTime;

        float x = event.values[SensorManager.DATA_X];
        float y = event.values[SensorManager.DATA_Y];
        float z = event.values[SensorManager.DATA_Z];

        float deltaX = x - lastX;
        float deltaY = y - lastY;
        float deltaZ = z - lastZ;

        lastX = x;
        lastY = y;
        lastZ = z;
        double speed = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ
                * deltaZ)/ timeInterval * 10000;
        if (speed >= SPEED_SHRESHOLD) {
            onShakeListener.onShake();
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public interface OnShakeListener {
        void onShake();
    }

}