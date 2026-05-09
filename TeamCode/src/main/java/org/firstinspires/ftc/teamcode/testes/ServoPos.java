package org.firstinspires.ftc.teamcode.testes;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ServoPos {
    private Servo servo;

    public void init(HardwareMap hwMap) {
        // Tente usar nomes genéricos no código e específicos no Hardware Map
        servo = hwMap.get(Servo.class, "servo");
    }

    /**
     * Define a posição do servo.
     * @param position Valor entre 0.0 e 1.0
     */
    public void setPos(double position) {
        // Garante que o valor esteja no intervalo correto para evitar erros
        if (position < 0) position = 0;
        if (position > 1) position = 1;

        servo.setPosition(position);
    }

    public double getPos() {
        return servo.getPosition();
    }
}