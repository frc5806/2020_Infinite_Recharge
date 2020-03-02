package frc.robot;
class Watchdog extends Thread {
    Drivetrain d;
    public Watchdog(Drivetrain mDrive){
        d = mDrive;
    }
    public void run(){
        Data.roboTVel = (((d.driveL1.getEncoder().getVelocity()+d.driveL2.getEncoder().getVelocity())/2)+((d.driveL1.getEncoder().getVelocity()+d.driveL2.getEncoder().getVelocity())/2))/2;
        Data.roboTVel = ((d.driveL1.getEncoder().getVelocity()+d.driveL2.getEncoder().getVelocity())/2)-((d.driveL1.getEncoder().getVelocity()+d.driveL2.getEncoder().getVelocity())/2);
    }
}