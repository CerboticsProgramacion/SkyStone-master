package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Resources.Constants;

public class Intake {

    public enum IntakeAction {IN, OUT, HIT, RELEASE, EGS_IN, EGS_OUT, STOP}

    ;
    IntakeAction[] actualAction = new IntakeAction[2];
    private DcMotorEx[] intakeMotors = new DcMotorEx[2];
    private Servo intakeServo;
    private CRServo endGameServo;
    Telemetry tel;

    public Intake(HardwareMap map, Telemetry telemetry, String[] names, boolean reverse) {
        for (int i = 0; i < names.length - 2; i++) {
            intakeMotors[i] = map.get(DcMotorEx.class, names[i]);
        }
        if (!reverse) {
            intakeMotors[0].setDirection(DcMotorEx.Direction.REVERSE);
            intakeMotors[1].setDirection(DcMotorEx.Direction.FORWARD);
        } else {
            intakeMotors[0].setDirection(DcMotorEx.Direction.FORWARD);
            intakeMotors[1].setDirection(DcMotorEx.Direction.REVERSE);
        }
        intakeServo = map.get(Servo.class, names[names.length - 2]);
        endGameServo = map.get(CRServo.class, names[names.length - 1]);

        tel = telemetry;

        setMotorBreak(DcMotorEx.ZeroPowerBehavior.BRAKE);
        setMotorModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public boolean isMotorBusy() {
        boolean isBusy = false;
        for (DcMotor _motor : intakeMotors) {
            if (_motor.isBusy()) {
                isBusy = true;
            }
        }

        return isBusy;
    }

    public void setMotorModes(DcMotor.RunMode mode) {
        for (DcMotor _motor : intakeMotors) {
            _motor.setMode(mode);
        }
    }

    public void setMotorBreak(DcMotor.ZeroPowerBehavior mode) {
        for (DcMotor _motor : intakeMotors) {
            _motor.setZeroPowerBehavior(mode);
        }
    }

    public void setIntakeMotors(IntakeAction action) {
        switch (action) {
            case IN:
                intakeMotors[0].setPower(Constants.INTAKE_IN_POWER);
                intakeMotors[1].setPower(Constants.INTAKE_IN_POWER);
                break;
            case OUT:
                intakeMotors[0].setPower(-Constants.INTAKE_OUT_POWER);
                intakeMotors[1].setPower(-Constants.INTAKE_OUT_POWER);
                break;
            case STOP:
                intakeMotors[0].setPower(0);
                intakeMotors[1].setPower(0);
                break;
        }
        actualAction[0] = action;
    }

    public void setIntakeSpank(IntakeAction action) {
        switch (action) {
            case HIT:
                intakeServo.setPosition(Constants.INTAKE_SERVO_HIT_POSITION);
                break;
            case RELEASE:
                intakeServo.setPosition(Constants.INTAKE_SERVO_RELEASE_POSITION);
                break;
        }
        actualAction[1] = action;
    }

    public void setEndGameServo(IntakeAction action) {
        switch (action) {
            case EGS_IN:
                endGameServo.setPower(1);
                break;
            case EGS_OUT:
                endGameServo.setPower(-1);
                break;
            case STOP:
                endGameServo.setPower(0);
                break;
        }
    }
}
