package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Orders {
	private int id;
	private int orderNumber;// UNICO Y AUTOMATICO
	private LocalDate creationDate;
	private String orderState; // (Registrada, En proceso, Completada, Entregada, Cancelada).
	private int idMechanic;
	private int idVehicle;
	private List<Services> services;
	private String observations;
	private int totalPrice;
	private Mechanic mechanic;
	
	public Orders() {
		this.services = new ArrayList<Services>();
	}
	
	public Orders(int id, int orderNumber,LocalDate creationDate,String orderState,int idMechanic
			,String observations,int totalPrice, int idVehicle) {
		this.id = id;
		this.orderNumber = orderNumber;
		this.creationDate = creationDate;
		this.orderState = orderState;
		this.idMechanic = idMechanic;
		this.services = (services != null) ? new ArrayList<>(services): new ArrayList<>();
		this.observations = observations;
		this.totalPrice = totalPrice;
		this.idVehicle = idVehicle;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate localDate) {
		this.creationDate = localDate;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public int getIdMechanic() {
		return idMechanic;
	}

	public void setIdMechanic(int idMechanic) {
		this.idMechanic = idMechanic;
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
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdVehicle() {
		return idVehicle;
	}

	public void setIdVehicle(int idVehicle) {
		this.idVehicle = idVehicle;
	}

	@Override
	public String toString() {
		return "Orders [orderNumber=" + orderNumber + ", creationDate=" + creationDate + ", orderState=" + orderState
				+ ", mechanic=" + idMechanic + ", services=" + services
				+ ", observations=" + observations + ", totalPrice=" + totalPrice + "]";
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	public void setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}
	
}
