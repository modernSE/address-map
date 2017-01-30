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
import de.cas.mse.address.map.data.Connection;
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
		ExecutorService threadPool = Executors.newFixedThreadPool(ConcurrencyParameters.THREAD_POOL_SIZE);
		AtomicInteger counter = new AtomicInteger(0);
		IntStream.range(0, part1.size()).forEach(i -> threadPool
				.execute(new CalculateTask(counter, part1.get(i), part2.get(i), progressIndicator, part1.size())));
		threadPool.shutdown();
		try {
			threadPool.awaitTermination(1, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			// i dont care
		}
	}

	private class CalculateTask implements Runnable {

		AtomicInteger counter;
		Address a1;
		Address a2;
		ProgressIndicator progress;
		int overallAmount;

		public CalculateTask(AtomicInteger counter, Address a1, Address a2, ProgressIndicator progress,
				int overallAmount) {
			this.counter = counter;
			this.a1 = a1;
			this.a2 = a2;
			this.progress = progress;
			this.overallAmount = overallAmount;
		}

		@Override
		public void run() {
			Connection c = service.computeConnection(a1, a2);
			getAddressCatalogue().getConnections().add(c);
			progress.updateProgress(counter.incrementAndGet(), overallAmount);
		}

	}

}
