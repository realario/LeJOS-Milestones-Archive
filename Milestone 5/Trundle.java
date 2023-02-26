package exercise1;

import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class Trundle implements Behavior {
		
	private MovePilot pilot;
		
	Trundle(MovePilot p) {
		this.pilot = p;
	}
	
	public void action() {
		if(!pilot.isMoving()) {
			pilot.forward();
		}
	}

	public void suppress() {}
			
	public boolean takeControl() {
		return true;
	}
	
}