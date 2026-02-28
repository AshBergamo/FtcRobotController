package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MotorControl {
    private DcMotor motor;
    public void init(HardwareMap hwMap){
        motor = hwMap.get(DcMotor.class,"motor");
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }
    public void setMotorSpeed(double speed){

        motor.setPower(speed);
    }
    public void stopMotor(){
        motor.setPower(0);
    }

}
