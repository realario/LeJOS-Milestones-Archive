package exercise1;

import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class StopCar {

	public static void main(String[] args) {
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.B);
		
		mLeft.setSpeed(720);
		mRight.setSpeed(720);
		
		mLeft.forward();
		mRight.forward();
		
		Button.ENTER.waitForPressAndRelease();
		
		mLeft.stop();
		mRight.stop();
		
		LCD.drawInt(mLeft.getTachoCount(), 0, 0);
		Button.ENTER.waitForPressAndRelease();
		
		mLeft.close();
		mRight.close();
	}
	
}