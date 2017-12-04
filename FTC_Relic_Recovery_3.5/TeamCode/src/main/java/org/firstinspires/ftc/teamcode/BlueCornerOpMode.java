package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.CustomOpMode.LinearCustomOpMode;
import org.firstinspires.ftc.teamcode.Movement.MovementLib;

/**
 * Created by josephodeh on 12/1/17.
 */

@Autonomous(name = "Blue Corner", group = "Blue and Red OpModes")
public class BlueCornerOpMode extends LinearCustomOpMode {
    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        robot.getColorServo().setPosition(0.31);

        waitForStart();
        runtime.reset();
        ColorSensor sensor = robot.getColorSensor();

        MovementLib.lowerCServo(this);
        sleep(100);
        double result = 0;
        for (int i = 0; i < 50 ; i++) {
            int rColor = sensor.red();
            int bColor = sensor.blue();
            telemetry.addData("Blue", bColor);
            telemetry.addData("Red", rColor);
            telemetry.update();
            result += bColor - rColor;
        }

        if (result/50 > 0) {
            MovementLib.forward(3, .2, this);
            MovementLib.raiseCServo(this);
            MovementLib.forward(-27, .2, this);

        }
        else {
            MovementLib.forward(-3, .2, this);
            MovementLib.raiseCServo(this);
            MovementLib.forward(-21, .2, this);
        }

        try {
            MovementLib.rotate(-90, .75, this);
            MovementLib.forward(18, .75, this);
            MovementLib.rotate(-85, .75, this);
            MovementLib.rotateArm(-10, .4, this);
            MovementLib.openArm(this);
            MovementLib.rotateArm(32, .4, this);
            MovementLib.closeArm(this);
            MovementLib.rotateArm(-215, .4, this);
            MovementLib.openArm(this);
            sleep(500);
            MovementLib.rotateArm(60, .4, this);
            MovementLib.closeArm(this);
            MovementLib.rotateArm(110, .4, this);
            sleep(500);
            MovementLib.forward(15, .2, this);
            MovementLib.forward(-3, .2, this);

        } catch (NullPointerException NPE) {
            telemetry.addData("Error", NPE.getMessage());
            telemetry.update();
        }
    }
}