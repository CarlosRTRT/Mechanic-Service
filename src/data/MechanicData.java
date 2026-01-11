package data;

import java.io.IOException;
import java.util.ArrayList;

import domain.Mechanic;

public class MechanicData {
	private static String filePath = "mechanics.json";
	
	private static JsonUtils<Mechanic> jsonUtils = new JsonUtils<Mechanic>(filePath);
	
	public static void saveMechanic(Mechanic mechanic) {
		try {
			jsonUtils.saveElement(mechanic);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("MechanicData.saveMechanic");
		}
	}
	
	public static void editMechanic(Mechanic mechanic) {
		try {
			ArrayList<Mechanic> mechanics = getList();
			
			for(Mechanic mechanicTemp : mechanics) {
				
				if(mechanicTemp.getId().equals(mechanic.getId()))
					
					mechanicTemp.setEmail(mechanic.getEmail());
					mechanicTemp.setFullName(mechanic.getFullName());
					mechanicTemp.setPhoneNumber(mechanic.getPhoneNumber());
					mechanicTemp.setSpecialty(mechanic.getSpecialty());
					
					jsonUtils.saveAll(mechanics);
					return;
			}
		} catch (Exception e) {
			
			System.out.println("ServicesData.editService");
			e.printStackTrace();
		}
	}
	
	public static void deleteService(Mechanic mechanic) {
		ArrayList<Mechanic> mechanics = getList();
		
		for(int i = 0; i < mechanics.size(); i++) {
			if(mechanics.get(i).getId().equals(mechanic.getId())) {
				mechanics.remove(i);
				jsonUtils.saveAll(mechanics);
				return;
			}
		}
	}
	
	public static ArrayList<Mechanic> getList(){
		try {
			return (ArrayList<Mechanic>) jsonUtils.getAll(Mechanic.class);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error al recuperar la lista de mecanicos");
			return new ArrayList<Mechanic>();
		}
	}
}
