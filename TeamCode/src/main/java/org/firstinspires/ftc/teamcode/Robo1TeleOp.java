package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.programas.IMUSensor;
import org.firstinspires.ftc.teamcode.programas.OMotor;

@TeleOp
public class Robo1TeleOp extends OpMode {
    OMotor motor = new OMotor();
    IMUSensor imu = new IMUSensor();
    
    @Override
    public void init() {
        motor.init(hardwareMap);
        imu.init(hardwareMap);
    }

    @Override
    public void loop() {
        motor.drive(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

        telemetry.addData("Heading", imu.getHeading());
    }
}
