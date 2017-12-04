package org.firstinspires.ftc.teamcode.SensorUtils;

/**
 * Created by Justin on 10/28/2017.
 */

public final class Timer implements Runnable {
    private long lastTime, currentTime;
    private long deltaTime;


    public Timer(){
        lastTime = System.nanoTime();
        deltaTime = 0;
        this.run();
    }

    @Override
    public void run() {
        setTimes();
    }

    private void setTimes(){
        synchronized (this) {
            currentTime = System.nanoTime();
            setDeltaTime(currentTime - lastTime);
            lastTime = currentTime;
        }
    }

    public void setDeltaTime(long time){
        synchronized (this){
            deltaTime += time;
        }
    }
    public long getDeltaTime(){
        synchronized (this){
        return deltaTime;
        }
    }

    public long getDeltaTimeAndReset(){
        synchronized (this) {
            long currentDeltaTime = deltaTime;
            deltaTime = 0;
            return currentDeltaTime;
        }
    }
}
