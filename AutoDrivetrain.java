package frc.robot;

import java.util.ListIterator;

import com.revrobotics.CANEncoder;

import com.kauailabs.navx.frc.AHRS;

final class AutoDrivetrain implements RobotData {

    private Drivetrain drive;

    private ListIterator<Double> S;
    private ListIterator<Double> T;

    private ListIterator<String> Type;

    private AHRS navX;

    private double pR;
    private double pL;
    private double hdg;
    private double speed;
    private double to;

    private boolean isMoving;
    private boolean isTurning;

    private CANEncoder eR1;
    private CANEncoder eL1;

    public AutoDrivetrain(Drivetrain drive, AHRS navX) {
        this.navX = navX;

        this.drive = drive;
        eR1 = drive.driveR1.getEncoder();
        eL1 = drive.driveL1.getEncoder();

        pR = 0;
        pL = 0;

        isMoving = false;
        isTurning = false;
    }

    public void aRun() {
        Data.ADEnabled=true;
        if (isMoving){
            getEP();
            isTurning = false;
            move();
        }
        else if (isTurning){
            getHeading();
            isMoving = false;
            turn();
        }
        else {
            switch (Type.next()) {
                case "move":
                    speed = S.next();
                    to = T.next();
                    isMoving = true;
                    break;
                case "turn":
                    speed = S.next();
                    to = T.next();
                    isTurning = true;
                    break;
                default:
                    System.out.println("ERROR IN CODE AUTODRIVETRAIN");
                break;
            }
        }
        Data.ADEnabled = false;
    }
    private void getEP(){
        pR = eR1.getPosition();
        pL = eL1.getPosition();
    }
    private void getHeading(){
        hdg = navX.getCompassHeading();
    }
    public void updateMove(double speed, double dist){
        if(S.hasNext())
            S.add(0d);
        if(T.hasNext())
            T.add(0d);
        S.add(speed);
        T.add(dist);
        Type.add("move");

    }
    public void updateTurn(double speed, double turn){
        if(S.hasNext())
            S.add(0d);
        if(T.hasNext())
            T.add(0d);
        S.add(speed);
        T.add(turn);
        Type.add("turn");
    }
    public void stopDrive(){
        drive.drive(0, 0);
    }
    private void move(){
        drive.arcadeDrive(-speed, 0);
        Data.moving = true;
        if((eR1.getPosition()-pR)>to||(eL1.getPosition()-pL)>to){
            isMoving = false;
            Data.moving = false;
        }
    }
    private void turn(){
        drive.arcadeDrive(0, speed);
        Data.turning = true;
        if(Math.abs((eR1.getPosition()-pR)+(eL1.getPosition()-pL))/2<=((to/90)*tickPer90)){
            isTurning = false;
            Data.turning = false;
        }
    }
}