package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Resources.Constants;
import org.firstinspires.ftc.teamcode.Resources.Names;
import org.firstinspires.ftc.teamcode.Subsystems.Arms;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Lift;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake;

public class ElectricBot {

    public Drive m_drive;
    public Intake m_intake;
    public Lift m_lift;
    public Outtake m_outtake;
    public Arms m_Arms;

    public ElectricBot(HardwareMap hardwareMap, Telemetry telemetry) {
        m_drive = new Drive(hardwareMap, telemetry, Names.driveMotorNames, false);
        m_intake = new Intake(hardwareMap, telemetry, Names.intakeMotorNames, true);
        m_lift = new Lift(hardwareMap, telemetry, Names.liftMotorNames, false);
        m_outtake = new Outtake(hardwareMap, telemetry, Names.outtakeServoNames);
        m_Arms = new Arms(hardwareMap, telemetry, Names.foundArmServoNmaes);
    }

    public void init() {
        m_drive.setPower(Constants.INIT_DRIVE_POWERS);
        m_intake.setIntakeMotors(Intake.IntakeAction.STOP);
        m_intake.setIntakeSpank(Intake.IntakeAction.HIT);
        m_lift.setLift(Lift.LiftAction.STOP);
        m_outtake.setOuttakeTurner(Outtake.OuttakePosition.IN);
        m_outtake.setOuttakeHolder(Outtake.OuttakePosition.FREE);
        m_Arms.setFoundationArms(Arms.ArmsPosition.FOUNDATION_UP);
        m_Arms.setSkystoneArms(Arms.ArmsPosition.SKYSTONE_UP);
    }
}
