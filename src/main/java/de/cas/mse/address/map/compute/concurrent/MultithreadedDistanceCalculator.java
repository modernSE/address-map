package de.cas.mse.address.map.compute.concurrent;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

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
	protected void calculateInternal(List<Address> part1, List<Address> part2, ProgressIndicator progressIndicator) {
		ExecutorService taskExecutor = Executors.newFixedThreadPool(ConcurrencyParameters.THREAD_POOL_SIZE);
		int amountWork = part1.size();
		progressIndicator.updateProgress(amountWork, 0);
		AtomicInteger counter = new AtomicInteger(0);
		IntStream.range(0, part1.size()).forEach(i -> taskExecutor
				.execute(new DistanceTask(part1.get(i), part2.get(i), amountWork, counter, progressIndicator)));
		taskExecutor.shutdown();
		try {
			taskExecutor.awaitTermination(1, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			// ignore and stop calculation;
		}
	}

	private final class DistanceTask implements Runnable {

		private int amountWork;
		private AtomicInteger counter;
		private ProgressIndicator progressIndicator;
		private Address a1;
		private Address a2;

		public DistanceTask(Address a1, Address a2, int amountWork, AtomicInteger counter,
				ProgressIndicator progressIndicator) {
			this.a1 = a1;
			this.a2 = a2;
			this.amountWork = amountWork;
			this.counter = counter;
			this.progressIndicator = progressIndicator;
		}

		@Override
		public void run() {
			getAddressCatalogue().getConnections().add(service.computeConnection(a1, a2));
			progressIndicator.updateProgress(counter.incrementAndGet(), amountWork);
		}
	}
}
