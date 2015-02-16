
package org.usfirst.frc.team3793.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Jaguar; //If you use Victors, import them instead
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer; //Make sure to use this version, not the java.util version of timer.
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
* This program initializes a PID Controller for the wheels and drives
* 3 feet using the encoders. PID works by changing the output of the
* motors by PID constants, based on how far away you are from the target.
* PID values need to be tuned for your robot.
*
* @author: Fredric Silberberg
*/
public class Robot extends IterativeRobot {
// This class is required to invert the direction of the motor.
// It negates the speed, so that the robot will drive forward.
	/*
	class MotorDrive extends Jaguar {
		public MotorDrive(int port) {
			super(port);
		}
		//Calls the super set method and gives it the negated speed.
		public void set(double speed) {
			super.set(-speed);
		}
	}
	*/
	//Initializes the motors.
	private Talon left = new Talon(0);
	private Talon right = new Talon(1);
	
	public Joystick joystick = new Joystick(0);
	//Initializes the Encoders.
	//private final Encoder leftEncoder = new Encoder(4, 5);
	//private final Encoder rightEncoder = new Encoder(2, 3);
	//Proportional, Integral, and Dervative constants.
	//These values will need to be tuned for your robot.
	private final double Kp = 0.3;
	private final double Ki = 0.0;
	private final double Kd = 0.0;
	private final double Kf = 1.0;
	//This must be fully initialized in the constructor, after the settings
	//for the encoders have been done.
	public Encoder leftEncoder = new Encoder(4, 5, false, Encoder.EncodingType.k4X);
	public Encoder rightEncoder = new Encoder(2, 3, false, Encoder.EncodingType.k4X);
	
	private PIDController leftPID = new PIDController(Kp, Ki, Kd, Kf, leftEncoder, left, 0.02);
	private PIDController rightPID = new PIDController(Kp, Ki, Kd, Kf, rightEncoder, right, 0.02);
	
	public void RobotInit() {
		//Sets the distance per pulse in inches.
		leftEncoder.setDistancePerPulse(.03489);
		rightEncoder.setDistancePerPulse(.03489);
		//Starts the encoders.
		//leftEncoder.start();	
		//rightEncoder.start();
		//Sets the encoders to use distance for PID.
		//If this is not done, the robot may not go anywhere.
		//It is also possible to use rate, by changing kDistance to kRate.
		leftEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate);
		rightEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate);
		//Initializes the PID Controllers
		//leftPID = new PIDController(Kp, Ki, Kd, leftEncoder, left);
		//rightPID = new PIDController(Kp, Ki, Kd, rightEncoder, right);
		//Enables the PID Controllers.
		//leftPID.enable();
		//rightPID.enable();
		//Sets the input range of the PID Controller.
		//These will change, and you should change them based on how far
		//your robot will be driving.
		//For this example, we set them at 100 inches.
		leftPID.setInputRange(-3000.0, 3000.0);
		rightPID.setInputRange(-3000.0, 3000.0);
		//leftPID.setPercentTolerance(10.0);
		//rightPID.setPercentTolerance(10.0);
	}
	/**
	 * This function is called once each time the robot enters operator control.
	 * Teleop commands are put in here
	 */
	public void testInit()
	{
		leftPID.startLiveWindowMode();
		rightPID.startLiveWindowMode();
		LiveWindow.addActuator("Drivetrain", "PID Controller L", leftPID);
		LiveWindow.addActuator("Drivetrain", "PID Controller R", rightPID);
	}
	
	public void teleopInit()
	{
		leftPID.setPID(Kp, Ki, Kd, 10);
		rightPID.setPID(Kp, Ki, Kd, -10);
		leftPID.setSetpoint(2600.0);
		rightPID.setSetpoint(-2600.0);
		leftPID.enable();
		rightPID.enable();
	}
	
	public void teleopPeriodic()
	{
		//left.pidWrite(0.2);
		//right.pidWrite(-0.2);
		SmartDashboard.putNumber("left Error ", leftPID.getError());
		SmartDashboard.putNumber("right Error ", rightPID.getError());
		SmartDashboard.putNumber("left setpoint ", leftPID.getSetpoint());
		SmartDashboard.putNumber("right setpoint ", rightPID.getSetpoint());
		//SmartDashboard.putBoolean("left onTarget", leftPID.onTarget());
		//SmartDashboard.putBoolean("right onTarget", rightPID.onTarget());
	}
}