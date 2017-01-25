package de.cas.mse.address.map.gui;

import java.io.IOException;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.cas.mse.address.map.Module;
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

		injector = Guice.createInjector(new Module());

		loadData();
		initRootLayout();
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		System.exit(0);
	}

	private void loadData() {
		injector.getInstance(DataLoader.class).loadAddessesFromCsv(DataSize.SMALL);
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
