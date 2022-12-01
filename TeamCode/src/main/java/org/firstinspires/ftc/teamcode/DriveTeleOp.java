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
@TeleOp(name="TeleOp testing", group="Marvels FTC")
//@Disabled
public class DriveTeleOp extends LinearOpMode {
    private MarvelsMecanumDrive bot = new MarvelsMecanumDrive();
    @Override
    public void runOpMode() throws InterruptedException {

        //Initialize telemetry and dashboard
        MultipleTelemetry mytelemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        FtcDashboard dashboard = FtcDashboard.getInstance();

        bot.init(hardwareMap);

        //RevIMU imu = new RevIMU(hardwareMap);
        GamepadEx Gamepad1 = new GamepadEx(gamepad1);
        GamepadEx Gamepad2 = new GamepadEx(gamepad2);

        ButtonReader reader = new ButtonReader(
                Gamepad1, GamepadKeys.Button.B
        );
        //imu.init();

        //Initialize working variables
        double x = 0;
        double y = 0;
        double z = 0;
        boolean spin = false;

        //Wait for the driver to hit Start
        waitForStart();


        while (opModeIsActive()) {
            telemetry.addData("Status", "servo: servoRight:" + bot.clawOpen);
            //Get stick inputs
            double leftY = Gamepad1.getLeftY();
            double leftX = Gamepad1.getLeftX();
            double rightX = Gamepad1.getRightX();
            reader.readValue();


//
//            if (Gamepad1.getButton(GamepadKeys.Button.DPAD_DOWN)){
//                bot.runSlide(-TeleOpConfig.SLIDE_SPEED);
//            }
//            else if (Gamepad1.getButton(GamepadKeys.Button.DPAD_UP)){
//                bot.runSlide(TeleOpConfig.SLIDE_SPEED);
//            }
//            else{
//                bot.runSlide(0);
//            }
//
            if (reader.wasJustPressed()){
                bot.toggleClaw();
            }
//


            bot.driveRobotCentric(leftX, leftY, rightX);


            bot.runClaw();
            mytelemetry.addData("Status", "power: x:" + x + " y:" + y + " z:" + z);
            mytelemetry.update();

        }
    }
}
