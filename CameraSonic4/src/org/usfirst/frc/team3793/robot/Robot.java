//PID code courtesy of Fredric Silberberg
package org.usfirst.frc.team3793.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
import java.lang.Math;

import edu.wpi.first.wpilibj.PowerDistributionPanel;


public class Robot extends IterativeRobot {

	public static final byte PISTONTOGGLETOTEGRABBEROPEN = 2; //joystick button 2
	public static final byte PISTONTOGGLETOTEGRABBERCLOSE = 1;//joystick button 1
	public static final byte PISTONTOGGLETHENOODLERCLOSE = 6; //joystick button 6
	public static final byte PISTONTOGGLETHENOODLEROPEN = 5;  //joystick button 5
	
	//noodler code may not be used
	
	long inchesUltrasonic; 
	double offset; 
	
	public static final double driveCoefficient = -0.75;
	public static final double turnCoefficient = -0.5;
	
	public Joystick driveJoystick = new Joystick (0);          //the joystick used for driving
	public Joystick pistonJoystick = new Joystick (1);         //the joystick not used for driving
	public Solenoid ToteGrabber = new Solenoid(1);              //the arms that grab the totes and bin
	public Solenoid theNoodler = new Solenoid  (0);            //the arm that grabs the pool noodles
	public Compressor compressor = new Compressor(0);          //the compressorzAz
	public Talon theLifter = new Talon(2);                     //the motor that moves the totegrabber up and down
	public Ultrasonic theScreecher = new Ultrasonic (1,0);     //the ultrasonic sensor
	
	public Encoder leftEncoder = new Encoder(4, 5, false, Encoder.EncodingType.k4X);     //the encoder for the left side of the drive train
	public Encoder rightEncoder = new Encoder(2, 3, false, Encoder.EncodingType.k4X);    //the encoder for the right side of the drive train
	 
	public RobotDrive drive = new RobotDrive(0, 1);
	
	public PowerDistributionPanel pdp = new PowerDistributionPanel();
	
	
  
    public void robotInit() 
    {
  	    compressor.start();
  	  
        //theScreecher.setEnabled(true);
        
  	    //ToteGrabber.set (true);  	  
  	   
  	    
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
  
    //This function is called periodically  during autonomous
    
    public boolean autoRun = false;
    
    public void autonomousInit() {
    	autoRun = false;
    }
    
    public void autonomousPeriodic() { 
    	if (!autoRun)
    	{
    		theLifter.setSafetyEnabled(false);
    		drive.setSafetyEnabled(false);
    		Timer.delay(2.0);
    		ToteGrabber.set(true);
    		Timer.delay(1.0);
    		theLifter.set(-1.0);
    		drive.drive(0.4, 0.0);
    		Timer.delay(1.7);
    		theLifter.set(0.0);
    		Timer.delay(0.1);      //runs for 1.8 seconds
    		drive.drive(0.0, 0.0);
    		drive.drive(0.0, 0.5);
    		Timer.delay (1.0);
    		
    		drive.setSafetyEnabled(true); 
    		theLifter.setSafetyEnabled(true);
    		autoRun = true;
    	}
    }
    //This function is called every 20 milliseconds during operator control
    public void teleopPeriodic() {
    	
    	//SmartDashboard.putNumber("Current Channel 0", pdp.getCurrent(0));
    	//SmartDashboard.putNumber("Current Elevator", pdp.getCurrent(14));
    	//SmartDashboard.putNumber("Current Compressor",pdp.getCurrent(1));
    	/*
    	if (driveJoystick.getPOV () == 0) { drive.arcadeDrive (1, driveJoystick.getRawAxis (0));}
    	if (driveJoystick.getPOV () == 180) { drive.arcadeDrive (-1, driveJoystick.getRawAxis (0));}
    	if (driveJoystick.getPOV () == -1) {drive.arcadeDrive (0, driveJoystick.getRawAxis (0));}
    	*/
	    //drive.arcadeDrive(-driveJoystick.getY(), -driveJoystick.getX(), true);
    	double dif = driveJoystick.getRawAxis(3)-driveJoystick.getRawAxis(2);
    	double turn = driveJoystick.getRawAxis(0);
    	
    	dif = scale(dif, 0.2);
    	turn = scale(turn, 0.2);
    	drive.arcadeDrive(driveCoefficient*dif, turnCoefficient*turn);
    	/*
    	else
    	{
    		if (Math.abs(leftEncoder.getRate()) < Math.abs(rightEncoder.getRate()))
    		{
    			try
    			{
    				offset = leftEncoder.getRate ()/rightEncoder.getRate ();
    			}
    			catch(Exception e)
    			{
    				offset = 1;
    			}
    			drive.setLeftRightMotorOutputs(dif, offset*dif);
    		}
    		
    		if (Math.abs(leftEncoder.getRate()) > Math.abs(rightEncoder.getRate()))
    		{
    			try
    			{
    				offset = rightEncoder.getRate ()/leftEncoder.getRate ();
    			}
    			catch(Exception e)
    			{
    				e.printStackTrace();
    			}
    			drive.
    			drive.setLeftRightMotorOutputs(offset*dif, dif);
    			//leftTalon.set(offset*dif);
    		    //rightTalon.set(dif);
    		}
    		
    	}*/
    	
	    //&& !(ToteGrabber.get () == false)
	    
	    if (pistonJoystick.getRawButton(PISTONTOGGLETOTEGRABBEROPEN)) //joystick button 2
	    {
	    	ToteGrabber.set(true);
	    }
	    if (pistonJoystick.getRawButton(PISTONTOGGLETOTEGRABBERCLOSE))//joystick button 1
	    {
	    	ToteGrabber.set(false);
	    }
	    
	    //noodler code may not be used
	    if (pistonJoystick.getRawButton(PISTONTOGGLETHENOODLEROPEN))  //joystick button 5
	    {
	    	theNoodler.set(true);
	    }
	    if (pistonJoystick.getRawButton(PISTONTOGGLETHENOODLERCLOSE)) //joystick button 6
	    {
	    	theNoodler.set(false);
	    }
	    
	    double liftDif = 0.65*pistonJoystick.getRawAxis(2) - pistonJoystick.getRawAxis(3);
	    theLifter.set (liftDif);
	    
	    
	    //ultrasonic code to calculate height of tote stack
	    /*inchesUltrasonic  = Math.round (theScreecher.getRangeInches ());
	    
	    if (inchesUltrasonic%13 < 2.0) { 								  		//stack of tote approximately 13 inches - 2 inch room for error
	    	SmartDashboard.putNumber ("Tote stack: ", inchesUltrasonic%13); //print stack of totes to smart dashboard
	    }
	    */
	    
	    //ultrasonic code to calculate distance from robot to tote stack
	    inchesUltrasonic = Math.round(theScreecher.getRangeInches()); 
	    
	    if (inchesUltrasonic >= 26.0) { //if distance from totes to Robot is greater that 26 inches, robot is clear to turn
	    	boolean b = true;
	    	SmartDashboard.putBoolean("Clear", b);
	    	
	    }
	    
	    if (inchesUltrasonic < 26.0) { //inverse of above clause
	    	boolean b = false;
	    	SmartDashboard.putBoolean("Clear", b);
	    } 
	    
    }
    
    
    
    
    public double scale(double start, double dead)
    {
    	if (start >= dead)
    	{
    		return (start-dead)*( (1.0)/(1.0-dead) );
    	}
    	else if (start <= -dead)
    	{
    		return (start+dead)*( (1.0)/(1.0-dead) );
    	}
    	return 0.0;
    }
}
