package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.programas.DriveMotor;
import org.firstinspires.ftc.teamcode.programas.IMUSensor;

@TeleOp
public class Robo1TeleOp extends OpMode {
    DriveMotor motors = new DriveMotor();
    IMUSensor imu = new IMUSensor();
    @Override
    public void init() {
        motors.init(hardwareMap);
        imu.init(hardwareMap);
    }

    @Override
    public void loop() {
        motors.drive(gamepad1.right_stick_x, gamepad1.right_stick_y);

        telemetry.addLine(motors.AddTelemetryMotor());
        telemetry.addData("X", gamepad1.right_stick_x);
        telemetry.addData("Y", gamepad1.right_stick_y);
        telemetry.addData("Angulo Direção", imu.getHeading());
        telemetry.update();
    }
}
