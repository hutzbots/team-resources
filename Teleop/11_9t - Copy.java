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
        public static float turnVal = 0.0f;


    }


    @Override public void loop ()

    {
        //begin left stick drive/strafe code
        public static float l_gp1_left_stick_y = gamepad1.left_stick_y;
        public static float l_gpl_left_stick_x = -gamepad1.left_stick_x; //reverse if need be
        public static float l_left_drive_power_forward
                = (float) scale_motor_power(l_gp1_left_stick_y);
        public static float l_left_drive_power_side
                = (float) scale_motor_power(l_gp1_left_stick_x);
        //strafe code indented
            if(l_gpl_left_stick_x >= abs(l_gpl_left_stick_y)) //right strafe
            {
                rightDrive.setPower(l_left_drive_power_forward);
                leftDrive.setPower(l_left_drive_power_forward);
                rightDriveB.setPower(-l_left_drive_power_side);
                leftDriveB.setPower(-l_left_drive_power_side);
            }
            else if(-l_gpl_left_stick_x >= abs(l_gpl_left_stick_y)) //left strafe
            {
                rightDrive.setPower(-l_left_drive_power_forward);
                leftDrive.setPower(-l_left_drive_power_forward);
                rightDriveB.setPower(l_left_drive_power_side);
                leftDriveB.setPower(l_left_drive_power_side);
            }
            else //forwards or backwards
            {
                rightDrive.setPower(l_left_drive_power_forward);
                leftDrive.setPower(l_left_drive_power_forward);
                rightDriveB.setPower(l_left_drive_power_forward);
                leftDriveB.setPower(l_left_drive_power_forward);
            }

        //end drive/strafe code
        //begin turn stick code
        public void turn(int direction, double seconds){
            if(direction==1){
                drive(-1.0,1.0,seconds);
            }
            if(direction==-1){
                drive(1.0,-1.0,seconds);
            }
        }
        public static float l_gpl_right_stick_x = -gamepad1.right_stick_x;  //reverse if need be
        public static float l_gpl_right_stick_y = gamepad1.right_stick_y;
        public static float l_right_drive_power_forward
                = (float) scale_motor_power(l_gp1_right_stick_y);
        public static float l_right_drive_power_side
                = (float) scale_motor_power(l_gp1_right_stick_x);
        //turn code indented; assume robot starts at 0 degrees
        //goal is to have robot turn for x time per concentric degree
            public static float[] pos = [l_right_drive_power_forward, l_right_drive_power_side];
            if(turnVal < /*whatever trig method to find out angle using y and x */ 0 )

            {
                turn(1, 0.1 /*test seconds; fix to time per one degree or 1/2 or whatever*/);
                turnVal += 1.0; //can change to 0.5 for 1/2 degree accuracy or whatever
            }
            if(turnVal == 90.0)
    {
        turnVal =0.0;
    }



    }
    @Override public void stop () {
    }

}