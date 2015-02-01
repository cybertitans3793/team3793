package org.usfirst.frc.team3793.robot;

//all imports below
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.vision.*;
import edu.wpi.first.wpilibj.Ultrasonic;
import java.lang.*;
//import edu.wpi.first.wpilibj.image.*;
//import edu.wpi.first.wpilibj.interfaces.*;

public class Robot extends IterativeRobot {//implements Runnable{

	/**
	 * Has the button to move the piston been released since the last state change
	 */
	private boolean hasReleased = true;
	private boolean isStopped = false;
	private long isScreech = 0;
	public Joystick pistonJoystick = new Joystick(0);
	public Joystick driveJoystick = new Joystick (1);
	public Solenoid solenoid = new Solenoid(0);
	public Solenoid theNoodler = new Solenoid  (1); //the arm that grabs the pool noodles
	public Compressor compressor = new Compressor(0); 
	//public Talon talon1 = new Talon(0);
	static public Timer time = new Timer (); //time functions
	public RobotDrive drive = new RobotDrive (0, 1); //arguments are the channels for the Left and Right motors
	public Ultrasonic theScreecher = new Ultrasonic (1,0);
	
	//------BUTTONS--------  (should be 1-#OfButtons)
	/**the button index used for toggling the piston state*/
	private final int pistonToggle = 1;
	/**the button for opening the Noodler*/
	private final int NoodlerOpen = 4;
	/**the button for closing the Noodler*/
	private final int NoodlerClose = 5;
	/**the button for toggling slow acceleration mode (true by default)*/
	private final int slowAccelToggle = 9;
	
	
	
	
	
	private double speedOfMotor =  1; //value higher than 1

	
	
	
	public void disabledInit()
	{
		//AxisCamera camera = new AxisCamera("10.37.93.15");
    	//Timer.delay(8.0);
    	//System.out.println("camera connected (supposedly)");
	}
	//This function below run once when robot starts 
    public void robotInit() 
    {
    	compressor.start();
        //accel.free ();
        time.reset ();
        theScreecher.setEnabled(true);
    }
    
    //This function is called periodically during autonomous
    public void autonomousPeriodic() { 
    	
    }
 
    
    //This function is called every 20 milliseconds approximately during operator control
    
    public double lastY = 0.0;
    public double currY = 0.0;
    public boolean slowAccel = true;
    public void teleopPeriodic() { 
    	
    	
    	//CODE BELOW IS FOR FORKLIFT?
    	if (pistonJoystick.getRawButton(pistonToggle)) {
    		if (hasReleased) {
    			solenoid.set(!solenoid.get());
    			hasReleased = false;
    		}
    	}
    	else if (!hasReleased){
    		hasReleased = true;
    	}
    	//END
    	
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
    	
    //	drive.arcadeDrive (driveJoystick.getY (), -driveJoystick.getX());
    //	if (driveJoystick.getRawButton (2)) {
    //		drive.arcadeDrive (driveJoystick.getY (), -driveJoystick.getX() - 0.4);	
    //	}
    	//END
    	
    	//CODE BELOW IS FOR THE DRIVE SYSTEM
    	
    	
    	//END
    	/*
    	currY = pistonJoystick.getRawAxis(1);
    	if (currY - lastY > 0.1)
    	{
    		drive.arcadeDrive(lastY+0.1, -driveJoystick.getX());
    		lastY += 0.1;
    	}
    	else if (lastY - currY > 0.1)
    	{
    		drive.arcadeDrive(lastY-0.1, -driveJoystick.getX());
    		lastY -= 0.1;
    	}
    	else
    	{
    		drive.arcadeDrive(currY, -driveJoystick.getX());
    		lastY = currY;
    	}*/
    	/*
    	if (pistonJoystick.getRawButton(slowAccelToggle))
    		slowAccel = !slowAccel;
    	if (slowAccel)
    	{
    		
    	}
    	else
    		drive.arcadeDrive(driveJoystick.getY(), -driveJoystick.getX());
    	*/    	
    	if (true) {
    		System.out.println(theScreecher.getRangeInches() + " inches");
    		isScreech++;
    	}
    	
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
            
   //This function is called periodically during test mode.
    public void testPeriodic() {	
    	
    }
  
}
//aa-	--------------------------------------