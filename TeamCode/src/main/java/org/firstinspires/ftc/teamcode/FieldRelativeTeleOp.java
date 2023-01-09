package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.gamepad.ButtonReader;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

// This is the main TeleOp, with full bot functionality as well as telemetry
@TeleOp(name="Field Relative 1", group="FTC 21836")
//@Disabled
public class FieldRelativeTeleOp extends LinearOpMode {
    PowerplayScorer scorer = new PowerplayScorer();
    MarvelsMecanumDrive drivetrain = new MarvelsMecanumDrive();

    ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

//      Initialize telemetry and dashboard
        MultipleTelemetry mytelemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        FtcDashboard dashboard = FtcDashboard.getInstance();

//      initializes code:
        scorer.init(hardwareMap);
        drivetrain.init(hardwareMap, true);

//      instantiates both gamepads:
        GamepadEx Gamepad1 = new GamepadEx(gamepad1);
        GamepadEx Gamepad2 = new GamepadEx(gamepad2);
//        instantiate a button reader for gamepad 1's A key
        ButtonReader control1A = new ButtonReader(Gamepad1, GamepadKeys.Button.A);
//        instantiates a button reader for gamepad 2's b key
        ButtonReader control2X = new ButtonReader(Gamepad2, GamepadKeys.Button.X);
        ButtonReader control2Up = new ButtonReader(Gamepad2, GamepadKeys.Button.DPAD_UP);


        waitForStart();

//      teleop control loop
        while (opModeIsActive()) {
            control1A.readValue();
            control2X.readValue();
            control2Up.readValue();
            telemetry.addData("Status", "servo: servoRight:" + scorer.clawOpen);
            //Get stick inputs
//            constantly moves the claw to its position dictated by "clawOpen"
            scorer.runClaw();
//            gamepad 1's left analog stick:
            double leftY = Gamepad1.getLeftY();
            double leftX = Gamepad1.getLeftX();
//            gamepad 1's right analog stick:
            double rightX = Gamepad1.getRightX();
//            gamepad 2's left analog stick:
            double liftPower = Gamepad2.getLeftY();

//          runs when the button reader for the b key detects it has been released
            if (control2X.wasJustPressed()) {
                //mytelemetry.addData("Status", "power1: x:" + x + " y:" + y + " z:" + z);
                scorer.toggleClaw();
            }

            if (control2Up.wasJustPressed()) {
                scorer.setLiftPos(PowerplayScorer.HEIGHT_VAL.TALL);
            }

            if (control1A.isDown()) {
                drivetrain.resetRotation();
            }


//            runs the lift using analog stick input
            scorer.runLift(liftPower);
//            greenBot.runLiftPos();

//          runs field-centric driving using analog stick inputs
            drivetrain.driveFieldCentric(leftX, leftY, rightX);

            mytelemetry.addData("Status", "power: x:" + leftX + " y:" + leftY + " z:" + rightX);
            mytelemetry.addData("angle", drivetrain.rotYaw);
            mytelemetry.update();
        }
    }
}
