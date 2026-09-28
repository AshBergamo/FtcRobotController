package org.firstinspires.ftc.teamcode.mecanismos;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

public class MecanumDrive {
    private DcMotor leftFrontMotor, leftBackMotor, rightFrontMotor, rightBackMotor;
    private IMU imu;

    public void init(HardwareMap hwMap){
        leftBackMotor = hwMap.get(DcMotor.class, "left_back_motor");
        leftFrontMotor = hwMap.get(DcMotor.class, "left_front_motor");
        rightBackMotor = hwMap.get(DcMotor.class, "right_back_motor");
        rightFrontMotor = hwMap.get(DcMotor.class, "right_front_motor");

        leftBackMotor.setDirection(DcMotor.Direction.REVERSE);
        leftFrontMotor.setDirection(DcMotor.Direction.REVERSE);

        leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        imu = hwMap.get(IMU.class, "imu");
        RevHubOrientationOnRobot RevOrientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
        );

        imu.initialize(new IMU.Parameters(RevOrientation));
    }

    public void drive(double forward, double strafe, double rotate){
        double frontLeftPower = forward + strafe + rotate;
        double backLeftPower = forward - strafe + rotate;
        double frontRightPower = forward - strafe - rotate;
        double backRightPower = forward + strafe - rotate;

        double maxPower = 1.0;
        double maxSpeed = 1.0;

        maxPower = Math.max(maxPower, Math.abs(frontLeftPower));
        maxPower = Math.max(maxPower, Math.abs(frontRightPower));
        maxPower = Math.max(maxPower, Math.abs(backLeftPower));
        maxPower = Math.max(maxPower, Math.abs(backRightPower));

        leftFrontMotor.setPower(maxSpeed * (frontLeftPower / maxPower));
        leftBackMotor.setPower(maxSpeed * (backLeftPower / maxPower));
        rightFrontMotor.setPower(maxSpeed * (frontRightPower / maxPower));
        rightBackMotor.setPower(maxSpeed * (backRightPower / maxPower));
    }
}
