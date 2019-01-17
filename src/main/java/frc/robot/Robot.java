/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commandgroups.GoFwd;
import frc.robot.teleop.Teleop_Arcade_Differential;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */

public class Robot extends TimedRobot {
  //public static Drivetrain drivetrain = new Drivetrain();
  public RobotBuilder robot = RobotBuilder.getInstance();
  public OI OpI;
  public DashboardOutput dashboard = new DashboardOutput();

  Command auto;
  SendableChooser<Command> autoChooser = new SendableChooser<>();

  private Teleop_Arcade_Differential Teleop;


  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    //Create an instance of the Operator Interface (Op--erator I--nterface)
    OpI = new OI();

    //Create a drop-down menu for selcting an autonomous program
    //Use .addOption for adding new autonomous routines
    autoChooser.setDefaultOption("Default Auto", new GoFwd());
    SmartDashboard.putData("Auto mode", autoChooser);

    //Initialize the various subsystems
    robot.init();
    
    robot.drivetrain.zeroEncoders();
    robot.drivetrain.resetGyro2();

    robot.revBoard.init();
    robot.drivetrain.zeroEncoders();

    robot.lemonlight.disableLights();
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
    dashboard.run();
    robot.revBoard.run();
    //robot.camera.init();
  }


  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
    robot.revBoard.disable();
  }


  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }


  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    auto = autoChooser.getSelected();

    robot.drivetrain.zeroEncoders();
    robot.drivetrain.resetGyro2();
   

    if (auto != null) {
      auto.start();
    }
  }


  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    //Runs the Command Scheduler as defined by the WPIlib API.
    //In other words, makes our auto actually execute.
    Scheduler.getInstance().run();
  }


  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (auto != null) {
      auto.cancel();
    }
    Teleop = new Teleop_Arcade_Differential();

    Teleop.init();
  }


  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    Teleop.run();
  }


  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}