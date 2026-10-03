package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.mecanismos.AprilTagWebcam;
import org.firstinspires.ftc.teamcode.mecanismos.MecanumDrive;
import org.firstinspires.ftc.teamcode.testes.IMUControler;
import org.firstinspires.ftc.teamcode.testes.MotorControl;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.util.Range;


@TeleOp
public class testeTeleOp extends OpMode{
    MecanumDrive motor = new MecanumDrive();
    IMUControler imus = new IMUControler();

    float encoder = 0.0f;
    double Kp = 0.02;
    double potenciaMax = 0.50;
    double rotate = 0;
    boolean controle = true;
    boolean lastY = false;
    boolean lastX = false;
    boolean lastA = false;
    boolean fieldOriented = false;
    boolean segueTag = false;

    AprilTagWebcam aprilTagWebcam = new AprilTagWebcam();

    @Override
    public void init(){
        telemetry = new MultipleTelemetry(
                telemetry, FtcDashboard.getInstance().getTelemetry()
        );
        motor.init(hardwareMap);
        imus.init(hardwareMap);
        aprilTagWebcam.init(hardwareMap, telemetry);
        telemetry.addData("Encoder:", encoder);
        telemetry.addData("IMUs", imus.getHeading());
    }
    @Override
    public void loop(){
        if (gamepad1.y && !lastY) {
            controle = !controle;
        }

        if (gamepad1.x && !lastX) {
            fieldOriented = !fieldOriented;
        }

        if (gamepad1.a && !lastA) {
            segueTag = !segueTag;
        }

        lastY = gamepad1.y;
        lastX = gamepad1.x;
        lastA = gamepad1.a;

        aprilTagWebcam.update();

        if (fieldOriented && !segueTag && controle){
            motor.driveFieldRelative(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        } else if (!fieldOriented && !segueTag && controle) {
            motor.drive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        } else if (fieldOriented && segueTag && controle) {
            double erro = aprilTagWebcam.erroAngularGraus();
            if (!Double.isNaN(erro) && !Double.isInfinite(erro)){
                double giroAutomatico = 0.0;

                if (Math.abs(erro) > 2.0) {
                    giroAutomatico = Range.clip(
                            -Kp * erro, -potenciaMax, potenciaMax
                    );
                }

                rotate = Range.clip(giroAutomatico + gamepad1.right_stick_x,-1.0, 1.0);
            }else{
                rotate = gamepad1.right_stick_x;
            }

            motor.driveFieldRelative(-gamepad1.left_stick_y, gamepad1.left_stick_x, rotate);
        } else if (!fieldOriented && segueTag && controle) {
            double erro = aprilTagWebcam.erroAngularGraus();
            if (!Double.isNaN(erro) && !Double.isInfinite(erro)){
                double giroAutomatico = 0.0;

                if (Math.abs(erro) > 2.0) {
                    giroAutomatico = Range.clip(
                            -Kp * erro, -potenciaMax, potenciaMax
                    );
                }

                rotate = Range.clip(giroAutomatico + gamepad1.right_stick_x,-1.0, 1.0
                );
            }else{
                rotate = gamepad1.right_stick_x;
            }

            motor.drive(-gamepad1.left_stick_y, gamepad1.left_stick_x, rotate);
        } else if (segueTag && !controle) {
            double erro = aprilTagWebcam.erroAngularGraus();
            if (!Double.isNaN(erro) && !Double.isInfinite(erro)){
                double giroAutomatico = 0.0;

                if (Math.abs(erro) > 2.0) {
                    giroAutomatico = Range.clip(
                            -Kp * erro, -potenciaMax, potenciaMax
                    );
                }

                rotate = giroAutomatico;
            }else{
                rotate = 0;
            }

            motor.drive(0, 0, rotate);
        }else{
            motor.drive(0,0,0);
        }


        telemetry.addData("IMU", imus.getHeading());
        telemetry.addData("Controle", controle);
        telemetry.addData("Field Oriented", fieldOriented);
        telemetry.addData("Seguir tag", segueTag);
        telemetry.update();
    }//fim void loop


}//fim testeTeleOp