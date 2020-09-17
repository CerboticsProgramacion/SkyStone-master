package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular O}pMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="titi", group="Linear Opmode")
public class time extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor atrasIzquierdo = null;
    private DcMotor atrasDerecho = null;
    private Servo servo1 = null;
    private Servo servo2 = null;

    double n = 0;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftDrive = hardwareMap.get(DcMotor.class, "m0");
        rightDrive = hardwareMap.get(DcMotor.class, "m1");
        atrasIzquierdo = hardwareMap.get(DcMotor.class, "m2");
        atrasDerecho = hardwareMap.get(DcMotor.class, "m3");
        servo1 = hardwareMap.get(Servo.class, "s1");
        servo2 = hardwareMap.get(Servo.class, "s2");

        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        atrasIzquierdo.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        atrasDerecho.setDirection(DcMotor.Direction.FORWARD);

        waitForStart();
        runtime.reset();

//MOVERSE AL FRENTE
        n += 1000;
        while (runtime.milliseconds() < n) {
            front(1);
        }
        absZero();

        n += 1000;
        while (runtime.milliseconds() < n){
            left(1);
        }

        n += 3000;
        while (runtime.milliseconds() < n){
            front(.2);
        }

    }

    public void absZero() {
        leftDrive.setPower(0);
        atrasIzquierdo.setPower(0);
        rightDrive.setPower(0);
        atrasDerecho.setPower(0);
    }

    public void back(double velocity) {
        leftDrive.setPower(velocity);
        atrasIzquierdo.setPower(velocity);
        rightDrive.setPower(velocity);
        atrasDerecho.setPower(velocity);
    }

    public void front(double velocity) {
        leftDrive.setPower(-velocity);
        atrasIzquierdo.setPower(-velocity);
        rightDrive.setPower(-velocity);
        atrasDerecho.setPower(-velocity);
    }

    public void left(double velocity) {
        leftDrive.setPower(-velocity);
        atrasIzquierdo.setPower(velocity);
        rightDrive.setPower(velocity);
        atrasDerecho.setPower(-velocity);
    }

    public void right(double velocity) {
        leftDrive.setPower(velocity);
        atrasIzquierdo.setPower(-velocity);
        rightDrive.setPower(-velocity);
        atrasDerecho.setPower(velocity);
    }

    public void rightTurn(double velocity) {
        leftDrive.setPower(velocity);
        atrasIzquierdo.setPower(velocity);
        rightDrive.setPower(-velocity);
        atrasDerecho.setPower(-velocity);
    }

    public void leftTurn(double velocity) {
        leftDrive.setPower(-velocity);
        atrasIzquierdo.setPower(-velocity);
        rightDrive.setPower(velocity);
        atrasDerecho.setPower(velocity);
    }

    public void setServos(int positionL, int positionR) {
        servo1.setPosition(positionL);
        servo2.setPosition(positionR);
    }
}