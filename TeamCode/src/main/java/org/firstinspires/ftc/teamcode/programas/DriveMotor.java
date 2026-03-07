package org.firstinspires.ftc.teamcode.programas;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DriveMotor {
    private DcMotor motor1, motor2;
    public void init(HardwareMap hwMap){
        motor1 = hwMap.get(DcMotor.class, "motor1");
        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motor2 = hwMap.get(DcMotor.class, "motor2");
        motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void SetMotor1(double speed, DcMotorSimple.Direction direcao){
        motor1.setDirection(direcao);
        motor1.setPower(speed);
    }
    public void SetMotor2(double speed, DcMotorSimple.Direction direcao){
        motor2.setDirection(direcao);
        motor2.setPower(speed);
    }

    public String AddTelemetryMotor(){
        int motor1Pos = motor1.getCurrentPosition();
        int motor2Pos = motor2.getCurrentPosition();
        return "Motor1: " + motor1Pos + "\n" + "Motor2: " + motor2Pos;
    }
}
