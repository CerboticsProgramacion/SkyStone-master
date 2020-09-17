package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

public abstract class NavigationHardware {
    public NavigationHardware(HardwareMap map, String name) {

    }

    public abstract double getHeading();

    public abstract double getError(double targetAngle);

    public  abstract void resetAngle();

    public abstract double getAngle();
}