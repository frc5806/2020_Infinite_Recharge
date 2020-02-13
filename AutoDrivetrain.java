package frc.robot;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

import com.revrobotics.CANEncoder;

final class AutoDrivetrain extends Thread implements RobotData{

    private Drivetrain drive;

    private LinkedList<Double> S;
    private LinkedList<Double> T;

    private ListIterator<Double> SI;
    private ListIterator<Double> TI;

    private LinkedList<String> Type;
    private ListIterator<String> TypeI;

    private double tickPerM;
    private double pR;
    private double pL;
    private double speed;
    private double to;

    private boolean enabled;
    private boolean isMoving;
    private boolean isTurning;
    private boolean running;
    private boolean pause;

    private CANEncoder eR1;
    private CANEncoder eL1;

    public AutoDrivetrain(Drivetrain drive){
        this.drive=drive;
        eR1 = drive.driveR1.getEncoder();
        eL1 = drive.driveL1.getEncoder();

        tickPerM = eR1.getCountsPerRevolution()/wheelACircum;
        
        pR = 0;
        pL = 0;

        enabled = true;
        isMoving = false;
        isTurning = false;
        running = false;
        pause = false;

        SI = S.listIterator();
        TI = T.listIterator();
        TypeI = Type.listIterator();
    }
    public void run(){
        while(enabled)
            while(!pause){
            if(!running&&(isMoving||isTurning))
                setEP();
            if(isMoving){
                isTurning = false;
                move();
            }
            else if(isTurning){
                isMoving = false;
                turn();
            }
            else{
                switch(TypeI.next()){
                    case "move":
                        speed = SI.next();
                        to = TI.next();
                        isMoving = true;
                        continue;
                    case "turn":
                        speed = SI.next();
                        to = TI.next();
                        isTurning = true;
                        continue;
                    default:
                        System.out.println("ERROR IN CODE AUTODRIVETRAIN");
                        break;
                }
            }
        }
    }
    public void tPause(){
        drive.drive(0, 0);
        pause = !pause;
        Data.autoDPaused = !Data.autoDPaused;
    }
    public void enableC(){
        enabled=!enabled;
        if(!enabled)
            drive.drive(0, 0);
    }
    public void setEP(){
        pR = eR1.getPosition();
        pL = eL1.getPosition();
    }
    public void updateMove(double speed, double dist){
        S.add(speed);
        T.add(dist);
        Type.add("move");
    }
    public void updateTurn(double speed, double turn){
        S.add(speed);
        T.add(turn);
        Type.add("turn");
    }
    public void stopDrive(){
        drive.drive(0, 0);
    }
    public void clear(){
        S.clear();
        T.clear();
        drive.drive(0, 0);
    }
    private void move(){
        drive.arcadeDrive(-speed, 0);
        Data.moving = true;
        if((eR1.getPosition()-pR)*tickPerM>to||(eL1.getPosition()-pL)*tickPerM>to){
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