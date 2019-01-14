package frc.robot;

import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.RevController;
import frc.robot.subsystems.TestMotor;
import frc.robot.subsystems.initableSubsystem;

import java.util.ArrayList;

public class RobotBuilder {

    public Drivetrain drivetrain = new Drivetrain();
    public TestMotor mast = new TestMotor();

    ArrayList<initableSubsystem> initiableList;
    public static RevController revController = new RevController();

    private static RobotBuilder robotBuilder;

    
    private RobotBuilder(){
        initiableList = new ArrayList<initableSubsystem>();

        initiableList.add(drivetrain);
        initiableList.add(mast);
    }

    public static RobotBuilder getInstance() {

        if (robotBuilder == null) {
            robotBuilder = new RobotBuilder();
        }

        list.add(drivetrain);
        list.add(revController);
        return robotBuilder;
    }

    public void init(){
        for(int i = 0; i < initiableList.size(); i++){
            initiableList.get(i).init();
        }
    }
}