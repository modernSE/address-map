package de.cas.mse.address.map.gui;

import java.io.IOException;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.cas.mse.address.map.ParallelModule;
import de.cas.mse.address.map.SequentialModule;
import de.cas.mse.address.map.data.DataLoader;
import de.cas.mse.address.map.data.DataLoader.DataSize;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AddressMapApplication extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private Injector injector;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Address Map");

		if (!getParameters().getUnnamed().isEmpty() && getParameters().getUnnamed().get(0).equals("par")) {
			injector = Guice.createInjector(new ParallelModule());
		} else {
			injector = Guice.createInjector(new SequentialModule());
		}

		loadData(DataSize.SMALL);
		initRootLayout();
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		System.exit(0);
	}

	private void loadData(DataSize size) {
		injector.getInstance(DataLoader.class).loadAddessesFromCsv(size);
	}

	private void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(AddressMapApplication.class.getResource("root.fxml"));
			rootLayout = (BorderPane) loader.load();

			Controller controller = loader.getController();
			controller.setInjector(injector);

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
