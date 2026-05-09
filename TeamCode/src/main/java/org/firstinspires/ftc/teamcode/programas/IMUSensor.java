package org.firstinspires.ftc.teamcode.programas;

import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class IMUSensor {
    private IMU imu;

    public void init(HardwareMap hwMap) {
        // Mapeamento do hardware
        imu = hwMap.get(IMU.class, "imu");

        // 1. Definir a orientação ANTES de inicializar
        RevHubOrientationOnRobot revOrientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD
        );

        // 2. Inicializar com os parâmetros
        imu.initialize(new IMU.Parameters(revOrientation));

        // 3. Resetar o Yaw APÓS a configuração estar pronta
        imu.resetYaw();
    }

    /**
     * Retorna o ângulo de guinada (Yaw) do robô.
     * @return Ângulo em graus de -180 a 180.
     */
    public double getHeading() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }

    /**
     * Permite que o piloto resete a orientação zero durante o jogo.
     */
    public void resetHeading() {
        imu.resetYaw();
    }
}