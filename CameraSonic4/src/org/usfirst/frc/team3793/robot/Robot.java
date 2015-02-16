//PID code courtesy of Fredric Silberberg
package org.usfirst.frc.team3793.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
import java.lang.Math;


public class Robot extends IterativeRobot {

	public static final byte PISTONTOGGLETOTEGRABBEROPEN = 1;
	public static final byte PISTONTOGGLETOTEGRABBERCLOSE = 2;
	public static final byte PISTONTOGGLETHENOODLERCLOSE = 4;
	public static final byte PISTONTOGGLETHENOODLEROPEN = 5;
	
	long inchesUltrasonic;
	double leftSpeed;
	double rightSpeed;
	
	public Joystick driveJoystick = new Joystick (0);          //the joystick used for driving
	public Joystick pistonJoystick = new Joystick (1);         //the joystick not used for driving
	public Solenoid ToteGrabber = new Solenoid(1);             //the arms that grab the totes and bin
	public Solenoid theNoodler = new Solenoid  (0);            //the arm that grabs the pool noodles
	public Compressor compressor = new Compressor(0);          //the compressor
	public Talon theLifter = new Talon(2);                     //the motor that moves the totegrabber up and down
	public Ultrasonic theScreecher = new Ultrasonic (1,0);     //the ultrasonic sensor
	
	public Encoder leftEncoder = new Encoder(4, 5, false, Encoder.EncodingType.k4X);     //the encoder for the left side of the drive train
	public Encoder rightEncoder = new Encoder(2, 3, false, Encoder.EncodingType.k4X);    //the encoder for the right side of the drive train
	
	//public Talon leftTalon = new Talon(0);                     //the left side of the drive train
	//public Talon rightTalon = new Talon(1);                    //the right side of the drive train
	public RobotDrive drive = new RobotDrive(0, 1);
	
	//public PIDController leftPID = new PIDController(0.1, 0.0, 0.0, leftEncoder, leftTalon);
	//public PIDController rightPID = new PIDController(0.1, 0.0, 0.0, rightEncoder, rightTalon);
	
	
  
    public void robotInit() 
    {
  	    compressor.start();
  	  
        //theScreecher.setEnabled(true);
        
        
        //leftPID.setPercentTolerance (5.0); //tolerance in percent 0.025
        //rightPID.setPercentTolerance (5.0); 
        //leftPID.enable ();
        //rightPID.enable ();
        
        rightEncoder.setMaxPeriod(.1);
        rightEncoder.setMinRate(10);
        rightEncoder.setDistancePerPulse(5);
        rightEncoder.setReverseDirection(true);
        rightEncoder.setSamplesToAverage(7);
        leftEncoder.setMaxPeriod(.1);
        leftEncoder.setMinRate(10);
        leftEncoder.setDistancePerPulse(5);
        leftEncoder.setReverseDirection(true);
        leftEncoder.setSamplesToAverage(7);
    }
  
    //This function is called periodically during autonomous
    public void autonomousPeriodic() { 
    }

    //This function is called every 20 milliseconds approximately during operator control
    public void teleopPeriodic() {
    	
    	
	    drive.arcadeDrive(-driveJoystick.getY(), -driveJoystick.getX(), true);
	    
	  
	    //&& !(ToteGrabber.get () == false)
	    
	    if (pistonJoystick.getRawButton(PISTONTOGGLETOTEGRABBEROPEN))
	    {
	    	ToteGrabber.set(true);
	    }
	    if (pistonJoystick.getRawButton(PISTONTOGGLETOTEGRABBERCLOSE))
	    {
	    	ToteGrabber.set(false);
	    }
	    if (pistonJoystick.getRawButton(PISTONTOGGLETHENOODLEROPEN))
	    {
	    	theNoodler.set(true);
	    }
	    if (pistonJoystick.getRawButton(PISTONTOGGLETHENOODLERCLOSE))
	    {
	    	ToteGrabber.set(false);
	    }
	    
	    theLifter.set (pistonJoystick.getY ());
	
	    //inchesUltrasonic  = Math.round (theScreecher.getRangeInches ());

	    //SmartDashboard.putNumber ("Feet", inchesUltrasonic/12);
	    //SmartDashboard.putNumber ("Inches", inchesUltrasonic%12);
	    
	
  	/*
  	//CODE BELOW IS FOR THE NOODLE GRABBER
  	if (pistonJoystick.getRawButton(3)) { //opens claw
  		if (theNoodler.get() == false) {
  			theNoodler.set(true);
  		}
  		else if (pistonJoystick.getRawButton (4)) //clamps
  		if (theNoodler.get() == true) {
  			theNoodler.set(false); 
  		}
  	}
  	*/
    }
}
