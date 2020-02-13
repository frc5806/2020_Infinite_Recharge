package frc.robot;
public class AutoFire extends Thread implements RobotData{
    private boolean enable = true;
    public void run(){
        while(!enable){
            Data.AFenabled = enable;

        }
        Data.AFenabled = false;
    }
    public void enableC(){
        enable=!enable;
    }
}