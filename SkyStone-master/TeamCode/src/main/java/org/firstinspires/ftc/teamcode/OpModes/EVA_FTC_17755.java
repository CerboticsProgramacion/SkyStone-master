package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.ElectricBot;
import org.firstinspires.ftc.teamcode.Resources.Constants;
import org.firstinspires.ftc.teamcode.Resources.Controller;
import org.firstinspires.ftc.teamcode.Resources.Names;
import org.firstinspires.ftc.teamcode.Subsystems.Arms;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake;

@TeleOp(name = "EVA", group = "TeleOp")
public class EVA_FTC_17755 extends OpMode {

    private ElectricBot m_electricBot;
    private Controller controller1, controller2;

    private double drive, strafe, turn, brake, accel, maxOutput;

    private double frontLeftDrivePower, frontRightDrivePower, rearLeftDrivePower, rearRightDrivePower;
    private double downPower, upPower;
    private double liftPower;

    private double actualAngle;
    private double newAngle;
    private double resultantPower;
    private double targetAngle;

    private double lastdrive;
    private double laststrafe;
    private double lastnewAngle;
    private double lasttargetAngle;

    private Outtake.OuttakePosition selectedPositioTurnern = Outtake.OuttakePosition.IN;
    private Outtake.OuttakePosition selectedPositionHolder = Outtake.OuttakePosition.FREE;

    private Arms.ArmsPosition selectedFoundationArmsPosition = Arms.ArmsPosition.FOUNDATION_UP;
    private Intake.IntakeAction selectedIntakeSpankyAction = Intake.IntakeAction.STOP;

    private Intake.IntakeAction selectedEndGameServoPosition = Intake.IntakeAction.STOP;

    @Override
    public void init() {
        telemetry.addData("Status:", "Initializing...");
        telemetry.addData("Name1: ", Names.driveMotorNames[3]);
        m_electricBot = new ElectricBot(hardwareMap, telemetry);
        m_electricBot.init();


        controller1 = new Controller(gamepad1);
        controller2 = new Controller(gamepad2);

        telemetry.addData("Status:", "Initialized.");
    }

    @Override
    public void init_loop() {
    }


