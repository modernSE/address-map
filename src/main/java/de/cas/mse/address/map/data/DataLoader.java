package de.cas.mse.address.map.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataLoader {

	public enum DataSize {
		SMALL, LARGE;
	}

	private AddressCatalogue addressCatalogue;

	@Inject
	public DataLoader(AddressCatalogue addressCatalogue) {
		this.addressCatalogue = addressCatalogue;
	}

	public void loadAddessesFromCsv(DataSize size) {
		try {
			List<String> lines = Files.readAllLines(Paths.get("data/addresses-small.data"));
			for (String line : lines) {
				String[] parts = line.split(";");
				Address a = new Address();
				a.setName(parts[0]);
				a.setStreet(parts[1]);
				a.setZip(parts[2]);
				a.setTown(parts[3]);
				addressCatalogue.getAddresses().add(a);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
