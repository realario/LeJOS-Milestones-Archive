package exercise1;

import lejos.hardware.Button;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class EmergencyStop implements Behavior {

	private MovePilot pilot;


	EmergencyStop(MovePilot p) {
		this.pilot = p;
	}
	
	public boolean takeControl() {
		return(Button.getButtons() == Button.ID_ENTER);
	}
	
	public void suppress() {}
	
	public void action() {
		pilot.stop();
		System.exit(0);
	}

}