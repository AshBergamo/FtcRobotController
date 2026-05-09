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

        // Configuração do IMU
        imu = hwMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot revOrientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.FORWARD,
                RevHubOrientationOnRobot.UsbFacingDirection.UP
        );
        imu.initialize(new IMU.Parameters(revOrientation));
        imu.resetYaw();
    }

    public void drive(double forward, double side, double roll) {
        // CORREÇÃO: Aplicando de fato o multiplicador de 1.1 no strafe!
        // As rodas mecanum perdem energia ao andar de lado devido aos roletes.
        side = side * 1.1;

        double PMFL = forward + side + roll;
        double PMFR = forward - side - roll;
        double PMBL = forward - side + roll;
        double PMBR = forward + side - roll;

        // Normalização de potência para não ultrapassar 1.0 e manter a proporção
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
        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        double rotSide = side * Math.cos(-botHeading) - forward * Math.sin(-botHeading);
        double rotForward = side * Math.sin(-botHeading) + forward * Math.cos(-botHeading);

        drive(rotForward, rotSide, roll);
    }

    // NOVO MÉTODO: O botão de pânico do piloto!
    public void resetIMU() {
        imu.resetYaw();
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

    public void brakeBack(double intensidade){
        motorBL.setPower(0);
        motorBR.setPower(0);
        motorFL.setPower(intensidade);
        motorFR.setPower(-intensidade);
    }
}