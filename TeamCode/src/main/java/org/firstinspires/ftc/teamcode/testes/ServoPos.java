package org.firstinspires.ftc.teamcode.testes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
@Disabled
public class ServoPos {
    private Servo servoPos; //servo de posicao
    //private CRServo servoRot;

    public void init(HardwareMap hwMap){
        servoPos = hwMap.get(Servo.class,"servo");
//        servoRot = hwMap.get(CRServo.class, "servo_rot");
    }
    public void setPos(double angle){
        servoPos.setPosition(angle);
    }

//    public void setServoRotSpeed(double speed, Direction direction){
//        servoRot.setDirection(direction); //Pode mudar a direção com DcMotorSimple.Direction. FORWARD/REVERSE
//        servoRot.setPower(speed);
//    }

}
