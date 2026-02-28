package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoPos {
    private Servo servoPos;

    public void init(HardwareMap hwMap){
        servoPos = hwMap.get(Servo.class,"servo");

    }
    public void setPos(double angle){
        servoPos.setPosition(angle);
    }

}
