package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.drive.DriveConstants.MAX_RPM;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.RevIMU;
//  import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
//  import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.drive.DriveConstants;


public class MarvelsMecanumDrive {
    HardwareMap hw;
    public MotorEx motor_frontLeft;
    public MotorEx motor_frontRight;
    public MotorEx motor_backLeft;
    public MotorEx motor_backRight;
    public IMU imu;


    public MecanumDrive mecanumDrivetrain;

    private static final double TICKS_PER_REV = DriveConstants.TICKS_PER_REV;
    public double rotationOffset = 0.0;
//    ftclib robot-centric mecanum drive code:
//    the 'true' at the end enables a squared power input for smoother acceleration
    public void driveRobotCentric (double leftX, double leftY, double rightX){
        mecanumDrivetrain.driveRobotCentric(leftX,leftY,rightX, true);
    }

//    ftclib field-centric mecanum drive code:
//    the 'true' at the end enables a squared power input for smoother acceleration
    public void ftclibDriveFieldCentric (double leftX, double leftY, double rightX){
        double heading = getOffsetRotation();
        mecanumDrivetrain.driveFieldCentric(leftX, leftY, rightX, heading, true);
    }
    public void resetRotation(){
        rotationOffset = getIMURotation();
    }
    public double getIMURotation(){
        return imu.getRobotOrientation(AxesReference.EXTRINSIC, AxesOrder.XYZ,AngleUnit.DEGREES).thirdAngle;
    }
    public double getOffsetRotation(){
        return getIMURotation() - rotationOffset;
    }


    public void init(HardwareMap hw, boolean isTeleop) {
        //cache the HardwareMap
        this.hw = hw;

        //Assign motors using their hardware map names, each drive-type can have different names if needed
        motor_frontLeft = new MotorEx(hw, "left front", TICKS_PER_REV, MAX_RPM);
        motor_frontRight = new MotorEx(hw, "right front", TICKS_PER_REV, MAX_RPM);
        motor_backLeft = new MotorEx(hw, "left back", TICKS_PER_REV, MAX_RPM);
        motor_backRight = new MotorEx(hw, "right back", TICKS_PER_REV, MAX_RPM);

        if (isTeleop) {
            imu = hw.get(IMU.class, "imu");;
            RevHubOrientationOnRobot orientation = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.LEFT, RevHubOrientationOnRobot.UsbFacingDirection.UP);
            IMU.Parameters parameters = new IMU.Parameters(orientation);
            imu.initialize(parameters);
            resetRotation();
        }


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

