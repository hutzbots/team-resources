package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class TeleOp extends OpMode

{
    DcMotor leftDrive;
    DcMotor rightDrive;
    DcMotor leftDriveB;
    DcMotor rightDriveB;
    Servo buttonServo;
    Servo climberServo;

    double scale_motor_power (double p_power)
    {
        //
        // Assume no scaling.
        //
        double l_scale; //There is no need to put double l_scale = 0.0f; as you can just leave it as double l_scale;. The = 0.0f is redundant.

        //
        // Ensure the values are legal.
        //
        double l_power = Range.clip (p_power, -1, 1);

        double[] l_array =
                {0.00, 0.05, 0.09, 0.10, 0.12
                        , 0.15, 0.18, 0.24, 0.30, 0.36
                        , 0.43, 0.50, 0.60, 0.72, 0.85
                        , 1.00, 1.00
                };

        //
        // Get the corresponding index for the specified argument/parameter.
        //
        int l_index = (int) (l_power * 16.0);
        if (l_index < 0)
        {
            l_index = -l_index;
        }
        else if (l_index > 16)
        {
            l_index = 16;
        }

        if (l_power < 0)
        {
            l_scale = -l_array[l_index];
        }
        else
        {
            l_scale = l_array[l_index];
        }

        return l_scale;

    }


    @Override public void init() {
        rightDrive = hardwareMap.dcMotor.get("rightDrive");
        leftDrive = hardwareMap.dcMotor.get("leftDrive");
        rightDriveB = hardwareMap.dcMotor.get("rightDriveB");
        leftDriveB = hardwareMap.dcMotor.get("leftDriveB");

        buttonServo = hardwareMap.servo.get("buttonServo");
        climberServo = hardwareMap.servo.get("climberServo");

        //motor power values is between -1 and 1
        buttonServo.setPosition(0);
        climberServo.setPosition(0);
    }


    @Override public void loop ()

    {

        float l_gp1_left_stick_y = gamepad1.left_stick_y;
        float l_left_drive_power
                = (float) scale_motor_power(l_gp1_left_stick_y);

        float l_gp1_right_stick_y = -gamepad1.right_stick_y;
        float l_right_drive_power
                = (float) scale_motor_power(l_gp1_right_stick_y);
        if(gamepad1.dpad_up && climberServo.getPosition() <= 0.9){
            climberServo.setPosition(climberServo.getPosition()+0.1);
        }
        if(gamepad1.dpad_down && climberServo.getPosition() >= 0.1){
            climberServo.setPosition(climberServo.getPosition()-0.1);
        }
        rightDrive.setPower(l_right_drive_power);
        leftDrive.setPower(l_left_drive_power);
        rightDriveB.setPower(l_right_drive_power);
        leftDriveB.setPower(l_left_drive_power);



    }
    @Override public void stop () {
    }

}