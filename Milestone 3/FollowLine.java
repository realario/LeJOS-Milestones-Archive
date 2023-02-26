package exercise1;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class FollowLine {

	public static void main(String[] args) {

		//Colour Sensor:
	
		float[] samples = new float[1];
		EV3ColorSensor cs = new EV3ColorSensor(SensorPort.S1);
		SampleProvider colour = cs.getRedMode();
 
		//Calibration:

		float darkest, lightest;
		
		LCD.drawString("Place on darkest!", 0, 0);
		Button.ENTER.waitForPressAndRelease();
		colour.fetchSample(samples, 0);
		darkest = samples[0];
		LCD.drawString("-->" + Float.toString(darkest), 0, 1);
		
		Delay.msDelay(2000);
		LCD.clear();
		
		LCD.drawString("Place on lightest!", 0, 0);
		Button.ENTER.waitForPressAndRelease();
		colour.fetchSample(samples, 0);
		lightest = samples[0];
		LCD.drawString("-->" + Float.toString(lightest), 0, 1);
		
		Delay.msDelay(2000);
		LCD.clear();
		
		//Variables / Constants:
		
		float AVERAGE = calcAverage(lightest, darkest); //Create Average w/ Min & Max Values
		int SPEED = 100;

		//Motors:

		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.B);
		
		mLeft.setSpeed(SPEED);
		mRight.setSpeed(SPEED);
		
		while(Button.getButtons() != Button.ID_ENTER) { //Runs until "ENTER" button is pressed...
			colour.fetchSample(samples, 0); //Updates current sample.

			LCD.drawString("Cur: " + Float.toString(samples[0]), 0, 0);
			LCD.drawString("Average: " + Float.toString(AVERAGE), 0, 1);
			//LCD.drawString("Dark: " + Float.toString(darkest), 0, 1);
			//LCD.drawString("Light: " + Float.toString(lightest), 0, 2);

			if (samples[0] > AVERAGE) { //L or R moves according to average light level...
				mLeft.forward();
				mRight.stop();
			} else {
				mRight.forward();
				mLeft.stop();
			}
		}
		
		cs.close();
		mLeft.close();
		mRight.close();

	}
	
	public static float calcAverage(float min, float max) {
		return (min + max) / 2;
	}

}