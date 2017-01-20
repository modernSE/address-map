package de.cas.mse.address.map.data;

import javafx.geometry.Point2D;

public class Address {

	private Point2D coordinate;
	private String name;
	private String street;
	private String zip;
	private String town;

	public Point2D getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Point2D coordinate) {
		this.coordinate = coordinate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

}
