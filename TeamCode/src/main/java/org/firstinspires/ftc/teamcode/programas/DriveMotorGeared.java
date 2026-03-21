package org.firstinspires.ftc.teamcode.programas;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Disabled
public class DriveMotorGeared {
    private DcMotor motor1, motor2;
    private float gear;

    public void init(HardwareMap hwMap){
        motor1 = hwMap.get(DcMotor.class, "motor1");
        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor1.setDirection(DcMotor.Direction.FORWARD);
        motor1.setDirection(DcMotor.Direction.FORWARD);

        motor2 = hwMap.get(DcMotor.class, "motor2");
        motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor2.setDirection(DcMotor.Direction.REVERSE);

        motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        gear = 1;
    }
    public void drive(double lado, double frente){

        double PM1 = frente + lado * (gear/5);
        double PM2 = frente - lado * (gear/5);

        double maior = Math.max(Math.abs(PM1), Math.abs(PM2));
        if (maior > 1){
            PM1 /= maior;
            PM2 /= maior;
        }

        motor1.setPower(PM1);
        motor2.setPower(PM2);
    }

    public void gearChange(int change){
        gear += change;
        if (gear < 1) {gear=1;}
        if (gear > 5) {gear=5;}
    }
    public String AddTelemetryMotor(){
        int motor1Pos = motor1.getCurrentPosition();
        int motor2Pos = motor2.getCurrentPosition();
        return "Motor1: " + motor1Pos + "\n" + "Motor2: " + motor2Pos;
    }
}
