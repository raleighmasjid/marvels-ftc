package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

public class MyFirstProgram extends LinearOpMode {
    DcMotor motorLeft;
    DcMotor motorRight;
    ColorSensor color1;
    DistanceSensor distance1;
    BNO055IMU imu;

    @Override
    public void runOpMode() {
        motorLeft = hardwareMap.get(DcMotor.class, "motorLeft");
        motorRight = hardwareMap.get(DcMotor.class, "motorRight");
        color1 = hardwareMap.get(ColorSensor.class, "color1");
        distance1 = hardwareMap.get(DistanceSensor.class, "distance1");
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        // Put initialization blocks here
        waitForStart();

        int currentColor = Color.argb(color1.alpha(), color1.red(), color1.green(), color1.blue());
        telemetry.addData("color", currentColor);
        telemetry.update();
        motorRight.setPower(0.5);
        motorLeft.setPower(-0.5);
        sleep(3000);
        motorRight.setPower(0);
        motorLeft.setPower(0);
        telemetry.addData("color", currentColor);
        telemetry.update();
        sleep(1300);

        // Put run blocks here
        while (opModeIsActive()) {
            currentColor = Color.argb(color1.alpha(), color1.red(), color1.green(), color1.blue());
            telemetry.addData("color", currentColor);
            telemetry.update();
            // Put loop blocks here
        }
    }
}
