package de.cas.mse.address.map.compute.seqential;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.cas.mse.address.map.compute.CoordinateCalculator;
import de.cas.mse.address.map.compute.Service;
import de.cas.mse.address.map.data.Address;
import de.cas.mse.address.map.data.AddressCatalogue;
import de.cas.mse.address.map.gui.tasks.ProgressIndicator;

@Singleton
public class SequentialCoordinateCalculator extends CoordinateCalculator {

	private Service service;

	@Inject
	public SequentialCoordinateCalculator(AddressCatalogue addressCatalogue, Service service) {
		super(addressCatalogue);
		this.service = service;
	}

	@Override
	public void calculateCatalogueCoordinates(ProgressIndicator progressIndicator) {
		int size = getAddressCatalogue().getAddresses().size();
		progressIndicator.updateProgress(0, size);
		long count = 0;
		for (Address address : getAddressCatalogue().getAddresses()) {
			address.setCoordinate(service.computeCoordinate(address));
			count++;
			progressIndicator.updateProgress(count, size);
		}
	}

}
