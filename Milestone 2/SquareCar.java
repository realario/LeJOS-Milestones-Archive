package exercise1;

import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class SquareCar {
	
	public static void main(String[] args) {
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.B);
		
		int SPEED = 500, DRIVE_TIME = 1000, TURN_TIME = 360, ITERATIONS = 4;
		
		mLeft.setSpeed(SPEED);
		mRight.setSpeed(SPEED);
		
		for(int i = 0; i < ITERATIONS; i++) {
			mLeft.forward();
			mRight.forward();
			
			Delay.msDelay(DRIVE_TIME);
			
			mLeft.forward();
			mRight.backward();
			Delay.msDelay(TURN_TIME);
		}
		
		mLeft.stop();
		mRight.stop();
		
		mLeft.close();
		mRight.close();
	}
	
}