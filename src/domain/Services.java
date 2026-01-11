package domain;

public class Services {
	private int serviceCode; // ES UNICO 
	private String serviceName;
	private String description; // (ej: “Cambio de aceite”, “Alineamiento y balanceo”, “Diagnóstico electrónico”).
	private int baseCost;
	private int estimatedTime; // EN HORAS
	
	public Services() {
		
	}
	
	public Services(int serviceCode, String serviceName, String description, int baseCost, int estimatedTime) {
		this.serviceCode = serviceCode;
		this.serviceName = serviceName;
		this.description = description;
		this.baseCost = baseCost;
		this.estimatedTime = estimatedTime;
	}

	public int getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(int serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getBaseCost() {
		return baseCost;
	}

	public void setBaseCost(int baseCost) {
		this.baseCost = baseCost;
	}

	public int getEstimatedTime() {
		return estimatedTime;
	}

	public void setEstimatedTime(int estimatedTime) {
		this.estimatedTime = estimatedTime;
	}

	@Override
	public String toString() {
		return serviceName;
	}
	
}
