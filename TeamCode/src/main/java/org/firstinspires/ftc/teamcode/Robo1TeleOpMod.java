package org.firstinspires.ftc.teamcode;

/**
 * OBS.: Esse código possui mudanças diferentes do anterior,
 *  O funcionamento funciona nomralmente, se quiser mudar, siga as intruções abaixo
 */


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.programas.DriveMotorGeared;
//import org.firstinspires.ftc.teamcode.programas.DriveMotor;
// to revet changes, delete the "Geared" from the import above ^ | and the instructions below
import org.firstinspires.ftc.teamcode.programas.IMUSensor;

@TeleOp
public class Robo1TeleOpMod extends OpMode {
    DriveMotorGeared motors = new DriveMotorGeared();
    // To revert changes, delete the "Geared" from both instances above | and the instructions above / below
    IMUSensor imu = new IMUSensor();
    @Override
    public void init() {
        motors.init(hardwareMap);
        imu.init(hardwareMap);
    }

    @Override
    public void loop() {
        motors.drive(gamepad1.right_stick_x, gamepad1.right_stick_y);

        telemetry.addLine(motors.AddTelemetryMotor());
        telemetry.addData("X", gamepad1.right_stick_x);
        telemetry.addData("Y", gamepad1.right_stick_y);
        telemetry.addData("Angulo Direção", imu.getHeading());
        telemetry.update();

        if (gamepad1.rightBumperWasPressed()) {motors.gearChange(1);} // to revert changes, delete both these lines
        if (gamepad1.leftBumperWasPressed()) {motors.gearChange(-1);} // and instructions above
    }
}

