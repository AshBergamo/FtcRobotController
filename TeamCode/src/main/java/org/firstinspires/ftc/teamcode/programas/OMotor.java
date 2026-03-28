package org.firstinspires.ftc.teamcode.programas;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class OMotor {
    private DcMotor motorFL, motorFR, motorBL, motorBR;

    public void init(HardwareMap hwMap){
        motorFL = hwMap.get(DcMotor.class, "motorFL");
        motorFR = hwMap.get(DcMotor.class, "motorFR");
        motorBL = hwMap.get(DcMotor.class, "motorBL");
        motorBR = hwMap.get(DcMotor.class, "motorBR");

        motorFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFL.setDirection(DcMotor.Direction.FORWARD); // Verificar no robo

        motorBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBL.setDirection(DcMotor.Direction.FORWARD); // Verificar no robo

        motorFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFR.setDirection(DcMotor.Direction.FORWARD); // Verificar no robo

        motorBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBR.setDirection(DcMotor.Direction.FORWARD); // Verificar no robo

        motorBL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorFL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorFR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void drive(double forward, double side, double roll){
        double PMFL = forward + side - roll;
        double PMFR = forward - side + roll;
        double PMBL = forward - side - roll;
        double PMBR = forward + side + roll;

        double maior = Math.max(Math.max(Math.abs(PMFL), Math.abs(PMFR)), Math.max(Math.abs(PMBL), Math.abs(PMBR)));

        if(maior > 1){
            PMBL /= maior;
            PMBR /= maior;
            PMFL /= maior;
            PMFR /= maior;
        }

        motorBL.setPower(PMBL);
        motorBR.setPower(PMBR);
        motorFL.setPower(PMFL);
        motorFR.setPower(PMFR);
    }
}
