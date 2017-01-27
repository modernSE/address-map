package de.cas.mse.address.map.data;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.inject.Singleton;

@Singleton
public class AddressCatalogue {

	private Set<Address> addresses = new HashSet<>();
	private Set<Connection> connections = new CopyOnWriteArraySet<>();

	public Set<Address> getAddresses() {
		return addresses;
	}

	public Set<Connection> getConnections() {
		return connections;
	}

}
