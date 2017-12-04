package org.firstinspires.ftc.teamcode.CustomOpMode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.CustomHardwareMap;
import org.firstinspires.ftc.teamcode.SensorUtils.Timer;

public abstract class LinearCustomOpMode extends LinearOpMode {
    //protected Timer timer = new Timer();
    //protected Thread timerThread = new Thread(timer);
    protected CustomHardwareMap robot = CustomHardwareMap.getInstance();
    protected ElapsedTime runtime = new ElapsedTime();

    /*public void startTimer(){
        timerThread.start();
    }

    public Timer getTimer() {
        return timer;
    }*/
}
