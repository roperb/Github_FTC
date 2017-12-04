package org.firstinspires.ftc.teamcode.Movement;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.CustomHardwareMap;
import org.firstinspires.ftc.teamcode.CustomOpMode.LinearCustomOpMode;
import org.firstinspires.ftc.teamcode.SensorUtils.GyroUtils;

public class MovementLib {
    public static final CustomHardwareMap ROBOT = CustomHardwareMap.instance;

    private MovementLib() {
    }

    /*public static void forward(DcMotor motor1, DcMotor motor2, double inches, double speed){
            motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            int counts = EncoderUtils.calcCounts(inches);

        motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motor1.setTargetPosition(counts);
        motor2.setTargetPosition(counts);

        motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motor1.setPower(speed);
        motor2.setPower(speed);

        while(motor1.getCurrentPosition() <= counts && motor2.getCurrentPosition() <= counts){
            if(motor1.getCurrentPosition() == counts && motor2.getCurrentPosition() == counts){
                motor1.setPower(0);
                motor2.setPower(0);

                motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                break;
            }
        }

    }*/
    public static <Mode extends LinearCustomOpMode> void forward(double inches, double speed, Mode mode) {
        mode.telemetry.addData("Working!", "");
        int counts = EncoderUtils.calcCounts(inches);

        ROBOT.getRight().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ROBOT.getLeft().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        ROBOT.getRight().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ROBOT.getLeft().setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        ROBOT.getRight().setTargetPosition(-counts * 4);
        ROBOT.getLeft().setTargetPosition(-counts * 4);

        ROBOT.getRight().setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ROBOT.getLeft().setMode(DcMotor.RunMode.RUN_TO_POSITION);

        ROBOT.getRight().setPower(speed);
        ROBOT.getLeft().setPower(speed);

        while (ROBOT.getRight().isBusy() && ROBOT.getLeft().isBusy()) {
            mode.telemetry.addData("Counts", counts);
            mode.telemetry.addData("Counts Left", ROBOT.getLeft().getCurrentPosition());
            mode.telemetry.addData("Counts Right", ROBOT.getRight().getCurrentPosition());
            mode.telemetry.update();
        }

        ROBOT.getRight().setPower(0);
        ROBOT.getLeft().setPower(0);

        ROBOT.getRight().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ROBOT.getLeft().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * Rotates the robot the number of degrees given using a gyro. As the robot approaches the target angle, the rate of rotation slows<p>
     * to ensure the robot does not over-rotate. The rate of deceleration is determined by the derivative of a sin function with the period<p>
     * double the target angle using {@link GyroUtils#calcTurnSpeed(double, double)}. Tbe function (@link GyroUtils#calcAngleTurned(CustomHardwareMap, long)})<p>that controls the deceleration is roughly
     * cos ((90/target) * angleTurned) {@link GyroUtils#calcAngleTurned(CustomHardwareMap, long)}. This also takes a generic "Mode" as an <p>
     * argument. "Mode" must extend {@link LinearCustomOpMode}. This ensures that whatever OpMode is passed, it with have <p>
     * the fields from {@link LinearCustomOpMode}.<p>
     * A custom hardware map must be passed ({@link  CustomHardwareMap}) so that the gyro and the driveMotors can be accessed.
     *
     * @param angle  the number of degrees to be turned by the robot
     * @param speed  the speed at which to turn the robot {0.0, 1.0}
     * @param mode   the OpMode using the method
     * @param <Mode> an OpMode that extends {@link LinearCustomOpMode}
     * @throws IllegalArgumentException Thrown if the argument angles = 0.
     * @see GyroUtils#calcTurnSpeed(double, double)
     * @see GyroUtils#calcAngleTurned(CustomHardwareMap, long)
     */
    public static <Mode extends LinearCustomOpMode> void rotate(double angle, double speed, Mode mode) {
        if (angle == 0) {
            throw new IllegalArgumentException("Angle cannot be 0!");
        }

        double currentAngle = 0;

        long lastTime = 0;
        long currentTime;
        long deltaTime;

        String motorReversed;
        DcMotor right = ROBOT.getRight();
        DcMotor left = ROBOT.getLeft();
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        double motorPower;

        if (angle != Math.abs(angle)) {
            motorReversed = "left";
        } else {
            motorReversed = "right";
        }
        while (currentAngle <= Math.abs(angle)) {
            currentTime = System.currentTimeMillis();
            if (lastTime == 0) {
                lastTime = currentTime;
                deltaTime = 0;
            } else {
                deltaTime = currentTime - lastTime;
                lastTime = currentTime;
            }
            currentAngle += GyroUtils.calcAngleTurned(ROBOT, deltaTime);
            motorPower = ((GyroUtils.calcTurnSpeed(currentAngle, angle))) * speed < .25 ? .25 : (GyroUtils.calcTurnSpeed(currentAngle, angle) * speed);
            // motorPower = speed - (currentAngle/ angle) * speed;
            mode.telemetry.addData("speed", motorPower);
            mode.telemetry.addData("gyro", currentAngle);
            mode.telemetry.addData("start", speed);
            //mode.telemetry.addData("dt", mode.getTimer().getDeltaTime());

            mode.telemetry.update();
            if (motorReversed.equals("left")) {
                left.setPower(motorPower);
                right.setPower(-motorPower);
            } else {
                right.setPower(motorPower);
                left.setPower(-motorPower);
            }
            if (Math.abs(currentAngle) >= Math.abs(angle)) {
                right.setPower(0);
                left.setPower(0);

                right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                break;
            }
            if (mode.isStopRequested()) {
                break;
            }
        }

        left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public static <Mode extends LinearCustomOpMode> void rotateArm(double angle, double speed, Mode mode) {
        ROBOT.getRot().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ROBOT.getRot().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ROBOT.getRot().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        int countsPerRotation = 1120;
        int counts = (int) (5 * angle * countsPerRotation / 360);
        ROBOT.getRot().setTargetPosition(counts);
        ROBOT.getRot().setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ROBOT.getRot().setPower(speed);
        while (ROBOT.getRot().isBusy()) {
            mode.telemetry.addData("Moving rotation arm to", counts);
            mode.telemetry.addData("Current Position", ROBOT.getRot().getCurrentPosition());
            mode.telemetry.update();
        }
        ROBOT.getRot().setPower(0);
        ROBOT.getRot().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //ROBOT.getRot().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public static <Mode extends LinearCustomOpMode> void closeArm(Mode mode) {
        ROBOT.getArm().setPosition(0.5);
    }

    public static <Mode extends LinearCustomOpMode> void openArm(Mode mode) {
        ROBOT.getArm().setPosition(0.4);
    }

    public static <Mode extends LinearCustomOpMode> void lowerCServo(Mode mode) {
        ROBOT.getColorServo().setPosition(1);
    }

    public static <Mode extends LinearCustomOpMode> void raiseCServo(Mode mode) {
        ROBOT.getColorServo().setPosition(0.31);
    }
}
