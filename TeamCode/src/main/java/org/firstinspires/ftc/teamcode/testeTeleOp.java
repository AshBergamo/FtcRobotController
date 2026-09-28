package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.mecanismos.AprilTagWebcam;
import org.firstinspires.ftc.teamcode.testes.IMUControler;
import org.firstinspires.ftc.teamcode.testes.MotorControl;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;


@TeleOp
public class testeTeleOp extends OpMode{
    MotorControl M = new MotorControl();
    IMUControler imus = new IMUControler();

    float encoder = 0.0f;
    double Kp = 0.01;
    double potenciaMax = 1;
    double potencia = 0;
    boolean controle = true;
    boolean lastY = false;

    AprilTagWebcam aprilTagWebcam = new AprilTagWebcam();

    @Override
    public void init(){
        telemetry = new MultipleTelemetry(
                telemetry, FtcDashboard.getInstance().getTelemetry()
        );
        M.init(hardwareMap);
        imus.init(hardwareMap);
        aprilTagWebcam.init(hardwareMap, telemetry);
        telemetry.addData("Encoder:", encoder);
        telemetry.addData("IMUs", imus.getHeading());
    }
    @Override
    public void loop(){
        float stick = gamepad1.right_trigger;
        stick = stick - gamepad1.left_trigger;
        aprilTagWebcam.update();

        if (controle){
            M.setMotorSpeed(stick);
        }else{
            if (!Double.isNaN(aprilTagWebcam.erroAngularGraus())){
                potencia = Math.max(-potenciaMax, Math.min(potenciaMax, Kp * aprilTagWebcam.erroAngularGraus()));
                M.setMotorSpeed(potencia);
            }else{
                M.setMotorSpeed(0);
            }
        }
        if (gamepad1.y && !lastY){
            controle = !controle;
            lastY = true;
        }else if(!gamepad1.y){
            lastY = false;
        }
        encoder = M.readEncoder();

        telemetry.addData("Potência", potencia);
        telemetry.addData("Encoder:", encoder);
        telemetry.addData("IMUs", imus.getHeading());
        telemetry.update();

    }//fim void loop


}//fim testeTeleOp
