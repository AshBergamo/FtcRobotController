package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.programas.OMotor;

@TeleOp(name="TeleOp Oficial: Robo1", group="Iterative Opmode")
public class Robo1TeleOp extends OpMode {
    OMotor motor = new OMotor();

    boolean fieldOriented = false;
    boolean yJaPressionado = false;

    @Override
    public void init() {
        motor.init(hardwareMap);
        telemetry.addData("Status", "Inicializado e Pronto para o Drift!");
    }

    @Override
    public void loop() {

        // --- 1. TOGGLES E COMANDOS ESPECIAIS ---

        // Toggle do Field Oriented (Botão Y)
        if (gamepad1.y && !yJaPressionado) {
            fieldOriented = !fieldOriented;
            yJaPressionado = true;
        } else if (!gamepad1.y) {
            yJaPressionado = false;
        }

        // Botão de Pânico: Resetar o Giroscópio (Botão BACK / OPTIONS)
        if (gamepad1.options) {
            motor.resetIMU(); // Requer que você tenha adicionado o resetIMU() no OMotor
        }

        // --- 2. LÓGICA DE MOVIMENTO (A MAIS IMPORTANTE) ---

        if (gamepad1.left_trigger > 0.5 && gamepad1.b) {

            motor.brakeBack(1.0);
        }
        else {

            double forward = -gamepad1.left_stick_y;
            double side    =  gamepad1.left_stick_x;
            double turn    =  gamepad1.right_stick_x;

            if (fieldOriented) {
                motor.fieldOrientedDrive(forward, side, turn);
            } else {
                motor.drive(forward, side, turn);
            }
        }

        // --- 3. TELEMETRIA ---
        telemetry.addData("Modo de Piloto", fieldOriented ? "🔴 FIELD CENTRIC" : "🔵 ROBOT CENTRIC");
        telemetry.addData("Gatilho Esq (Drift)", gamepad1.left_trigger);
        telemetry.addData("Controles", "LX: %.2f | LY: %.2f | RX: %.2f",
                gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);
        telemetry.addLine("Encoders:");
        telemetry.addData(" FR", motor.getEncoder(1));
        telemetry.addData(" FL", motor.getEncoder(2));
        telemetry.addData(" BR", motor.getEncoder(3));
        telemetry.addData(" BL", motor.getEncoder(4));
        telemetry.update();
    }
}