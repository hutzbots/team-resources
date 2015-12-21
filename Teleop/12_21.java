package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class TeleOp extends OpMode {

    DcMotor leftDrive;
    DcMotor rightDrive;
    DcMotor leftDriveB;
    DcMotor rightDriveB;
    //Servo buttonServo;
    Servo climberServo;
    DcMotor tapeMeasure;

    double scale_motor_power(double p_power) {
        //
        // Assume no scaling.
        //
        double l_scale; //There is no need to put double l_scale = 0.0f; as you can just leave it as double l_scale;. The = 0.0f is redundant.

        //
        // Ensure the values are legal.
        //
        double l_power = Range.clip(p_power, -0.9, 0.9);

        double[] l_array =
                {0.00, 0.05, 0.09, 0.10, 0.12
                        , 0.15, 0.18, 0.24, 0.30, 0.36
                        , 0.43, 0.50, 0.60, 0.72, 0.85
                        , 0.9, 1.0
                };

        //
        // Get the corresponding index for the specified argument/parameter.
        //
        int l_index = (int) (l_power * 16.0);
        if (l_index < 0) {
            l_index = -l_index;
        } else if (l_index > 16) {
            l_index = 16;
        }

        if (l_power < 0) {
            l_scale = -l_array[l_index];
        } else {
            l_scale = l_array[l_index];
        }

        return l_scale;

    }


    @Override
    public void init() {
        rightDrive = hardwareMap.dcMotor.get("rightDrive");
        leftDrive = hardwareMap.dcMotor.get("leftDrive");
        rightDriveB = hardwareMap.dcMotor.get("rightDriveB");
        leftDriveB = hardwareMap.dcMotor.get("leftDriveB");
        tapeMeasure = hardwareMap.dcMotor.get("tapeMeasure");

        //buttonServo = hardwareMap.servo.get("buttonServo");
        climberServo = hardwareMap.servo.get("climberServo");

        //motor power values is between -1 and 1
        //buttonServo.setPosition(0);
        climberServo.setPosition(0.5);

    }


        @Override public void loop ()

        {

            float l_gp1_left_stick_y = gamepad1.left_stick_y;
            float l_left_drive_power
                    = (float) scale_motor_power(l_gp1_left_stick_y);

            float l_gp1_right_stick_y = -gamepad1.right_stick_y;
            float l_right_drive_power
                    = (float) scale_motor_power(l_gp1_right_stick_y);

            float l_gp2_right_stick_y = -gamepad2.right_stick_y;
            float l_tape_measure_power
                    = (float) scale_motor_power(l_gp2_right_stick_y);

            if (gamepad1.dpad_up) {
                climberServo.setPosition(0.75);
            }
            if (gamepad1.dpad_down) {
                climberServo.setPosition(0.25);
            }
            if (gamepad1.x) {
                climberServo.setPosition(0.5);
            }



            rightDrive.setPower(l_right_drive_power);
            tapeMeasure.setPower(l_tape_measure_power);
            leftDrive.setPower(l_left_drive_power);
            rightDriveB.setPower(l_right_drive_power);
            leftDriveB.setPower(l_left_drive_power);


        }

}