package org.firstinspires.ftc.teamcode;


import static org.firstinspires.ftc.teamcode.drive.DriveConstants.MAX_RPM;

import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Creates a 'GreenBot' class that extends the mecanum drive class
 * @author Arshad Anas
 * @since 2022/12/24
 */

public class GreenBot extends MarvelsMecanumDrive {
    // (i think) informs the control hub that the following motors exist:
    public MotorEx lift_motor1;
    public MotorEx lift_motor2;
//    public MotorEx lift_motor3;
    public SimpleServo clawLeft;
    public SimpleServo clawRight;
    public PIDFController liftControl;

    // the following is the code that runs during initialization
    public void init(HardwareMap hw, boolean isTeleop) {
        super.init(hw, isTeleop);

        clawLeft = new SimpleServo(hw,"claw left",0,180);
        clawRight = new SimpleServo(hw,"claw right",0,180);

        lift_motor1 = new MotorEx(hw, "lift motor 1", LIFT_TICKS, MAX_RPM);
        lift_motor2 = new MotorEx(hw, "lift motor 2", LIFT_TICKS, MAX_RPM);
//        lift_motor3 = new MotorEx(hw, "lift motor 3", LIFT_TICKS, MAX_RPM);

        liftControl = new PIDFController(
                TeleOpConfig.LIFT_P,
                TeleOpConfig.LIFT_I,
                TeleOpConfig.LIFT_D,
                TeleOpConfig.LIFT_F
        );

        lift_motor1.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        lift_motor2.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        lift_motor1.setRunMode(Motor.RunMode.VelocityControl);
        lift_motor2.setRunMode(Motor.RunMode.VelocityControl);
//        lift_motor3.setRunMode(Motor.RunMode.VelocityControl);
//        lift_motor3.setInverted(true);


    }
    //  lift motor encoder resolution (ticks):
    public static final double LIFT_TICKS = 145.1;

    // states that the claw should be closed upon teleop control loop start (to grasp preloaded cone)
    public boolean clawOpen = false;

    // squares input but keeps +/- sign
    public double signSquare (double x) {
        return x * Math.abs(x);
    }

    public enum HEIGHT_VAL {
            ONE, TWO, THREE, FOUR, FIVE, GROUND, LOW, MED, TALL
    }

    public void setLiftPos(HEIGHT_VAL height) {
        switch (height){
            case ONE:
                liftControl.setSetPoint(TeleOpConfig.ONE_HEIGHT);
                break;
            case TWO:
                liftControl.setSetPoint(TeleOpConfig.TWO_HEIGHT);
                break;
            case THREE:
                liftControl.setSetPoint(TeleOpConfig.THREE_HEIGHT);
                break;
            case FOUR:
                liftControl.setSetPoint(TeleOpConfig.FOUR_HEIGHT);
                break;
            case FIVE:
                liftControl.setSetPoint(TeleOpConfig.FIVE_HEIGHT);
                break;
            case GROUND:
                liftControl.setSetPoint(TeleOpConfig.GROUND_HEIGHT);
                break;
            case LOW:
                liftControl.setSetPoint(TeleOpConfig.LOW_HEIGHT);
                break;
            case MED:
                liftControl.setSetPoint(TeleOpConfig.MEDIUM_HEIGHT);
                break;
            case TALL:
                liftControl.setSetPoint(TeleOpConfig.TALL_HEIGHT);
                break;
        }
    }

    public void runLiftPos() {
        if (!liftControl.atSetPoint()) {
            double velocity = liftControl.calculate(lift_motor1.getCurrentPosition());
            runLift(velocity);
        }
    }

    // takes an analog stick input (-1 to 1)
    public void runLift(double velocity) {
        // squares input but keeps +/- sign
        velocity = signSquare(velocity);
        // sets both lift motor power to squared value
        // this allows for smoother acceleration
        lift_motor1.set(velocity);
        lift_motor2.set(velocity);
//        lift_motor3.set(-power);
    }

    // inverts claw state boolean
    public void toggleClaw () {
        clawOpen = !clawOpen;
    }

    // the following code switches the open/closed state of the claw based on the boolean above
    // it is looped infinitely--think of the forever>if() loop in scratch
    public void runClaw() {
        if (clawOpen){
            clawLeft.setPosition(TeleOpConfig.CLAW_LEFT_OPEN);
            clawRight.setPosition(TeleOpConfig.CLAW_RIGHT_OPEN);
        } else {
            clawLeft.setPosition(TeleOpConfig.CLAW_LEFT_CLOSED);
            clawRight.setPosition(TeleOpConfig.CLAW_RIGHT_CLOSED);
        }
    }
}
