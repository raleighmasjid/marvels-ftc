package org.firstinspires.ftc.teamcode;


import static org.firstinspires.ftc.teamcode.drive.DriveConstants.MAX_RPM;

import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

//  creates a 'GreenBot' class that extends the mecanum drive class
public class GreenBot extends MarvelsMecanumDrive {
//    (i think) informs the control hub that the following motors exist:
    public MotorEx lift_motor1;
    public MotorEx lift_motor2;
    public SimpleServo clawLeft;
    public SimpleServo clawRight;
//    lift motor encoder resolution (ticks):
    public static final double LIFT_TICKS = 145.1;

//    states that the claw should be closed upon start (to grasp preloaded cone)
    public boolean clawOpen = false;

//  squares input but keeps +/- sign
    public double signSquare (double x) {
        return x * Math.abs(x);
    }

//    takes an analog stick input (-1 to 1)
    public void runSlide (double power){
//        squares input but keeps +/- sign
        double squaredPower = signSquare(power);
//        sets both lift motor power to squared value
//        this allows for smoother acceleration
        lift_motor1.set(squaredPower);
        lift_motor2.set(squaredPower);
    }

//    the following code inverts the boolean that states if the claw is open or not
public void toggleClaw (){
        clawOpen = !clawOpen;
    }

//    the following code switches the open/closed state of the claw based on the boolean above
    public void runClaw(){
        if (clawOpen){
            clawLeft.setPosition(TeleOpConfig.CLAW_LEFT_OPEN);
            clawRight.setPosition(TeleOpConfig.CLAW_RIGHT_OPEN);
        }else{
            clawLeft.setPosition(TeleOpConfig.CLAW_LEFT_CLOSED);
            clawRight.setPosition(TeleOpConfig.CLAW_RIGHT_CLOSED);
        }
    }


//  the following is the code that runs during initialization
    public void init(HardwareMap hw, boolean isTeleop){
        super.init(hw, isTeleop);

        clawLeft = new SimpleServo(hw,"claw left",0,180);
        clawRight = new SimpleServo(hw,"claw right",0,180);

        lift_motor1 = new MotorEx(hw, "lift motor 1", LIFT_TICKS, MAX_RPM);
        lift_motor2 = new MotorEx(hw, "lift motor 2", LIFT_TICKS, MAX_RPM);

        lift_motor1.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        lift_motor2.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

    }
}
