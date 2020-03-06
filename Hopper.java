package frc.robot;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
final class Hopper{
    private CANSparkMax intakeM1;
    private CANSparkMax intakeM2;
    public Hopper(int CANIDS1,int CANIDS2){
        intakeM1 = new CANSparkMax(CANIDS1,MotorType.kBrushless);
        intakeM2 = new CANSparkMax(CANIDS2,MotorType.kBrushless);
    }
    public void spinUp(double speed){
        intakeM1.set(speed);
        intakeM2.follow(intakeM1);
    }
    public void spinDown(){
        intakeM1.stopMotor();;
        intakeM2.stopMotor();;
    }
}