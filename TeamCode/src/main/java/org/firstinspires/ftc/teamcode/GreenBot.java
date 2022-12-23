package org.firstinspires.ftc.teamcode;


import static org.firstinspires.ftc.teamcode.drive.DriveConstants.MAX_RPM;

import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class GreenBot extends MarvelsMecanumDrive {
    public MotorEx lift_motor1;
    public MotorEx lift_motor2;
    public SimpleServo clawLeft;
    public SimpleServo clawRight;
    public static final double LIFT_TICKS = 145.1;

    public boolean clawOpen = false;

    public void runSlide (double power){
        lift_motor1.set(power);
        lift_motor2.set(power);
    }

    public void runClaw(){
        if (clawOpen){
            clawLeft.setPosition(TeleOpConfig.CLAW_LEFT_OPEN);
            clawRight.setPosition(TeleOpConfig.CLAW_RIGHT_OPEN);
        }else{
            clawLeft.setPosition(TeleOpConfig.CLAW_LEFT_CLOSED);
            clawRight.setPosition(TeleOpConfig.CLAW_RIGHT_CLOSED);
        }
    }

    public void toggleClaw (){
        clawOpen = !clawOpen;
    }



    public void init(HardwareMap hw){
        super.init(hw);

        clawLeft = new SimpleServo(hw,"claw left",0,180);
        clawRight = new SimpleServo(hw,"claw right",0,180);

        lift_motor1 = new MotorEx(hw, "lift motor 1", LIFT_TICKS, MAX_RPM);
        lift_motor2 = new MotorEx(hw, "lift motor 2", LIFT_TICKS, MAX_RPM);

        lift_motor1.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        lift_motor2.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

    }
}
