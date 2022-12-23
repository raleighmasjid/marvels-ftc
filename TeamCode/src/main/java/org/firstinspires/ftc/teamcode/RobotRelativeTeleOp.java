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

        greenBot.init(hardwareMap);

        GamepadEx Gamepad1 = new GamepadEx(gamepad1);
        GamepadEx Gamepad2 = new GamepadEx(gamepad2);
        ButtonReader bReader = new ButtonReader(Gamepad2, GamepadKeys.Button.B);

        //Initialize working variables
        double x = 0;
        double y = 0;
        double z = 0;

        waitForStart();


        while (opModeIsActive()) {
            bReader.readValue();
            telemetry.addData("Status", "servo: servoRight:" + greenBot.clawOpen);
            //Get stick inputs
            greenBot.runClaw();
            double leftY = Gamepad1.getLeftY();
            double leftX = Gamepad1.getLeftX();
            double rightX = Gamepad1.getRightX();
            double liftPower = Gamepad2.getLeftY();


            if (bReader.wasJustPressed()) {
                //mytelemetry.addData("Status", "power1: x:" + x + " y:" + y + " z:" + z);
                greenBot.toggleClaw();
            }

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




            greenBot.driveRobotCentric(leftX, leftY, rightX);

            mytelemetry.addData("Status", "power: x:" + x + " y:" + y + " z:" + z);
            mytelemetry.update();

        }
    }
}
