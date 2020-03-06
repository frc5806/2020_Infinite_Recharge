/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  Drivetrain drive;
  private Joystick joystickR;
  private Joystick joystickL;
  private Joystick buttonBoard;
  private AutoDrivetrain a;
  private Intake in;
  private Hopper hop;
  private Climb climb;
  private Turret turret;
  private Compressor comp;
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    Vision vision = new Vision();
    drive = new Drivetrain(4, 3, 10, 9);
    comp = new Compressor();
    vision.start();
    joystickR = new Joystick(1);
    turret = new Turret(1);
    AutoFire autofire = new AutoFire(turret, drive);
    autofire.start();
    joystickL = new Joystick(2);
    buttonBoard = new Joystick(0);
    a = new AutoDrivetrain(drive);
    in = new Intake(7);
    hop = new Hopper(8, 6);
    climb = new Climb(0);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        break;
    }
  }
 
  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    //drive.drive(joystick.getRawAxis(1), joystick2.getRawAxis(0));
    //drive.drive(joystickR.getRawAxis(1), joystickL.getRawAxis(0));
    if(joystickR.getRawButtonPressed(5))
      climb.changeArm();

    if(joystickR.getRawButton(2)&&Data.armIsRaised){
      System.out.println(joystickR.getRawAxis(2));
      climb.winch(joystickR.getRawAxis(2));
      drive.safteyDrive();
    }
    else{
      drive.drive(joystickR.getRawAxis(1), joystickL.getRawAxis(0));
      climb.winchStop();
    }

    if(joystickR.getRawButton(2)&&!Data.armIsRaised){
      turret.turn(joystickR.getRawAxis(2));
    }
    else{
      turret.stop();
    }
    if(joystickR.getRawButton(1))
      in.spin(1);
    else
      in.spinDown();
      if(joystickR.getRawButtonPressed(6))
        if(comp.enabled())
          comp.stop();
        else
          comp.start();
    if(buttonBoard.getRawButton(1))
      climb.winchOut();
    else if(buttonBoard.getRawButton(2))
      climb.winchIn();
    else
      climb.winchStop();
      SmartDashboard.putBoolean("Arm is raised", Data.armIsRaised);
  }
    /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
