package controllers;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
/*
 * The TimeController class manages the timer functionality in the GUI.
 * It allows starting and stopping the timer, updating the clock, and getting the remaining time.
*/
public class TimeController {

	@SuppressWarnings("unused")
	private int totalMinutes; // Total minutes for the timer
	private CountDown countDown; // Reference to the CountDown object
	private Timer timer; // Timer object for scheduling timer tasks
	private int totalSeconds; // Total seconds for the timer

	/**
	 * Constructs a TimeController object with the specified minutes and CountDown object.
	 * The totalSeconds is calculated based on the provided minutes.
	 *
	 * @param minutes   the total minutes for the timer
	 * @param countDown the CountDown object to update the countdown text
	 */
	public TimeController(int minutes, CountDown countDown) {
	    this.totalMinutes = minutes;
	    this.countDown = countDown;
	    totalSeconds = minutes * 60; // Convert minutes to seconds
	}

	/**
	 * Starts the timer by creating a new Timer object and scheduling a TimerTask to run every second.
	 * The remaining seconds are decremented, and if the timer reaches zero, it is canceled.
	 * The countdown text is updated on the JavaFX application thread using Platform.runLater().
	 */
	public void startTimer() {
	    timer = new Timer();
	    timer.scheduleAtFixedRate(new TimerTask() {
	        @Override
	        public void run() {
	            totalSeconds--; // Decrement the remaining seconds
	            if (totalSeconds < 0) {
	                timer.cancel();
	                Platform.runLater(() -> countDown.endOfTime());
	                return;
	            }
	            String time = String.format("%02d:%02d:%02d", (totalSeconds / 3600), ((totalSeconds % 3600) / 60), (totalSeconds % 60));
	            Platform.runLater(() -> countDown.setTextCountdown(time));
	        }
	    }, 0, 1000);
	}

	/**
	 * Stops the timer by canceling the Timer object.
	 */
	public void stopTimer() {
	    if (timer != null) {
	        timer.cancel();
	    }
	}

	/**
	 * Returns the remaining time in the format "HH:MM:SS".
	 *
	 * @return the remaining time as a formatted string
	 */
	public int timeLeft() {
	    return  (totalSeconds / 60);
	}

	/**
	 * Updates the clock by adding additional minutes to the totalSeconds.
	 *
	 * @param minutes the number of additional minutes to add to the timer
	 */
	public void updateClock(double minutes) {
	    int additionalSeconds = (int) (minutes * 60);
	    totalSeconds += additionalSeconds;
	}
}