    @Override
    public void loop() {
        controller1.Update();
        controller2.Update();

        //DRIVE
        drive = gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        turn = gamepad1.right_stick_x;

        brake = gamepad1.left_trigger;
        accel = gamepad1.right_trigger;

        if (false) {
            targetAngle = 0;
            if (drive != 0) targetAngle = Math.atan(-strafe / -drive);
            actualAngle = m_electricBot.m_drive.imu.getHeading();
            newAngle = targetAngle - actualAngle;
            if (newAngle < 0) newAngle = 360 + newAngle;
            if (newAngle > 360) newAngle = 360 - newAngle;
            resultantPower = Math.sqrt(drive * drive + strafe * strafe);
            drive = Math.cos(newAngle) * resultantPower;
            strafe = Math.sin(newAngle) * resultantPower;
            if (targetAngle != 0) lasttargetAngle = targetAngle;
            telemetry.addData("Target: ", lasttargetAngle);
            telemetry.addData("Actual: ", actualAngle);
            if (newAngle != 0) lastnewAngle = newAngle;
            telemetry.addData("New: ", lastnewAngle);
            if (drive != 0) lastdrive = drive;
            telemetry.addData("Drive: ", lastdrive);
            if (strafe != 0) laststrafe = strafe;
            telemetry.addData("Strafe: ", laststrafe);
        }

        maxOutput = Constants.Out + (Constants.maxOot - Constants.Out) * accel - (Constants.Out - Constants.minOut) * brake;

        frontLeftDrivePower = Range.clip(drive - strafe - turn, -1, 1) * maxOutput;
        frontRightDrivePower = Range.clip(drive + strafe + turn, -1, 1) * maxOutput;
        rearLeftDrivePower = Range.clip(drive + strafe - turn, -1, 1) * maxOutput;
        rearRightDrivePower = Range.clip(drive - strafe + turn, -1, 1) * maxOutput;

        double drivePowers[] = {frontLeftDrivePower, frontRightDrivePower, rearLeftDrivePower, rearRightDrivePower};
        m_electricBot.m_drive.setPower(drivePowers);

        //INTAKE
        m_electricBot.m_intake.setIntakeMotors(controller1.LeftBumper == Controller.ButtonState.PRESSED ? Intake.IntakeAction.OUT : controller1.RightBumper == Controller.ButtonState.PRESSED ? Intake.IntakeAction.IN : Intake.IntakeAction.STOP);

        //LIFT
        downPower = gamepad2.left_trigger;
        upPower = gamepad2.right_trigger;

        liftPower = Range.clip(upPower - downPower, -1, 1);

        m_electricBot.m_lift.setLift(liftPower);

        //OUTTAKE
        //m_electricBot.m_outtake.setOuttakeTurner(controller2.XState == Controller.ButtonState.PRESSED ? Outtake.OuttakePosition.IN : controller2.BState == Controller.ButtonState.PRESSED ? Outtake.OuttakePosition.OUT : m_electricBot.m_outtake.getTurnerPosition());
        if (controller2.XState == Controller.ButtonState.PRESSED) {
            selectedPositioTurnern = Outtake.OuttakePosition.IN;
        } else if (controller2.BState == Controller.ButtonState.PRESSED) {
            selectedPositioTurnern = Outtake.OuttakePosition.OUT;
        }
        m_electricBot.m_outtake.setOuttakeTurner(selectedPositioTurnern);

        //m_electricBot.m_outtake.setOuttakeHolder(controller2.AState == Controller.ButtonState.PRESSED ? Outtake.OuttakePosition.HOLDING : controller2.YState == Controller.ButtonState.PRESSED ? Outtake.OuttakePosition.FREE : m_electricBot.m_outtake.getHolderPosition());
        if (controller2.AState == Controller.ButtonState.PRESSED) {
            selectedPositionHolder = Outtake.OuttakePosition.HOLDING;
        } else if (controller2.YState == Controller.ButtonState.PRESSED) {
            selectedPositionHolder = Outtake.OuttakePosition.FREE;
        }
        m_electricBot.m_outtake.setOuttakeHolder(selectedPositionHolder);

        //FOUNDATION ARMS
        //m_electricBot.m_Arms.setFoundationArms(controller1.AState == Controller.ButtonState.PRESSED ? Arms.ArmsPosition.FOUNDATION_DOWN : controller1.YState == Controller.ButtonState.PRESSED ? Arms.ArmsPosition.FOUNDATION_UP : m_electricBot.m_Arms.getFoundationArmsPosition());
        if (controller1.AState == Controller.ButtonState.PRESSED) {
            selectedFoundationArmsPosition = Arms.ArmsPosition.FOUNDATION_DOWN;
        } else if (controller1.YState == Controller.ButtonState.PRESSED) {
            selectedFoundationArmsPosition = Arms.ArmsPosition.FOUNDATION_UP;
        }
        m_electricBot.m_Arms.setFoundationArms(selectedFoundationArmsPosition);

        //SPANK
        //m_electricBot.m_intake.setIntakeSpank(controller2.LeftBumper == Controller.ButtonState.PRESSED ? Intake.IntakeAction.RELEASE : controller2.RightBumper == Controller.ButtonState.PRESSED ? Intake.IntakeAction.HIT : m_electricBot.m_intake.getActualAction(1));
        if (controller2.LeftBumper == Controller.ButtonState.PRESSED) {
            selectedIntakeSpankyAction = Intake.IntakeAction.RELEASE;
        } else if (controller2.RightBumper == Controller.ButtonState.PRESSED) {
            selectedIntakeSpankyAction = Intake.IntakeAction.HIT;
        }
        m_electricBot.m_intake.setIntakeSpank(selectedIntakeSpankyAction);

        //END GAME SERVO
        if (controller1.XState == Controller.ButtonState.PRESSED) {
            selectedEndGameServoPosition = Intake.IntakeAction.EGS_OUT;
        } else if (controller1.BState == Controller.ButtonState.PRESSED) {
            selectedEndGameServoPosition = Intake.IntakeAction.EGS_IN;
        } else {
            selectedEndGameServoPosition = Intake.IntakeAction.STOP;
        }
        m_electricBot.m_intake.setEndGameServo(selectedEndGameServoPosition);

        telemetry.addData("Heading: ", m_electricBot.m_drive.imu.getHeading());
        telemetry.addData("Error: ", m_electricBot.m_drive.imu.getError(0));
        telemetry.addData("Angle: ", m_electricBot.m_drive.imu.getAngle());

        telemetry.addData("XValue", gamepad1.left_stick_x);
        telemetry.addData("YValue", gamepad1.left_stick_y);

        telemetry.addData("Left:", m_electricBot.m_drive.getEncoderPosition(2));
        telemetry.addData("Right:", m_electricBot.m_drive.getEncoderPosition(3));

        telemetry.addData("Average:", m_electricBot.m_drive.getAveragdeEncodersDistance());
    }

    @Override
    public void stop() {
        super.stop();
    }
}
