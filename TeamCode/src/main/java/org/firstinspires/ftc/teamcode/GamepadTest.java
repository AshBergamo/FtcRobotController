package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp
public class GamepadTest extends OpMode {
    MotorControl bench = new MotorControl();
    ServoPos servo = new ServoPos();
    @Override
    public void init(){
        bench.init(hardwareMap);
        servo.init(hardwareMap);
    }
    @Override
    public void loop(){
        telemetry.addData("X",gamepad1.left_stick_x);
        telemetry.addData("Y",gamepad1.left_stick_y);
        telemetry.addData("A",gamepad1.a);
        telemetry.addData("B",gamepad1.b);
        if (gamepad1.right_trigger_pressed){
            bench.setMotorSpeed(1.0);
            telemetry.addData("Frente","Esta para frente!");

        }
        else {
            bench.stopMotor();
        }
        if(gamepad1.left_trigger_pressed) {
            bench.setMotorSpeed(-1.00);
            telemetry.addData("Trás","Está para trás");
        }
        else{
            bench.stopMotor();
        }
        if (gamepad1.a){
            servo.setPos(-1.0);
        }
        else if (gamepad1.b){
            servo.setPos(1.0);
        }

    }
}
