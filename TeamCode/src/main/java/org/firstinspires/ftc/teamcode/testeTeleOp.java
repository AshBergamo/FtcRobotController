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

    AprilTagWebcam aprilTagWebcam = new AprilTagWebcam();

    @Override
    public void init(){
        M.init(hardwareMap);
        imus.init(hardwareMap);
        aprilTagWebcam.init(hardwareMap, telemetry);
        telemetry.addData("Encoder:", encoder);
        telemetry.addData("IMUs", imus.getHeading());
        telemetry = new MultipleTelemetry(
                telemetry, FtcDashboard.getInstance().getTelemetry()
        );
    }
    @Override
    public void loop(){
        float stick = gamepad1.right_trigger;
        stick = stick - gamepad1.left_trigger;
        M.setMotorSpeed(stick);
        encoder = M.readEncoder();
        aprilTagWebcam.update();
        telemetry.addData("Encoder:", encoder);
        telemetry.addData("IMUs", imus.getHeading());
        telemetry.update();

    }//fim void loop


}//fim testeTeleOp
