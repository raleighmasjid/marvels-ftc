package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
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
    private final MarvelsMecanumDrive bot = new MarvelsMecanumDrive();
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
        boolean spin = false;
        ElapsedTime timer = new ElapsedTime();
        ElapsedTime toggleTimer = new ElapsedTime();
        //Wait for the driver to hit Start
        waitForStart();


        while (opModeIsActive()) {
            //Get stick inputs
            double leftY = Gamepad1.getLeftY();
            double leftX = Gamepad1.getLeftX();
            double rightX = Gamepad1.getRightX();

            if (Gamepad1.getButton(GamepadKeys.Button.A)){
                spin = true;
                timer.reset();
            }

            if (Gamepad1.getButton(GamepadKeys.Button.DPAD_DOWN)){
                bot.runSlide(-TeleOpConfig.SLIDE_SPEED);
            }
            else if (Gamepad1.getButton(GamepadKeys.Button.DPAD_UP)){
                bot.runSlide(TeleOpConfig.SLIDE_SPEED);
            }
            else{
                bot.runSlide(0);
            }

            if (Gamepad1.getButton(GamepadKeys.Button.B)){
                if (toggleTimer.seconds() > 0.3){
                    bot.toggleClaw();
                }
                toggleTimer.reset();
            }

            bot.runClaw();

            if (!spin){
                bot.driveRobotCentric(leftX, leftY, rightX);
            }else{
                if (timer.seconds() > 2.0){
                    timer.reset();
                    spin = false;
                }
                bot.driveRobotCentric(leftX, leftY, TeleOpConfig.SPIN_SPEED);
            }


            telemetry.addData("Status", "power: x:" + x + " y:" + y + " z:" + z);
            telemetry.update();

        }
    }
}

