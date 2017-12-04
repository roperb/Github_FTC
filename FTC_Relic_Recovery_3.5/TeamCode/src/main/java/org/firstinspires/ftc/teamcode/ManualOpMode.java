package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Griffins on 9/30/2017.
 */
@TeleOp(name = "manual", group = "TeleOp")
//@Disabled
public class ManualOpMode extends OpMode {
    private DcMotor leftMotor;
    private DcMotor rightMotor;
    private DcMotor liftMotor;
    private CRServo leftFeeder;
    private CRServo rightFeeder;
    private DcMotor Rotator;
    private Servo armMotor;
    private Servo colorServo;
    private double liftDirection;
    private double powerReducer = 2;
    private double openPosition = 0.4;
    private double closedPosition = 0.5;
    private double colorLowered = 1;
    private double colorRaised = 0.45;
    private double directionDown = 0.2;
    private double directionUp = -0.667;
    private CustomHardwareMap chwMap = CustomHardwareMap.getInstance();

    @Override
    public void init() {
        chwMap.init(hardwareMap);
        try {
            leftMotor = chwMap.getLeft();
            rightMotor = chwMap.getRight();
            liftMotor = chwMap.getLift();
            leftFeeder = chwMap.getLeftFeeder();
            rightFeeder = chwMap.getRightFeeder();
            colorServo = chwMap.getColorServo();
            armMotor = chwMap.getArm();
            Rotator = chwMap.getRot();
            rightMotor.setDirection(DcMotor.Direction.FORWARD);
            leftMotor.setDirection(DcMotor.Direction.REVERSE);
        }catch(NullPointerException NPE){
            telemetry.addData("Error", NPE.getMessage());
            telemetry.addData("right", rightMotor.toString());
            telemetry.addData("left", leftMotor.toString());
        }
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        armMotor.setPosition(openPosition);
        colorServo.setPosition(colorRaised);

        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop() {
        //powerLeft = -gamepad1.left_stick_y + gamepad1.left_stick_x;
        //powerRight = -gamepad1.left_stick_y - gamepad1.left_stick_x;

        //powerFeeder = (-gamepad1.left_trigger + gamepad1.right_trigger) / powerReducer;

        //powerRotator = gamepad1.right_stick_y;

        leftMotor.setPower(power("left"));
        rightMotor.setPower(power("right"));
        leftFeeder.setPower(power("feeders"));
        rightFeeder.setPower(-power("feeders"));

        if (gamepad1.left_trigger > .1) {
            liftDirection = directionDown;
        } else if (gamepad1.right_trigger > .1) {
            liftDirection = directionUp;
        } else {
            liftDirection = 0;
        }

        liftMotor.setPower(liftDirection);
        Rotator.setPower(power("rotator"));

        telemetry.addData("rotation power" , Rotator.getPower());

        //armMotor.setPosition(armPos);

        if (gamepad1.a) {
            armMotor.setPosition(openPosition);
        } else if (gamepad1.b) {
            armMotor.setPosition(closedPosition);
        }
        if (gamepad1.x) {
            colorServo.setPosition(colorRaised);
        } else if (gamepad1.y) {
            colorServo.setPosition(colorLowered);
        }

        //rightMotor.setPower(gamepad1.right_trigger);
        //leftMotor.setPower(gamepad1.left_trigger);
    }
    private double power(String side){
        /*if (side.equals("left")) {
            return -gamepad1.left_stick_y + gamepad1.left_stick_x;
        }
        else if (side.equals("right")){
            return -gamepad1.left_stick_y - gamepad1.left_stick_x;
        }
        else if (side.equals("feeder")) {
            return (-gamepad1.left_trigger + gamepad1.right_trigger) / powerReducer;
        }
        else if (side.equals("rotator")) {
            return gamepad1.right_stick_y;
        }
        else throw new IllegalArgumentException("There is no such motor!");*/

        switch (side){
            case "left": return (-gamepad1.left_stick_y + gamepad1.left_stick_x)/2;
            case "right": return (-gamepad1.left_stick_y - gamepad1.left_stick_x)/2;
            case "feeders": if (gamepad1.right_bumper) {return 1;}
                else if (gamepad1.left_bumper) {return -1;}
                else {return 0;}
            case "rotator": return gamepad1.right_stick_y;
            default: throw new IllegalArgumentException("There is no such motor!");
        }
    }

}
