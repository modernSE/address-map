package de.cas.mse.address.map.compute.seqential;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.cas.mse.address.map.compute.CoordinateCalculator;
import de.cas.mse.address.map.compute.Service;
import de.cas.mse.address.map.data.Address;
import de.cas.mse.address.map.data.AddressCatalogue;
import de.cas.mse.address.map.gui.tasks.ProgressIndicator;

@Singleton
public class SequentialCoordinateCalculator implements CoordinateCalculator {

	private AddressCatalogue addressCatalogue;
	private Service service;

	@Inject
	public SequentialCoordinateCalculator(AddressCatalogue addressCatalogue, Service service) {
		this.addressCatalogue = addressCatalogue;
		this.service = service;
	}

	@Override
	public void calculateCatalogueCoordinates(ProgressIndicator progressIndicator) {
		int size = addressCatalogue.getAddresses().size();
		progressIndicator.updateProgress(0, size);
		long count = 0;
		for (Address address : addressCatalogue.getAddresses()) {
			address.setCoordinate(service.computeCoordinate(address));
			count++;
			progressIndicator.updateProgress(count, size);
		}
	}

}
