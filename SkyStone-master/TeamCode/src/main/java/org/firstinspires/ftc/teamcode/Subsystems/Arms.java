package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Resources.Constants;

public class Arms {
    public enum ArmsPosition {FOUNDATION_UP, FOUNDATION_DOWN, SKYSTONE_UP, SKYSTONE_DOWN}

    ;

    public Servo[] armsServos = new Servo[3];
    ArmsPosition foundArmsActualPosition, skystoneArmActualPosition;
    Telemetry tel;

    public Arms(HardwareMap map, Telemetry telemetry, String[] names) {
        for (int i = 0; i < armsServos.length; i++) {
            armsServos[i] = map.get(Servo.class, names[i]);
        }
    }

    public void setFoundationArms(ArmsPosition position) {
        switch (position) {
            case FOUNDATION_UP:
                for (int i = 0; i < armsServos.length-1; i++) {
                    armsServos[i].setPosition(Constants.FOUND_ARMS_UP_SERVO_POSITIONS[i]);
                }
                break;
            case FOUNDATION_DOWN:
                for (int i = 0; i < armsServos.length-1; i++) {
                    armsServos[i].setPosition(Constants.FOUND_ARMS_DOWN_SERVO_POSITIONS[i]);
                }
                break;
        }
        foundArmsActualPosition = position;
    }

    public ArmsPosition getFoundationArmsPosition() {
        return foundArmsActualPosition;
    }

    public void setSkystoneArms(ArmsPosition position) {
        switch (position) {
            case SKYSTONE_UP:
                armsServos[armsServos.length-1].setPosition(Constants.SKYSTONE_ARMS_UP_SERVO_POSITIONS);
                break;
            case SKYSTONE_DOWN:
                armsServos[armsServos.length-1].setPosition(Constants.SKYSTONE_ARMS_DOWN_SERVO_POSITIONS);
                break;
        }
        skystoneArmActualPosition = position;
    }

    public ArmsPosition getSkystoneArmPosition() {
        return skystoneArmActualPosition;
    }
}