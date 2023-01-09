package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.gamepad.ButtonReader;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.util.Timing;
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
        drivetrain.init(hardwareMap);

//      instantiates both gamepads:
        GamepadEx Gamepad1 = new GamepadEx(gamepad1);
        GamepadEx Gamepad2 = new GamepadEx(gamepad2);

        ButtonReader control1A = new ButtonReader(Gamepad1, GamepadKeys.Button.A);

        ButtonReader control2A = new ButtonReader(Gamepad2, GamepadKeys.Button.A);
        ButtonReader control2B = new ButtonReader(Gamepad2, GamepadKeys.Button.B);
        ButtonReader control2X = new ButtonReader(Gamepad2, GamepadKeys.Button.X);

        ButtonReader control2Up = new ButtonReader(Gamepad2, GamepadKeys.Button.DPAD_UP);
        ButtonReader control2Left = new ButtonReader(Gamepad2, GamepadKeys.Button.DPAD_LEFT);
        ButtonReader control2Right = new ButtonReader(Gamepad2, GamepadKeys.Button.DPAD_RIGHT);
        ButtonReader control2Down = new ButtonReader(Gamepad2, GamepadKeys.Button.DPAD_DOWN);

        scorer.setLiftPos(PowerplayScorer.HEIGHT_VAL.ONE);

        waitForStart();

//      teleop control loop
        while (opModeIsActive()) {
            // get button inputs
            control1A.readValue();

            control2B.readValue();
            control2A.readValue();
            control2X.readValue();

            control2Up.readValue();
            control2Left.readValue();
            control2Right.readValue();
            control2Down.readValue();

//            constantly moves the claw to its position dictated by "clawOpen"
            scorer.runClaw();
            telemetry.addData("Status", "servo: servoRight:" + scorer.clawOpen);

            //runs the lift using analog stick input
//            scorer.runLift(control2LeftY);

            scorer.runLiftPos();
//            gamepad 1's left analog stick:

            //Get stick inputs
            double control1LeftY = Gamepad1.getLeftY();
            double control1LeftX = Gamepad1.getLeftX();
//            gamepad 1's right analog stick:
            double control1RightX = Gamepad1.getRightX();
//            gamepad 2's left analog stick:
            double control2LeftY = Gamepad2.getLeftY();

//          runs when the button reader for the b key detects it has been released
            if (control2X.wasJustPressed()) {
                scorer.toggleClaw();
            }

            if (control2B.wasJustPressed()) {
                scorer.clawOpen = false;
                scorer.setLiftPos(PowerplayScorer.HEIGHT_VAL.GROUND);
            }

            if (control2A.wasJustPressed()) {
                scorer.setLiftPos(PowerplayScorer.HEIGHT_VAL.ONE);
                scorer.clawOpen = true;
            }

            if (control2Up.wasJustPressed()) {
                scorer.setLiftPos(PowerplayScorer.HEIGHT_VAL.TALL);
            }

            if (control2Left.wasJustPressed()) {
                scorer.setLiftPos(PowerplayScorer.HEIGHT_VAL.MED);
            }

            if (control2Right.wasJustPressed()) {
                scorer.setLiftPos(PowerplayScorer.HEIGHT_VAL.LOW);
            }

            if (control2Down.wasJustPressed()) {
                scorer.setLiftPos(PowerplayScorer.HEIGHT_VAL.GROUND);
            }


            if (control1A.isDown()) {
                drivetrain.resetRotation();
            }

            // runs field-centric driving using analog stick inputs
            drivetrain.driveFieldCentric(control1LeftX, control1LeftY, control1RightX);

            mytelemetry.addData("Status", "power: x:" + control1LeftX + " y:" + control1LeftY + " z:" + control1RightX);
            mytelemetry.addData("angle", drivetrain.rotYaw);
            mytelemetry.update();
        }
    }
}
