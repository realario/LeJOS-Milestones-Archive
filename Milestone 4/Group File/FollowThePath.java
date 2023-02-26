package exercise1;

import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.Pose;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.pathfinding.Path;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.geometry.Line;
import lejos.robotics.geometry.Rectangle;
import lejos.robotics.mapping.LineMap;
import lejos.robotics.pathfinding.PathFinder;
import lejos.robotics.pathfinding.ShortestPathFinder;

public class FollowThePath {
	
	final static int WHEEL_DIAMETER = 56, AXLE_LENGTH = 122,  ANGULAR_SPEED = 100, LINEAR_SPEED = 200;

	public static void main(String[] args) throws Exception {
		
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
		
		PoseProvider poseProvider = new OdometryPoseProvider(pilot);
		
		//Setting Speeds:
		pilot.setLinearSpeed(LINEAR_SPEED);
		pilot.setAngularSpeed(ANGULAR_SPEED);
		
		Navigator navigator = new Navigator(pilot, poseProvider);

		Line[] lines = new Line[4];
		lines[0] = new Line(0, 375f, 375f, 375f);
		lines[1] = new Line(0, 825f, 825f, 825f);
		lines[2] = new Line(0, 375f, 0, 825f);
		lines[3] = new Line(375f, 375f, 375f, 825f);
		Rectangle bounds = new Rectangle(0, 0, 1000, 1000);
		
		LineMap myMap = new LineMap(lines, bounds);
		
		PathFinder pf = new ShortestPathFinder(myMap);
		Path route = pf.findRoute(new Pose(0,0,90), new Waypoint(0, 850));
		
		navigator.followPath(route);
		navigator.waitForStop();
		
	}

}