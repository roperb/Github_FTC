package org.firstinspires.ftc.teamcode.Movement;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Justin on 10/16/2017.
 */

public class EncoderUtils {
    private static final int COUNTS_PER_ROTATION_60 = 420;
    private static final int COUNTS_PER_ROTATION_40 = 1120;
    private static final int COUNTS_PER_ROTATION_20 = 420;
    private static final double GEAR_RATIO = 2d;
    private static final double PI = Math.PI;
    private static final double WHEEL_CIRC = 3.875d * PI;
    private static final double ANGLE_OF_COUNT_DEGREES = 360 / 420;
    private static final double ANGLE_OF_COUNT_RADIANS = (2 * PI) / 420;

    /**
     * Calculating distance from the number of counts
     *
     * @param counts
     * @return distance
     */
    public static double calcDistance(int counts) {
        return (counts / COUNTS_PER_ROTATION_60) * GEAR_RATIO * WHEEL_CIRC;
    }

    /**
     * Returns number of counts based off of inches
     *
     * @param inches - inches to travel
     * @return Encoder counts
     */
    public static int calcCounts(double inches) {
        return (int) (inches * COUNTS_PER_ROTATION_60 / (GEAR_RATIO * WHEEL_CIRC));
    }

    /**
     * Returns ratio between two encoders
     *
     * @param count1 - encoder count from encoder 1
     * @param count2 - encoder count from encoder 2
     * @return ratio
     */
    private static double encRatio(long count1, long count2) {
        return count1 / count2;
    }

    /**
     * Modifies the stronger motor to match the weaker motor.
     *
     * @param motor1 - reference to a motor
     * @param motor2 - reference to a motor
     * @param count1 - encoder count of motor1
     * @param count2 - encoder count of motor2
     */
    public static void calibrateMotor(DcMotor motor1, DcMotor motor2, long count1, long count2) {
        double ratio = encRatio(count1, count2);
        if (ratio > 1) {
            motor1.setPower(motor1.getPower() / ratio);
        } else if (ratio < 1) {
            motor2.setPower(motor2.getPower() * ratio);
        }
    }

    /**
     *
     * @param angle
     *
     * @return
     */
    public static int degreeRotation(double angle) {
        return (int) (ANGLE_OF_COUNT_DEGREES * angle);
    }

    public static int radianRotation(double angle) {
        return (int) (ANGLE_OF_COUNT_RADIANS * angle);
    }
}
