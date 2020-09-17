package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.hardware.bosch.BNO055IMU;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@TeleOp(name = "TeleOp FTC 17755", group = "Iterative Opmode")
public class FTC_17755 extends OpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    private BNO055IMU gyro = null;
    private Orientation lastAngles = null;
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor atrasIzquierdo = null;
    private DcMotor atrasDerecho = null;

    private Servo autonomo;

    private Servo take1 = null;

    private DcMotor lin = null;

    private DcMotor Intake1 = null;
    private DcMotor Intake2 = null;

    private Servo servo1 = null;
    private Servo servo2 = null;

    private Servo take = null;

    double maxOutput = 0.6;

    private Servo setUp = null;

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        Intake1 = hardwareMap.get(DcMotor.class, "verde1");
        Intake2 = hardwareMap.get(DcMotor.class, "verde2");
        servo1 = hardwareMap.get(Servo.class, "s1");
        servo2 = hardwareMap.get(Servo.class, "s2");
        take = hardwareMap.get(Servo.class, "s3");
        lin = hardwareMap.get(DcMotor.class, "lineal");
        take1 = hardwareMap.get(Servo.class, "s4");
        leftDrive = hardwareMap.get(DcMotor.class, "m0");
        rightDrive = hardwareMap.get(DcMotor.class, "m1");
        atrasIzquierdo = hardwareMap.get(DcMotor.class, "m2");
        atrasDerecho = hardwareMap.get(DcMotor.class, "m3");
        setUp = hardwareMap.get(Servo.class,"s5");
        autonomo = hardwareMap.get(Servo.class,"s6");

        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        atrasIzquierdo.setDirection(DcMotor.Direction.REVERSE);
        atrasDerecho.setDirection(DcMotor.Direction.FORWARD);
        Intake1.setDirection(DcMotor.Direction.REVERSE);
        Intake2.setDirection(DcMotor.Direction.FORWARD);
        servo2.setDirection(Servo.Direction.REVERSE);
        lin.setDirection(DcMotor.Direction.FORWARD);

        lin.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        pseudoinit();

        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {

        double drive = gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;
        double strafe = gamepad1.left_stick_x;


        double frontLeftPower = Range.clip(-drive + strafe + turn, -1, 1);
        double backLeftPower = Range.clip(-drive - strafe + turn, -1, 1);
        double frontRightPower = Range.clip(-drive - strafe - turn, -1, 1);
        double backRightPower = Range.clip(-drive + strafe - turn, -1, 1);

        double linea = gamepad2.left_trigger;
        double linead = gamepad2.right_trigger;

        double linPower = linead - linea;

        double maxOut = 1;
        double Out = 0.6;
        double minOut = 0.4;

        double brake = Out / minOut * gamepad1.left_trigger;
        double accelerator = maxOut / Out * gamepad1.right_trigger;

        maxOutput *= (accelerator - brake);

        double power = 0.6;

        leftDrive.setPower(frontLeftPower * power);
        rightDrive.setPower(frontRightPower * power);
        atrasIzquierdo.setPower(backLeftPower * power);
        atrasDerecho.setPower(backRightPower * power);

        lin.setPower(linPower);
        servo1.setPosition(gamepad1.y ? 0 : gamepad1.a ? 1.0 : servo1.getPosition());
        servo2.setPosition(gamepad1.y ? 0 : gamepad1.a ? 1.0 : servo2.getPosition());
        Intake1.setPower(gamepad1.left_bumper ? 0.7 : gamepad1.right_bumper ? -0.7 : 0.0);
        Intake2.setPower(gamepad1.left_bumper ? 0.7 : gamepad1.right_bumper ? -0.7 : 0.0);
        take.setPosition(gamepad2.y ? 0.0 : gamepad2.a ? 1.0 : take.getPosition());
        take1.setPosition(gamepad2.x ? 0.0 : gamepad2.b ? 1.0 : take1.getPosition());
        setUp.setPosition(gamepad1.dpad_left ? 0 : gamepad1.dpad_right ? 0.6 : setUp.getPosition());

        setUp.setPosition(setUp.getPosition());

        telemetry.addData("Power:", leftDrive.getPower());
        telemetry.addData("Power:", rightDrive.getPower());
        telemetry.addData("Power:", atrasIzquierdo.getPower());
        telemetry.addData("Power:", atrasDerecho.getPower());
    }

    public void pseudoinit() {
        servo1.setPosition(1);
        servo2.setPosition(1);
        take.setPosition(0);
        take1.setPosition(0);
        setUp.setPosition(0);
    }
}
