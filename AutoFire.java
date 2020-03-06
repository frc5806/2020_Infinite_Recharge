package frc.robot;

public class AutoFire extends Thread implements RobotData, AutoFireData{
    private boolean enable = true;
    private Turret turret;
    private Drivetrain drive;
    private boolean onRight = true;
    public AutoFire(Turret turret, Drivetrain drive){
        this.turret = turret;
        this.drive = drive;
    }
    public void run(){
        while(!enable){
            Data.roboTVel = (((drive.driveL1.getEncoder().getVelocity()+drive.driveL2.getEncoder().getVelocity())/2)+((drive.driveL1.getEncoder().getVelocity()+drive.driveL2.getEncoder().getVelocity())/2))/2;
            Data.roboTVel = ((drive.driveL1.getEncoder().getVelocity()+drive.driveL2.getEncoder().getVelocity())/2)-((drive.driveL1.getEncoder().getVelocity()+drive.driveL2.getEncoder().getVelocity())/2);
            Data.AFenabled = enable;

        }
    }
    public void enableC(){
        enable=!enable;
    }
    public void track(){
        double tValue = (maxTRRPM*Math.sqrt(Math.abs(Data.x)-dToBound()))/(Math.sqrt(visionBound-dToBound()))+(onRight?toSpeed():-1*toSpeed())+(-1*Data.roboTVel);
        tValue = tValue>1?1:tValue;
        if(Data.x<0)
            turret.turnRPM(tValue);
        else
            turret.turnRPM(-1*tValue);
    }
}