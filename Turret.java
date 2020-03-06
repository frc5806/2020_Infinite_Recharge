package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

final class Turret implements PIDTurretData{
    TalonSRX turretM;
    public Turret(int CANIDT){
        turretM = new TalonSRX(CANIDT);

        turretM.configFactoryDefault();
        turretM.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

        turretM.setSensorPhase(true);

        turretM.configNominalOutputForward(0);
        turretM.configNominalOutputReverse(0);
        turretM.configPeakOutputForward(1);
        turretM.configPeakOutputReverse(-1);

        turretM.config_kF(0, FF);
        turretM.config_kP(0, P);
        turretM.config_kI(0, I);
        turretM.config_kD(0, I);
    }
    public void turn(double speed){
        turretM.set(ControlMode.PercentOutput, speed);
    }
    public void turnRPM(double velocity){
        turretM.set(ControlMode.Velocity, velocity * 4096 / 600);
    }
    public void stop(){
        turretM.set(ControlMode.PercentOutput, 0);
    }
}