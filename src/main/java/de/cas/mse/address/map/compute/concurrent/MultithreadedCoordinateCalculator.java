package de.cas.mse.address.map.compute.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.cas.mse.address.map.compute.CoordinateCalculator;
import de.cas.mse.address.map.compute.Service;
import de.cas.mse.address.map.data.Address;
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
		ExecutorService taskExecutor = Executors.newFixedThreadPool(ConcurrencyParameters.THREAD_POOL_SIZE);
		int amountWork = getAddressCatalogue().getAddresses().size();
		progressIndicator.updateProgress(amountWork, 0);
		AtomicInteger counter = new AtomicInteger(0);
		getAddressCatalogue().getAddresses()
				.forEach(a -> taskExecutor.execute(new CoordinateTask(a, amountWork, counter, progressIndicator)));
		taskExecutor.shutdown();
		try {
			taskExecutor.awaitTermination(1, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			// ignore and stop calculation;
		}
	}

	private final class CoordinateTask implements Runnable {

		private Address address;
		private int amountWork;
		private AtomicInteger counter;
		private ProgressIndicator progressIndicator;

		public CoordinateTask(Address address, int amountWork, AtomicInteger counter,
				ProgressIndicator progressIndicator) {
			this.address = address;
			this.amountWork = amountWork;
			this.counter = counter;
			this.progressIndicator = progressIndicator;
		}

		@Override
		public void run() {
			address.setCoordinate(service.computeCoordinate(address));
			progressIndicator.updateProgress(counter.incrementAndGet(), amountWork);
		}
	}
}
