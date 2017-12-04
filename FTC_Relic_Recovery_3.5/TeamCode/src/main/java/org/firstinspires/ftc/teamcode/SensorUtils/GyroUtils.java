package org.firstinspires.ftc.teamcode.SensorUtils;

import org.firstinspires.ftc.teamcode.CustomHardwareMap;

/**
 * Created by Justin on 10/28/2017.
 */

public class GyroUtils {
    /**
     *
     * @param robot
     * @param deltaTime
     * @return
     */
    public static double calcAngleTurned(CustomHardwareMap robot, long deltaTime){
       return deltaTime != 0 ? robot.getGyroscope().getAngularVelocity().xRotationRate * deltaTime / 1000 : 0;
    }
    /*public static double calcTurnSpeed(double currentAngle, double targetAngle){
        double result = Math.abs(Math.cos((Math.PI/(2 * targetAngle)) * currentAngle));
        return result;
    }*/
    public static double calcTurnSpeed(double currentAngle, double targetAngle){
        if (targetAngle == 0) throw new IllegalArgumentException("Target cannot be 0!");
        return Math.abs(Math.cos(( Math.PI/2 * currentAngle)/targetAngle));
    }
}
