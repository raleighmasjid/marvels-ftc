package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
//import com.arcrobotics.ftclib.hardware.RevIMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

// This is the main TeleOp, with full bot functionality as well as telemetry
@TeleOp(name="TeleOp testing", group="Marvels FTC")
//@Disabled
public class DriveTeleOp extends LinearOpMode {
    private MarvelsMecanumDrive bot = new MarvelsMecanumDrive();
    @Override
    public void runOpMode() throws InterruptedException {

        //Initialize telemetry and dashboard
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        FtcDashboard dashboard = FtcDashboard.getInstance();

        bot.init(hardwareMap);

        //RevIMU imu = new RevIMU(hardwareMap);
        GamepadEx Gamepad1 = new GamepadEx(gamepad1);
        GamepadEx Gamepad2 = new GamepadEx(gamepad2);
        //imu.init();

        //Initialize working variables
        double x = 0;
        double y = 0;
        double z = 0;

        //Wait for the driver to hit Start
        waitForStart();


        while (opModeIsActive()) {
            //Get stick inputs
            double leftY = Gamepad1.getLeftY();
            double leftX = Gamepad1.getLeftX();
            double rightX = Gamepad1.getRightX();

            bot.driveRobotCentric(leftX, leftY, rightX);
            telemetry.addData("Status", "power: x:" + x + " y:" + y + " z:" + z);
            telemetry.update();

        }
    }
}

