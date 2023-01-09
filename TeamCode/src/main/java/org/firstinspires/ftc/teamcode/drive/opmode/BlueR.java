package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.PowerplayScorer;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

/*
 * This is an example of a more complex path to really test the tuning.
 */
@Autonomous(group = "drive")
public class BlueR extends LinearOpMode {
    PowerplayScorer scorer = new PowerplayScorer();

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(new Pose2d(35, -60, Math.toRadians(90)));

        //  Initialize telemetry and dashboard
        MultipleTelemetry mytelemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        FtcDashboard dashboard = FtcDashboard.getInstance();

        //  initializes code:
        scorer.init(hardwareMap);

        waitForStart();

        if (isStopRequested()) return;


        TrajectorySequence traj1 = drive.trajectorySequenceBuilder(new Pose2d(35, -60, Math.toRadians(90)))
                .splineTo(new Vector2d(35, -25), Math.toRadians(90))
                .splineTo(new Vector2d(28, -7), Math.toRadians(120))
                .waitSeconds(0.1)
                .setReversed(true)
                .build()
                ;


        drive.followTrajectorySequence(traj1);
    }
}
