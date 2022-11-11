package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.drive.DriveConstants.MAX_RPM;

import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.drive.DriveConstants;

public class PowerPlayRobot extends  MarvelsMecanumDrive{
    public MotorEx lift_motor;
    public SimpleServo claw;

    private boolean clawOpen = true;

    private static final double LIFT_TICKS_PER_REV = DriveConstants.TICKS_PER_REV;

    public void runSlide (double power){
        lift_motor.set(power);
    }


    public void runClaw(){
        if (clawOpen){
            claw.setPosition(TeleOpConfig.CLAW_OPEN);
        }
        else{
            claw.setPosition(TeleOpConfig.CLAW_CLOSED);
        }
    }
    public void toggleClaw (){
        clawOpen = !clawOpen;
    }


    public void init(HardwareMap hw){
        super.init(hw);
        claw = new SimpleServo(hw,"claw",0,180);

        lift_motor = new MotorEx(hw, "lift motor", LIFT_TICKS_PER_REV, MAX_RPM);
    }
}
