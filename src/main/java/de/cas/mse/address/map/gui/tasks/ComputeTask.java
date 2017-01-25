package de.cas.mse.address.map.gui.tasks;

import javax.inject.Inject;

import de.cas.mse.address.map.compute.CoordinateCalculator;
import de.cas.mse.address.map.compute.DistanceCalculator;
import de.cas.mse.address.map.data.AddressCatalogue;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;

public class ComputeTask extends Task<Void> {

	private CoordinateCalculator coordinateCalculator;
	private DistanceCalculator distanceCalculator;

	private EventHandler<ComputeEvent> onComputationEvent;
	private AddressCatalogue addressCatalogue;

	@Inject
	public ComputeTask(CoordinateCalculator coordinateCalculator, DistanceCalculator distanceCalculator,
			AddressCatalogue addressCatalogue) {
		this.coordinateCalculator = coordinateCalculator;
		this.distanceCalculator = distanceCalculator;
		this.addressCatalogue = addressCatalogue;
	}

	@Override
	protected Void call() throws Exception {
		addressCatalogue.getConnections().clear();
		updateMessage("Calculating coordinates...");
		coordinateCalculator.calculateCatalogueCoordinates(this::progressDone);
		updateMessage("Calculating distances...");
		distanceCalculator.calculatePairDistances(this::progressDone);
		updateMessage("Calculation done.");
		updateProgress(1, 1);
		return null;
	}

	private void progressDone(long p, long overall) {
		updateProgress(p, overall);
		if (onComputationEvent != null) {
			onComputationEvent.handle(new ComputeEvent());
		}
	}

	public void setOnComputation(EventHandler<ComputeEvent> onComputationEvent) {
		this.onComputationEvent = onComputationEvent;
	}

	public static final class ComputeEvent extends Event {
		private static final long serialVersionUID = 3255195138271093839L;

		public ComputeEvent() {
			super(ANY);
		}

	}

}
