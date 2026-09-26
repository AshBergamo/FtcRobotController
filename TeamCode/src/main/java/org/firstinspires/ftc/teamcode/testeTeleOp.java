package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.testes.MotorControl;


@TeleOp
public class testeTeleOp extends OpMode{
    MotorControl M = new MotorControl();

    float encoder = 0.0f;

    @Override
    public void init(){
        M.init(hardwareMap);
        telemetry.addData("Encoder:", encoder);
    }
    @Override
    public void loop(){
        float stick = gamepad1.left_stick_y;
        M.setMotorSpeed(stick);
        encoder = M.readEncoder();
        telemetry.addData("Encoder:", encoder);

    }//fim void loop


}//fim testeTeleOp
