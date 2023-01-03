package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.GreenBot;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import java.net.PortUnreachableException;

/*
 * This is an example of a more complex path to really test the tuning.
 */
@Autonomous(group = "drive")
public class MySplineTest extends LinearOpMode {
    GreenBot greenBot = new GreenBot();

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(new Pose2d(35, -60, Math.toRadians(90)));

        //  Initialize telemetry and dashboard
        MultipleTelemetry mytelemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        FtcDashboard dashboard = FtcDashboard.getInstance();

        //  initializes code:
        greenBot.init(hardwareMap, false);

        waitForStart();

        if (isStopRequested()) return;


        TrajectorySequence traj1 = drive.trajectorySequenceBuilder(new Pose2d(35, -60, Math.toRadians(90)))
                .splineTo(new Vector2d(35, -25), Math.toRadians(90))
                .splineTo(new Vector2d(28, -7), Math.toRadians(120))
                .waitSeconds(0.1)
                .setReversed(true)
                .build();
        TrajectorySequence traj2 = drive.trajectorySequenceBuilder(traj1.end())
                .splineToSplineHeading(new Pose2d(45, -14, Math.toRadians(90)), Math.toRadians(0))
                .splineToSplineHeading(new Pose2d(60, -12, Math.toRadians(0)), Math.toRadians(0))
                .waitSeconds(0.1)
                .splineToSplineHeading(new Pose2d(45, -14, Math.toRadians(90)), Math.toRadians(180))
                .splineToSplineHeading(new Pose2d(28, -7, Math.toRadians(120)), Math.toRadians(-250))
                .waitSeconds(0.1)
                .build();


        drive.followTrajectorySequence(traj1);

    }
}
