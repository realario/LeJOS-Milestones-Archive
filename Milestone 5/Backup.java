package exercise1;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;
import java.util.Random;

public class Backup implements Behavior {
	
	private MovePilot pilot;
	private EV3UltrasonicSensor sensor = new EV3UltrasonicSensor(SensorPort.S1);
	private SampleProvider distance = sensor.getDistanceMode();
	private float[] sample = new float[1];
	private Random rand = new Random();

	public Backup(MovePilot p) {
		this.pilot = p;
	}
	
	public boolean takeControl() {
		distance.fetchSample(sample, 0);
		return(sample[0] < 0.1f);
	}

	public void action() { //Reverses, then randomly turns left or right
		pilot.travel(-50);
		pilot.rotate((2 * rand.nextInt(2) - 1) * 30);
	}

	public void suppress() {}

}