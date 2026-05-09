package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.programas.OMotor;

@TeleOp(name="TeleOp Oficial: Robo1", group="Iterative Opmode")
public class Robo1TeleOp extends OpMode {
    OMotor motor = new OMotor();

    // Variáveis para o Toggle (Edge Detector)
    boolean fieldOriented = false;
    boolean yJaPressionado = false;

    @Override
    public void init() {
        motor.init(hardwareMap);
        telemetry.addData("Status", "Inicializado");
    }

    @Override
    public void loop() {

        if(gamepad1.left_trigger_pressed && gamepad1.b){
            motor.brakeBack(1);
        }
        else if (gamepad1.y && !yJaPressionado) {
            fieldOriented = !fieldOriented;
            yJaPressionado = true;
        } else if (!gamepad1.y) {
            yJaPressionado = false;
        }


        double forward = -gamepad1.left_stick_y;
        double side    =  gamepad1.left_stick_x;
        double turn    =  gamepad1.right_stick_x;

        if (fieldOriented) {
            motor.fieldOrientedDrive(forward, side, turn);
        } else {
            motor.drive(forward, side, turn);
        }

        // --- TELEMETRIA ---
        telemetry.addData("Modo", fieldOriented ? "FIELD CENTRIC" : "ROBOT CENTRIC");
        telemetry.addData("Controles", "LX: %.2f | LY: %.2f | RX: %.2f", side, forward, turn);
        telemetry.addLine("Encoders:");
        telemetry.addData(" FR", motor.getEncoder(1));
        telemetry.addData(" FL", motor.getEncoder(2));
        telemetry.addData(" BR", motor.getEncoder(3));
        telemetry.addData(" BL", motor.getEncoder(4));
        telemetry.update();
    }
}