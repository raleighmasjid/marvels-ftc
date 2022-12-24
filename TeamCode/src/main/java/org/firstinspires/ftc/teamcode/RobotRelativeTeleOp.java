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
    private GreenBot greenBot = new GreenBot();


    ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        //Initialize telemetry and dashboard
        MultipleTelemetry mytelemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        FtcDashboard dashboard = FtcDashboard.getInstance();

//        initializes code:
        greenBot.init(hardwareMap);

//      instantiates both gamepads:
        GamepadEx Gamepad1 = new GamepadEx(gamepad1);
        GamepadEx Gamepad2 = new GamepadEx(gamepad2);
//        instantiates a button reader for gamepad 2's b key
        ButtonReader bReader = new ButtonReader(Gamepad2, GamepadKeys.Button.B);

        //Initialize working variables
        double x = 0;
        double y = 0;
        double z = 0;

        waitForStart();

//      the code that runs during teleop
        while (opModeIsActive()) {
            bReader.readValue();
            telemetry.addData("Status", "servo: servoRight:" + greenBot.clawOpen);
            //Get stick inputs
            greenBot.runClaw();
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
                greenBot.toggleClaw();
            }

//            runs the lift using analog stick input
            greenBot.runSlide(liftPower);

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
            greenBot.driveRobotCentric(leftX, leftY, rightX);

            mytelemetry.addData("Status", "power: x:" + x + " y:" + y + " z:" + z);
            mytelemetry.update();

        }
    }
}
