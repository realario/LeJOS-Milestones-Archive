package exercise1;

import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class CarRotate {
	
	public static void main(String[] args) {
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.B);
		
		mLeft.synchronizeWith(new BaseRegulatedMotor[] {mRight});
		
		int SPEED = 720, ITERATIONS = 4, DEGREES = 400;
		
		mLeft.setSpeed(SPEED);
		mRight.setSpeed(SPEED);
		
		for(int i = 0; i < ITERATIONS; i++) {
			mLeft.rotate(DEGREES);
			
			mLeft.startSynchronization();
			
			mLeft.rotate(SPEED, true);
			mRight.rotate(SPEED, true);
			
			mLeft.endSynchronization();
			
			mLeft.waitComplete();
			mRight.waitComplete();
		}
		
		mLeft.close();
		mRight.close();
	}
	
}