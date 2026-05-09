package org.firstinspires.ftc.teamcode.programas;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class OMotor {
    private DcMotor motorFL, motorFR, motorBL, motorBR;
    private IMU imu;

    public void init(HardwareMap hwMap) {
        motorFL = hwMap.get(DcMotor.class, "motorFL");
        motorFR = hwMap.get(DcMotor.class, "motorFR");
        motorBL = hwMap.get(DcMotor.class, "motorBL");
        motorBR = hwMap.get(DcMotor.class, "motorBR");
        motorFL.setDirection(DcMotor.Direction.FORWARD);
        motorBL.setDirection(DcMotor.Direction.FORWARD);
        motorFR.setDirection(DcMotor.Direction.REVERSE);
        motorBR.setDirection(DcMotor.Direction.REVERSE);

        DcMotor[] motores = {motorFL, motorFR, motorBL, motorBR};
        for (DcMotor m : motores) {
            m.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            m.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            m.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        // Configuração do IMU - VERIFIQUE A POSIÇÃO FÍSICA NO SEU ROBÔ
        imu = hwMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot revOrientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.FORWARD,
                RevHubOrientationOnRobot.UsbFacingDirection.UP
        );
        imu.initialize(new IMU.Parameters(revOrientation));
        imu.resetYaw();
    }

    public void drive(double forward, double side, double roll) {
        // Multiplicador de 1.1 no side ajuda a compensar a imperfeição do mecanum no strafe
        double PMFL = forward + side + roll;
        double PMFR = forward - side - roll;
        double PMBL = forward - side + roll;
        double PMBR = forward + side - roll;

        // Normalização de potência para não ultrapassar 1.0
        double maior = Math.max(Math.abs(PMFL), Math.max(Math.abs(PMFR),
                Math.max(Math.abs(PMBL), Math.abs(PMBR))));

        if (maior > 1.0) {
            PMFL /= maior; PMFR /= maior;
            PMBL /= maior; PMBR /= maior;
        }

        motorFL.setPower(PMFL);
        motorFR.setPower(PMFR);
        motorBL.setPower(PMBL);
        motorBR.setPower(PMBR);
    }

    public void fieldOrientedDrive(double forward, double side, double roll) {
        // Obtém o ângulo atual do robô em radianos
        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        // Rotação de vetor para alinhar os comandos ao campo (Matriz de Rotação)
        // x' = x cosθ - y sinθ
        // y' = x sinθ + y cosθ
        double rotSide = side * Math.cos(-botHeading) - forward * Math.sin(-botHeading);
        double rotForward = side * Math.sin(-botHeading) + forward * Math.cos(-botHeading);

        drive(rotForward, rotSide, roll);
    }

    public int getEncoder(int nMotor) {
        switch (nMotor) {
            case 1: return motorFR.getCurrentPosition();
            case 2: return motorFL.getCurrentPosition();
            case 3: return motorBR.getCurrentPosition();
            case 4: return motorBL.getCurrentPosition();
            default: return 0;
        }
    }
    public void brakeBack(int intensidade){
        motorBL.setPower(0);
        motorBR.setPower(0);
        motorFL.setPower(intensidade);
        motorFR.setPower(-intensidade);
    }
}