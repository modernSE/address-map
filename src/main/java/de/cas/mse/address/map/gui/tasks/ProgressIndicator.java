package de.cas.mse.address.map.gui.tasks;

@FunctionalInterface
public interface ProgressIndicator {

	public void updateProgress(long newProgress, long overallAmount);
}
