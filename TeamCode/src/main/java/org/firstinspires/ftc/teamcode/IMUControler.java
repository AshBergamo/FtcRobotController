package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class IMUControler {
    private IMU imu;

    public void init(HardwareMap hwMap){
        imu = hwMap.get(IMU.class, "imu");

        RevHubOrientationOnRobot RevOrientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP, //colocar a direção da logo do controlador no robo (neste caso para cima)
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD //colocar a direção do usb do controlador no robo (nesse caso para frente)
        );

        imu.initialize(new IMU.Parameters(RevOrientation));
    }

    public double getHeading(){
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES); //Yaw = giro
    }
}
