package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

final class Turret implements PIDTurretData{
    TalonSRX turretM;
    public Turret(int CANIDT){
        turretM = new TalonSRX(CANIDT);

        //configure PID or Closed Loop Control
    }
    public void Rotate(double rotations){
        turretM.set(ControlMode.MotionMagic, rotations);
    }
    public void spinUp(double speed){
        turretM.set(ControlMode.PercentOutput, speed);
    }
    public void spinDown(){
        turretM.set(ControlMode.PercentOutput, 0);
    }
}