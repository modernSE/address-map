package de.cas.mse.address.map.compute;

import java.util.ArrayList;
import java.util.List;

import de.cas.mse.address.map.data.Address;
import de.cas.mse.address.map.data.AddressCatalogue;
import de.cas.mse.address.map.gui.tasks.ProgressIndicator;

public abstract class DistanceCalculator {

	private final AddressCatalogue addressCatalogue;

	public DistanceCalculator(AddressCatalogue addressCatalogue) {
		this.addressCatalogue = addressCatalogue;
	}

	/**
	 * divides the set of addresses into two sets and calculates a distance
	 * between a pair from each set.
	 */
	public void calculatePairDistances(ProgressIndicator progressIndicator) {
		List<Address> part1 = new ArrayList<>();
		List<Address> part2 = new ArrayList<>();
		int i = 0;
		for (Address a : getAddressCatalogue().getAddresses()) {
			if (i % 2 == 0) {
				part1.add(a);
			} else {
				part2.add(a);
			}
			i++;
		}
		if (getAddressCatalogue().getAddresses().size() % 2 != 0) {
			part2.add(part2.get(0));
		}
		progressIndicator.updateProgress(0, part1.size());

		calculateInternal(part1, part2, progressIndicator);
	}

	/**
	 * gets two equal sized lists of addresses. The calculation adds the
	 * connections directly to the {@link AddressCatalogue}.
	 * 
	 * @param part1
	 * @param part2
	 * @param progressIndicator
	 */
	protected abstract void calculateInternal(List<Address> part1, List<Address> part2,
			ProgressIndicator progressIndicator);

	protected AddressCatalogue getAddressCatalogue() {
		return addressCatalogue;
	}
}
