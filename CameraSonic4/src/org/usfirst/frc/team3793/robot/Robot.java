
package org.usfirst.frc.team3793.robot;

//all imports below
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.*;
import edu.wpi.first.wpilibj.Ultrasonic;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

public class Robot extends IterativeRobot {//implements Runnable{ 

	/**
	 * Has the button to move the piston been released since the last state change
	 */
	private boolean hasReleased1 = true;
	private boolean hasReleased2 = true;
	private boolean isStopped = false;
	
	//public Joystick pistonJoystick = new Joystick(1);
	public Joystick drive1Joystick = new Joystick (0);
	public Joystick drive2Joystick = new Joystick (1);
	//public Solenoid ToteGrabber = new Solenoid(1);
	//public Solenoid theNoodler = new Solenoid  (0); //the arm that grabs the pool noodles
	//public Compressor compressor = new Compressor(0); 
	//public Talon talon1 = new Talon(0);
	public Victor victor1 = new Victor (2);
	static public Timer time = new Timer (); //time functions
	//public RobotDrive drive = new RobotDrive (0, 1); //arguments are the channels for the Left and Right motors
	public Ultrasonic theScreecher = new Ultrasonic (1,0);
	public Encoder leftEncoder = new Encoder(4, 5, false, Encoder.EncodingType.k4X);
	public Encoder rightEncoder = new Encoder(2, 3, false, Encoder.EncodingType.k4X);
	public Talon leftTalon = new Talon(0);
	public Talon rightTalon = new Talon(1);
	/*
	public PIDController leftPID = new PIDController(0.1, 0.1, 0.1, leftEncoder, leftTalon);
	public PIDController rightPID = new PIDController(0.1, 0.1, 0.1, rightEncoder, rightTalon);
	*/
	
	//------BUTTONS--------  (should be 1-#OfButtons)

	
	
	
	public void disabledInit()
	{
	//AxisCamera camera = new AxisCamera("10.37.93.15");
  	//Timer.delay(8.0);
  	//System.out.println("camera connected (supposedly)");
	}
	//This function below run once when robot starts 
	
	int session;
  Image frame;
  
  public void robotInit() 
  {
	  /*
  	compressor.start();
  	*/
      //accel.free ();
      time.reset ();
      theScreecher.setEnabled(true);
      /*
      frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
      session = NIVision.IMAQdxOpenCamera("cam1",
              NIVision.IMAQdxCameraControlMode.CameraControlModeController);
      NIVision.IMAQdxConfigureGrab(session);
      */
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
      /*
      rightPID.setOutputRange(-1.0, 1.0);
      rightPID.setInputRange(-1.0, 1.0);
      leftPID.setOutputRange(-1.0, 1.0);
      leftPID.setInputRange(-1.0, 1.0);
      rightPID.enable();
      leftPID.enable();
      */
  }
  
  //This function is called periodically during autonomous
  public void autonomousPeriodic() { 
  	
  }

  
  public void teleopInit()
  {
  	//NIVision.IMAQdxStartAcquisition(session);
  }
  
  //This function is called every 20 milliseconds approximately during operator control
  
  //public double lastY = 0.0;
  //public double currY = 0.0;
  //public boolean slowAccel = true;
  
  public void teleopPeriodic() { 
  	/*
  	NIVision.IMAQdxGrab(session, frame, 1);
  	CameraServer.getInstance().setImage(frame);
  	*/
	  /*
  	victor1.set(pistonJoystick.getRawAxis(1));
  	SmartDashboard.putNumber("Victor 1 ", pistonJoystick.getRawAxis(1));
  	*/
  	
  	//CODE BELOW IS FOR FORKLIFT?
  	/*
  	if (pistonJoystick.getRawButton(1)) {
  		if (hasReleased1) { 
  			solenoid.set(!solenoid.get());
  			hasReleased1 = false;
  		}
  	}
  	else if (!hasReleased1){
  		hasReleased1 = true;
  	}
  	
  	if (pistonJoystick.getRawButton(2)) {
  		if (hasReleased2) { 
  			theNoodler.set(!theNoodler.get());
  			hasReleased2 = false;
  		}
  	}
  	else if (!hasReleased2){
  		hasReleased2 = true;
  	}*/
  	//END 
  	/*
  	//CODE BELOW IS FOR THE NOODLE GRABBER
  	if (pistonJoystick.getRawButton(NoodlerOpen)) { //opens claw
  		if (theNoodler.get() == false) {
  			theNoodler.set(true);
  		}
  		else if (pistonJoystick.getRawButton (NoodlerClose)) //clamps
  		if (theNoodler.get() == true) {
  			theNoodler.set(false); 
  		}
  	}
  	*/
  	
  //	drive.arcadeDrive (driveJoystick.getY (), -driveJoystick.getX());
  //	if (driveJoystick.getRawButton (2)) {
  //		drive.arcadeDrive (driveJoystick.getY (), -driveJoystick.getX() - 0.4);	
  //	}
  	//END
  	
    //leftPID.setSetpoint(-1.0);
    //rightPID.setSetpoint(-1.0);
    leftTalon.set(-drive1Joystick.getY());
    rightTalon.set(drive2Joystick.getY());
    SmartDashboard.putNumber("right rate  ", rightEncoder.getRate());
    SmartDashboard.putNumber("left rate   ", leftEncoder.getRate());
    /*
  	drive.arcadeDrive(-driveJoystick.getY(), -driveJoystick.getX());

  	
  	if(SmartDashboard.getBoolean("reset", false))
  		encoder.reset();
  	SmartDashboard.putNumber("count", encoder.get());
  	SmartDashboard.putNumber("raw count", encoder.getRaw());
  	SmartDashboard.putNumber("distance", encoder.getDistance());
  	//SmartDashboard.putNumber("period", encoder.getPeriod()); deprecated
  	SmartDashboard.putNumber("rate", encoder.getRate());
  	SmartDashboard.putBoolean("direction", encoder.getDirection());
  	SmartDashboard.putBoolean("stopped", encoder.getStopped());
  	*/
  	
  	//XBOX Controller Drive
  	//drive.drive(driveJoystick.getRawAxis(R2/L2axis), driveJoystick.getRawAxis(leftStickX;));
  	
  	/*
  	if (isStopped) {
  		double x = 0.01;
  		while (x <= 1) {
  			drive.arcadeDrive (x*driveJoystick.getY (), -driveJoystick.getX ());
  			x = x + 0.01;
  			Timer.delay (20);
  		}
  		
  		
  		isStopped = false;
  	}
  	else
  		drive.arcadeDrive (driveJoystick.getY (), -driveJoystick.getX ());
  	if ((driveJoystick.getY () < 0.01) && (driveJoystick.getY () > -0.01)) {
  		isStopped = true;
  	}*/
  }
  
  public void disabledPeriodic()
  {
  	//NIVision.IMAQdxStartAcquisition(session);
  }
          
 //This function is called periodically during test mode.
  public void testPeriodic() {	
  	
  }

}
//aa-	--------------------------------------