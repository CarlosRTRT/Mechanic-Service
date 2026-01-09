package data;

import java.io.IOException;
import java.util.ArrayList;

import domain.Services;

public class ServicesData {
	
	private static String filePath = "services.json";
	
	private static JsonUtils<Services> jsonUtils = new JsonUtils<Services>(filePath);
	
	public static void saveService(Services service) {
		try {
			jsonUtils.saveElement(service);
		} catch (Exception e) {
			e.getStackTrace();
			System.out.println("ServicesData.saveService");
		}
	}
	
	public static void editService(Services service) {
		try {
			ArrayList<Services> services = getList();
			
			for(Services serviceTemp : services) {
				if(serviceTemp.getServiceCode() == service.getServiceCode()) {
					
					serviceTemp.setBaseCost(service.getBaseCost());
					serviceTemp.setDescription(service.getDescription());
					serviceTemp.setEstimatedTime(service.getEstimatedTime());
					serviceTemp.setServiceName(service.getServiceName());
					
					jsonUtils.saveAll(services);
					return;
					
				}
			}
		} catch (Exception e) {
			
			System.out.println("ServicesData.editService");
			e.getStackTrace();
		}
	}
	
	public static void deleteService(Services service) {
		ArrayList<Services> services = getList();
		
		for(int i = 0; i < services.size(); i++) {
			if(services.get(i).getServiceCode() == service.getServiceCode()) {
				services.remove(i);
				jsonUtils.saveAll(services);
				return;
			}
		}
	}
	
	public static ArrayList<Services> getList(){
		try {
			return (ArrayList<Services>) jsonUtils.getAll(Services.class);
		} catch (IOException e) {
			 System.out.println("Error al recuperar la lista de servicios");
			 return new ArrayList<Services>();
		}
	}
}
