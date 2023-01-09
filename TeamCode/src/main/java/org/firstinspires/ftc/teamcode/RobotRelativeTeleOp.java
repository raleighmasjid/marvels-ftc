package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.gamepad.ButtonReader;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
//import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

// This is the main TeleOp, with full bot functionality as well as telemetry
@TeleOp(name="Robot Relative 1", group="FTC 21836")
//@Disabled
public class RobotRelativeTeleOp extends LinearOpMode {
    private PowerplayScorer scorer = new PowerplayScorer();
    MarvelsMecanumDrive drivetrain = new MarvelsMecanumDrive();


    ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        //Initialize telemetry and dashboard
        MultipleTelemetry mytelemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        FtcDashboard dashboard = FtcDashboard.getInstance();

//        initializes code:
        scorer.init(hardwareMap);
        drivetrain.init(hardwareMap, true);

//      instantiates both gamepads:
        GamepadEx Gamepad1 = new GamepadEx(gamepad1);
        GamepadEx Gamepad2 = new GamepadEx(gamepad2);
//        instantiates a button reader for gamepad 2's b key
        ButtonReader bReader = new ButtonReader(Gamepad2, GamepadKeys.Button.B);


        waitForStart();

//      the code that runs during teleop
        while (opModeIsActive()) {
            bReader.readValue();
            telemetry.addData("Status", "servo: servoRight:" + scorer.clawOpen);
            //Get stick inputs
            scorer.runClaw();
//            gamepad 1's left analog stick:
            double leftY = Gamepad1.getLeftY();
            double leftX = Gamepad1.getLeftX();
//            gamepad 1's right analog stick:
            double rightX = Gamepad1.getRightX();
//            gamepad 2's left analog stick
            double liftPower = Gamepad2.getLeftY();

//          runs when the button reader for the b key detects it has been released
            if (bReader.wasJustPressed()) {
                //mytelemetry.addData("Status", "power1: x:" + x + " y:" + y + " z:" + z);
                scorer.toggleClaw();
            }

//            runs the lift using analog stick input
            scorer.runLift(liftPower);

            // if (Gamepad2.getButton(GamepadKeys.Button.DPAD_DOWN)){
            //     bot.runSlide(-(TeleOpConfig.SLIDE_SPEED));

            // }
            // else if (Gamepad2.getButton(GamepadKeys.Button.DPAD_UP)){
            //     bot.runSlide(TeleOpConfig.SLIDE_SPEED);
            // }
            // else{
            //     bot.runSlide(0);
            // }

//          runs robot-centric driving using analog stick inputs
            drivetrain.driveRobotCentric(leftX, leftY, rightX);

            mytelemetry.addData("Status", "power: x:" + leftX + " y:" + leftY + " z:" + rightX);
            mytelemetry.update();

        }
    }
}
