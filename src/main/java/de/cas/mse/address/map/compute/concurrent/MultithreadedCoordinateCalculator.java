package de.cas.mse.address.map.compute.concurrent;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.cas.mse.address.map.compute.CoordinateCalculator;
import de.cas.mse.address.map.compute.Service;
import de.cas.mse.address.map.data.AddressCatalogue;
import de.cas.mse.address.map.gui.tasks.ProgressIndicator;

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
		// TODO start coding here

	}

}
