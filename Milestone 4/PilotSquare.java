package exercise1;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.MovePilot;

public class PilotSquare {
	
	//Constants:
	final static int WHEEL_DIAMETER = 56, AXLE_LENGTH = 122, ANGULAR_SPEED = 100, LINEAR_SPEED = 200;
	final static int SIDES = 4, DISTANCE = 1000, ANGLE = 90;
	
	public static void main(String[] args) {
		
		//Motors:
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.B);
		
		//Wheels:
		Wheel wLeft = WheeledChassis.modelWheel(mLeft, WHEEL_DIAMETER).offset(AXLE_LENGTH / 2);
		Wheel wRight = WheeledChassis.modelWheel(mRight, WHEEL_DIAMETER).offset(-(AXLE_LENGTH) / 2);
		
		//This is an array of type "Wheel". It contains the previously made left and right wheels.
		//We use this to set up the "WheeledChassis" object, appropriately named "chassis".
		Wheel[] wheels = new Wheel[] {wLeft, wRight};
		Chassis chassis = new WheeledChassis(wheels, WheeledChassis.TYPE_DIFFERENTIAL);
		//N.B. A DifferentialChassis in particular accepts an array of "Wheel" objects.

		
		//We create our MovePilot using the previous created chassis.
		/* By simply supplying our robot's specs, i.e. wheel diameter, axle length, etc, it calculates the number of rotations -
		- which need to be made in order to travel x metres / rotate y degrees. */
		MovePilot pilot = new MovePilot(chassis);
		
		/* Evidently, this PoseProvider uses Odometry to figure out where it is.
		i.e. It estimates its current position relative to its starting position. */
		PoseProvider poseProvider = new OdometryPoseProvider(pilot);
		
		pilot.setLinearSpeed(LINEAR_SPEED);
		pilot.setAngularSpeed(ANGULAR_SPEED);
		
		LCD.drawString(poseProvider.getPose().toString(), 0, 0); //Pose Before
		
		for(int i = 0; i < SIDES; i++) {
			pilot.travel(DISTANCE);
			pilot.rotate(ANGLE);
			//pilot.arc(WHEEL_DIAMETER, ANGLE);
		}
		
		LCD.drawString(poseProvider.getPose().toString(), 0, 1); //Pose After
		
		//Loop below allows us to actually see the values for the pose after without the program suddenly ending.
		while(Button.getButtons() != Button.ID_ENTER) {
			continue;
		}
		
	}
	
}