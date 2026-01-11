package domain;

import java.util.ArrayList;
import java.util.List;

public class Orders {
	private int orderNumber;// UNICO Y AUTOMATICO
	private String creationDate;
	private String orderState; // (Registrada, En proceso, Completada, Entregada, Cancelada).
	private Mechanic mechanic;
	private List<Services> services;
	private String observations;
	private int totalPrice;
	
	public Orders(int orderNumber, String creationDate, String orderState,
			Mechanic mechanic, Services services, String observations, int totalPrice) {
		super();
		this.orderNumber = orderNumber;
		this.creationDate = creationDate;
		this.orderState = orderState;
		this.mechanic = mechanic;
		this.services = new ArrayList<>();
		this.observations = observations;
		this.totalPrice = totalPrice;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	public void setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	public List<Services> getServices() {
		return services;
	}

	public void setServices(List<Services> services) {
		this.services = services;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public void addService(Services service) {
		this.services.add(service);
	}

	@Override
	public String toString() {
		return "Orders [orderNumber=" + orderNumber + ", creationDate=" + creationDate + ", orderState=" + orderState
				+ ", mechanic=" + mechanic + ", services=" + services
				+ ", observations=" + observations + ", totalPrice=" + totalPrice + "]";
	}
	
}
