package frc.robot;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drivetrain implements PIDDrivetrainData{
    CANSparkMax driveR1;
    CANSparkMax driveR2;
    CANSparkMax driveL1;
    CANSparkMax driveL2;
    CANPIDController PIDdriveR1;
    CANPIDController PIDdriveR2;
    CANPIDController PIDdriveL1;
    CANPIDController PIDdriveL2; 
    SpeedControllerGroup left;
    SpeedControllerGroup right;
    DifferentialDrive drive;
    public Drivetrain(int CANIDS1, int CANIDS2, int CANIDS3, int CANIDS4){
        driveR1 = new CANSparkMax(CANIDS1, MotorType.kBrushless);
        driveR2 = new CANSparkMax(CANIDS2, MotorType.kBrushless);
        driveL1 = new CANSparkMax(CANIDS3, MotorType.kBrushless);
        driveL2 = new CANSparkMax(CANIDS4, MotorType.kBrushless);

        PIDdriveR1 = new CANPIDController(driveR1);
        PIDdriveR2 = new CANPIDController(driveR2);
        PIDdriveL1 = new CANPIDController(driveL1);
        PIDdriveL2 = new CANPIDController(driveL2);

        PIDdriveR1.setFF(gFF+tIM1FF);
        PIDdriveR1.setP(gP+tIM1P);
        PIDdriveR1.setI(gI+tIM1I);

        PIDdriveR2.setFF(gFF+tIM2FF);
        PIDdriveR2.setP(gP+tIM2P);
        PIDdriveR2.setI(gI+tIM2I);

        PIDdriveL1.setFF(gFF+tIM3FF);
        PIDdriveL1.setP(gFF+tIM3P);
        PIDdriveL1.setI(gFF+tIM3I);

        PIDdriveL2.setFF(gFF+tIM4FF);
        PIDdriveL2.setP(gFF+tIM4P);
        PIDdriveL2.setI(gFF+tIM4I);

        right = new SpeedControllerGroup(driveR1, driveR2);
        left = new SpeedControllerGroup(driveL1, driveL2);
        drive = new DifferentialDrive(left, right);
    }
    public void drive(double speed, double turn){
        drive.curvatureDrive(speed, turn, speed<.15);
    }
}