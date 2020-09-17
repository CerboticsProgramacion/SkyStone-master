package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Resources.Constants;

public class Outtake {
    public enum OuttakePosition {IN, OUT, HOLDING, FREE}

    ;

    public Servo[] outtakeServos = new Servo[2];
    OuttakePosition turnerActualPosition, holderActualPosition;
    Telemetry tel;

    public Outtake(HardwareMap map, Telemetry telemetry, String[] names) {
        for (int i = 0; i < outtakeServos.length; i++) {
            outtakeServos[i] = map.get(Servo.class, names[i]);
        }
        tel = telemetry;
    }

    public void setOuttakeTurner(OuttakePosition position) {
        switch (position) {
            case IN:
                outtakeServos[0].setPosition(Constants.SKYSTONE_IN);
                break;
            case OUT:
                outtakeServos[0].setPosition(Constants.SKYSTONE_OUT);
                break;
        }
            turnerActualPosition = position;
    }

    public OuttakePosition getTurnerPosition() {
        return turnerActualPosition;
    }

    public void setOuttakeHolder(OuttakePosition position) {
        switch (position) {
            case HOLDING:
                outtakeServos[1].setPosition(Constants.SKYSTONE_HOLDING);
                break;
            case FREE:
                outtakeServos[1].setPosition(Constants.SKYSTONE_FREE);
                break;
        }
        holderActualPosition = position;
    }

    public OuttakePosition getHolderPosition() {
        return holderActualPosition;
    }
}

