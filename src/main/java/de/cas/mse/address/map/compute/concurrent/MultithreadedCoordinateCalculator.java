package de.cas.mse.address.map.compute.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.cas.mse.address.map.compute.CoordinateCalculator;
import de.cas.mse.address.map.compute.Service;
import de.cas.mse.address.map.data.Address;
import de.cas.mse.address.map.data.AddressCatalogue;
import de.cas.mse.address.map.gui.tasks.ProgressIndicator;
import javafx.geometry.Point2D;

@Singleton
public class MultithreadedCoordinateCalculator extends CoordinateCalculator {

	private Service service;

	@Inject
	public MultithreadedCoordinateCalculator(Service service, AddressCatalogue addressCatalogue) {
		super(addressCatalogue);
		this.service = service;
	}

	@Override
	public void calculateCatalogueCoordinates(ProgressIndicator progressIndicator) {
		int numberAddresses = getAddressCatalogue().getAddresses().size();
		AtomicInteger counter = new AtomicInteger(0);
		progressIndicator.updateProgress(0, numberAddresses);
		getAddressCatalogue().getAddresses().parallelStream()
				.forEach(address -> setCoordinate(address, counter, progressIndicator, numberAddresses));

	}

	private void setCoordinate(Address address, AtomicInteger count, ProgressIndicator progressIndicator,
			int numberAddresses) {
		Point2D point = service.computeCoordinate(address);
		address.setCoordinate(point);
		progressIndicator.updateProgress(count.incrementAndGet(), numberAddresses);
	}

}
