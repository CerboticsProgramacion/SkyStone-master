package org.firstinspires.ftc.teamcode.Autonomous.IMU;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.ElectricBot;
import org.firstinspires.ftc.teamcode.Subsystems.Arms;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 * <p>
 * This particular O}pMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 * <p>
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name = "Red FoundationAuto_Pro_IMU", group = "Linear Opmode")
public class Red_Foundation_Pro_IMU extends LinearOpMode {

    // Declare OpMode members.
    private ElectricBot m_electricBot;
    private ElapsedTime runtime = new ElapsedTime();

    double n = 0;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        m_electricBot = new ElectricBot(hardwareMap, telemetry);
        m_electricBot.init();
        m_electricBot.m_drive.setMotorBreak(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        m_electricBot.m_drive.setTimer(runtime);

        m_electricBot.m_drive.setDriveEncoders(0, 0.4, 27, true);
        sleep(500);
        m_electricBot.m_drive.setStrafeHeading(90, 0.25, 250);
        m_electricBot.m_drive.turnToHeading(0,0,500);
        m_electricBot.m_Arms.setFoundationArms(Arms.ArmsPosition.FOUNDATION_DOWN);
        sleep(1100);
        m_electricBot.m_drive.setDriveHeading(0, -0.5, 1400);
        m_electricBot.m_drive.turnToHeading(-90, 0.1, 1500);
        m_electricBot.m_drive.setDriveHeading(-90, 0.5, 1400);
        m_electricBot.m_Arms.setFoundationArms(Arms.ArmsPosition.FOUNDATION_UP);
        sleep(1100);
        m_electricBot.m_drive.setDriveEncoders(-90, -0.85, 35, true);
        sleep(500);
        m_electricBot.m_drive.setStrafeHeading(180, 0.1, 800);

    }
}
