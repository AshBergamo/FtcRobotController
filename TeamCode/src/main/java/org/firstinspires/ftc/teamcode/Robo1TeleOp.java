package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


import org.firstinspires.ftc.teamcode.programas.DriveMotor;

@TeleOp
public class Robo1TeleOp extends OpMode {
    DriveMotor motors = new DriveMotor();
    @Override
    public void init() {
        motors.init(hardwareMap);
    }

    @Override
    public void loop() {
        if (gamepad1.right_trigger_pressed){
            motors.SetMotor1(1.0, DcMotor.Direction.FORWARD);
            motors.SetMotor2(1.0, DcMotor.Direction.REVERSE);
        }else if (gamepad1.left_trigger_pressed) {
            motors.SetMotor1(-1.0, DcMotor.Direction.FORWARD);
            motors.SetMotor2(-1.0, DcMotor.Direction.REVERSE);
        }else if(gamepad1.right_stick_x > 0.005){
            motors.SetMotor1(1.0, DcMotorSimple.Direction.FORWARD);
            motors.SetMotor2(1.0, DcMotorSimple.Direction.REVERSE);
        }else if(gamepad1.right_stick_x < -0.005){
            motors.SetMotor1(-1.0, DcMotorSimple.Direction.FORWARD);
            motors.SetMotor2(-1.0, DcMotorSimple.Direction.REVERSE);
        }else{
            motors.SetMotor1(0.0, DcMotor.Direction.FORWARD);
            motors.SetMotor2(0.0, DcMotor.Direction.REVERSE);
        }

        telemetry.addLine(motors.AddTelemetryMotor());
        telemetry.update();
    }
}
