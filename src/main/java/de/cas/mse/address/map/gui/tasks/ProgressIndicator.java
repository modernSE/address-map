package de.cas.mse.address.map.gui.tasks;

@FunctionalInterface
public interface ProgressIndicator {

	/**
	 * updates the progress on the ui
	 * 
	 * @param newProgress
	 *            new part
	 * @param overallAmount
	 *            overall part
	 */
	public void updateProgress(long newProgress, long overallAmount);
}
