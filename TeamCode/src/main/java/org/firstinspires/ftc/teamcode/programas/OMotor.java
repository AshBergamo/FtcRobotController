package org.firstinspires.ftc.teamcode.programas;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class OMotor {
    private DcMotor motorFL, motorFR, motorBL, motorBR;
    private IMU imu;

    public void init(HardwareMap hwMap){
        motorFL = hwMap.get(DcMotor.class, "motorFL");
        motorFR = hwMap.get(DcMotor.class, "motorFR");
        motorBL = hwMap.get(DcMotor.class, "motorBL");
        motorBR = hwMap.get(DcMotor.class, "motorBR");

        motorFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFL.setDirection(DcMotor.Direction.FORWARD); // Verificar no robo

        motorBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBL.setDirection(DcMotor.Direction.FORWARD); // Verificar no robo

        motorFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFR.setDirection(DcMotor.Direction.FORWARD); // Verificar no robo

        motorBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBR.setDirection(DcMotor.Direction.FORWARD); // Verificar no robo

        motorBL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorFL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorFR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        imu = hwMap.get(IMU.class, "imu");

        imu.resetYaw();

        RevHubOrientationOnRobot RevOrientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD
        );

        imu.initialize(new IMU.Parameters(RevOrientation));
    }

    public void drive(double forward, double side, double roll){
        double PMFL = forward + side - roll;
        double PMFR = forward - side + roll;
        double PMBL = forward - side - roll;
        double PMBR = forward + side + roll;

        double maior = Math.max(Math.max(Math.abs(PMFL), Math.abs(PMFR)), Math.max(Math.abs(PMBL), Math.abs(PMBR)));

        if(maior > 1){
            PMBL /= maior;
            PMBR /= maior;
            PMFL /= maior;
            PMFR /= maior;
        }

        motorBL.setPower(PMBL);
        motorBR.setPower(PMBR);
        motorFL.setPower(PMFL);
        motorFR.setPower(PMFR);
    }

    public void fieldOrientedDrive(double forward, double side, double roll){
        double theta = Math.atan2(forward, side);
        double r = Math.hypot(side, forward);

        theta = AngleUnit.normalizeRadians(theta - imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));

        double newForward = r * Math.sin(theta);
        double newSide = r * Math.cos(theta);

        this.drive(newForward, newSide, roll);
    }
}
