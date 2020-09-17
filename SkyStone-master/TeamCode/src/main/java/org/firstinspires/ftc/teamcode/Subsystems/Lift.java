package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Resources.Constants;

public class Lift {
    public enum LiftAction {UP, DOWN, STOP}

    ;
    LiftAction actualAction;
    public DcMotorEx[] liftMotors = new DcMotorEx[1];
    Telemetry tel;

    public Lift(HardwareMap map, Telemetry telemetry, String[] names, boolean reverse) {
        for (int i = 0; i < liftMotors.length; i++) {
            liftMotors[i] = map.get(DcMotorEx.class, names[i]);
        }
        if (!reverse) {
            liftMotors[0].setDirection(DcMotorEx.Direction.REVERSE);
        } else {
            liftMotors[0].setDirection(DcMotorEx.Direction.FORWARD);
        }

        tel = telemetry;

        setMotorBreak(DcMotor.ZeroPowerBehavior.BRAKE);
        setMotorModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public boolean isMotorBusy() {
        boolean isBusy = false;
        for (DcMotor _motor : liftMotors) {
            if (_motor.isBusy()) {
                isBusy = true;
            }
        }

        return isBusy;
    }

    public void setMotorModes(DcMotor.RunMode mode) {
        for (DcMotor _motor : liftMotors) {
            _motor.setMode(mode);
        }
    }

    public void setMotorBreak(DcMotor.ZeroPowerBehavior mode) {
        for (DcMotor _motor : liftMotors) {
            _motor.setZeroPowerBehavior(mode);
        }
    }

    public void setLift(double power) {
        liftMotors[0].setPower(power);
    }

    public void setLift(LiftAction action) {
        switch (action) {
            case UP:
                liftMotors[0].setPower(Constants.LIFT_UP_POWER);
                break;
            case DOWN:
                liftMotors[0].setPower(Constants.LIFT_DOWN_POWER);
                break;
            case STOP:
                liftMotors[0].setPower(0);
                break;
        }
        actualAction = action;
    }

    public void setTargetPosition(int target) {
        liftMotors[0].setTargetPosition(target);
    }

    public LiftAction getActualAction() {
        return actualAction;
    }
}
