package exercise1;

import lejos.hardware.Battery;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class BatteryLevel implements Behavior {
	
	private MovePilot pilot;
	
	BatteryLevel(MovePilot p) {
		this.pilot = p;
	}

    public void suppress() {}

    public boolean takeControl() {
    	LCD.drawString("" + Battery.getVoltage(), 0, 4);
        return(Battery.getVoltage() < 6.2f);
    }

    public void action() {
	while(takeControl()) {
		Sound.beep();
		pilot.stop();
		LCD.drawString("Battery low!", 0, 3);
		Delay.msDelay(500);
		LCD.clear();
		Delay.msDelay(500);
	}
    }

}