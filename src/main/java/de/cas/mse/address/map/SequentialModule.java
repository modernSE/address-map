package de.cas.mse.address.map;

import com.google.inject.AbstractModule;

import de.cas.mse.address.map.compute.CoordinateCalculator;
import de.cas.mse.address.map.compute.DistanceCalculator;
import de.cas.mse.address.map.compute.seqential.SequentialCoordinateCalculator;
import de.cas.mse.address.map.compute.seqential.SequentialDistanceCalculator;

public class SequentialModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(CoordinateCalculator.class).to(SequentialCoordinateCalculator.class);
		bind(DistanceCalculator.class).to(SequentialDistanceCalculator.class);
	}

}
