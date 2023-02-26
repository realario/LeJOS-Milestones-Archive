package exercise1;

import lejos.hardware.Button;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Driver {
	
	final static int WHEEL_DIAMETER = 56, AXLE_LENGTH = 122,  ANGULAR_SPEED = 100, LINEAR_SPEED = 200;

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
		
		pilot.setLinearSpeed(LINEAR_SPEED);
		pilot.setAngularSpeed(ANGULAR_SPEED);
		
		Behavior trundle = new Trundle(pilot);
		Behavior stop = new EmergencyStop(pilot);
		Behavior backup = new Backup(pilot);
		Behavior battery = new BatteryLevel(pilot);
		
		Arbitrator ab = new Arbitrator(new Behavior[] {trundle, backup, stop, battery});
		
		ab.go();
		
		while(true) {
			if(Button.getButtons() == Button.ID_ESCAPE) {
				System.exit(0);
			}
		}
		
	}

}