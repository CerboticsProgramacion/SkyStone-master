package org.firstinspires.ftc.teamcode.Resources;

public class Names {
    private static String leftDriveFront = "dm0";
    private static String rightDriveFront = "dm1";
    private static String leftDriveRear = "dm2";
    private static String rightDriveRear = "dm3";
    public static String[] driveMotorNames = {leftDriveFront, rightDriveFront, leftDriveRear, rightDriveRear};

    private static String leftIntake = "im0";
    private static String rightIntake = "im1";
    private static String intakeServo = "is0";
    private static String endGameServo = "eg0";
    public static String[] intakeMotorNames = {leftIntake, rightIntake, intakeServo, endGameServo};

    private static String liftMotor = "lm0";
    public static String[] liftMotorNames = {liftMotor};

    private static String outtakeServoHold = "os0";
    private static String outtakeServoTurn = "os1";
    public static String[] outtakeServoNames = {outtakeServoTurn, outtakeServoHold};

    private static String foundationServoLeft = "fs0";
    private static String foundationServoRight = "fs1";
    private static String skystoneArm = "sa0";
    public static String[] foundArmServoNmaes = {foundationServoLeft, foundationServoRight, skystoneArm};

    public static String colorSensor = "cs";

    public static String imuName = "imu";
}
