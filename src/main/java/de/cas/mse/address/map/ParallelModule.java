package de.cas.mse.address.map;

import com.google.inject.AbstractModule;

import de.cas.mse.address.map.compute.CoordinateCalculator;
import de.cas.mse.address.map.compute.DistanceCalculator;
import de.cas.mse.address.map.compute.concurrent.MultithreadedCoordinateCalculator;
import de.cas.mse.address.map.compute.concurrent.MultithreadedDistanceCalculator;

public class ParallelModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(CoordinateCalculator.class).to(MultithreadedCoordinateCalculator.class);
		bind(DistanceCalculator.class).to(MultithreadedDistanceCalculator.class);
	}

}
