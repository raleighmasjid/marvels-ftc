package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.drive.DriveConstants.MAX_RPM;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.drive.DriveConstants;


public class MarvelsMecanumDrive {
    HardwareMap hw;
    public MotorEx motor_frontLeft;
    public MotorEx motor_frontRight;
    public MotorEx motor_backLeft;
    public MotorEx motor_backRight;
    public RevIMU imu;


    public MecanumDrive mecanumDrivetrain;

    private static final double TICKS_PER_REV = DriveConstants.TICKS_PER_REV;

    public void driveRobotCentric (double leftX, double leftY, double rightX){
        mecanumDrivetrain.driveRobotCentric(leftX,leftY,rightX, true);
    }

    public void ftclibDriveFieldCentric (double leftX, double leftY, double rightX){
        mecanumDrivetrain.driveFieldCentric(leftX, leftY, rightX, imu.getRotation2d().getDegrees(), true);
    }

//    the following is gm0 field-relative code
//    we are using the one above from ftclib
    public void ourDriveFieldCentric (double leftX, double leftY, double rightX){
        // Read inverse IMU heading, as the IMU heading is CW positive
        double botHeading = -imu.getRotation2d().getRadians();

        double rotX = leftX * Math.cos(botHeading) - leftY * Math.sin(botHeading);
        double rotY = leftX * Math.sin(botHeading) + leftY * Math.cos(botHeading);

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(leftY) + Math.abs(leftX) + Math.abs(rightX), 1);
        double frontL = (rotY + rotX + rightX) / denominator;
        double backL = (rotY - rotX + rightX) / denominator;
        double frontR = (rotY - rotX - rightX) / denominator;
        double backR = (rotY + rotX - rightX) / denominator;

        motor_frontLeft.set(frontL);
        motor_backLeft.set(backL);
        motor_frontRight.set(frontR);
        motor_backRight.set(backR);
    }




    public void init(HardwareMap hw) {
        //cache the HardwareMap
        this.hw = hw;

        //Assign motors using their hardware map names, each drive-type can have different names if needed
        motor_frontLeft = new MotorEx(hw, "left front", TICKS_PER_REV, MAX_RPM);
        motor_frontRight = new MotorEx(hw, "right front", TICKS_PER_REV, MAX_RPM);
        motor_backLeft = new MotorEx(hw, "left back", TICKS_PER_REV, MAX_RPM);
        motor_backRight = new MotorEx(hw, "right back", TICKS_PER_REV, MAX_RPM);
        imu = new RevIMU(hw);
        imu.init();

        motor_frontLeft.setInverted(true);
        motor_backLeft.setInverted(true);
        motor_frontRight.setInverted(true);
        motor_backRight.setInverted(true);

        motor_frontLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        motor_frontRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        motor_backLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        motor_backRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);




        //Initialize the FTCLib drive-base
        mecanumDrivetrain = new MecanumDrive(motor_frontLeft, motor_frontRight,
                motor_backLeft, motor_backRight);
    }
}

