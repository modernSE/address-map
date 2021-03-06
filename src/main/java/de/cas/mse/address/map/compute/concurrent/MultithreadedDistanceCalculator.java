package de.cas.mse.address.map.compute.concurrent;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.cas.mse.address.map.compute.DistanceCalculator;
import de.cas.mse.address.map.compute.Service;
import de.cas.mse.address.map.data.Address;
import de.cas.mse.address.map.data.AddressCatalogue;
import de.cas.mse.address.map.gui.tasks.ProgressIndicator;

@Singleton
public class MultithreadedDistanceCalculator extends DistanceCalculator {

	private Service service;

	@Inject
	public MultithreadedDistanceCalculator(AddressCatalogue addressCatalogue, Service service) {
		super(addressCatalogue);
		this.service = service;
	}

	@Override
	protected void calculateInternal(List<Address> part1, List<Address> part2,
			ProgressIndicator progressIndicator) {
		// TODO Start coding here
	}

}
