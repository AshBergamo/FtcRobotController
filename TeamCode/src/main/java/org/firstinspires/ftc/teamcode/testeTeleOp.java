package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.testes.IMUControler;
import org.firstinspires.ftc.teamcode.testes.MotorControl;


@TeleOp
public class testeTeleOp extends OpMode{
    MotorControl M = new MotorControl();
    IMUControler imus = new IMUControler();

    float encoder = 0.0f;

    @Override
    public void init(){
        M.init(hardwareMap);
        imus.init(hardwareMap);
        telemetry.addData("Encoder:", encoder);
        telemetry.addData("IMUs", imus.getHeading());
    }
    @Override
    public void loop(){
        float stick = gamepad1.left_stick_y;
        M.setMotorSpeed(stick);
        encoder = M.readEncoder();
        telemetry.addData("Encoder:", encoder);
        telemetry.addData("IMUs", imus.getHeading());

    }//fim void loop


}//fim testeTeleOp
