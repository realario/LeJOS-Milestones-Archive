package exercise1;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.NXTSoundSensor;
import lejos.robotics.SampleProvider;

public class ClapClapCar {

	public static void main(String[] args) {

		//Sound Sensor:

		float[] level = new float[1];
		NXTSoundSensor ss = new NXTSoundSensor(SensorPort.S1);
		SampleProvider sp = ss.getDBAMode();

		//Variables:

		float maxSoundLevel = 0, minSoundLevel;
		sp.fetchSample(level, 0);
		minSoundLevel = level[0];

		while(Button.getButtons() != Button.ID_ENTER) {

			sp.fetchSample(level, 0);

			if(level[0] > maxSoundLevel) {
				maxSoundLevel = level[0];
			}

			if(level[0] < minSoundLevel) {
				minSoundLevel = level[0];
			}
			
			LCD.drawString("Cur: " + Float.toString(level[0]), 0, 0);
			LCD.drawString("Max: " + Float.toString(maxSoundLevel), 0, 1);
			LCD.drawString("Min: " + Float.toString(minSoundLevel), 0, 2);

		}

		ss.close();

	}

}