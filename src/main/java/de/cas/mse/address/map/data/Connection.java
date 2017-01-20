package de.cas.mse.address.map.data;

import java.math.BigDecimal;

public class Connection {

	private Address point1;
	private Address point2;
	private BigDecimal distance;

	public Address getPoint1() {
		return point1;
	}

	public void setPoint1(Address point1) {
		this.point1 = point1;
	}

	public Address getPoint2() {
		return point2;
	}

	public void setPoint2(Address point2) {
		this.point2 = point2;
	}

	public BigDecimal getDistance() {
		return distance;
	}

	public void setDistance(BigDecimal distance) {
		this.distance = distance;
	}

}
