package com.example.sensorsurvey;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;
/*
    https://codelabs.developers.google.com/codelabs/advanced-android-training-sensor-data/#2
    demo to use SensorManager to list all sensors found.
 */
public class SensorSurvey extends AppCompatActivity {
    private SensorManager mSensorManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor_survey);
        //
        String newLine= System.getProperty("line.separator");
        mSensorManager =
                (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensorList  =
                mSensorManager.getSensorList(Sensor.TYPE_ALL);
        StringBuilder sensorText = new StringBuilder();
        for (Sensor currentSensor : sensorList ) {
            sensorText.append(currentSensor.getName()).append(newLine);
        }
        TextView sensorTextView = (TextView) findViewById(R.id.sensor_list);
        sensorTextView.setText(sensorText);
    }
}
