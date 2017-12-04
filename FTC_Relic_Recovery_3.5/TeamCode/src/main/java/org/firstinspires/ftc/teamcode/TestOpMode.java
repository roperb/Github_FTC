package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Griffins on 9/11/2017.
 */
@TeleOp(name = "TestOpMode", group = "TeleOp")
//@Disabled
public class TestOpMode extends OpMode{

    public DcMotor left;
    public DcMotor right;
    public Servo servoBoi;
    double lel;
    double leftPower;
    double rightPower;
    double currentLeftPower;
    double currentRightPower;
    double lt = 0.5;
    Thread main = Thread.currentThread();

    {
        lel = 0;
    }

    @Override
    public void init() {
        left = hardwareMap.dcMotor.get("left");
        right = hardwareMap.dcMotor.get("right");
        left.setDirection(DcMotorSimple.Direction.REVERSE);
        //servoBoi = hardwareMap.servo.get("bigboi");

    }

    @Override
    public void loop() {
        //DriveMotorFunctions.driveForward(left, right);
        currentLeftPower = gamepad1.left_stick_y - gamepad1.left_stick_x;
        currentRightPower = gamepad1.left_stick_y + gamepad1.left_stick_x;
        //currentLeftPower = Range.clip(currentLeftPower, -lt, lt);
        //currentRightPower = Range.clip(currentRightPower, -lt, lt);

        if (currentLeftPower < .04 && currentLeftPower > -.04)
            currentLeftPower = 0;
        if (currentRightPower < .04 && currentRightPower > -.04)
            currentRightPower = 0;

        if (leftPower < currentLeftPower) {
            leftPower += .05;
            if (leftPower >= lt) {
                leftPower = lt;
            }
        }
        if (leftPower > currentLeftPower) {
            leftPower -= .05;
            if (leftPower <= -lt) {
                leftPower = -lt;
            }
        }
        if (rightPower < currentRightPower) {
            rightPower += .05;
            if (rightPower >= lt) {
                rightPower = lt;
            }
        }
        if (rightPower > currentRightPower) {
            rightPower -= .05;
            if (rightPower <= -lt) {
                rightPower = -lt;
            }
        }

        left.setPower(leftPower);
        right.setPower(rightPower);
        try {
            main.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}