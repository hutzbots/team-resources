package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import java.lang.*;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by alexbulanov on 11/1/15.
 */
public class Autonomous extends OpMode

{
    int MOTOR_FORWARD = 1; //Variable for moving forward
    int MOTOR_BACKWARD = 1;//Variable for moving backwards
    DcMotor leftDrive;
    DcMotor rightDrive;
    DcMotor leftDriveB;
    DcMotor rightDriveB;

    @Override public void init() {
        rightDrive = hardwareMap.dcMotor.get("rightDrive");
        leftDrive = hardwareMap.dcMotor.get("leftDrive");
        rightDriveB = hardwareMap.dcMotor.get("rightDriveB");
        leftDriveB = hardwareMap.dcMotor.get("leftDriveB");


        //motor power values is between -1 and 1

        driveRight(1, 5);
        driveLeft(1, 5);
    }
    @Override public void loop ()
    {

    }
    @Override public void stop () {
    }
    public void driveRight(int power, int seconds){ //Method for driving on right side with parameters for power and seconds
        rightDrive.setPower(-power); //Set the right wheel to -power because right side has to be negative
        rightDriveB.setPower(-power);
        try {
            // thread to sleep for 1000 milliseconds
            Thread.sleep(seconds*1000); //Wait for seconds * 1000 to get milliseconds
        } catch (Exception e) {
            System.out.println(e);
        }
        rightDrive.setPower(0); //Set values to 0
        rightDriveB.setPower(0);
    }
    public void driveLeft(int power, int seconds){ //Method for driving on right side with parameters for power and seconds
        leftDrive.setPower(power); //Set the left wheels to +power because left side has to be positive
        leftDriveB.setPower(power);
        try {
            // thread to sleep for 1000 milliseconds
            Thread.sleep(seconds*1000); //Wait for seconds * 1000 to get milliseconds
        } catch (Exception e) {
            System.out.println(e);
        }
        leftDrive.setPower(0); //Set values to 0
        leftDriveB.setPower(0);
    }
}
