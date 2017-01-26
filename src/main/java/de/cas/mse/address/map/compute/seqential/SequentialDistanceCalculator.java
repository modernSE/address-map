package de.cas.mse.address.map.compute.seqential;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.cas.mse.address.map.compute.DistanceCalculator;
import de.cas.mse.address.map.compute.Service;
import de.cas.mse.address.map.data.Address;
import de.cas.mse.address.map.data.AddressCatalogue;
import de.cas.mse.address.map.gui.tasks.ProgressIndicator;

@Singleton
public class SequentialDistanceCalculator extends DistanceCalculator {

	private Service service;

	@Inject
	public SequentialDistanceCalculator(AddressCatalogue addressCatalogue, Service service) {
		super(addressCatalogue);
		this.service = service;
	}

	@Override
	protected void calculateInternal(List<Address> part1, List<Address> part2,
			ProgressIndicator progressIndicator) {
		for (int i = 0; i < part1.size(); i++) {
			getAddressCatalogue().getConnections().add(service.computeConnection(part1.get(i), part2.get(i)));
			progressIndicator.updateProgress(i, part1.size());
		}
	}

}
