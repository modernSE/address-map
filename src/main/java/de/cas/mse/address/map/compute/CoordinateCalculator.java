package de.cas.mse.address.map.compute;

import javax.inject.Inject;

import de.cas.mse.address.map.data.AddressCatalogue;
import de.cas.mse.address.map.gui.tasks.ProgressIndicator;

public abstract class CoordinateCalculator {

	private AddressCatalogue addressCatalogue;

	@Inject
	public CoordinateCalculator(AddressCatalogue addressCatalogue) {
		this.addressCatalogue = addressCatalogue;
	}

	/**
	 * caclulates all coordinates of the {@link AddressCatalogue}.
	 * 
	 * @param progressIndicator
	 */
	public abstract void calculateCatalogueCoordinates(ProgressIndicator progressIndicator);

	protected AddressCatalogue getAddressCatalogue() {
		return addressCatalogue;
	}
}
