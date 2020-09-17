package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Resources.Names;

public class Drive {
    //public enum DriveModes {ENCODERS, NO_ENCODERS, POSITION};

    // 0 = frontLeft      1 = frontRight
    // 2 = rearLeft       3 = rearRight
    private DcMotorEx[] driveMotors = new DcMotorEx[4];
    public IMU imu;

    private ElapsedTime timer;
    public double error;
    public double gain;

    static final double COUNTS_PER_MOTOR_REV  = 383.6;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION  = 2;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 3.93;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.14159);

    Telemetry tel;

    public Drive(HardwareMap map, Telemetry telemetry, String[] names, boolean reverse) {
        for (int i = 0; i < names.length; i++) {
            driveMotors[i] = map.get(DcMotorEx.class, names[i]);
        }
        if (!reverse) {
            driveMotors[0].setDirection(DcMotorSimple.Direction.FORWARD);
            driveMotors[1].setDirection(DcMotorSimple.Direction.REVERSE);
            driveMotors[2].setDirection(DcMotorSimple.Direction.FORWARD);
            driveMotors[3].setDirection(DcMotorSimple.Direction.REVERSE);
        } else {
            driveMotors[0].setDirection(DcMotorSimple.Direction.REVERSE);
            driveMotors[1].setDirection(DcMotorSimple.Direction.FORWARD);
            driveMotors[2].setDirection(DcMotorSimple.Direction.REVERSE);
            driveMotors[3].setDirection(DcMotorSimple.Direction.FORWARD);
        }
        tel = telemetry;

        setMotorBreak(DcMotor.ZeroPowerBehavior.FLOAT);
        resetEncoders();
        setMotorModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        imu = new IMU(map, Names.imuName);
    }

    public boolean isMotorBusy() {
        boolean isBusy = false;
        for (DcMotor _motor : driveMotors) {
            if (_motor.isBusy()) {
                isBusy = true;
            }
        }

        return isBusy;
    }

    public void setMotorModes(DcMotor.RunMode mode) {
        for (DcMotor _motor : driveMotors) {
            _motor.setMode(mode);
        }
    }

    public void setMotorBreak(DcMotor.ZeroPowerBehavior mode) {
        for (DcMotor _motor : driveMotors) {
            _motor.setZeroPowerBehavior(mode);
        }
    }

    public void setPower(double[] drivePowers) {
        for (int i = 0; i < driveMotors.length; i++) {
            driveMotors[i].setPower(drivePowers[i]);
        }
    }

    public double getEncoderPosition(int motorNumber) {
        return driveMotors[motorNumber].getCurrentPosition();
    }

    public void resetEncoders() {
        setMotorModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public double getAveragdeEncodersDistance() {
        return (driveMotors[2].getCurrentPosition() + driveMotors[3].getCurrentPosition()) / 2;
    }

    public void setTimer(ElapsedTime timerIn) {
        timer = timerIn;
    }

    //vuelta
    public void turnToHeading(double targetHeading, double inPower, double timeout) {
        gain = 0.025;

        double power = inPower;
        double correction;
        double left;
        double right;

        timer.reset();
        setMotorModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMotorModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while (timer.milliseconds() < timeout) {
            error = imu.getError(targetHeading);
            correction = error * gain;
            left = power + correction;
            right = -power - correction;
            double correctedPowers[] = {left, right, left, right};
            for (int i = 0; i < driveMotors.length; i++) {
                driveMotors[i].setPower(correctedPowers[i]);
            }
        }
        for (int i = 0; i < driveMotors.length; i++) {
            driveMotors[i].setPower(0);
        }
    }

    //moverse en tal angulo
    public void setDriveHeading(double targetHeading, double inPower, double timeout) {

        gain = 0.075;

        double power = inPower;

        double correction;
        double left;
        double right;

        timer.reset();
        setMotorModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while (timer.milliseconds() < timeout) {
            error = imu.getError(targetHeading);
            correction = error * gain;
            left = power + correction;
            right = power - correction;
            double correctedPowers[] = {left, right, left, right};
            for (int i = 0; i < driveMotors.length; i++) {
                driveMotors[i].setPower(correctedPowers[i]);
            }
        }
        for (int i = 0; i < driveMotors.length; i++) {
            driveMotors[i].setPower(0);
        }
    }

    public void setDriveEncoders(double targetHeading, double inPower, double inches, boolean directionReversed) {
        resetEncoders();
        gain = 0.05;

        double power = inPower;

        double correction;
        double left;
        double right;

        double targetPosition = inches * COUNTS_PER_INCH;

        setMotorModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        timer.reset();
        while (Math.abs(getAveragdeEncodersDistance()) < targetPosition) {
            error = imu.getError(targetHeading);
            correction = error * gain;
            left = power + correction;
            right = power - correction;
            double correctedPowers[] = {left, right, left, right};
            for (int i = 0; i < driveMotors.length; i++) {
                driveMotors[i].setPower(directionReversed ? correctedPowers[i] : -correctedPowers[i]);
            }
        }
        for (int i = 0; i < driveMotors.length; i++) {
            driveMotors[i].setPower(0);
        }
    }

    //moverse de lado
    public void setStrafeHeading(double targetHeading, double inPower, double timeout) {

        gain = 0.05;

        double power = inPower;

        double correction;
        double left;
        double right;
        //double targetPosition = inches * COUNTS_PER_INCH;

        timer.reset();
        setMotorModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while (timer.milliseconds() < timeout) {
            error = imu.getError(targetHeading);
            correction = error * gain;
            left = power + correction;
            right = power - correction;
            double correctedPowers[] = {left, right, right, left};
            for (int i = 0; i < driveMotors.length; i++) {
                driveMotors[i].setPower(correctedPowers[i]);
            }
        }
        for (int i = 0; i < driveMotors.length; i++) {
            driveMotors[i].setPower(0);
        }
    }

    public void setStrafeHeading(double targetHeading, double inPower, boolean onTarget) {

        gain = 0.05;

        double power = inPower;

        double correction;
        double left;
        double right;
        //double targetPosition = inches * COUNTS_PER_INCH;

        timer.reset();
        setMotorModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while (!onTarget) {
            error = imu.getError(targetHeading);
            correction = error * gain;
            left = power + correction;
            right = power - correction;
            double correctedPowers[] = {left, right, right, left};
            for (int i = 0; i < driveMotors.length; i++) {
                driveMotors[i].setPower(correctedPowers[i]);
            }
        }
        for (int i = 0; i < driveMotors.length; i++) {
            driveMotors[i].setPower(0);
        }
    }

    public void setAngleMovement(double targetAngle, double power, double timeout) {
        double drive = Math.sin(targetAngle) * power;
        double strafe = Math.cos(targetAngle) * power;
        double turn = 0;

        timer.reset();
        setMotorModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while (timer.milliseconds() < timeout) {
            driveMotors[0].setPower(Range.clip(drive - strafe - turn, -1, 1));
            driveMotors[1].setPower(Range.clip(drive + strafe + turn, -1, 1));
            driveMotors[2].setPower(Range.clip(drive + strafe - turn, -1, 1));
            driveMotors[3].setPower(Range.clip(drive - strafe + turn, -1, 1));
        }
        for (int i = 0; i < driveMotors.length; i++) {
            driveMotors[i].setPower(0);
        }
    }
}
