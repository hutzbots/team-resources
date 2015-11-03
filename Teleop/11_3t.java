package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class TeleOp extends OpMode

{
    DcMotor leftDrive;
    DcMotor rightDrive;
    DcMotor leftDriveB;
    DcMotor rightDriveB;
    Servo climber;
    Servo buttons;

    double climberPosition;
    double buttonsPosition;
    double climberDelta = double buttonsDelta = 0.1;  //Amt for servo to change by. Increase for faster speed but less accuracy.

    public TeleOp () {

    }

    double scale_motor_power (double p_power)
    {
        //
        // Assume no scaling.
        //
        double l_scale = 0.0f;

        //
        // Ensure the values are legal.
        //
        double l_power = Range.clip (p_power, -1, 1);

        double[] l_array =
                { 0.00, 0.05, 0.09, 0.10, 0.12
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
        climber = hardwareMap.servo.get("servo_2");
        buttons = hardwareMap.servo.get("servo_1");

        climberPosition = 0.6; //test value; unknown, please change so it works in future.
        buttonsPosition = 0.6; //" " " " " " " " " " " " "" " " " " " " " " " " " " " " "


    }

    @Override public void loop ()

    {

        float l_gp1_left_stick_y = gamepad1.left_stick_y;
        float l_left_drive_power
                = (float) scale_motor_power(l_gp1_left_stick_y);

        float l_gp1_right_stick_y = -gamepad1.right_stick_y;
        float l_right_drive_power
                = (float) scale_motor_power(l_gp1_right_stick_y);
        if(gamepad2.y){
            climberPosition -= climberDelta; //could be backwards, haven't tested.
        }
        if(gamepad2.dpad_left){
            buttonsPosition -= buttonsDelta;
        }
        if(gamepad2.dpad_right){
            buttonsPosition += buttonsDelta;
        }
        if(gamepad2.a) {
            climberPosition += climberDelta; //See gamepad2.y comment.
        }

        rightDrive.setPower(l_right_drive_power);
        leftDrive.setPower(l_left_drive_power);
        rightDriveB.setPower(l_right_drive_power);
        leftDriveB.setPower(l_left_drive_power);

        climberPosition = Range.clip(wristPosition, 0, 1);
        buttonsPosition = Range.clip(clawPosition, 0, 1);   //makes so servo doesnt go too far right or left

        climber.setPosition(climberPosition);
        buttons.setPosition(buttonsPosition);



    }
    @Override public void stop () {
    }

}