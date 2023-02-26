package exercise1;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;

import lejos.hardware.sensor.NXTSoundSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class SimpleChap {

	public static void main(String[] args) {
		
		//Sound Filter:
		float[] level = new float[1];
		NXTSoundSensor ss = new NXTSoundSensor(SensorPort.S4);
		SensorMode sound = (SensorMode) ss.getDBAMode(); //Type is manually casted to "SensorMode", an interface which extends SampleProvider.
		SampleProvider sp = new ClapFilter(sound, 0.80f, 1000);
		
		//Distance Filter:
		float[] distance = new float[1];
		EV3UltrasonicSensor ds = new EV3UltrasonicSensor(SensorPort.S1);
		SampleProvider dp = ds.getDistanceMode();
		
		//Motors:
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.B);

		mLeft.setSpeed(500);
		mRight.setSpeed(500);

		while(level[0] != 1.0f) {
			sp.fetchSample(level, 0);
			if(level[0] == 1.0f) {
				break;
			}
		}

		while(Button.getButtons() != Button.ID_ENTER) {
			mLeft.forward();
			mRight.forward();
			sp.fetchSample(level, 0);
			dp.fetchSample(distance, 0);
			LCD.drawString("Sound: " + Float.toString(level[0]), 0, 0);
			LCD.drawString("Distance: " + Float.toString(distance[0]), 0, 1);
			
			if(level[0] == 1.0f) {
				mRight.stop();
				mLeft.rotate(400);
			}
			
			if(distance[0] < 0.50f) {
				mLeft.stop();
				mRight.stop();
				
				mLeft.close();
				mRight.close();
			}
		}
		
		ss.close();
		ds.close();
		
	}
	
}

/* Ensures that we don't hear two loud sounds within a certain amount of time. */

class ClapFilter implements SampleProvider {

	private final float threshold; //Loudness value considered to be a clap.
	private final int timeGap; //Time to wait after hearing a clap before reporting another.
	private final SampleProvider clap_ss; //SampleProvider required to create the ClapFilter object.
	private long lastHeard; //Time since last heard clap. N.B. Not used in constructor below.

	/* e.g. ClapFilter clap = new ClapFilter(ss, 0.6dB, 2000ms); */

	/* Constructor below. Contains exception check to make sure that we are indeed dealing with a sound sensor. */
	
	ClapFilter(SensorMode soundMode, float level, int gap) {
		timeGap = gap; clap_ss = soundMode; threshold = level;
		if (!soundMode.getName().startsWith("Sound")) {
			throw new IllegalArgumentException ("A clap filter can only filter sound sensors...");
		}

		lastHeard = -2 * timeGap ; //Now we define "lastHeard". Initially, we say that it's been a long time since we've heard a clap.
	}

	public void fetchSample(float level [], int index) {
		level [index] = 0.0f; //Default - no clap, i.e. 0.
		long now = System.currentTimeMillis();
		if ( now - lastHeard > timeGap) { //Checks whether we have waited long enough.
			clap_ss.fetchSample (level , index);
			if (level[index] >= threshold) { //Checks against the threshold, i.e. if the sound heard is loud enough.
				level[index] = 1.0f; //Set to 1 - clap heard!
				lastHeard = now;
			} else {
				level[index] = 0.0f; //Set to 0 - no clap detected...
			}
		}
	}

	public int sampleSize() { //Modified method of SampleProvider. Returns the number of elements in a sample.
		return 1; //In this case we always return the bool 1.
	}
	
}