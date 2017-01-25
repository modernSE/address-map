package de.cas.mse.address.map.compute;

import java.math.BigDecimal;
import java.util.Random;

import javax.inject.Singleton;

import de.cas.mse.address.map.data.Address;
import de.cas.mse.address.map.data.Connection;
import de.cas.mse.address.map.gui.Controller;
import javafx.geometry.Point2D;

@Singleton
public class Service {

	private Random random = new Random();

	public Point2D computeCoordinate(Address address) {
		try {
			Thread.sleep(random.nextInt(800));
		} catch (InterruptedException e) {
			throw new RuntimeException("interrupted!");
		}
		return new Point2D(random.nextDouble() * Controller.CANVAS_WIDTH,
				random.nextDouble() * Controller.CANVAS_HEIGHT);
	}

	public Connection computeConnection(Address a1, Address a2) {
		Connection c = new Connection(a1, a2);
		try {
			Thread.sleep(random.nextInt(1000));
		} catch (InterruptedException e) {
			throw new RuntimeException("interrupted!");
		}
		c.setDistance(BigDecimal.valueOf(random.nextDouble() * 100));
		return c;
	}
}
