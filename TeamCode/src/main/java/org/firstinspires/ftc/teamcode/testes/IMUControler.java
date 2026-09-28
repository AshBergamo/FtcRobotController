package org.firstinspires.ftc.teamcode.testes;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
@Disabled
public class IMUControler {
    private IMU imu;
    private IMU imuE;

    public void init(HardwareMap hwMap){
        imu = hwMap.get(IMU.class, "imu");
        //imuE = hwMap.get(IMU.class, "imuE");

        RevHubOrientationOnRobot RevOrientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP, //colocar a direção da logo do controlador no robo (neste caso para cima)
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD //colocar a direção do usb do controlador no robo (nesse caso para frente)
        );

        //RevHubOrientationOnRobot RevOrientationE = new RevHubOrientationOnRobot(
                //RevHubOrientationOnRobot.LogoFacingDirection.UP,
                //RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD
        //);

        imu.initialize(new IMU.Parameters(RevOrientation));
        //imuE.initialize(new IMU.Parameters(RevOrientationE));
    }

    public double getHeading(){
        double leituraImu = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        //double leituraImuE = imuE.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        //double[] ImuLeituras = {leituraImu, leituraImuE};
        return leituraImu; //Yaw = giro
        // Já é normalizado, quando passa de -180 vai para +180
    }
}
