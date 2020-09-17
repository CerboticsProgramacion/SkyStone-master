package org.firstinspires.ftc.teamcode.Subsystems;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;

public class ColorSensor {

    private NormalizedColorSensor colorSensor;
    float[] hsvValues = new float[3];
    final float values[] = hsvValues;
    public NormalizedRGBA colors;

    public void ColorSensor(HardwareMap map, String name) {
         //colorSensor = map.get(NormalizedColorSensor.class, name);
    }

    public void turnLightOn() {
        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight)colorSensor).enableLight(true);
        }
    }

    public void loop() {
        if (colorSensor instanceof SwitchableLight) {
            SwitchableLight light = (SwitchableLight)colorSensor;
            light.enableLight(!light.isLightOn());
        }
        colors = colorSensor.getNormalizedColors();
        Color.colorToHSV(colors.toColor(), hsvValues);
    }

    public NormalizedColorSensor getColorSensor() {
        return colorSensor;
    }

    public float getHSVValues(int i) {
        return hsvValues[i];
    }

    public NormalizedRGBA getColors() {
        return colors;
    }
}
