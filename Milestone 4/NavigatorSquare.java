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
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Pose;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;

public class NavigatorSquare {
	
	//Constants:
	final static int WHEEL_DIAMETER = 56, AXLE_LENGTH = 122, ANGULAR_SPEED = 100, LINEAR_SPEED = 70;
	
	public static void main(String[] args) {
		
		//Motors:
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.B);
		
		//Wheels:
		Wheel wLeft = WheeledChassis.modelWheel(mLeft, WHEEL_DIAMETER).offset(AXLE_LENGTH / 2);
		Wheel wRight = WheeledChassis.modelWheel(mRight, WHEEL_DIAMETER).offset(-(AXLE_LENGTH) / 2);
		
		//Setting up our MovePilot:
		Wheel[] wheels = new Wheel[] {wLeft, wRight};
		Chassis chassis = new WheeledChassis(wheels, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		
		//Creating our OdometryPoseProvider:
		PoseProvider poseProvider = new OdometryPoseProvider(pilot);
		
		//Setting Speeds:
		pilot.setLinearSpeed(LINEAR_SPEED);
		pilot.setAngularSpeed(ANGULAR_SPEED);
		
		//Creating our Navigator:
		Navigator navigator = new Navigator(pilot, poseProvider);
		
		Path squarePath = new Path();
		squarePath.add(new Waypoint(100,0));
		squarePath.add(new Waypoint(100,100));
		squarePath.add(new Waypoint(0,100));
		squarePath.add(new Waypoint(0,0));
		
		//Sets the starting pose to avoid major issues w/ direction:
		Pose starting = new Pose(0, 0, 0);
		poseProvider.setPose(starting);
		LCD.drawString(poseProvider.getPose().toString(), 0, 0);
		
		/* Using the method "followPath()" from the Navigator class, we can get the robot to -
		- visit the waypoints as created in our custom path above. */
		navigator.followPath(squarePath);
		navigator.waitForStop();
		LCD.drawString(poseProvider.getPose().toString(), 0, 0);
		
		while(Button.getButtons() != Button.ID_ENTER) {
			continue;
		}
		
	}
	
}