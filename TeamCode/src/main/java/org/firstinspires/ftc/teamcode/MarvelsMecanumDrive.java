package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.drive.DriveConstants.MAX_RPM;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.RevIMU;
//  import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
//  import com.qualcomm.robotcore.hardware.IMU;

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

//    ftclib robot-centric mecanum drive code:
//    the 'true' at the end enables a squared power input for smoother acceleration
    public void driveRobotCentric (double leftX, double leftY, double rightX){
        mecanumDrivetrain.driveRobotCentric(leftX,leftY,rightX, true);
    }

//    ftclib field-centric mecanum drive code:
//    the 'true' at the end enables a squared power input for smoother acceleration
    public void ftclibDriveFieldCentric (double leftX, double leftY, double rightX){
        double heading = imu.getRotation2d().getDegrees();
        mecanumDrivetrain.driveFieldCentric(leftX, leftY, rightX, heading, true);
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

