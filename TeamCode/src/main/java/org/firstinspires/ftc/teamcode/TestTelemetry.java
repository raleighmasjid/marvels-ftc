package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

// This is the main TeleOp, with full bot functionality as well as telemetry
@TeleOp(name="Telemetry Test", group="Apex Robotics 3916")
//@Disabled
public class TestTelemetry extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //Initialize telemetry and dashboard
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        FtcDashboard dashboard = FtcDashboard.getInstance();

        GamepadEx Gamepad1 = new GamepadEx(gamepad1);
        GamepadEx Gamepad2 = new GamepadEx(gamepad2);

        //Initialize working variables
        double x = 0;
        double y = 0;
        double z = 0;

        //Wait for the driver to hit Start
        waitForStart();


        while (opModeIsActive()) {
            telemetry.addData("Status", "power: x:" + x + " y:" + y + " z:" + z);
//            telemetry.addData("Front Left Motor", "pos: "+bot.motor_frontLeft.encoder.getPosition());
//            telemetry.addData("Front Right Motor", "pos: "+bot.motor_frontRight.encoder.getPosition());
//            telemetry.addData("Back Left Motor", "pos: "+bot.motor_backLeft.encoder.getPosition());
//            telemetry.addData("Back Right Motor", "pos: "+bot.motor_backRight.encoder.getPosition());
//            telemetry.addData("Slide Motor", "pos: "+slidePos);
//            telemetry.addData("Forearm Motor", "pos: "+bot.forearmMotor.encoder.getPosition());
//            telemetry.addData("Limit Switch", "val: "+bot.slideLimit.getValue());
//            telemetry.addData("Limit Switch", "isTouched"+bot.slideLimit.isPressed());
            telemetry.update();
            x++; y++; z++;
        }
    }
}

