package de.cas.mse.address.map.gui;

import com.google.common.base.Stopwatch;
import com.google.inject.Injector;

import de.cas.mse.address.map.data.Address;
import de.cas.mse.address.map.data.AddressCatalogue;
import de.cas.mse.address.map.data.Connection;
import de.cas.mse.address.map.gui.tasks.ComputeTask;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;

public class Controller {

	public static int CANVAS_WIDTH = 600;
	public static int CANVAS_HEIGHT = 600;

	@FXML
	private Canvas drawCanvas;
	@FXML
	private Label progressLabel;
	@FXML
	private ProgressBar progressBar;
	@FXML
	private Label timeLabel;

	private Injector injector;
	private AddressCatalogue catalogue;

	public void setInjector(Injector injector) {
		this.injector = injector;
		catalogue = injector.getInstance(AddressCatalogue.class);
	}

	@FXML
	private void handleCompute() {
		ComputeTask task = injector.getInstance(ComputeTask.class);
		progressLabel.textProperty().bind(task.messageProperty());
		progressBar.progressProperty().bind(task.progressProperty());
		task.setOnComputation(e -> redraw());
		Stopwatch sw = Stopwatch.createStarted();
		task.setOnSucceeded(e -> timeLabel.setText("Time: " + sw.stop().toString()));
		new Thread(task).start();
	}

	private void redraw() {
		GraphicsContext gc = drawCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, drawCanvas.getWidth(), drawCanvas.getHeight());
		gc.setFill(Color.BLACK);
		for (Address address : catalogue.getAddresses()) {
			Point2D currentCoordinate = address.getCoordinate();
			if (currentCoordinate != null) {
				gc.fillRect((int) currentCoordinate.getX(), (int) currentCoordinate.getY(), 3, 3);
			}
		}
		gc.setStroke(Color.GRAY);
		for (Connection c : catalogue.getConnections()) {
			Point2D c1 = c.getPoint1().getCoordinate();
			Point2D c2 = c.getPoint2().getCoordinate();
			gc.strokeLine(c1.getX() + 1, c1.getY() + 1, c2.getX() + 1, c2.getY() + 1);
		}
	}

}
