package org.firstinspires.ftc.teamcode.Autonomous.IMU;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ElectricBot;


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

@Autonomous(name = "Red Test", group = "Linear Opmode")
public class Test extends LinearOpMode {

    // Declare OpMode members.
    private ElectricBot m_electricBot;
    private ElapsedTime runtime = new ElapsedTime();
    DistanceSensor distance;

    double n = 0;
    boolean flag = true;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        m_electricBot = new ElectricBot(hardwareMap, telemetry);
        distance = hardwareMap.get(DistanceSensor.class, "sensor_color");
        m_electricBot.init();
        m_electricBot.m_drive.setMotorBreak(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        m_electricBot.m_drive.setTimer(runtime);

        while(flag) {
            telemetry.addData("Distance: ", distance.getDistance(DistanceUnit.INCH));
            flag = distance.getDistance(DistanceUnit.INCH) > 4;
            m_electricBot.m_drive.setStrafeHeading(-90, 0.1, flag);
        }
    }
}
