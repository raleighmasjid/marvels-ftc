package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.drive.DriveConstants.MAX_RPM;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.drive.DriveConstants;

public class MarvelsMecanumDrive {
    HardwareMap hw;
    public MotorEx motor_frontLeft;
    public MotorEx motor_frontRight;
    public MotorEx motor_backLeft;
    public MotorEx motor_backRight;
//    public MotorEx lift_motor;
//    public SimpleServo claw;

    public MecanumDrive mecanumDrivetrain;

    private boolean clawOpen = true;

    private static final double TICKS_PER_REV = DriveConstants.TICKS_PER_REV;

    public void driveRobotCentric (double x, double y, double z){

        mecanumDrivetrain.driveRobotCentric(x,y,z);

    }
//    public void runSlide (double power){
//        lift_motor.set(power);
//    }


//    public void runClaw(){
//        if (clawOpen){
//            claw.setPosition(TeleOpConfig.CLAW_OPEN);
//        }
//        else{
//            claw.setPosition(TeleOpConfig.CLAW_CLOSED);
//        }
//    }
//
//
//    public void toggleClaw (){
//        clawOpen = !clawOpen;
//    }
    public void init(HardwareMap hw) {
        //cache the HardwareMap
        this.hw = hw;

        //Assign motors using their hardware map names, each drivetype can have different names if needed
        motor_frontLeft = new MotorEx(hw, "left front", TICKS_PER_REV, MAX_RPM);
        motor_frontRight = new MotorEx(hw, "right front", TICKS_PER_REV, MAX_RPM);
        motor_backLeft = new MotorEx(hw, "left back", TICKS_PER_REV, MAX_RPM);
        motor_backRight = new MotorEx(hw, "right back", TICKS_PER_REV, MAX_RPM);

        motor_frontRight.setInverted(true);
        //motor_backRight.setInverted(true);

//        claw = new SimpleServo(hw,"claw",0,180);

//        lift_motor = new MotorEx(hw, "lift motor", TICKS_PER_REV, MAX_RPM);

        motor_frontLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        motor_frontRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        motor_backLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        motor_backRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);



        //Initialize the FTCLib drivebase
        mecanumDrivetrain = new MecanumDrive(motor_frontLeft, motor_frontRight,
                motor_backLeft, motor_backRight);
    }
}


