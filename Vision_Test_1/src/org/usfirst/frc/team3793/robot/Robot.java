
package org.usfirst.frc.team3793.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.*;
import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import com.ni.vision.NIVision.CriteriaCollection;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	   
	    //This function is run when the robot is first started up and should be
	    //used for any initialization code.
	   
	    Joystick control = new Joystick (1); //new joystick control for port 1
	    Joystick pistoncontrols = new Joystick (2); //for piston mechanics
	    //Joystick camera = new Joystick (3); //for camera control
	    RobotDrive drive = new RobotDrive (1, 2); //, 3, 4); //4 wheels on robot
	    Compressor piston = new Compressor (0); //digital IO second set of slots
	    Solenoid pistonextract = new Solenoid (1);  //don't put module number
	    Solenoid pistoncontract = new Solenoid (2);
	    //AxisCamera frontcam = AxisCamera.getInstance(); //working witht eh front camera
	    //in getIntance(); pass IP adress of the camera, or use the standard port
	    //Accelerometer accel = new Accelerometer (5, 1); //slot(port?), channel
	    //Timer time = new Timer ();
	    CriteriaCollection xx; //no clue
	    //objects above
	    
	    //variables below
	    //boolean cameraCapture = false; //if true, camera will take pics
	    double a = 0; //acceleration
	    double vF = 0; //velocity m/s
	    double vFm = 0; //velocity mph
	    double aa = 0; //average accelleration
	    double t = 0; //time elapsed
	    int rl = 0, rh = 0, gl = 0, gh  =0,  bl = 0, bh = 0; //red, green, blue, low and highs for color detection
	    double meterstomiles = 2.23694; //m/s to mph conversion ratio
	  
	    
	    //code below run once when robot starts
	    public void robotInit() {
	        piston.start ();
	        //accel.free ();
	        //time.reset ();
	        /*
	        xx = new CriteriaCollection ();
	        xx.addCriteria(NIVision.MeasurementType.IMAQ_MT_BOUNDING_RECT_WIDTH, 40, 400, false);
	        xx.addCriteria(NIVision.MeasurementType.IMAQ_MT_BOUNDING_RECT_HEIGHT, 40, 400, false);
	        */
	        //functions above identify retangles - just size 
	        //colorOfRect (); //identify green, red, blue rectangles
	        
	    }

	    //This function is called periodically during autonomous - requires code as of now
	    public void autonomousPeriodic() {

	    }

	    // This function is called every 20 miliseconds approxmately during operator control
	    public void teleopPeriodic() {
	        /*
	        drive.mecanumDrive_Cartesian( //Cartesian system driving
	                //mechanumDrive_Polor available, not reccommended
	                control.getAxis(Joystick.AxisType.kX), //receives X input from joystick
	                control.getAxis(Joystick.AxisType.kY), //y input
	                0, //control.getAxis(Joystick.AxisType.kZ), //z axis for rotation - if supported comment in
	                0); //gyroscope 
	        
	        //kinetics ();
	        */
	        
	        //drive.tankDrive (control, pistoncontrols);
	        //if (!drive.isSafetyEnabled ()) {
	          //  drive.setSafetyEnabled(true); //sets safety enabled for emergencies
	       // drive.setTurnRadius (1.000); //experimental value
	        drive.arcadeDrive (control);
	        
	        
	        
	        if (pistoncontrols.getRawButton(1)){ //when button 1 is pressed, expandd the piston
	            
	            pistonextract.set (true);
	            pistoncontract.set (true);
	        }
	        else if (pistoncontrols.getRawButton (2)) { //when button 2 is pressed, compress the piston
	            pistonextract.set (false);
	            pistoncontract.set (false);
	        }
	        /*
	        else if (pistoncontrols.getRawButton(3)){ //when button 1 is pressed, expandd the piston
	            pistoncontract.set (true);
	        }
	        else if (pistoncontrols.getRawButton (4)) { //when button 2 is pressed, compress the piston
	            pistoncontract.set (false);
	        }
	        else if (pistoncontrols.getRawButton (5)) { //when button 3 is pressed,
	            pistonextract.set (false);              //piston will retact if not allready restarcted
	            pistoncontract.set (false);
	        }
	        */
	        
	        /*
	        if (camera.getRawButton (1)) { //toggle camera on
	            cameraCapture = true;
	        }
	        else if (camera.getRawButton (2)) { //toggle camera off
	            cameraCapture = false;
	        }
	        
	        if (cameraCapture) {
	            acquireTarget (); //function for camera mechanics
	        }
	                */
	        
	    }
	            
	    //function that does targeting processing  - currently detects green rectangles
	    
	    private void acquireTarget () {
	        ColorImage image = null;
	        BinaryImage thresholdImage = null;
	        BinaryImage bigObjectsImage = null;
	        BinaryImage convexHullImage = null;
	        BinaryImage filteredImage = null;
	        
	        try {
	             
	            image = frontcam.getImage(); //retrieves image from camera
	            thresholdImage = image.thresholdRGB(0, 45, 25, 255, 0, 45); //SETS COLOR DETECTIONS
	            //thresholdImage = image.thresholdRGB(rl, rh, gl, gh, bl, bh); //if colorOfRect (); works, then this will set 
	            //color detection to red, green, or blue with console arguments
	            bigObjectsImage = thresholdImage.removeSmallObjects(false, 2);//removes small objects
	            convexHullImage = bigObjectsImage.convexHull(false);//finds big objects
	            filteredImage = convexHullImage.particleFilter(xx);
	            ParticleAnalysisReport[] reports = filteredImage.getOrderedParticleAnalysisReports(); //idk what this does        
	            for (int i = 0; i < reports.length + 1; i++) {
	                
	                System.out.println (reports [i].center_mass_x);
	                
	            }
	            
	        } 
	        catch (Exception ex) {
	        
	        } finally {
	            //System.out.println (ex); //if exception is thrown, print to console
	        }
	        
	        try {
	            filteredImage.free();
	            convexHullImage.free();
	            bigObjectsImage.free ();
	            thresholdImage.free ();
	            image.free (); 
	            //frees up all resources 
	        } catch (NIVisionException ex) {
	            ex.printStackTrace ();
	        }
	    }
	         
	    // This function is called periodically during test mode
	    public void testPeriodic() {
	    
	    }
	    
	    //wrote this function to detect acceleration and calculate speed
	    /*
	    public void kinetics () {
	        
	        time.reset (); //resets stopwatch
	        
	        int ai = 0; //sets loop counter to find average accelleration
	        
	        time.start (); //starts stopwatch
	        
	        while (accel.getAcceleration () != 0.0) {
	            a =  + accel.getAcceleration ();
	            ai++;
	        }
	        
	        time.stop ();
	        t = time.get ();
	        
	        aa = a/ai; //average accelleration
	        vF = (aa + (t*t)); //average speed meters per second
	        //Vf = Vo + at^2
	        vFm = vF*meterstomiles;
	        
	        System.out.println (vF + " meters per second.\n"); //prints to console
	        System.out.println (vFm + " miles per hour.\n");
	    }
	    */
	}

	//THS CyberTitans
